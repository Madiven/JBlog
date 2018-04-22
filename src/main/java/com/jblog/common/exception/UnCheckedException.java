package com.jblog.common.exception;

public class UnCheckedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code = 500;

    public UnCheckedException(String msg) {
        super(msg);
    }

    public UnCheckedException(String msg, Throwable e) {
        super(msg, e);
    }

    public UnCheckedException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public UnCheckedException(String msg, int code, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
