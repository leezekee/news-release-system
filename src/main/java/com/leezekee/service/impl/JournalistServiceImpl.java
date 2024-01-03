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
import java.util.UUID;

@Service
public class JournalistServiceImpl implements JournalistService {
    @Autowired
    JournalistMapper journalistMapper;
    @Autowired
    NewsService newsService;
    @Autowired
    ImageService imageService;

    @Override
    public void addJournalist(Journalist journalist) {
        if (journalist.getUsername() == null) {
            journalist.setUsername(CommonUtil.randomUsername(Role.JOURNALIST));
        }
        if (journalist.getPassword() == null) {
            journalist.setPassword(CommonUtil.randomPassword());
        }
        journalist.setUuid(String.valueOf(UUID.randomUUID()));
        journalistMapper.addJournalist(journalist);
        if (journalist.getTelephoneNumber() != null) {
            journalist.setTelephoneNumber(CommonUtil.hideTelephoneNumber(journalist.getTelephoneNumber()));
        }
        if (journalist.getIdCardNumber() != null) {
            journalist.setIdCardNumber(CommonUtil.hideIdCardNumber(journalist.getIdCardNumber()));
        }
        journalist.setPassword(null);
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
        Journalist journalist = journalistMapper.findJournalistById(id);
        if (journalist == null) {
            return null;
        }
        if (journalist.getTelephoneNumber() != null) {
            journalist.setTelephoneNumber(CommonUtil.hideTelephoneNumber(journalist.getTelephoneNumber()));
        }
        if (journalist.getIdCardNumber() != null) {
            journalist.setIdCardNumber(CommonUtil.hideIdCardNumber(journalist.getIdCardNumber()));
        }
        journalist.setPassword(null);
        return journalist;
    }

    @Override
    public Journalist findJournalistByUsername(String username) {
        Journalist journalist = journalistMapper.findJournalistByUsername(username);
        if (journalist == null) {
            return null;
        }
        if (journalist.getTelephoneNumber() != null) {
            journalist.setTelephoneNumber(CommonUtil.hideTelephoneNumber(journalist.getTelephoneNumber()));
        }
        if (journalist.getIdCardNumber() != null) {
            journalist.setIdCardNumber(CommonUtil.hideIdCardNumber(journalist.getIdCardNumber()));
        }
        journalist.setPassword(null);
        return journalist;
    }

    @Override
    public List<Journalist> findAllJournalist() {
        List<Journalist> allJournalist = journalistMapper.findAllJournalist();
        for (Journalist journalist : allJournalist) {
            if (journalist.getTelephoneNumber() != null) {
                journalist.setTelephoneNumber(CommonUtil.hideTelephoneNumber(journalist.getTelephoneNumber()));
            }
            if (journalist.getIdCardNumber() != null) {
                journalist.setIdCardNumber(CommonUtil.hideIdCardNumber(journalist.getIdCardNumber()));
            }
            journalist.setPassword(null);
        }
        return allJournalist;
    }

    @Override
    public void updatePassword(String newPasswordMd5, Integer id) {
        journalistMapper.updatePassword(newPasswordMd5, id);
    }

    @Override
    public Journalist findJournalistByIdWithoutHidingInformation(Integer id) {
        return journalistMapper.findJournalistById(id);
    }

    @Override
    public Journalist findJournalistByUsernameWithoutHidingInformation(String username) {
        return journalistMapper.findJournalistByUsername(username);
    }
}
