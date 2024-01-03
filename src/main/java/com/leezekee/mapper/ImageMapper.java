package com.leezekee.mapper;

import com.leezekee.pojo.Image;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {
    void deleteImageByJournalistId(Integer id);

    void deleteImageByNewsId(Integer id);

    List<Image> findImageByNewsId(Integer newsId);

    Integer insertImage(Image image);

    void updateImage(Image image);

    void deleteImage(Integer imageId);

    Image findImageById(Integer imageId);
}
