package com.oywy.web.controller;

import com.oywy.core.common.ApiDataTableResponse;
import com.oywy.core.common.ApiResponse;
import com.oywy.web.form.DataTableSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 后台管理
 * Created by oywy on 2018/3/14.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Value("${PHOTO_UPLOAD_PATH}")
    private final String PHOTO_UPLOAD_PATH = null;
    private final String PREFIX = "admin/";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 欢迎页面
     * @return
     */
    @GetMapping("/welcome")
    public String welcomePage() {
        return PREFIX + "welcome";
    }

    /**
     * 登陆页面
     * @return
     */
    @GetMapping("/login")
    public String loginPage() {
        logger.error("hahahahahahahah");
        return PREFIX + "login";
    }

    /**
     * 中心页面
     * @return
     */
    @GetMapping("/center")
    public String centerPage() {
        return PREFIX + "center";
    }

    /**
     * 房源管理页面
     * @return
     */
    @GetMapping("/house/list")
    public String houseListPage() {
        return PREFIX + "house-list";
    }
    @PostMapping("admin/houses")
    @ResponseBody
    public ApiDataTableResponse housses(DataTableSearch dataTableSearch){

        return null;
    }
    /**
     * 添加房源页面
     * @return
     */
    @GetMapping("/add/house")
    public String addHousePage() {
        return PREFIX + "house-add";
    }

    /**
     * 上传照片接口
     * @param file
     * @return
     */
    @PostMapping(value = "/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPhoto(@RequestParam("file") MultipartFile file) {
        //判断是否为空
        if (file.isEmpty())
            return ApiResponse.status(ApiResponse.Status.NOT_VALID_PARAM);
        String filename = file.getOriginalFilename();
        try {
            file.transferTo(new File(PHOTO_UPLOAD_PATH + filename));
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.status(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return ApiResponse.success();
    }

}
