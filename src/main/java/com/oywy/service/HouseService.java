package com.oywy.service;

import com.oywy.service.result.ServiceMultiResult;
import com.oywy.service.result.ServiceResult;
import com.oywy.web.dto.HouseDTO;
import com.oywy.web.form.DataTableSearch;
import com.oywy.web.form.HouseForm;
import com.oywy.web.form.RentSearch;

/**
 * Created by oywy on 2018/3/15.
 */
public interface HouseService {
    ServiceResult<HouseDTO> save(HouseForm houseForm);

    ServiceMultiResult<HouseDTO> adminQuery(DataTableSearch dataTableSearch);

    ServiceResult<HouseDTO> findCompleteOne(Long id);

//    ServiceResult<HouseDTO> update(HouseForm houseForm);

    ServiceMultiResult<HouseDTO> query(RentSearch rentSearch);
}
