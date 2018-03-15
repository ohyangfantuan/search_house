package com.oywy.entity;

import java.sql.Timestamp;

public class House {
    private Long id;
    private String title;
    private Long price;
    private Long area;
    private Long room;
    private Long floor;
    private Long totalFloor;
    private Long watchTimes;
    private Long buildYear;
    private Long status;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp lastUpdateTime;
    private String cityEnName;
    private String regionEnName;
    private String cover;
    private Long direction;
    private Long distanceToSubway;
    private Long parlour;
    private String district;
    private Long adminId;
    private Long bathroom;
    private String street;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getRoom() {
        return room;
    }

    public void setRoom(Long room) {
        this.room = room;
    }

    public Long getFloor() {
        return floor;
    }

    public void setFloor(Long floor) {
        this.floor = floor;
    }

    public Long getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Long totalFloor) {
        this.totalFloor = totalFloor;
    }

    public Long getWatchTimes() {
        return watchTimes;
    }

    public void setWatchTimes(Long watchTimes) {
        this.watchTimes = watchTimes;
    }

    public Long getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(Long buildYear) {
        this.buildYear = buildYear;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCityEnName() {
        return cityEnName;
    }

    public void setCityEnName(String cityEnName) {
        this.cityEnName = cityEnName;
    }

    public String getRegionEnName() {
        return regionEnName;
    }

    public void setRegionEnName(String regionEnName) {
        this.regionEnName = regionEnName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getDirection() {
        return direction;
    }

    public void setDirection(Long direction) {
        this.direction = direction;
    }

    public Long getDistanceToSubway() {
        return distanceToSubway;
    }

    public void setDistanceToSubway(Long distanceToSubway) {
        this.distanceToSubway = distanceToSubway;
    }

    public Long getParlour() {
        return parlour;
    }

    public void setParlour(Long parlour) {
        this.parlour = parlour;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getBathroom() {
        return bathroom;
    }

    public void setBathroom(Long bathroom) {
        this.bathroom = bathroom;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
