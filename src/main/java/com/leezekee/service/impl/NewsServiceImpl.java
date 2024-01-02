package com.leezekee.service.impl;

import com.leezekee.mapper.NewsMapper;
import com.leezekee.pojo.News;
import com.leezekee.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsMapper newsMapper;

    @Override
    public News getNewsByTitle(String title) {
        return null;
    }

    @Override
    public void addNews(News news) {

    }

    @Override
    public void updateNews(News news) {

    }

    @Override
    public News getNewsById(Integer id) {
        return null;
    }

    @Override
    public Object getAllNews() {
        return null;
    }

    @Override
    public Object getAllNewsByJournalistId(Integer id) {
        return null;
    }

    @Override
    public Object getNewsList() {
        return null;
    }

    @Override
    public Object getUnreviewedNewsList() {
        return null;
    }

    @Override
    public void deleteNewsById(Integer id) {

    }
}
