package com.leezekee.service;

import com.leezekee.pojo.News;

public interface NewsService {
    News getNewsByTitle(String title);

    void addNews(News news);

    void updateNews(News news);

    News getNewsById(Integer id);

    Object getAllNews();

    Object getAllNewsByJournalistId(Integer id);

    Object getNewsList();

    Object getUnreviewedNewsList();

    void deleteNewsById(Integer id);
}
