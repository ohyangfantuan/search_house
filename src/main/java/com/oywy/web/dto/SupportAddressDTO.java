package com.oywy.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by oywy on 2018/3/16.
 */
public class SupportAddressDTO {
    private Long id;
    @JsonProperty("belong_to")
    private String belongTo;
    @JsonProperty("en_name")
    private String enName;
    @JsonProperty("cn_name")
    private String cnName;
    private String level;

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
}
