package com.caring.dao.service;

import com.caring.dao.model.Image;
import com.caring.dao.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author james
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    public Image saveImage(Image image) {
        image = imageRepository.save(image);
        image.setFileUrl(image.getFileUrl() + "/" + image.getId());
        image.setThumbnailsUrl(image.getThumbnailsUrl() + "/" + image.getId());
        return imageRepository.save(image);
    }

    public Image findImageById(Long id) {
        return imageRepository.findOne(id);
    }
}
