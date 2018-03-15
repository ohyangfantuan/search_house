package com.oywy.web.controller;

import com.oywy.core.common.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by oywy on 2018/3/16.
 */
@Controller
@RequestMapping("/house")
public class HouseController {
    @GetMapping("/support/cities")
    @ResponseBody
    public ApiResponse getSupportCities(){
        return ApiResponse.success();
    }

}
