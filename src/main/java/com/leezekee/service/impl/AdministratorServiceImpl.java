package com.leezekee.service.impl;

import com.leezekee.mapper.AdministratorMapper;
import com.leezekee.pojo.Administrator;
import com.leezekee.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    AdministratorMapper administratorMapper;

    @Override
    public Administrator findAdministratorByUsername(String username) {
        return administratorMapper.findAdministratorByUsername(username);
    }
}
