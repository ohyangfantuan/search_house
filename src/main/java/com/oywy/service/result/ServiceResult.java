package com.oywy.service.result;

/**
 * 单行记录返回
 * Created by oywy on 2018/3/16.
 */
public class ServiceResult<T> {
    private boolean success;
    private String message;
    private T result;

    public ServiceResult(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static <T> ServiceResult<T> success(T result) {
        ServiceResult<T> serviceResult = new ServiceResult<>(true);
        serviceResult.setResult(result);
        return serviceResult;
    }

    public static <T> ServiceResult<T> notFound() {
        return new ServiceResult<>(false, "资源不存在");
    }

    public ServiceResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
