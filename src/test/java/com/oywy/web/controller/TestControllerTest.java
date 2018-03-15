package com.oywy.web.controller;

import com.oywy.SearchhouseApplicationTests;
import com.oywy.entity.User;
import com.oywy.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by oywy on 2018/3/13.
 */
public class TestControllerTest extends SearchhouseApplicationTests{
    @Autowired
    private UserMapper userMapper;
    @Test
    public void test1() throws Exception {
        User user = userMapper.selectById(2);
        assertEquals("admin",user.getName());
    }

}