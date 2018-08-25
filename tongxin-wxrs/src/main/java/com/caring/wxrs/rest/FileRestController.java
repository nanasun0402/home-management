package com.caring.wxrs.rest;

import static com.caring.dao.config.ModelConst.Image.CUSTOMER_HEAD_IMAGE_PURPOSE;
import static com.caring.dao.config.ModelConst.Image.DOCOTOR_HEAD_IMAGE_PURPOSE;
import static com.caring.dao.config.ModelConst.Image.HISTORY_IMAGE_PURPOSE;
import static com.caring.dao.config.ModelConst.Image.INQUIRY_IMAGE_PURPOSE;
import com.caring.dao.model.Image;
import com.caring.service.file.FileLocalStorageService;
import com.caring.wxrs.rest.request.AudioUploadRequest;
import com.caring.wxrs.rest.response.FileUploadMaxSizeResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import static com.caring.dao.config.ModelConst.Image.INQUIRY_AUDIO_PURPOSE;

/**
 *
 * @author james
 */
@RestController
@RequestMapping("/api/v1/file")
public class FileRestController extends BaseController {

    @Autowired
    private FileLocalStorageService fileLocalStorageService;
//    @Autowired
//    private WxMpService wxService;

//    @RequestMapping(value = "/filemaxsize",
//                    method = RequestMethod.GET,
//                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public FileUploadMaxSizeResponse getFileMaxSize() {
//        return new FileUploadMaxSizeResponse(fileLocalStorageService.getFileMaxSize());
//    }

    @PostMapping(value = "/history/image",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Image handleFileUploadHistoryImage(@RequestParam("file") MultipartFile file) throws IOException {
        return storageFileImage(file, HISTORY_IMAGE_PURPOSE, null);
    }

//    @PostMapping(value = "/inquiry/image",
//                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//                 produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Image handleFileUploadInquiryImage(@RequestParam("file") MultipartFile file) throws IOException {
//        return storageFileImage(file, INQUIRY_IMAGE_PURPOSE, null);
//    }

    @PostMapping(value = "/doctor/head_image",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Image handleFileUploadDoctorHeadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return storageFileImage(file, DOCOTOR_HEAD_IMAGE_PURPOSE, null);
    }

//    @PostMapping(value = "/customer/head_image",
//                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//                 produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Image handleFileUploadCustomerHeadImage(@RequestParam("file") MultipartFile file) throws IOException {
//        return storageFileImage(file, CUSTOMER_HEAD_IMAGE_PURPOSE, null);
//    }

//    @PostMapping(value = "/audio/id",
//                 consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
//                 produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Audio handleUploadFileAudioById(@RequestBody AudioUploadRequest audioRequest) throws IOException, WxErrorException {
//        return storageFileAudioById(audioRequest, INQUIRY_AUDIO_PURPOSE, null);
//    }
//
//    @PostMapping(value = "/audio",
//                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//                 produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public Audio handleUploadFileAudio(@RequestParam("file") MultipartFile file) throws IOException, WxErrorException {
//        return storageFileAudio(file, INQUIRY_AUDIO_PURPOSE, null);
//    }

    @RequestMapping(value = "/imagethumbnails/{fileId}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] readImageThumbnails(@PathVariable Long fileId) throws IOException {
        try (InputStream in = fileLocalStorageService.loadImageThumbnails(fileId).getDataInput()) {
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw e;
        }
    }

    @RequestMapping(value = "/image/{fileId}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] readImage(@PathVariable Long fileId) throws IOException {
        try (InputStream in = fileLocalStorageService.loadImage(fileId).getDataInput()) {
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw e;
        }
    }

//    @RequestMapping(value = "/audio/{fileId}",
//                    method = RequestMethod.GET,
//                    produces = "audio/mp3")
//    public byte[] readAudio(@PathVariable Long fileId) throws IOException {
//        try (InputStream in = fileLocalStorageService.loadAudio(fileId).getDataInput()) {
//            return IOUtils.toByteArray(in);
//        } catch (IOException e) {
//            throw e;
//        }
//    }

    private Image storageFileImage(MultipartFile file, String purpose, Long ownerId) throws IOException {
        try (InputStream in = file.getInputStream()) {
            Image image = new Image();
            image.setOwnerId(ownerId);
            image.setName(FilenameUtils.normalize(file.getOriginalFilename()));
            image.setFilePurpose(purpose);
            image.setDataInput(in);
            image.setFileUrl("/api/file/image");
            image.setThumbnailsUrl("/api/file/imagethumbnails");
            return fileLocalStorageService.storage(image);
        }
    }

//    private Audio storageFileAudio(MultipartFile file, String purpose, Long ownerId) throws IOException, WxErrorException {
//        try (InputStream in = file.getInputStream()) {
//            Audio audio = new Audio();
//            audio.setOwnerId(ownerId);
//            audio.setDataInput(in);
//            audio.setName(FilenameUtils.normalize(file.getOriginalFilename()));
//            audio.setFilePurpose(purpose);
//            audio.setFileUrl("/api/file/audio");
//            return fileLocalStorageService.storage(audio);
//        }
//    }

//    private Audio storageFileAudioById(AudioUploadRequest audioRequest, String purpose, Long ownerId) throws IOException, WxErrorException {
//        Audio audio = new Audio();
//        audio.setOwnerId(ownerId);
//        audio.setName(audioRequest.getMediaId());
//        audio.setFilePurpose(purpose);
//        audio.setFileUrl("/api/file/audio");
//        File mediaFile = wxService.getMaterialService().mediaDownload(audioRequest.getMediaId());
//        try (InputStream in = new FileInputStream(mediaFile)) {
//            audio.setDataInput(in);
//            return fileLocalStorageService.storage(audio);
//        }
//    }
}
