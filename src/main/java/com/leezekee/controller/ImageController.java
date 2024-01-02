package com.leezekee.controller;

import com.leezekee.pojo.Code;
import com.leezekee.pojo.Image;
import com.leezekee.pojo.Response;
import com.leezekee.pojo.Role;
import com.leezekee.service.ImageService;
import com.leezekee.utils.AuthorizationUtil;
import com.leezekee.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * # 2. 要求有以下功能：
 * # （1）基本信息维护：能够录入、修改、删除记者基本信息；
 * # （2）新闻提交：登录确认，新闻录入，个人信息维护；
 * # （3）审核：新闻显示控制，新闻删除，图片删除；
 * # （4）显示：新闻题目显示，新闻内容显示，图片显示；
 */

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    public Response uploadImage(@RequestBody @Validated Image image) {
        if (!AuthorizationUtil.noLowerThanCurrentUser(Role.JOURNALIST)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("userId");
        image.setJournalistId(userId);
        imageService.uploadImage(image);
        return Response.success("上传成功");
    }

    @PutMapping
    public Response updateImage(@RequestBody @Validated Image image) {
        Image imageById = imageService.findImageById(image.getId());
        if (imageById == null) {
            return Response.error(Code.NOT_EXIST, "图片不存在");
        }
        if (!AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(Role.JOURNALIST, imageById.getJournalistId())) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        imageService.updateImage(image);
        return Response.success("修改成功");
    }

    @DeleteMapping("/{imageId}")
    public Response deleteImage(@PathVariable Integer imageId) {
        Image imageById = imageService.findImageById(imageId);
        if (imageById == null) {
            return Response.error(Code.NOT_EXIST, "图片不存在");
        }
        if (!AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(Role.JOURNALIST, imageById.getJournalistId())) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        imageService.deleteImage(imageId);
        return Response.success("删除成功");
    }

    @GetMapping("/list")
    public Response listImage() {
        return Response.success("获取成功", imageService.findAllImage());
    }

    @GetMapping("/{imageId}")
    public Response getImage(@PathVariable Integer imageId) {
        Image imageById = imageService.findImageById(imageId);
        if (imageById == null) {
            return Response.error(Code.NOT_EXIST, "图片不存在");
        }
        return Response.success("获取成功", imageById);
    }
}
