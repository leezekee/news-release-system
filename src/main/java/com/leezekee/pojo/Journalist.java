package com.leezekee.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Journalist implements User {
    String uuid;
    Integer id;
    String username;
    String password;
    String name;
    Integer age;
    String gender;
    String idCardNumber;
    String address;
    String telephoneNumber;
    String email;
    String introduction;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    @JsonIgnore
    Integer isDelete;
    Integer role = Role.JOURNALIST;
}
