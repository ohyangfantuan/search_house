package com.oywy.core.common;

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

    public static ApiResponse success() {
        return new ApiResponse(Status.SUCCESS.code, Status.SUCCESS.getStandardMessage(), null, false);
    }

    public static ApiResponse message(int code, String message) {
        return new ApiResponse(code, message, null, false);
    }

    public static ApiResponse status(Status status) {
        return new ApiResponse(status.getCode(), status.getStandardMessage(), null, false);
    }

    public enum Status {
        SUCCESS(200, "成功"),
        BAD_REQUEST(400, "请求无效"),
        INTERNAL_SERVER_ERROR(500, "服务器错误"),
        NOT_VALID_PARAM(40005, "参数无效"),
        NOT_SUPPORTED_OPERATION(40006, "操作不支持"),
        NOT_LOGIN(50000, "未登录");
        private int code;
        private String standardMessage;

        Status(int code, String standardMessage) {
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
