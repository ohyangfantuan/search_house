package com.oywy.entity;

public class HousePicture {
  private Long id;
  private Long houseId;
  private String cdnPrefix;
  private Long width;
  private Long height;
  private String location;
  private String path;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getHouseId() {
    return houseId;
  }

  public void setHouseId(Long houseId) {
    this.houseId = houseId;
  }

  public String getCdnPrefix() {
    return cdnPrefix;
  }

  public void setCdnPrefix(String cdnPrefix) {
    this.cdnPrefix = cdnPrefix;
  }

  public Long getWidth() {
    return width;
  }

  public void setWidth(Long width) {
    this.width = width;
  }

  public Long getHeight() {
    return height;
  }

  public void setHeight(Long height) {
    this.height = height;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
