package com.oywy.service;

import com.oywy.service.result.ServiceMultiResult;
import com.oywy.web.form.RentSearch;

/**
 * ES服务
 * Created by oywy on 2018/3/18.
 */
public interface SearchService {
    /**
     * 添加房源
     *
     * @param houseId
     */
    void index(Long houseId);

    /**
     * 删除房源
     * @param houseId
     */
    void remove(Long houseId);

    /**
     * 查询房源
     *
     * @param rentSearch
     * @return
     */
    ServiceMultiResult<Long> query(RentSearch rentSearch);
}
