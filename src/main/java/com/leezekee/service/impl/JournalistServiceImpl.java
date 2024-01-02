package com.leezekee.service.impl;

import com.leezekee.mapper.JournalistMapper;
import com.leezekee.pojo.Journalist;
import com.leezekee.service.JournalistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalistServiceImpl implements JournalistService {
    @Autowired
    JournalistMapper journalistMapper;

    @Override
    public int addJournalist(Journalist journalist) {
        return 0;
    }

    @Override
    public void deleteJournalist(Integer id) {

    }

    @Override
    public void updateJournalist(Journalist journalist) {

    }

    @Override
    public Journalist findJournalistById(Integer id) {
        return null;
    }

    @Override
    public Journalist findJournalistByUsername(String username) {
        return null;
    }

    @Override
    public Object findAllJournalist() {
        return null;
    }
}
