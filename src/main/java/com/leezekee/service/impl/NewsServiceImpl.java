package com.leezekee.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leezekee.mapper.NewsMapper;
import com.leezekee.pojo.News;
import com.leezekee.pojo.PageBean;
import com.leezekee.service.ImageService;
import com.leezekee.service.NewsService;
import com.leezekee.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    ImageService imageService;

    @Override
    public int addNews(News news) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer journalistId = (Integer) claims.get("id");
        news.setJournalistId(journalistId);
        return newsMapper.addNews(news);
    }

    @Override
    public void updateNews(News news) {
        if (news.getStatus() == -1) {
            news.setStatus(0);
        } else if (news.getStatus() == 1) {
            news.setStatus(0);
        }
        newsMapper.updateNews(news);
    }

    @Override
    public News findNewsById(Integer id) {
        return newsMapper.findNewsById(id);
    }

    @Override
    public PageBean<News> findAllNews(Integer pageNum, Integer pageSize) {
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.findAllNews();
        Page<News> page =(Page<News>) newsList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public PageBean<News> findAllNewsByJournalistId(Integer id, Integer pageNum, Integer pageSize) {
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.findAllNewsByJournalistId(id);
        Page<News> page =(Page<News>) newsList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public PageBean<News> findNewsList(Integer pageNum, Integer pageSize) {
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.findAllReleasedNews();
        Page<News> page =(Page<News>) newsList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public PageBean<News> findUnreviewedNewsList(Integer pageNum, Integer pageSize) {
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.findUnreviewedNewsList();
        Page<News> page =(Page<News>) newsList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    @Transactional
    public void deleteNewsById(Integer id) {
        imageService.deleteImageByNewsId(id);
        newsMapper.deleteNews(id);
    }

    @Override
    public void reviewNews(News news) {
        newsMapper.updateStatus(news);
    }

    @Override
    public List<News> search(String keyword, Integer limit) {
        return newsMapper.searchByKeyword(keyword, limit);
    }

    @Override
    public PageBean<News> searchAll(String keyword, Integer pageNum, Integer pageSize) {
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.searchAllByKeyword(keyword);
        Page<News> page =(Page<News>) newsList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    @Transactional
    public void deleteNewsByJournalistId(Integer id) {
        imageService.deleteImageByJournalistId(id);
        newsMapper.deleteNewsByJournalistId(id);
    }

    @Override
    public void saveNews(News news) {
        newsMapper.saveNews(news);
    }

    @Override
    public void submitNews(News news) {
        newsMapper.submitNews(news);
    }
}
