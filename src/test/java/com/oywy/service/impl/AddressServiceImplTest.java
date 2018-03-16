package com.oywy.service.impl;

import com.oywy.SearchhouseApplicationTests;
import com.oywy.service.AddressService;
import com.oywy.service.ServiceMultiResult;
import com.oywy.web.dto.SupportAddressDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by oywy on 2018/3/16.
 */
public class AddressServiceImplTest extends SearchhouseApplicationTests {
    @Autowired
    AddressService addressService;

    @Test
    public void findAllCities() throws Exception {
    }

    @Test
    public void findAllRegionsByCityName() throws Exception {
        String cityName = "bj";
        ServiceMultiResult<SupportAddressDTO> allRegionsByCityName = addressService.findAllRegionsByCityName(cityName);
        System.out.println(allRegionsByCityName.getResult());
    }

}