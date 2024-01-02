package com.leezekee.controller;

import com.leezekee.pojo.Code;
import com.leezekee.pojo.News;
import com.leezekee.pojo.Response;
import com.leezekee.pojo.Role;
import com.leezekee.service.NewsService;
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
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    @PostMapping
    public Response addNews(@RequestBody @Validated News news) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Role role = (Role) claims.get("role");
        Integer id = (Integer) claims.get("id");
        if (!AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(Role.JOURNALIST, id)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        News newsByTitle = newsService.getNewsByTitle(news.getTitle());
        if (newsByTitle != null) {
            return Response.error(Code.WRONG_PARAMETER, "标题已经存在");
        }
        news.setJournalistId(id);
        newsService.addNews(news);
        return Response.success("添加成功");
    }

    @PutMapping
    public Response updateNews(@RequestBody @Validated News news) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        if (!AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(Role.JOURNALIST, id)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        News newsByTitle = newsService.getNewsByTitle(news.getTitle());
        if (newsByTitle != null && !newsByTitle.getId().equals(news.getId())) {
            return Response.error(Code.WRONG_PARAMETER, "标题已经存在");
        }
        newsService.updateNews(news);
        return Response.success("修改成功");
    }

    @GetMapping("/{id}")
    public Response getNewsById(@PathVariable Integer id) {
        News news = newsService.getNewsById(id);
        if (news == null) {
            return Response.error(Code.WRONG_PARAMETER, "新闻不存在");
        }
        return Response.success("查询成功", news);
    }

    @GetMapping("/all")
    public Response getAllNews() {
        if (!AuthorizationUtil.noLowerThanCurrentUser(Role.CHIEF_EDITOR)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        return Response.success("查询成功", newsService.getAllNews());
    }

    @GetMapping("/all/{id}")
    public Response getAllNewsByJournalistId(@PathVariable Integer id) {
        if (!AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(Role.JOURNALIST, id)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        return Response.success("查询成功", newsService.getAllNewsByJournalistId(id));
    }

    @GetMapping("/list")
    public Response getNewsList() {
        return Response.success("查询成功", newsService.getNewsList());
    }

    @GetMapping("/list/unreviewed")
    public Response getUnreviewedNewsList() {
        if (!AuthorizationUtil.noLowerThanCurrentUser(Role.CHIEF_EDITOR)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        return Response.success("查询成功", newsService.getUnreviewedNewsList());
    }

    @PutMapping("/list/reviewe")
    public Response getReviewedNewsList(@RequestBody @Validated News news) {
        if (!AuthorizationUtil.noLowerThanCurrentUser(Role.CHIEF_EDITOR)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        News newsById = newsService.getNewsById(news.getId());
        if (newsById == null) {
            return Response.error(Code.WRONG_PARAMETER, "新闻不存在");
        }
        Integer newStatus = news.getStatus();
        Integer status = newsById.getStatus();
        if (newStatus == null || status == null) {
            return Response.error(Code.WRONG_PARAMETER, "参数错误");
        }
        if (newStatus.equals(status)) {
            return Response.error(Code.WRONG_PARAMETER, "新闻状态未改变");
        }
        if (newStatus == -1 && news.getReviewComment() == null) {
            return Response.error(Code.WRONG_PARAMETER, "缺少审核意见");
        }
        newsService.updateNews(news);
        return Response.success("修改成功");
    }

    @DeleteMapping("/{id}")
    public Response deleteNewsById(@PathVariable Integer id) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        Role role = (Role) claims.get("role");
        News newsById = newsService.getNewsById(id);
        if (newsById == null) {
            return Response.error(Code.WRONG_PARAMETER, "新闻不存在");
        }
        if (!AuthorizationUtil.noLowerThanCurrentUserAndOneSelf(Role.JOURNALIST, newsById.getId())) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        newsService.deleteNewsById(id);
        return Response.success("删除成功");
    }

    @GetMapping("/detail/{id}")
    public Response getNewsDetailById(@PathVariable Integer id) {
        News newsById = newsService.getNewsById(id);
        if (newsById == null) {
            return Response.error(Code.WRONG_PARAMETER, "新闻不存在");
        }
        return Response.success("查询成功", newsById);
    }
}
