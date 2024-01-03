package com.leezekee.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leezekee.anno.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Journalist implements User {
    String uuid;

    @NotNull(groups = Update.class, message = "id不能为空")
    Integer id;

    @NotNull(groups = {Auth.class}, message = "用户名不能为空")
    String username;

    @NotNull(groups = {Auth.class}, message = "用户名不能为空")
    String password;

    @NotBlank(groups = {Add.class}, message = "姓名不能为空")
    String name;

    Integer age;
    @Gender(groups = {ChiefEditor.Add.class, ChiefEditor.Update.class}, message = "性别只能为男或女")
    String gender;

    @Pattern(groups = {ChiefEditor.Add.class, ChiefEditor.Update.class}, regexp = "^\\d{17}[\\dXx]$", message = "身份证号格式不正确")
    String idCardNumber;
    @Length(groups = {ChiefEditor.Add.class, ChiefEditor.Update.class}, max = 50, message = "地址长度不能超过50")
    String address;

    @Pattern(groups = {ChiefEditor.Add.class, ChiefEditor.Update.class}, regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    String telephoneNumber;
    @Email(groups = {ChiefEditor.Add.class, ChiefEditor.Update.class}, message = "邮箱格式不正确")
    String email;
    @Length(groups = {ChiefEditor.Add.class, ChiefEditor.Update.class}, max = 200, message = "简介长度不能超过200")
    String introduction;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateTime;
    @JsonIgnore
    Integer isDelete;
    @JsonIgnore
    Integer role = Role.JOURNALIST;

    public interface Auth {}
    public interface Add {}
    public interface Update {}
}
