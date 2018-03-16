package com.oywy.entity;

public class HouseTag {
  private Long houseId;
  private Long id;
  private String name;

    public HouseTag(Long houseId, String name) {
        this.houseId = houseId;
        this.name = name;
    }

  public Long getHouseId() {
    return houseId;
  }

  public void setHouseId(Long houseId) {
    this.houseId = houseId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
