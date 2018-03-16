package com.oywy.web.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.oywy.core.common.ApiDataTableResponse;
import com.oywy.core.common.ApiResponse;
import com.oywy.core.common.QiniuResponse;
import com.oywy.core.enumeration.CityLevelEnum;
import com.oywy.core.enumeration.ResponseStatusEnum;
import com.oywy.service.*;
import com.oywy.web.dto.HouseDTO;
import com.oywy.web.dto.SupportAddressDTO;
import com.oywy.web.form.DataTableSearch;
import com.oywy.web.form.HouseForm;
import com.qiniu.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

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
    @Autowired
    private AddressService addressService;
    @Autowired
    private HouseService houseService;

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

    /**
     * 展示房源列表
     *
     * @param dataTableSearch
     * @return
     */
    @PostMapping("/houses")
    @ResponseBody
    public ApiDataTableResponse housses(DataTableSearch dataTableSearch) {
        ServiceMultiResult<HouseDTO> result = houseService.adminQuery(dataTableSearch);
        ApiDataTableResponse response = new ApiDataTableResponse(ResponseStatusEnum.SUCCESS);
        response.setData(result.getResult());
        response.setRecordsFiltered(result.getTotal());
        response.setRecordsTotal(result.getTotal());
        response.setDraw(dataTableSearch.getDraw());
        return response;
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
     * 添加房源
     *
     * @param houseForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/add/house")
    @ResponseBody
    public ApiResponse addHouse(@Valid @ModelAttribute("form-house-add") HouseForm houseForm, BindingResult bindingResult) {
        //表单验证
        if (bindingResult.hasErrors())
            return ApiResponse.message(HttpStatus.BAD_REQUEST.value(), bindingResult.getAllErrors().get(0).getDefaultMessage());
        //图片验证
        if (CollUtil.isEmpty(houseForm.getPhotos()) || ObjectUtil.isNull(houseForm.getCover()))
            return ApiResponse.message(HttpStatus.BAD_REQUEST.value(), "必须上传图片");
        //城市和地区验证
        Map<CityLevelEnum, SupportAddressDTO> cityAndRegion = addressService.findCityAndRegion(houseForm.getCityEnName(), houseForm.getRegionEnName());
        if (cityAndRegion.size() < 2)
            return ApiResponse.status(ResponseStatusEnum.NOT_VALID_PARAM);
        ServiceResult<HouseDTO> result = houseService.save(houseForm);
        //添加成功
        if (result.isSuccess())
            return ApiResponse.success(result.getResult());
        //添加失败
        return ApiResponse.status(ResponseStatusEnum.NOT_VALID_PARAM);
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
