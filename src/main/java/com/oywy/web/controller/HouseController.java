package com.oywy.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.oywy.core.common.ApiResponse;
import com.oywy.core.enumeration.ResponseStatusEnum;
import com.oywy.service.AddressService;
import com.oywy.service.ServiceMultiResult;
import com.oywy.web.dto.SubwayDTO;
import com.oywy.web.dto.SubwayStationDTO;
import com.oywy.web.dto.SupportAddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by oywy on 2018/3/16.
 */
@Controller
@RequestMapping("/address")
public class HouseController {
    @Autowired
    private AddressService addressService;

    /**
     * 获取全部城市
     *
     * @return
     */
    @GetMapping("/support/cities")
    @ResponseBody
    public ApiResponse getSupportCities() {
        ServiceMultiResult<SupportAddressDTO> multiResult = addressService.findAllCities();
        if (multiResult.getResultSize() == 0)
            return ApiResponse.status(ResponseStatusEnum.NOT_FOUND);
        return ApiResponse.success(multiResult.getResult());
    }

    /**
     * 根据城市获取地区
     *
     * @param cityName
     * @return
     */
    @GetMapping("support/regions")
    @ResponseBody
    public ApiResponse getRegionsByCityname(@RequestParam("city_name") String cityName) {
        ServiceMultiResult<SupportAddressDTO> multiResult = addressService.findAllRegionsByCityName(cityName);
        if (multiResult.getResultSize() == 0)
            return ApiResponse.status(ResponseStatusEnum.NOT_FOUND);
        return ApiResponse.success(multiResult.getResult());
    }

    /**
     * 根据城市获取地铁线路
     *
     * @param cityEnName
     * @return
     */
    @GetMapping("/support/subway/line")
    @ResponseBody
    public ApiResponse getSupportSubwayLine(@RequestParam(name = "city_name") String cityEnName) {
        List<SubwayDTO> subways = addressService.findAllSubwayByCity(cityEnName);
        if (CollUtil.isEmpty(subways))
            return ApiResponse.status(ResponseStatusEnum.NOT_FOUND);
        return ApiResponse.success(subways);
    }

    /**
     * 根据线路获取地铁站
     *
     * @param subwayId
     * @return
     */
    @GetMapping("/support/subway/station")
    @ResponseBody
    public ApiResponse getSupportSubwayStation(@RequestParam(name = "subway_id") Long subwayId) {
        List<SubwayStationDTO> stations = addressService.findAllStationBySubway(subwayId);
        if (CollUtil.isEmpty(stations))
            return ApiResponse.status(ResponseStatusEnum.NOT_FOUND);
        return ApiResponse.success(stations);
    }
}
