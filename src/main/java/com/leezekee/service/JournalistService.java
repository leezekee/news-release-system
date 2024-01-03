package com.leezekee.service;

import com.leezekee.pojo.Journalist;

import java.util.List;

public interface JournalistService {
    void addJournalist(Journalist journalist);

    void deleteJournalist(Integer id);

    void updateJournalist(Journalist journalist);

    Journalist findJournalistById(Integer id);

    Journalist findJournalistByUsername(String username);

    List<Journalist> findAllJournalist();

    void updatePassword(String newPasswordMd5, Integer id);

    Journalist findJournalistByIdWithoutHidingInformation(Integer id);

    Journalist findJournalistByUsernameWithoutHidingInformation(String username);
}
