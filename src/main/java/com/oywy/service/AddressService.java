package com.oywy.service;

import com.oywy.entity.SupportAddress;

import java.util.List;

/**
 * Created by oywy on 2018/3/16.
 */
public interface AddressService {
    List<SupportAddress> findAllCities();
}
