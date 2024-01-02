package com.leezekee.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    Integer id;
    String title;
    Integer journalistId;
    LocalDateTime time;
    String content;
    Integer imageId;
    Integer status;
    String reviewComment;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
