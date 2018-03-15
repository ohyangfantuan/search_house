package com.oywy.core.enumeration;

/**
 * 城市行政级别定义
 * Created by oywy on 2018/3/15.
 */
public enum CityLevelEnum {
    CITY("city"), REGION("region");
    private String value;

    CityLevelEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CityLevelEnum LevelOf(String value) {
        for (CityLevelEnum level : CityLevelEnum.values())
            if (level.getValue().equals(value))
                return level;
            else
                throw new IllegalArgumentException("城市行政级别不存在:" + value);
        return null;
    }
}
