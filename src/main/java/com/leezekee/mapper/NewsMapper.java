package com.leezekee.mapper;

import com.leezekee.pojo.News;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper {
    int addNews(News news);

    void updateNews(News news);

    News findNewsById(Integer id);

    List<News> findAllNews();

    List<News> findAllNewsByJournalistId(Integer id);

    List<News> findAllReleasedNews();

    List<News> findUnreviewedNewsList();

    List<News> searchByKeyword(String keyword, Integer limit);

    List<News> searchAllByKeyword(String keyword);

    void deleteNews(Integer id);

    void updateStatus(News news);

    void deleteNewsByJournalistId(Integer id);

    void saveNews(News news);

    void submitNews(News news);

    News findNewsDetailById(Integer id);

    List<News> findUnpassedNewsList();
}
