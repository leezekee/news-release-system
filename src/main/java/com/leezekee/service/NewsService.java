package com.leezekee.service;

import com.leezekee.pojo.News;
import com.leezekee.pojo.PageBean;

import java.util.List;

public interface NewsService {
    int addNews(News news);

    void updateNews(News news);

    News findNewsById(Integer id);

    PageBean<News> findAllNews(Integer pageNum, Integer pageSize);

    PageBean<News> findAllNewsByJournalistId(Integer id, Integer pageNum, Integer pageSize);

    PageBean<News> findNewsList(Integer pageNum, Integer pageSize);

    PageBean<News> findUnreviewedNewsList(Integer pageNum, Integer pageSize);

    void deleteNewsById(Integer id);

    void reviewNews(News news);

    List<News> search(String keyword, Integer limit);

    PageBean<News> searchAll(String keyword, Integer pageNum, Integer pageSize);

    void deleteNewsByJournalistId(Integer id);

    void saveNews(News news);

    void submitNews(News news);
}
