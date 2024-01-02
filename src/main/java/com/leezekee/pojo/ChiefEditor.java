package com.leezekee.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiefEditor implements User {
    String uuid;

    @NotNull(groups = Update.class, message = "id不能为空")
    Integer id;

    @NotNull(groups = {Auth.class, Add.class}, message = "用户名不能为空")
    @Max(value = 20, groups = {Auth.class, Add.class}, message = "用户名长度不能超过20")
    @Min(value = 3, groups = {Auth.class, Add.class}, message = "用户名长度不能小于3")
    String username;

    @NotNull(groups = {Auth.class,  Add.class}, message = "密码不能为空")
    @Max(value = 20, groups = {Auth.class, Add.class}, message = "密码长度不能超过20")
    @Min(value = 3, groups = {Auth.class, Add.class}, message = "密码长度不能小于3")
    String password;

    @NotBlank(groups = {Add.class}, message = "姓名不能为空")
    String name;

    Integer age;
    String gender;
    String idCardNumber;
    String address;
    String telephoneNumber;
    String email;
    String introduction;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateTime;
    @JsonIgnore
    Integer role = Role.CHIEF_EDITOR;
    @JsonIgnore
    Integer isDelete;

    public interface Auth {}
    public interface Add {}
    public interface Update {}
}
