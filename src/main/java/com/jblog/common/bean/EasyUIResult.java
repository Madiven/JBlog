package com.jblog.common.bean;

import lombok.Data;

import java.util.List;

@Data
public class EasyUIResult<T> {

    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int NO_PERMISSION = 2;
    private String msg = "success";
    private int code = SUCCESS;
    private Integer total;
    private List<?> rows;
    private T data;

    public EasyUIResult() {
        super();
    }

    public EasyUIResult(T data) {
        super();
        this.data = data;
    }

    public EasyUIResult(Integer total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public EasyUIResult(Long total, List<T> rows) {
        this.total = total.intValue();
        this.rows = rows;
    }

    public EasyUIResult(Throwable e) {
        super();
        this.msg = e.getLocalizedMessage();
        this.code = FAIL;
    }

    public EasyUIResult(String msg, int code) {
        super();
        this.msg = msg;
        this.code = code;
    }
}
