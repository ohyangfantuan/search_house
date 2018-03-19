package com.oywy.service;

/**
 * ES服务
 * Created by oywy on 2018/3/18.
 */
public interface SearchService {
    void index(Long houseId);

    void remove(Long houseId);
}
