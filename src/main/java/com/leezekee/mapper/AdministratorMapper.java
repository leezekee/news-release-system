package com.leezekee.mapper;

import com.leezekee.pojo.Administrator;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdministratorMapper {
    Administrator findAdministratorByUsername(String username);
}
