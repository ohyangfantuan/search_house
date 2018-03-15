package com.oywy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by oywy on 2018/3/14.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final String PREFIX = "user/";

    @GetMapping("/login")
    public String loginPage() {
        return PREFIX + "login";
    }

    @GetMapping("/center")
    public String centerPage() {
        return PREFIX + "center";
    }
}
