package com.leezekee.service.impl;

import com.leezekee.mapper.JournalistMapper;
import com.leezekee.pojo.Journalist;
import com.leezekee.pojo.Role;
import com.leezekee.service.ImageService;
import com.leezekee.service.JournalistService;
import com.leezekee.service.NewsService;
import com.leezekee.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JournalistServiceImpl implements JournalistService {
    @Autowired
    JournalistMapper journalistMapper;
    @Autowired
    NewsService newsService;
    @Autowired
    ImageService imageService;

    @Override
    public int addJournalist(Journalist journalist) {
        if (journalist.getUsername() == null) {
            journalist.setUsername(CommonUtil.randomUsername(Role.JOURNALIST));
        }
        if (journalist.getPassword() == null) {
            journalist.setPassword(CommonUtil.randomPassword());
        }
        return journalistMapper.addJournalist(journalist);
    }

    @Override
    @Transactional
    public void deleteJournalist(Integer id) {
        imageService.deleteImageByJournalistId(id);
        newsService.deleteNewsByJournalistId(id);
        journalistMapper.deleteJournalist(id);
    }

    @Override
    public void updateJournalist(Journalist journalist) {
        journalistMapper.updateJournalist(journalist);
    }

    @Override
    public Journalist findJournalistById(Integer id) {
        return journalistMapper.findJournalistById(id);
    }

    @Override
    public Journalist findJournalistByUsername(String username) {
        return journalistMapper.findJournalistByUsername(username);
    }

    @Override
    public List<Journalist> findAllJournalist() {
        return journalistMapper.findAllJournalist();
    }
}
