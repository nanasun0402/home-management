package com.caring.service.file;

import com.caring.dao.model.BaseFile;
import com.caring.dao.model.Image;
import com.caring.service.CaringServiceException;
import com.caring.service.ServiceUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.caring.dao.service.ImageService;
import java.util.concurrent.TimeUnit;
import org.springframework.core.env.Environment;

/**
 *
 * @author james
 */
@Service
public class FileLocalStorageService {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    private final static long thumbnailsSize = 200 * 1024;

    @Autowired
    private Environment env;
    @Autowired
    private FileLocalStorageConfig config;
    @Autowired
    private ImageService imageService;
//    @Autowired
//    private AudioService audioService;

    public Long getFileMaxSize() {
        return config.getFileMaxSizeMb() * 1024L * 1024L;
    }

    public Image storage(Image image) {
        image.setType(StorageType.Image.name());
        return imageService.saveImage((Image) storageFile(image));
    }

//    public Audio storage(Audio audio) {
//        try {
//            audio.setType(StorageType.Audio.name());
//            audio = (Audio) storageFile(audio);
//            audio.setOriginFilePath(audio.getFilePath());
//            String newFilePath = audio.getFilePath() + ".mp3";
//
//            String ffmpegBin = env.getProperty("ffmpeg.bin");
//            LOG.info("ffmpeg.bin: {}", ffmpegBin);
//            String ffmpeg = StringUtils.isNotEmpty(ffmpegBin) ? ffmpegBin : "ffmpeg";
//            Runtime commandExecutor = Runtime.getRuntime();
//            StringBuilder command = new StringBuilder();
//            command.append(ffmpeg).append(" -y -i ").append(audio.getFilePath()).append(" -ar 32000 ").append(newFilePath);
//            String execCommand = command.toString();
//            LOG.info("amr -> mp3 converting: {}", execCommand);
//            Process result = commandExecutor.exec(execCommand);
//
//            if (result.waitFor(10, TimeUnit.SECONDS)) {
//                LOG.info("amr -> mp3 converting successful: {}", execCommand);
//                audio.setFilePath(newFilePath);
//                return audioService.saveAudio(audio);
//            }
//            throw new CaringServiceException("音频转换错误，原始文件已经保存");
//        } catch (IOException ex) {
//            LOG.error("{}", ex.fillInStackTrace());
//            throw new CaringServiceException("文件损坏，读取错误");
//        } catch (InterruptedException ex) {
//            LOG.error("{}", ex.fillInStackTrace());
//            throw new CaringServiceException("文件处理被终止");
//        }
//    }

    public BaseFile storageFile(BaseFile file) {
        try {
            StorageType type = StorageType.valueOf(file.getType());
            if (StringUtils.isEmpty(file.getName())) {
                file.setName(generateFileName());
            } else {
                file.setName(generateFileName() + "-" + file.getName());
            }
            byte[] data = IOUtils.toByteArray(file.getDataInput());
            String savedFilePath = storageFile(file.getName(), file.getOwnerId(), file.getFilePurpose(), type, data);
            file.setFilePath(savedFilePath);
            file.setOriginFilePath(savedFilePath);

            if (type == StorageType.Image && file instanceof Image) {
                String fileThumbailsFileName = "thumbails-" + file.getName();
                BufferedImage bi = ImageIO.read(new ByteArrayInputStream(data));
                LOG.info("Image height:{}, width: {}", bi.getData().getHeight(), bi.getData().getWidth());
                ((Image) file).setHeight(bi.getData().getHeight());
                ((Image) file).setWidth(bi.getData().getWidth());
                byte[] smallData = generateThumbails(bi, data.length, "png", 50 * 1024);
                String savedThumbailsFilePath = storageFile(fileThumbailsFileName, file.getOwnerId(), file.getFilePurpose(), type, smallData != null ? smallData : data);
                file.setFilePath(savedThumbailsFilePath);
            }
            return file;
        } catch (IOException ex) {
            LOG.error("Error: {}", ex.fillInStackTrace());
            throw new CaringServiceException("保存文件错误");
        }
    }

