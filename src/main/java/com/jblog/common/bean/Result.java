package com.jblog.common.bean;

import lombok.Data;

@Data
public class Result {

    public static final boolean SUCCESS = true;
    public static final boolean FAIL = false;
    private boolean status = SUCCESS;

    public Result(){
    }

    public Result(boolean status){
        this.status = status;
    }
}
