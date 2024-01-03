package com.leezekee.controller;

import com.leezekee.pojo.Code;
import com.leezekee.pojo.Journalist;
import com.leezekee.pojo.Response;
import com.leezekee.pojo.Role;
import com.leezekee.service.JournalistService;
import com.leezekee.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/journalist")
public class JournalistController {
    @Autowired
    JournalistService journalistService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping
    public Response addJournalist(@RequestBody @Validated(Journalist.Add.class) Journalist journalist) {
        if (AuthorizationUtil.lowerThanCurrentUser(Role.CHIEF_EDITOR)) {
            return Response.error(Code.UNAUTHORIZED,"无权添加记者！");
        }
        int id = journalistService.addJournalist(journalist);
        return Response.success("添加成功", id);
    }

    @DeleteMapping("/{id}")
    public Response deleteJournalist(@PathVariable Integer id) {
        if (AuthorizationUtil.lowerThanCurrentUser(Role.CHIEF_EDITOR)) {
            return Response.error(Code.UNAUTHORIZED,"无权删除记者！");
        }
        journalistService.deleteJournalist(id);
        return Response.success("删除成功");
    }

    @PutMapping
    public Response updateJournalist(@RequestBody @Validated(Journalist.Update.class) Journalist journalist) {
        if (!AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(journalist)) {
            return Response.error(Code.UNAUTHORIZED,"无权修改其他记者信息！");
        }
        if (journalist.getName() != null) {
            if (AuthorizationUtil.lowerThanCurrentUser(Role.CHIEF_EDITOR)) {
                return Response.error(Code.UNAUTHORIZED,"无权修改姓名！");
            }
        }
        if (journalist.getUsername() != null) {
            Journalist journalistByUsername = journalistService.findJournalistByUsername(journalist.getUsername());
            if (journalistByUsername != null && !journalistByUsername.getId().equals(journalist.getId())) {
                return Response.error(Code.WRONG_PARAMETER,"用户名已存在！");
            }
        }
        if (journalist.getPassword() != null) {
            journalist.setPassword(Md5Util.genMd5String(journalist.getPassword()));
        }
        journalistService.updateJournalist(journalist);
        return Response.success("修改成功");
    }

    @GetMapping("/{id}")
    public Response getJournalist(@PathVariable Integer id) {
        Journalist journalist = journalistService.findJournalistById(id);
        if (!AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(journalist)) {
            journalist.setIdCardNumber(CommonUtil.hideIdCardNumber(journalist.getIdCardNumber()));
            journalist.setTelephoneNumber(CommonUtil.hideTelephoneNumber(journalist.getTelephoneNumber()));
        }
        return Response.success("查询成功", journalist);
    }

    @GetMapping
    public Response getCurrentJournalist() {
        if (AuthorizationUtil.equalsCurrentUser(Role.JOURNALIST)) {
            return Response.error(Code.UNAUTHORIZED,"无权查询信息！");
        }
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        Journalist journalist = journalistService.findJournalistById(id);
        journalist.setPassword(null);
        return Response.success("查询成功", journalist);
    }

    @PostMapping("/login")
    public Response login(@RequestBody @Validated(Journalist.Auth.class) Journalist journalist) {
        String username = journalist.getUsername();
        String password = journalist.getPassword();
        String md5Password = Md5Util.genMd5String(password);
        Journalist journalistByUsername = journalistService.findJournalistByUsername(username);
        if (journalistByUsername == null) {
            return Response.error(Code.WRONG_PARAMETER, "用户名不存在");
        }
        if (!journalistByUsername.getPassword().equals(md5Password)) {
            return Response.error(Code.WRONG_PARAMETER, "密码错误");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", journalistByUsername.getId());
        claims.put("username", username);
        claims.put("role", Role.JOURNALIST);
        String token = JwtUtil.genToken(claims);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(token, token, 10, TimeUnit.HOURS);
        return Response.success("登录成功", token);
    }

    @GetMapping("/all")
    public Response getAllJournalist() {
        return Response.success("查询成功", journalistService.findAllJournalist());
    }
}
