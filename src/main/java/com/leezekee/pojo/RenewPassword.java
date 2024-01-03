package com.leezekee.pojo;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RenewPassword {
    @NotNull(message = "旧密码不能为空")
    String oldPassword;
    @NotNull(message = "新密码不能为空")
    String newPassword;
}
