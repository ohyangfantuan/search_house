package com.oywy.service;

import com.oywy.entity.User;

/**
 * Created by oywy on 2018/3/14.
 */
public interface UserService {
    User findByName(String userName);
}
