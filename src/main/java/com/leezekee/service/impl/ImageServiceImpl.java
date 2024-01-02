package com.leezekee.service.impl;

import com.leezekee.mapper.ImageMapper;
import com.leezekee.pojo.Image;
import com.leezekee.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageMapper imageMapper;

    @Override
    public Image findImageById(Integer imageId) {
        return null;
    }

    @Override
    public Object findAllImage() {
        return null;
    }

    @Override
    public void deleteImage(Integer imageId) {

    }

    @Override
    public void updateImage(Image image) {

    }

    @Override
    public void uploadImage(Image image) {

    }
}
