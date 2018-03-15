package com.oywy.web.controller;

import cn.hutool.json.JSONUtil;
import com.oywy.core.common.ApiDataTableResponse;
import com.oywy.core.common.ApiResponse;
import com.oywy.core.common.QiniuResponse;
import com.oywy.core.enumeration.ResponseStatusEnum;
import com.oywy.service.QiniuService;
import com.oywy.web.form.DataTableSearch;
import com.qiniu.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 后台管理
 * Created by oywy on 2018/3/14.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Value("${PHOTO_UPLOAD_PATH}")
    private String PHOTO_UPLOAD_PATH;
    private final String PREFIX = "admin/";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private QiniuService qiniuService;

    /**
     * 欢迎页面
     *
     * @return
     */
    @GetMapping("/welcome")
    public String welcomePage() {
        return PREFIX + "welcome";
    }

    /**
     * 登陆页面
     *
     * @return
     */
    @GetMapping("/login")
    public String loginPage() {
        logger.error("hahahahahahahah");
        return PREFIX + "login";
    }

    /**
     * 中心页面
     *
     * @return
     */
    @GetMapping("/center")
    public String centerPage() {
        return PREFIX + "center";
    }

    /**
     * 房源管理页面
     *
     * @return
     */
    @GetMapping("/house/list")
    public String houseListPage() {
        return PREFIX + "house-list";
    }

    @PostMapping("admin/houses")
    @ResponseBody
    public ApiDataTableResponse housses(DataTableSearch dataTableSearch) {

        return null;
    }

    /**
     * 添加房源页面
     *
     * @return
     */
    @GetMapping("/add/house")
    public String addHousePage() {
        return PREFIX + "house-add";
    }

    /**
     * 上传照片接口
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPhoto(@RequestParam("file") MultipartFile file) {
        //判断是否为空
        if (file.isEmpty())
            return ApiResponse.status(ResponseStatusEnum.NOT_VALID_PARAM);
        String filename = file.getOriginalFilename();
        //上传到七牛云oss
        try {
            Response response = qiniuService.uploadFile(file.getInputStream());
            if (response.isOK()) {
                QiniuResponse qiniuResponse = JSONUtil.toBean(response.bodyString(), QiniuResponse.class);
                return ApiResponse.success(qiniuResponse);
            } else
                return ApiResponse.message(response.statusCode, response.getInfo());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ApiResponse.status(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
    }

}
