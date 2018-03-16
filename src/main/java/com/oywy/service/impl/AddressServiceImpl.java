package com.oywy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.oywy.core.enumeration.CityLevelEnum;
import com.oywy.entity.Subway;
import com.oywy.entity.SubwayStation;
import com.oywy.entity.SupportAddress;
import com.oywy.mapper.SubWayMapper;
import com.oywy.mapper.SubWayStationMapper;
import com.oywy.mapper.SupportAddressMapper;
import com.oywy.service.AddressService;
import com.oywy.service.ServiceMultiResult;
import com.oywy.web.dto.SubwayDTO;
import com.oywy.web.dto.SubwayStationDTO;
import com.oywy.web.dto.SupportAddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by oywy on 2018/3/16.
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private SupportAddressMapper supportAddressMapper;
    @Autowired
    private SubWayMapper subWayMapper;
    @Autowired
    private SubWayStationMapper subWayStationMapper;

    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllCities() {
        //查询所有supportAddress
        List<SupportAddress> addresses = supportAddressMapper.selectList(
                new EntityWrapper<SupportAddress>()
                        .eq("level", CityLevelEnum.CITY));
        //转换成DTO
        List<SupportAddressDTO> supportAddressDTOS = addresses.stream().map(address -> {
            SupportAddressDTO supportAddressDTO = new SupportAddressDTO();
            BeanUtil.copyProperties(address, supportAddressDTO);
            return supportAddressDTO;
        }).collect(Collectors.toList());
        //用servlceMultiResult包装
        return new ServiceMultiResult<SupportAddressDTO>(supportAddressDTOS.size(), supportAddressDTOS);
    }

    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityName) {
        //判断cityName非空
        if (cityName == null) {
            return new ServiceMultiResult<>(0, null);
        }
        //根据cityName查询
        List<SupportAddress> addresses = supportAddressMapper.selectList(
                new EntityWrapper<SupportAddress>()
                        .eq("level", CityLevelEnum.REGION)
                        .and()
                        .eq("belong_to", cityName));
        //转换成DTO
        List<SupportAddressDTO> supportAddressDTOS = addresses.stream().map(address -> {
            SupportAddressDTO supportAddressDTO = new SupportAddressDTO();
            BeanUtil.copyProperties(address, supportAddressDTO);
            return supportAddressDTO;
        }).collect(Collectors.toList());
        //用servlceMultiResult包装
        return new ServiceMultiResult<SupportAddressDTO>(supportAddressDTOS.size(), supportAddressDTOS);
    }

    @Override
    public Map<CityLevelEnum, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName) {
        //根据enName查询城市
        SupportAddress city = supportAddressMapper.selectList(new EntityWrapper<SupportAddress>()
                .eq("level", CityLevelEnum.CITY)
                .and()
                .eq("en_name", cityEnName)).get(0);
        //根据belongTo和enName查询地区
        SupportAddress region = supportAddressMapper.selectList(new EntityWrapper<SupportAddress>()
                .eq("en_name", regionEnName)
                .and()
                .eq("belong_to", city.getEnName())).get(0);
        //转换成DTO
        SupportAddressDTO cityDTO = new SupportAddressDTO();
        BeanUtil.copyProperties(city, cityDTO);
        SupportAddressDTO regionDTO = new SupportAddressDTO();
        BeanUtil.copyProperties(region, regionDTO);
        Map<CityLevelEnum, SupportAddressDTO> result = new HashMap<>();
        result.put(CityLevelEnum.CITY, cityDTO);
        result.put(CityLevelEnum.REGION, regionDTO);
        return result;
    }

    @Override
    public List<SubwayDTO> findAllSubwayByCity(String cityEnName) {
        //根据cityEnName查询
        List<Subway> subways = subWayMapper.selectList(
                new EntityWrapper<Subway>()
                        .eq("city_en_name", cityEnName));
        //转换成DTO
        List<SubwayDTO> result = subways.stream().map(subway -> {
            SubwayDTO subwayDTO = new SubwayDTO();
            BeanUtil.copyProperties(subway, subwayDTO);
            return subwayDTO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<SubwayStationDTO> findAllStationBySubway(Long subwayId) {
        //根据subwayId查询
        List<SubwayStation> stations = subWayStationMapper.selectList(
                new EntityWrapper<SubwayStation>()
                        .eq("subway_id", subwayId));
        //转换成DTO
        List<SubwayStationDTO> result = stations.stream().map(station -> {
            SubwayStationDTO subwayStationDTO = new SubwayStationDTO();
            BeanUtil.copyProperties(station, subwayStationDTO);
            return subwayStationDTO;
        }).collect(Collectors.toList());
        return result;
    }
}
