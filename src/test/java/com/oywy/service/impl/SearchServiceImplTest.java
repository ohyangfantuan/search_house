package com.oywy.service.impl;

import com.oywy.SearchhouseApplicationTests;
import com.oywy.entity.House;
import com.oywy.mapper.HouseMapper;
import com.oywy.service.HouseService;
import com.oywy.service.SearchService;
import com.oywy.service.result.ServiceMultiResult;
import com.oywy.web.form.RentSearch;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by oywy on 2018/3/19.
 */
public class SearchServiceImplTest extends SearchhouseApplicationTests {
    @Autowired
    SearchService searchService;

    @Autowired
    HouseMapper houseMapper;
    /**
     * 测试,把所有记录插入es
     *
     * @throws Exception
     */
    @Test
    public void index() throws Exception {
        List<House> houses = houseMapper.selectList(null);
        houses.stream().map(house -> house.getId()).forEach(searchService::index);
    }

    @Test
    public void remove() throws Exception {
        searchService.remove(35L);
    }

    @Test
    public void query() throws Exception {
        RentSearch rentSearch = new RentSearch();
        rentSearch.setCityEnName("bj");
        //rentSearch.setRegionEnName("regionname");
        ServiceMultiResult<Long> query = searchService.query(rentSearch);
        System.out.println(query.getTotal());
        System.out.println(query.getResult());

    }

}