package com.oywy.service;

import com.oywy.web.dto.HouseDTO;
import com.oywy.web.form.DataTableSearch;
import com.oywy.web.form.HouseForm;

/**
 * Created by oywy on 2018/3/15.
 */
public interface HouseService {
    ServiceResult<HouseDTO> save(HouseForm houseForm);

    ServiceMultiResult<HouseDTO> adminQuery(DataTableSearch dataTableSearch);
}
