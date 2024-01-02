package com.leezekee.service;

import com.leezekee.pojo.Journalist;

public interface JournalistService {
    int addJournalist(Journalist journalist);

    void deleteJournalist(Integer id);

    void updateJournalist(Journalist journalist);

    Journalist findJournalistById(Integer id);

    Journalist findJournalistByUsername(String username);

    Object findAllJournalist();
}
