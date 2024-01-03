package com.leezekee.service.impl;

import com.leezekee.mapper.ImageMapper;
import com.leezekee.pojo.Image;
import com.leezekee.service.ImageService;
import com.leezekee.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageMapper imageMapper;

    @Override
    public Image findImageById(Integer imageId) {
        return imageMapper.findImageById(imageId);
    }

    @Override
    public void deleteImage(Integer imageId) {
        imageMapper.deleteImage(imageId);
    }

    @Override
    public void updateImage(Image image) {
        imageMapper.updateImage(image);
    }

    @Override
    public Image uploadImage(String url, Integer newsId) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("userId");
        Image image = new Image();
        image.setNewsId(newsId);
        image.setJournalistId(userId);
        image.setImageUrl(url);
        Integer id = imageMapper.insertImage(image);
        image.setId(id);
        return image;
    }

    @Override
    public List<Image> findImageByNewsId(Integer newsId) {
        return imageMapper.findImageByNewsId(newsId);
    }

    @Override
    public void deleteImageByNewsId(Integer id) {
        imageMapper.deleteImageByNewsId(id);
    }

    @Override
    public void deleteImageByJournalistId(Integer id) {
        imageMapper.deleteImageByJournalistId(id);
    }
}
