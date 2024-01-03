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
    String username;

    @NotBlank(message = "密码不能为空", groups = Auth.class)
    String password;
    @JsonIgnore
    Integer role = Role.ADMINISTRATOR;

    public interface Auth {}
}
