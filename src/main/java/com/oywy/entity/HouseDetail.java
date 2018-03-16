package com.oywy.entity;

public class HouseDetail {
  private Long id;
  private String description;
  private String layoutDesc;
  private String traffic;
  private String roundService;
    private Integer rentWay;
  private String address;
  private Long subwayLineId;
  private String subwayLineName;
  private Long subwayStationId;
  private String subwayStationName;
  private Long houseId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLayoutDesc() {
    return layoutDesc;
  }

  public void setLayoutDesc(String layoutDesc) {
    this.layoutDesc = layoutDesc;
  }

  public String getTraffic() {
    return traffic;
  }

  public void setTraffic(String traffic) {
    this.traffic = traffic;
  }

  public String getRoundService() {
    return roundService;
  }

  public void setRoundService(String roundService) {
    this.roundService = roundService;
  }

    public Integer getRentWay() {
    return rentWay;
  }

    public void setRentWay(Integer rentWay) {
    this.rentWay = rentWay;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Long getSubwayLineId() {
    return subwayLineId;
  }

  public void setSubwayLineId(Long subwayLineId) {
    this.subwayLineId = subwayLineId;
  }

  public String getSubwayLineName() {
    return subwayLineName;
  }

  public void setSubwayLineName(String subwayLineName) {
    this.subwayLineName = subwayLineName;
  }

  public Long getSubwayStationId() {
    return subwayStationId;
  }

  public void setSubwayStationId(Long subwayStationId) {
    this.subwayStationId = subwayStationId;
  }

  public String getSubwayStationName() {
    return subwayStationName;
  }

  public void setSubwayStationName(String subwayStationName) {
    this.subwayStationName = subwayStationName;
  }

  public Long getHouseId() {
    return houseId;
  }

  public void setHouseId(Long houseId) {
    this.houseId = houseId;
  }
}
