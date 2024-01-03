package com.leezekee.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leezekee.anno.MultiFieldAssociationCheck;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MultiFieldAssociationCheck.List(
        value = {
                @MultiFieldAssociationCheck(
                        when = "#status.equals(-1)",
                        must = "#reviewComment != null",
                        message = "审核不通过时，必须填写审核意见"
                ),
        }
)
public class News {
    @NotNull(groups = {Update.class, Save.class, Submit.class}, message = "id不能为空")
    Integer id;

    @NotBlank(groups = {Add.class, Submit.class}, message = "标题不能为空")
    String title;

    Integer journalistId;
    String journalistName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime time;

    @NotBlank(groups = {Save.class, Submit.class}, message = "内容不能为空")
    String content;

    @NotNull(groups = {Review.class}, message = "审核状态不能为空")
    Integer status;

    @Max(groups = {Review.class}, value = 100, message = "审核意见不能超过100个字符")
    String reviewComment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateTime;
    @JsonIgnore
    Integer isDelete;

    public interface Add {}
    public interface Update {}
    public interface Review {}
    public interface Save {}
    public interface Submit {}
}
