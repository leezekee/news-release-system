package com.leezekee.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leezekee.mapper.NewsMapper;
import com.leezekee.pojo.Journalist;
import com.leezekee.pojo.News;
import com.leezekee.pojo.PageBean;
import com.leezekee.service.ImageService;
import com.leezekee.service.JournalistService;
import com.leezekee.service.NewsService;
import com.leezekee.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    ImageService imageService;
    @Autowired
    JournalistService journalistService;

    @Override
    public void addNews(News news) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer journalistId = (Integer) claims.get("id");
        news.setJournalistId(journalistId);
        newsMapper.addNews(news);
    }

    @Override
    public void updateNews(News news) {
        news.setStatus(-2);
        newsMapper.updateNews(news);
    }

    @Override
    public News findNewsById(Integer id) {
        News news = newsMapper.findNewsById(id);
        Journalist journalist = journalistService.findJournalistById(news.getJournalistId());
        news.setJournalistName(journalist.getName());
        return news;
    }

    @Override
    public PageBean<News> findAllNews(Integer pageNum, Integer pageSize) {
        List<Journalist> journalistList = journalistService.findAllJournalist();
        Map<Integer, String> journalistMap = journalistList.stream().collect(
                Collectors.toMap(Journalist::getId, Journalist::getName));
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.findAllNews();
        for (News news : newsList) {
            news.setJournalistName(journalistMap.get(news.getJournalistId()));
            if (news.getContent() != null && news.getContent().length() > 20) {
                news.setContent(news.getContent().substring(0, 20) + "...");
            }
        }
        Page<News> page =(Page<News>) newsList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public PageBean<News> findAllNewsByJournalistId(Integer id, Integer pageNum, Integer pageSize) {
        List<Journalist> journalistList = journalistService.findAllJournalist();
        Map<Integer, String> journalistMap = journalistList.stream().collect(
                Collectors.toMap(Journalist::getId, Journalist::getName));
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.findAllNewsByJournalistId(id);
        for (News news : newsList) {
            news.setJournalistName(journalistMap.get(news.getJournalistId()));
            if (news.getContent() != null && news.getContent().length() > 20) {
                news.setContent(news.getContent().substring(0, 20) + "...");
            }
        }
        Page<News> page =(Page<News>) newsList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public PageBean<News> findNewsList(Integer pageNum, Integer pageSize) {
        List<Journalist> journalistList = journalistService.findAllJournalist();
        Map<Integer, String> journalistMap = journalistList.stream().collect(
                Collectors.toMap(Journalist::getId, Journalist::getName));
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.findAllReleasedNews();
        for (News news : newsList) {
            news.setJournalistName(journalistMap.get(news.getJournalistId()));
            if (news.getContent() != null && news.getContent().length() > 20) {
                news.setContent(news.getContent().substring(0, 20) + "...");
            }
            news.setReviewComment(null);
            news.setStatus(null);
        }
        Page<News> page =(Page<News>) newsList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public PageBean<News> findUnreviewedNewsList(Integer pageNum, Integer pageSize) {
        List<Journalist> journalistList = journalistService.findAllJournalist();
        Map<Integer, String> journalistMap = journalistList.stream().collect(
                Collectors.toMap(Journalist::getId, Journalist::getName));
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.findUnreviewedNewsList();
        for (News news : newsList) {
            news.setJournalistName(journalistMap.get(news.getJournalistId()));
            if (news.getContent() != null && news.getContent().length() > 20) {
                news.setContent(news.getContent().substring(0, 20) + "...");
            }
        }
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
        List<Journalist> journalistList = journalistService.findAllJournalist();
        Map<Integer, String> journalistMap = journalistList.stream().collect(
                Collectors.toMap(Journalist::getId, Journalist::getName));
        List<News> newsList = newsMapper.searchByKeyword(keyword, limit);
        for (News news : newsList) {
            news.setJournalistName(journalistMap.get(news.getJournalistId()));
            if (news.getContent() != null && news.getContent().length() > 20) {
                news.setContent(news.getContent().substring(0, 20) + "...");
            }
        }
        return newsList;
    }

    @Override
    public PageBean<News> searchAll(String keyword, Integer pageNum, Integer pageSize) {
        List<Journalist> journalistList = journalistService.findAllJournalist();
        Map<Integer, String> journalistMap = journalistList.stream().collect(
                Collectors.toMap(Journalist::getId, Journalist::getName));
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.searchAllByKeyword(keyword);
        for (News news : newsList) {
            news.setJournalistName(journalistMap.get(news.getJournalistId()));
            if (news.getContent() != null && news.getContent().length() > 20) {
                news.setContent(news.getContent().substring(0, 20) + "...");
            }
        }
        Page<News> page =(Page<News>) newsList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public void submitNews(News news) {
        newsMapper.submitNews(news);
    }

    @Override
    public News findNewsDetailById(Integer id) {
        News news = newsMapper.findNewsDetailById(id);
        if (news == null) {
            return null;
        }
        Journalist journalist = journalistService.findJournalistById(news.getJournalistId());
        news.setJournalistName(journalist.getName());
        news.setReviewComment(null);
        news.setStatus(null);
        return news;
    }

    @Override
    public List<News> findUnpassedNewsList(Integer pageNum, Integer pageSize) {
        PageBean<News> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        List<News> newsList = newsMapper.findUnpassedNewsList();
        for (News news : newsList) {
            if (news.getContent() != null && news.getContent().length() > 20) {
                news.setContent(news.getContent().substring(0, 20) + "...");
            }
        }
        Page<News> page =(Page<News>) newsList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return newsList;
    }
}
