package com.oywy.core.enumeration;

/**
 * 房源状态
 * Created by oywy on 2018/3/17.
 */
public enum HouseStatusEnum {
    NOT_AUDITED(0),//未审核
    passes(1),//审核通过
    RENTED(2),//已出租
    deleted(3);//逻辑删除

    private int value;

    HouseStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
