package com.oywy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by oywy on 2018/3/14.
 */
@Controller
public class HomeController {
    @GetMapping({"/", "/index"})
    public String index(ModelMap map) {
        map.addAttribute("world", "世界");
        return "index";
    }

    @GetMapping("/logout/page")
    public String logoutPage() {
        return "logout";
    }
}
