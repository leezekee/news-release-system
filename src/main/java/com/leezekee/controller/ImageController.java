package com.leezekee.controller;

import com.leezekee.pojo.*;
import com.leezekee.service.ImageService;
import com.leezekee.utils.AliOssUtil;
import com.leezekee.utils.AuthorizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    static final Set<String> imageSuffix = Set.of("jpg", "jpeg", "gif", "png", "webp");

    @RequestMapping("/upload")
    public Response uploadImage(@RequestParam("image") MultipartFile file,
                                           @RequestParam("newsId") Integer newsId) throws IOException {
        if (AuthorizationUtil.lowerThanCurrentUser(Role.JOURNALIST)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return Response.error(Code.WRONG_PARAMETER, "文件名为空");
        }
        int index = originalFilename.lastIndexOf(".");
        if (index == -1) {
            return Response.error(Code.WRONG_PARAMETER, "文件名不合法");
        }
        String suffix = originalFilename.substring(index + 1);
        if (!imageSuffix.contains(suffix)) {
            return Response.error(Code.WRONG_PARAMETER,
                    "文件类型不合法, 仅支持jpg, jpeg, gif, png, webp的图片");
        }
        String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = AliOssUtil.uploadFile(fileName, file.getInputStream());
        if (url == null || url.isEmpty())  {
            return Response.error(Code.UNKNOWN_ERROR, "上传失败");
        }

        Image image = imageService.uploadImage(url, newsId);
        return Response.success("上传成功", image);
    }

    @PutMapping
    public Response updateImage(@RequestBody @Validated Image image) {
        Image imageById = imageService.findImageById(image.getId());
        if (imageById == null) {
            return Response.error(Code.NOT_EXIST, "图片不存在");
        }
        if (AuthorizationUtil.lowerThanCurrentUserOrNotOneSelf(Role.JOURNALIST, imageById.getJournalistId())) {
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
        if (AuthorizationUtil.lowerThanCurrentUserOrNotOneSelf(Role.JOURNALIST, imageById.getJournalistId())) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        imageService.deleteImage(imageId);
        return Response.success("删除成功");
    }

    @GetMapping("/{imageId}")
    public Response getImage(@PathVariable Integer imageId) {
        Image imageById = imageService.findImageById(imageId);
        if (imageById == null) {
            return Response.error(Code.NOT_EXIST, "图片不存在");
        }
        return Response.success("获取成功", imageById);
    }

    @GetMapping("/list/{newsId}")
    public Response listImageByNewsId(@PathVariable Integer newsId) {
        return Response.success("获取成功", imageService.findImageByNewsId(newsId));
    }
}
