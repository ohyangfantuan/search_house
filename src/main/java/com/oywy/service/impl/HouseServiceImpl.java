package com.oywy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.oywy.core.security.SecurityUtil;
import com.oywy.entity.*;
import com.oywy.mapper.*;
import com.oywy.service.HouseService;
import com.oywy.service.ServiceMultiResult;
import com.oywy.service.ServiceResult;
import com.oywy.web.dto.HouseDTO;
import com.oywy.web.dto.HouseDetailDTO;
import com.oywy.web.dto.HousePictureDTO;
import com.oywy.web.form.DataTableSearch;
import com.oywy.web.form.HouseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by oywy on 2018/3/15.
 */
@Service
@Transactional
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private HouseDetailMapper houseDetailMapper;
    @Autowired
    private HousePictureMapper housePictureMapper;
    @Autowired
    private HouseTagMapper HouseTagMapper;
    @Autowired
    private SubWayMapper subWayMapper;
    @Autowired
    private SubWayStationMapper subWayStationMapper;
    @Value("${qiniu.cdn.prefix}")
    private String cdnPrefix;

    @Override
    public ServiceResult<HouseDTO> save(HouseForm houseForm) {
        //1.house
        //转换成po
        House house = new House();
        BeanUtil.copyProperties(houseForm, house);
        //补全属性
        Date date = new Date();
        house.setCreateTime(date);
        house.setLastUpdateTime(date);
        house.setAdminId(SecurityUtil.currentUserId());
        //保存
        houseMapper.insert(house);
        //2.houseDetail
        //补全属性
        HouseDetail detail = new HouseDetail();
        ServiceResult<HouseDTO> subwayValidtionResult = wrapperDetailInfo(detail, houseForm);
        if (ObjectUtil.isNotNull(subwayValidtionResult))
            return subwayValidtionResult;
        //保存
        detail.setHouseId(house.getId());
        houseDetailMapper.insert(detail);

        //3.图片
        //补全属性
        List<HousePicture> pictures = generatePictures(houseForm, house.getId());
        //保存
        pictures.forEach(picture -> housePictureMapper.insert(picture));

        //4.tag
        //补全属性
        List<HouseTag> houseTags = houseForm.getTags().stream().map(tag -> new HouseTag(house.getId(), tag)).collect(Collectors.toList());
        //保存
        houseTags.forEach(tag -> HouseTagMapper.insert(tag));


        //转换成DTO
        HouseDTO houseDTO = new HouseDTO();
        BeanUtil.copyProperties(house, houseDTO);

        HouseDetailDTO houseDetailDTO = new HouseDetailDTO();
        BeanUtil.copyProperties(detail, houseDetailDTO);
        houseDTO.setHouseDetail(houseDetailDTO);

        List<HousePictureDTO> pictureDTOs = pictures.stream().map(picture -> {
            HousePictureDTO housePictureDTO = new HousePictureDTO();
            BeanUtil.copyProperties(picture, housePictureDTO);
            return housePictureDTO;
        }).collect(Collectors.toList());
        houseDTO.setPictures(pictureDTOs);

        houseDTO.setCover(this.cdnPrefix + houseDTO.getCover());

        houseDTO.setTags(houseForm.getTags());

        return new ServiceResult<HouseDTO>(true, null, houseDTO);
    }

    @Override
    public ServiceMultiResult<HouseDTO> adminQuery(DataTableSearch dataTableSearch) {
        //查询
        int start = dataTableSearch.getStart();
        int length = dataTableSearch.getLength();
        String orderBy = dataTableSearch.getOrderBy();
        String direction = dataTableSearch.getDirection();

        Page<House> page = new Page<>();
        page.setCurrent(start / length + 1)
                .setSize(length)
                .setOrderByField(StrUtil.toUnderlineCase(orderBy))
                .setAsc("asc".equals(direction));
        List<House> houses = houseMapper.selectPage(page, new EntityWrapper<House>());
        //转换DTO
        List<HouseDTO> houseDTOs = houses.stream().map(house -> {
            HouseDTO houseDTO = new HouseDTO();
            BeanUtil.copyProperties(house, houseDTO);
            houseDTO.setCover(cdnPrefix + house.getCover());
            return houseDTO;
        }).collect(Collectors.toList());
        return new ServiceMultiResult<>(page.getTotal(), houseDTOs);
    }

    /**
     * 房源信息属性填充
     *
     * @param houseDetail
     * @param houseForm
     * @return
     */
    private ServiceResult<HouseDTO> wrapperDetailInfo(HouseDetail houseDetail, HouseForm houseForm) {
        //校验subway
        Subway subway = subWayMapper.selectById(houseForm.getSubwayLineId());
        if (subway == null)
            return new ServiceResult<>(false, "Not valid subway line!");
        //校验subwayStation
        SubwayStation subwayStation = subWayStationMapper.selectById(houseForm.getSubwayStationId());
        if (subwayStation == null || subway.getId() != subwayStation.getSubwayId())
            return new ServiceResult<>(false, "Not valid subway station!");

        houseDetail.setSubwayLineId(subway.getId());
        houseDetail.setSubwayLineName(subway.getName());

        houseDetail.setSubwayStationId(subwayStation.getId());
        houseDetail.setSubwayStationName(subwayStation.getName());

        houseDetail.setDescription(houseForm.getDescription());
        houseDetail.setAddress(houseForm.getDetailAddress());
        houseDetail.setLayoutDesc(houseForm.getLayoutDesc());
        houseDetail.setRentWay(houseForm.getRentWay());
        houseDetail.setRoundService(houseForm.getRoundService());
        houseDetail.setTraffic(houseForm.getTraffic());
        return null;

    }

    /**
     * 图片属性填充
     *
     * @param form
     * @param houseId
     * @return
     */
    private List<HousePicture> generatePictures(HouseForm form, Long houseId) {
        if (CollUtil.isEmpty(form.getPhotos()))
            return Collections.EMPTY_LIST;
        List<HousePicture> pictures = form.getPhotos().stream().map(photoForm -> {
            HousePicture picture = new HousePicture();
            picture.setHouseId(houseId);
            picture.setCdnPrefix(cdnPrefix);
            picture.setPath(photoForm.getPath());
            picture.setWidth(photoForm.getWidth());
            picture.setHeight(photoForm.getHeight());
            return picture;
        }).collect(Collectors.toList());
        return pictures;
    }
}
