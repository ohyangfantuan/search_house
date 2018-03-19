package com.oywy.service.result;

import cn.hutool.core.util.ObjectUtil;

import java.util.List;

/**
 * 多行结果返回
 *
 * @param <T>
 */
public class ServiceMultiResult<T> {
    private long total;
    private List<T> result;

    public ServiceMultiResult(long total, List<T> result) {
        this.total = total;
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getResultSize() {
        return ObjectUtil.length(result);
    }
}
