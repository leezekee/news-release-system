package com.leezekee.service;

import com.leezekee.pojo.Image;

public interface ImageService {
    Image findImageById(Integer imageId);

    Object findAllImage();

    void deleteImage(Integer imageId);

    void updateImage(Image image);

    void uploadImage(Image image);
}
