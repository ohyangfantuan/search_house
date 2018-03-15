package com.oywy.entity;

public class SupportAddress {
  private Long id;
  private String belongTo;
  private String enName;
  private String cnName;
  private String level;
  private Double baiduMapLng;
  private Double baiduMapLat;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBelongTo() {
    return belongTo;
  }

  public void setBelongTo(String belongTo) {
    this.belongTo = belongTo;
  }

  public String getEnName() {
    return enName;
  }

  public void setEnName(String enName) {
    this.enName = enName;
  }

  public String getCnName() {
    return cnName;
  }

  public void setCnName(String cnName) {
    this.cnName = cnName;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public Double getBaiduMapLng() {
    return baiduMapLng;
  }

  public void setBaiduMapLng(Double baiduMapLng) {
    this.baiduMapLng = baiduMapLng;
  }

  public Double getBaiduMapLat() {
    return baiduMapLat;
  }

  public void setBaiduMapLat(Double baiduMapLat) {
    this.baiduMapLat = baiduMapLat;
  }
}
