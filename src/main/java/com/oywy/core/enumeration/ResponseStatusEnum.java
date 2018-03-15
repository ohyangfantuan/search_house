package com.oywy.core.enumeration;

/**
 * Created by oywy on 2018/3/15.
 */
public enum ResponseStatusEnum {
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求无效"),
    INTERNAL_SERVER_ERROR(500, "服务器错误"),
    NOT_VALID_PARAM(40005, "参数无效"),
    NOT_SUPPORTED_OPERATION(40006, "操作不支持"),
    NOT_LOGIN(50000, "未登录");
    private int code;
    private String standardMessage;

    ResponseStatusEnum(int code, String standardMessage) {
        this.code = code;
        this.standardMessage = standardMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStandardMessage() {
        return standardMessage;
    }

    public void setStandardMessage(String standardMessage) {
        this.standardMessage = standardMessage;
    }
}