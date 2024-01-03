package com.leezekee.utils;

import com.leezekee.pojo.Role;

public class CommonUtil {
    public static String hideIdCardNumber(String idCardNumber) {
        return idCardNumber.substring(0, 3) + "***********" + idCardNumber.substring(14);
    }

    public static String hideTelephoneNumber(String telephoneNumber) {
        return telephoneNumber.substring(0, 3) + "****" + telephoneNumber.substring(7);
    }

    public static String randomUsername(Integer role) {
        String username = String.valueOf(System.currentTimeMillis());
        if (role.equals(Role.JOURNALIST)) {
            username = "j" + username;
        } else if (role.equals(Role.CHIEF_EDITOR)) {
            username = "c" + username;
        } else if (role.equals(Role.READER)) {
            username = "r" + username;
        }
        return username;
    }

    public static String randomPassword() {
        String[] randomList = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "A", "B", "C", "D",
                "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
                "Y", "Z"
        };
        // 随机8位字符串
        StringBuilder randomPasswordList = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randomIndex = (int) (Math.random() * randomList.length);
            randomPasswordList.append(randomList[randomIndex]);
        }
        return Md5Util.genMd5String(randomPasswordList.toString());
    }
}
