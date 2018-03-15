package com.oywy.core.common;

/**
 * 接口DataTable返回数据
 * Created by oywy on 2018/3/15.
 */
public class ApiDataTableResponse extends ApiResponse{
    private  int draw;
    private long recordsTotal;
    private long recordsFiltered;
    public ApiDataTableResponse(int code, String message, Object data, boolean more) {
        super(code, message, data, more);
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
}
