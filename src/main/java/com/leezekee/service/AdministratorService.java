package com.leezekee.service;

import com.leezekee.pojo.Administrator;

public interface AdministratorService {
    Administrator findAdministratorByUsername(String username);
}
