package com.oywy.service;

import com.oywy.core.enumeration.CityLevelEnum;
import com.oywy.service.result.ServiceMultiResult;
import com.oywy.service.result.ServiceResult;
import com.oywy.web.dto.SubwayDTO;
import com.oywy.web.dto.SubwayStationDTO;
import com.oywy.web.dto.SupportAddressDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by oywy on 2018/3/16.
 */
public interface AddressService {
    ServiceMultiResult<SupportAddressDTO> findAllCities();

    ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityName);

    Map<CityLevelEnum, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName);

    List<SubwayDTO> findAllSubwayByCity(String cityEnName);

    List<SubwayStationDTO> findAllStationBySubway(Long subwayId);

    ServiceResult<SubwayDTO> findSubway(Long subwayId);

    ServiceResult<SubwayStationDTO> findSubwayStation(Long stationId);
}
