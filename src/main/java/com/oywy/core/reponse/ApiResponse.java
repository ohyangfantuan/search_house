package com.oywy.core.reponse;

import com.oywy.core.enumeration.ResponseStatusEnum;

/**
 * 接口返回数据
 * Created by oywy on 2018/3/14.
 */
public class ApiResponse {
    private int code;
    private String message;
    private Object data;
    private boolean more;

    public ApiResponse(int code, String message, Object data, boolean more) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.more = more;
    }

    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(ResponseStatusEnum status) {
        this(status.getCode(), status.getStandardMessage(), null, false);
    }

    public static ApiResponse success() {
        return new ApiResponse(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getStandardMessage(), null, false);
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getStandardMessage(), data, false);
    }

    public static ApiResponse message(int code, String message) {
        return new ApiResponse(code, message, null, false);
    }

    public static ApiResponse status(ResponseStatusEnum status) {
        return new ApiResponse(status.getCode(), status.getStandardMessage(), null, false);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }
}
