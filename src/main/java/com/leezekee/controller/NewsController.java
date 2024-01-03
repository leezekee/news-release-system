package com.leezekee.controller;

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

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    @PostMapping
    public Response addNews(@RequestBody @Validated(News.Add.class) News news) {
        if (!AuthorizationUtil.equalsCurrentUser(Role.JOURNALIST)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        newsService.addNews(news);
        return Response.success("添加成功", news);
    }

    @PostMapping("/submit")
    public Response submitNews(@RequestBody @Validated(News.Submit.class) News news) {
        if (news.getJournalistId() == null) {
            return Response.error(Code.WRONG_PARAMETER, "缺少参数");
        }
        if (!AuthorizationUtil.equalsCurrentUser(Role.JOURNALIST, news.getJournalistId())) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        newsService.submitNews(news);
        return Response.success("提交成功");
    }

    @PutMapping
    public Response updateNews(@RequestBody @Validated(News.Update.class) News news) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        if (AuthorizationUtil.lowerThanCurrentUserOrNotOneSelf(Role.JOURNALIST, id)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        newsService.updateNews(news);
        return Response.success("修改成功");
    }

    @GetMapping("/{id}")
    public Response findNewsById(@PathVariable Integer id) {
        News news = newsService.findNewsById(id);
        if (news == null) {
            return Response.error(Code.WRONG_PARAMETER, "新闻不存在");
        }
        return Response.success("查询成功", news);
    }

    @GetMapping("/search")
    public Response search(@RequestParam("key") String key,
                           @RequestParam("limit") Integer limit,
                           @RequestParam("pageNum") Integer pageNum,
                           @RequestParam("pageSize") Integer pageSize) {
        if (limit == -1) {
            return Response.success("查询成功", newsService.searchAll(key, pageNum, pageSize));
        }
        return Response.success("查询成功", newsService.search(key, limit));
    }

    @GetMapping("/all")
    public Response findAllNews(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        if (AuthorizationUtil.lowerThanCurrentUser(Role.CHIEF_EDITOR)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        return Response.success("查询成功", newsService.findAllNews(pageNum, pageSize));
    }

    @GetMapping("/all/{id}")
    public Response findAllNewsByJournalistId(@PathVariable Integer id, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        if (AuthorizationUtil.lowerThanCurrentUser(Role.JOURNALIST)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        return Response.success("查询成功", newsService.findAllNewsByJournalistId(id, pageNum, pageSize));
    }

    @GetMapping("/list")
    public Response findNewsList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return Response.success("查询成功", newsService.findNewsList(pageNum, pageSize));
    }

    @GetMapping("/list/unreviewed")
    public Response findUnreviewedNewsList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        if (AuthorizationUtil.lowerThanCurrentUser(Role.CHIEF_EDITOR)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        return Response.success("查询成功", newsService.findUnreviewedNewsList(pageNum, pageSize));
    }

    @PutMapping("/review")
    public Response reviewedNewsList(@RequestBody @Validated(News.Review.class) News news) {
        if (AuthorizationUtil.lowerThanCurrentUser(Role.CHIEF_EDITOR)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        News newsById = newsService.findNewsById(news.getId());
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
        newsService.reviewNews(news);
        return Response.success("审核提交成功");
    }

    @GetMapping("/list/{id}/unpassed")
    public Response findUnpassedNewsList(@PathVariable Integer id,
                                         @RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize) {
        if (AuthorizationUtil.equalsCurrentUser(Role.JOURNALIST, id)) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        return Response.success("查询成功", newsService.findUnpassedNewsList(pageNum, pageSize));
    }

    @DeleteMapping("/{id}")
    public Response deleteNewsById(@PathVariable Integer id) {
        News newsById = newsService.findNewsById(id);
        if (newsById == null) {
            return Response.error(Code.WRONG_PARAMETER, "新闻不存在");
        }
        if (AuthorizationUtil.lowerThanCurrentUserOrNotOneSelf(Role.JOURNALIST, newsById.getId())) {
            return Response.error(Code.UNAUTHORIZED, "权限不足");
        }
        newsService.deleteNewsById(id);
        return Response.success("删除成功");
    }

    @GetMapping("/detail/{id}")
    public Response findNewsDetailById(@PathVariable Integer id) {
        News newsById = newsService.findNewsDetailById(id);
        if (newsById == null) {
            return Response.error(Code.NEWS_NOT_EXIST, "新闻不存在");
        }
        return Response.success("查询成功", newsById);
    }
}
