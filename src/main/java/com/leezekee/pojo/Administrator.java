package com.leezekee.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administrator implements User{
    String uuid;
    Integer id;

    @NotBlank(message = "用户名不能为空", groups = Auth.class)
    @Min(value = 6, message = "用户名长度不能小于6位", groups = Auth.class)
    @Max(value = 20, message = "用户名长度不能大于20位", groups = Auth.class)
    String username;

    @NotBlank(message = "密码不能为空", groups = Auth.class)
    @Min(value = 6, message = "密码长度不能小于6位", groups = Auth.class)
    @Max(value = 20, message = "密码长度不能大于20位", groups = Auth.class)
    String password;
    @JsonIgnore
    Integer role = Role.ADMINISTRATOR;

    public interface Auth {}
}
