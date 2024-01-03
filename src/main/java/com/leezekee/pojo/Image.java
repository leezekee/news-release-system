package com.leezekee.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @NotNull(groups = {Delete.class, Update.class}, message = "id不能为空")
    Integer id;
    @NotBlank(groups = {Update.class}, message = "图片地址不能为空")
    String imageUrl;
    Integer newsId;
    Integer journalistId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateTime;
    @JsonIgnore
    Integer isDelete;

    public interface Delete {}
    public interface Update {}
}
