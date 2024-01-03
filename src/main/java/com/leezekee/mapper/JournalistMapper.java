package com.leezekee.mapper;

import com.leezekee.pojo.Journalist;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JournalistMapper {
    int addJournalist(Journalist journalist);

    void deleteJournalist(Integer id);

    void updateJournalist(Journalist journalist);

    Journalist findJournalistById(Integer id);

    Journalist findJournalistByUsername(String username);

    List<Journalist> findAllJournalist();
}
