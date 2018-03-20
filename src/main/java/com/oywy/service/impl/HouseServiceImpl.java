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
import com.oywy.service.SearchService;
import com.oywy.service.result.ServiceMultiResult;
import com.oywy.service.result.ServiceResult;
import com.oywy.web.dto.HouseDTO;
import com.oywy.web.dto.HouseDetailDTO;
import com.oywy.web.dto.HousePictureDTO;
import com.oywy.web.form.DataTableSearch;
import com.oywy.web.form.HouseForm;
import com.oywy.web.form.RentSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private HouseSubscribeMapper houseSubscribeMapper;
    @Autowired
    private SubWayMapper subWayMapper;
    @Autowired
    private SubWayStationMapper subWayStationMapper;
    @Autowired
    private SearchService searchService;
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
        //分页条件
        int start = dataTableSearch.getStart();
        int length = dataTableSearch.getLength();
        String orderBy = dataTableSearch.getOrderBy();
        String direction = dataTableSearch.getDirection();

        Page<House> page = new Page<>();
        page.setCurrent(start / length + 1)
                .setSize(length)
                .setOrderByField(StrUtil.toUnderlineCase(orderBy))
                .setAsc("asc".equals(direction));
        //查询条件
        EntityWrapper<House> wrapper = new EntityWrapper<>();
        //城市
        wrapper.eq(StrUtil.isNotBlank(dataTableSearch.getCity()), "city_en_name", dataTableSearch.getCity());
        //状态
        wrapper.eq(ObjectUtil.isNotNull(dataTableSearch.getStatus()), "status", dataTableSearch.getStatus());
        //时间上限
        wrapper.le(ObjectUtil.isNotNull(dataTableSearch.getCreateTimeMax()), "create_time", dataTableSearch.getCreateTimeMax());
        //时间下限
        wrapper.ge(ObjectUtil.isNotNull(dataTableSearch.getCreateTimeMin()), "create_time", dataTableSearch.getCreateTimeMin());
        //标题
        wrapper.like(StrUtil.isNotBlank(dataTableSearch.getTitle()), "title", dataTableSearch.getTitle());
        //查询
        List<House> houses = houseMapper.selectPage(page, wrapper);
        //转换DTO
        List<HouseDTO> houseDTOs = houses.stream().map(house -> {
            HouseDTO houseDTO = new HouseDTO();
            BeanUtil.copyProperties(house, houseDTO);
            houseDTO.setCover(cdnPrefix + house.getCover());
            return houseDTO;
        }).collect(Collectors.toList());
        return new ServiceMultiResult<>(page.getTotal(), houseDTOs);
    }

    @Override
    public ServiceResult<HouseDTO> findCompleteOne(Long id) {
        //获取房源
        House house = houseMapper.selectById(id);
        //获取房源信息
        HouseDetail detail = houseDetailMapper.selectList(new EntityWrapper<HouseDetail>().eq("house_id", id)).get(0);
        //获取图片
        List<HousePicture> pictures = housePictureMapper.selectList(new EntityWrapper<HousePicture>().eq("house_id", id));
        //获取标签
        List<HouseTag> tags = HouseTagMapper.selectList(new EntityWrapper<HouseTag>().eq("house_id", id));
        //转成DTO
        HouseDTO houseDTO = new HouseDTO();
        BeanUtil.copyProperties(house, houseDTO);

        HouseDetailDTO houseDetailDTO = new HouseDetailDTO();
        BeanUtil.copyProperties(detail, houseDetailDTO);

        List<HousePictureDTO> housePictureDTOS = pictures.stream().map(picture -> {
            HousePictureDTO housePictureDTO = new HousePictureDTO();
            BeanUtil.copyProperties(picture, housePictureDTO);
            return housePictureDTO;
        }).collect(Collectors.toList());

        List<String> tagList = tags.stream().map(tag -> tag.getName()).collect(Collectors.toList());

        houseDTO.setHouseDetail(houseDetailDTO);
        houseDTO.setPictures(housePictureDTOS);
        houseDTO.setTags(tagList);

        if (SecurityUtil.currentUserId() > 0) { // 已登录用户
            List<HouseSubscribe> subscribies = houseSubscribeMapper.selectList(
                    new EntityWrapper<HouseSubscribe>()
                            .eq("user_id", SecurityUtil.currentUserId())
                            .eq("house_id", house.getId()));
            if (CollUtil.isNotEmpty(subscribies))
                houseDTO.setSubscribeStatus(subscribies.get(0).getStatus());
        }

        return ServiceResult.success(houseDTO);
    }

    @Override
    public ServiceMultiResult<HouseDTO> query(RentSearch rentSearch) {
        if (rentSearch.getKeywords() != null && !rentSearch.getKeywords().isEmpty()) {
            ServiceMultiResult<Long> serviceResult = searchService.query(rentSearch);
            if (serviceResult.getTotal() == 0) {
                return new ServiceMultiResult<>(0, new ArrayList<>());
            }

            return new ServiceMultiResult<>(serviceResult.getTotal(), wrapperHouseResult(serviceResult.getResult()));
        }

        return null;
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

    /**
     * 房源填充
     *
     * @param houseIds
     * @return
     */
    private List<HouseDTO> wrapperHouseResult(List<Long> houseIds) {
        List<HouseDTO> result = new ArrayList<>();

        Map<Long, HouseDTO> idToHouseMap = new HashMap<>();
        List<House> houses = houseMapper.selectBatchIds(houseIds);
        houses.forEach(house -> {
            HouseDTO houseDTO = new HouseDTO();
            BeanUtil.copyProperties(house, houseDTO);
            idToHouseMap.put(house.getId(), houseDTO);
        });

        wrapperHouseList(houseIds, idToHouseMap);

        // 矫正顺序
        for (Long houseId : houseIds) {
            result.add(idToHouseMap.get(houseId));
        }
        return result;
    }

    /**
     * 渲染详细信息 及 标签
     *
     * @param houseIds
     * @param idToHouseMap
     */
    private void wrapperHouseList(List<Long> houseIds, Map<Long, HouseDTO> idToHouseMap) {
        List<HouseDetail> details = houseDetailMapper.selectBatchIds(houseIds);
        details.forEach(houseDetail -> {
            HouseDTO houseDTO = idToHouseMap.get(houseDetail.getHouseId());
            HouseDetailDTO houseDetailDTO = new HouseDetailDTO();
            BeanUtil.copyProperties(houseDetail, houseDetailDTO);
            houseDTO.setHouseDetail(houseDetailDTO);
        });

        List<HouseTag> houseTags = HouseTagMapper.selectBatchIds(houseIds);
        houseTags.forEach(houseTag -> {
            HouseDTO house = idToHouseMap.get(houseTag.getHouseId());
            house.getTags().add(houseTag.getName());
        });
    }

}
