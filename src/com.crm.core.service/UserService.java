package com.crm.core.service;

import com.crm.core.po.User;

public interface UserService {
    public User findUser(String usercode,String password);
}
