package com.leezekee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    Integer id;
    String imageUrl;
    Integer newsId;
    Integer journalistId;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
