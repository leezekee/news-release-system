package com.leezekee.service;

import com.leezekee.pojo.Image;

import java.util.List;

public interface ImageService {
    Image findImageById(Integer imageId);

    void deleteImage(Integer imageId);

    void updateImage(Image image);

    void deleteImageByJournalistId(Integer id);

    Image uploadImage(String url, Integer newsId);

    List<Image> findImageByNewsId(Integer newsId);

    void deleteImageByNewsId(Integer id);
}
