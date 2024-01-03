package com.leezekee.utils;

import com.leezekee.pojo.User;

import java.util.Map;

public class AuthorizationUtil {
    public static boolean lowerThanCurrentUser(Integer userRole) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer currentRole = (Integer) claims.get("role");
        return userRole - currentRole > 0;
    }


    public static boolean lowerThanCurrentUserOrNotOneSelf(User user) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer currentRole = (Integer) claims.get("role");
        Integer currentId = (Integer) claims.get("id");
        Integer userRole = user.getRole();
        Integer userId = user.getId();
        if (userRole - currentRole > 0) {
            return true;
        } else if (userRole.compareTo(currentRole) == 0) {
            return !userId.equals(currentId);
        } else {
            return false;
        }
    }

    public static boolean lowerThanCurrentUserOrNotOneSelf(Integer userRole, Integer userId) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer currentRole = (Integer) claims.get("role");
        Integer currentId = (Integer) claims.get("id");
        if (userRole.compareTo(currentRole) > 0) {
            return false;
        } else if (userRole.compareTo(currentRole) == 0) {
            return !userId.equals(currentId);
        } else {
            return true;
        }
    }

    public static boolean equalsCurrentUser(Integer userRole) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer currentRole = (Integer) claims.get("role");
        return userRole.equals(currentRole);
    }
}
