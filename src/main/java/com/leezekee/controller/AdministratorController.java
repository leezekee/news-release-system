package com.leezekee.controller;

import com.leezekee.pojo.*;
import com.leezekee.service.AdministratorService;
import com.leezekee.utils.JwtUtil;
import com.leezekee.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *                     _ooOoo_
 * 	                  o8888888o
 * 	                  88" . "88
 * 	                  (| -_- |)
 * 	                  O\  =  /O
 * 	               ____/`---'\____
 * 	             .'  \\|     |//  `.
 * 	            /  \\|||  :  |||//  \
 * 	           /  _||||| -:- |||||-  \
 * 	           |   | \\\  -  /// |   |
 * 	           | \_|  ''\-/''  |   |
 * 	           \  .-\__  `-`  ___/-. /
 * 	         ___`. .'  /-.-\  `. . __
 * 	      ."" '<  `.___\_<|>_/___.'  >'"".
 * 	     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * 	     \  \ `-.   \_ __\ /__ _/   .-` /  /
 * 	======`-.____`-.___\_____/___.-`____.-'======
 * 	                   `=-='
 */
@RestController
@RequestMapping("/admin")
public class AdministratorController {
    @Autowired
    AdministratorService administratorService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Response login(@RequestBody @Validated(Administrator.Auth.class) Administrator administrator) {
        String username = administrator.getUsername();
        String password = administrator.getPassword();
        String md5Password = Md5Util.genMd5String(password);
        Administrator administratorByUsername = administratorService.findAdministratorByUsername(username);
        if (administratorByUsername == null) {
            return Response.error(Code.WRONG_PARAMETER, "用户名不存在");
        }
        if (!administratorByUsername.getPassword().equals(md5Password)) {
            return Response.error(Code.WRONG_PARAMETER, "密码错误");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", administratorByUsername.getId());
        claims.put("username", username);
        claims.put("role", Role.ADMINISTRATOR);
        String token = JwtUtil.genToken(claims);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(token, token, 10, TimeUnit.HOURS);
        return Response.success("登录成功", token);
    }
}