    public long getAmrDuration(File file) {
        long duration = -1;
        int[] packedSize = {12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0, 0, 0};
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            long length = file.length();//文件的长度
            int pos = 6;//设置初始位置
            int frameCount = 0;//初始帧数
            int packedPos;
            /////////////////////////////////////////////////////
            byte[] datas = new byte[1];//初始数据值
            while (pos <= length) {
                randomAccessFile.seek(pos);
                if (randomAccessFile.read(datas, 0, 1) != 1) {
                    duration = length > 0 ? ((length - 6) / 650) : 0;
                    break;
                }
                packedPos = (datas[0] >> 3) & 0x0F;
                pos += packedSize[packedPos] + 1;
                frameCount++;
            }
            /////////////////////////////////////////////////////
            duration += frameCount * 20;//帧数*20
        } catch (FileNotFoundException ex) {
            LOG.error("Error: {}", ex.fillInStackTrace());
            throw new CaringServiceException("文件未发现");
        } catch (IOException ex) {
            LOG.error("Error: {}", ex.fillInStackTrace());
            throw new CaringServiceException("文件IO错误");
        } finally {
            IOUtils.closeQuietly(randomAccessFile);
        }
        return duration;
    }

    public Image loadImageThumbnails(Long imageId) {
        return loadLocalImage(imageId, true);
    }

    public Image loadImage(Long imageId) {
        return loadLocalImage(imageId, false);
    }

//    public Audio loadAudio(Long audioId) {
//        try {
//            Audio audio = audioService.findAudioById(audioId);
//            if (audio != null && StringUtils.isNotEmpty(audio.getFilePath())) {
//                LOG.debug("Audio path: {}", audio.getFilePath());
//                audio.setDataInput(new FileInputStream(audio.getFilePath()));
//            } else {
//                LOG.debug("No path");
//                audio.setDataInput(new ByteArrayInputStream("".getBytes()));
//            }
//            return audio;
//        } catch (FileNotFoundException ex) {
//            LOG.error("Error: {}", ex.fillInStackTrace());
//            throw new CaringServiceException(String.format("未发现文件[%d]", audioId));
//        }
//    }

    public byte[] generateThumbails(BufferedImage bi, int imageSize, String imageType, long targetSize) throws IOException {
        targetSize = targetSize <= 0 ? thumbnailsSize : targetSize;
        if (bi != null && imageSize > targetSize) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            double scale = (targetSize / (imageSize * 1.0d));
            Thumbnails.of(bi).scale(scale).outputFormat(imageType).toOutputStream(out);
            return out.toByteArray();
        }
        return null;
    }

    private Image loadLocalImage(Long imageId, boolean isThumbnails) {
        try {
            Image image = imageService.findImageById(imageId);
            if (image == null) {
                throw new CaringServiceException(String.format("未发现文件[%d]", imageId));
            }
            if (isThumbnails && StringUtils.isEmpty(image.getOriginFilePath())) {
                try (InputStream in = new FileInputStream(image.getFilePath())) {
                    byte[] data = IOUtils.toByteArray(in);
                    BufferedImage bi = ImageIO.read(new ByteArrayInputStream(data));
                    byte[] smallData = generateThumbails(bi, data.length, "png", 50 * 1024);
                    image.setDataInput(new ByteArrayInputStream(smallData != null ? smallData : data));
                    return image;
                }
            }
            String filePath = isThumbnails
                              ? image.getFilePath()
                              : (StringUtils.isEmpty(image.getOriginFilePath()) ? image.getFilePath() : image.getOriginFilePath());
            if (StringUtils.isNotEmpty(filePath)) {
                image.setDataInput(new FileInputStream(filePath));
            } else {
                image.setDataInput(new ByteArrayInputStream("".getBytes()));
            }
            return image;
        } catch (Exception ex) {
            LOG.error("Error: {}", ex.fillInStackTrace());
            throw new CaringServiceException(String.format("未发现文件[%d]", imageId));
        }
    }

    private String generateFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()) + ServiceUtils.getRandomNumberByLength(6);
    }

    private String storageFile(String fileName, Long owner, String purpose, StorageType type, byte[] data) {
        try {
            if (StringUtils.isEmpty(purpose)) {
                purpose = "unknown";
            }
            boolean filePathReady = true;
            File file = new File(config.getFileStorageRoot() + "/" + purpose);
            if (!file.exists()) {
                filePathReady &= file.mkdirs();
            }

            String filePath = file.getPath() + File.separatorChar + type.toString();
            if (owner != null) {
                filePath = filePath + File.separatorChar + owner;
            }

            file = new File(filePath);
            if (filePathReady && !file.exists()) {
                filePathReady &= file.mkdirs();
            }
            filePath = filePath + File.separatorChar + fileName;
            file = new File(filePath);
            if (filePathReady && !file.exists()) {
                filePathReady &= file.createNewFile();
            }
            if (!filePathReady) {
                throw new CaringServiceException("文件路径错误");
            }
            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write(data, 0, data.length);
            }
            return file.getPath();
        } catch (IOException ex) {
            LOG.error("Error: {}", ex.fillInStackTrace());
            throw new CaringServiceException("保存文件失败");
        }
    }
}
