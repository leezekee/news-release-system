package com.leezekee.controller;

import com.leezekee.pojo.ChiefEditor;
import com.leezekee.pojo.Code;
import com.leezekee.pojo.Response;
import com.leezekee.pojo.Role;
import com.leezekee.service.ChiefEditorService;
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
@RequestMapping("/chiefEditor")
@Validated
public class ChiefEditorController {
    @Autowired
    ChiefEditorService chiefEditorService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping
    public Response addChiefEditor(@RequestBody @Validated({ChiefEditor.Add.class}) ChiefEditor chiefEditor) {
        if (AuthorizationUtil.lowerThanCurrentUser(Role.ADMINISTRATOR)) {
            return Response.error(Code.UNAUTHORIZED, "无权添加用户");
        }
        int id = chiefEditorService.addChiefEditor(chiefEditor);
        return Response.success("添加成功", id);
    }

    @PostMapping("/login")
    public Response login(@RequestBody @Validated(ChiefEditor.Auth.class) ChiefEditor chiefEditor) {
        String username = chiefEditor.getUsername();
        String password = chiefEditor.getPassword();
        String md5Password = Md5Util.genMd5String(password);
        ChiefEditor chiefEditorByUsername = chiefEditorService.findChiefEditorByUsername(username);
        if (chiefEditorByUsername == null) {
            return Response.error(Code.WRONG_PARAMETER, "用户名不存在");
        }
        if (!chiefEditorByUsername.getPassword().equals(md5Password)) {
            return Response.error(Code.WRONG_PARAMETER, "密码错误");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", chiefEditorByUsername.getId());
        claims.put("username", username);
        claims.put("role", Role.CHIEF_EDITOR);
        String token = JwtUtil.genToken(claims);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(token, token, 10, TimeUnit.HOURS);
        return Response.success("登录成功", token);
    }

    @GetMapping("/info")
    public Response getCurrentChiefEditorInfo() {
        if (AuthorizationUtil.lowerThanCurrentUser(Role.CHIEF_EDITOR)) {
            return Response.error(Code.UNAUTHORIZED, "无权获取信息");
        }
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer currentId = (Integer) claims.get("id");
        ChiefEditor chiefEditor = chiefEditorService.findChiefEditorById(currentId);
        chiefEditor.setPassword(null);
        return Response.success("获取成功", chiefEditor);
    }

    @GetMapping("/info/{id}")
    public Response getChiefEditorInfo(@PathVariable Integer id) {
        ChiefEditor chiefEditor = chiefEditorService.findChiefEditorById(id);
        chiefEditor.setPassword(null);
        if (!AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(chiefEditor)) {
            chiefEditor.setTelephoneNumber(null);
            chiefEditor.setIdCardNumber(CommonUtil.hideIdCardNumber(chiefEditor.getIdCardNumber()));
            chiefEditor.setTelephoneNumber(CommonUtil.hideTelephoneNumber(chiefEditor.getTelephoneNumber()));
        }
        return Response.success("获取成功", chiefEditor);
    }

    @PutMapping("/info")
    public Response updateChiefEditorInfo(@RequestBody @Validated(ChiefEditor.Update.class) ChiefEditor chiefEditor) {
        if (AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(chiefEditor)) {
            return Response.error(Code.UNAUTHORIZED,"无权修改他人信息！");
        }
        if (chiefEditor.getName() != null) {
            if (AuthorizationUtil.lowerThanCurrentUser(Role.ADMINISTRATOR)) {
                chiefEditor.setName(null);
                return Response.error(Code.UNAUTHORIZED,"无权修改姓名！");
            }
        }
        if (chiefEditor.getUsername() != null) {
            ChiefEditor chiefEditorByUsername = chiefEditorService.findChiefEditorByUsername(chiefEditor.getUsername());
            if (chiefEditorByUsername != null) {
                return Response.error(Code.WRONG_PARAMETER,"用户名已存在！");
            }
        }
        if (chiefEditor.getPassword() != null) {
            chiefEditor.setPassword(Md5Util.genMd5String(chiefEditor.getPassword()));
        }
        chiefEditorService.updateChiefEditor(chiefEditor);
        return Response.success("修改成功");
    }

    @DeleteMapping
    public Response deleteChiefEditor(@RequestBody @Validated(ChiefEditor.Update.class) ChiefEditor chiefEditor) {
        if (AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(chiefEditor)) {
            return Response.error(Code.UNAUTHORIZED,"无权删除他人信息！");
        }
        chiefEditorService.deleteChiefEditor(chiefEditor);
        return Response.success("删除成功");
    }
}
