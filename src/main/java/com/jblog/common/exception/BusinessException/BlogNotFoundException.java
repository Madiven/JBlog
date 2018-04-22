package com.jblog.common.exception.BusinessException;

public class BlogNotFoundException extends BusinessException {

    public BlogNotFoundException(){
        super();
    }

    public BlogNotFoundException(Exception e, String exception){
        super(e, exception);
    }

    public BlogNotFoundException(Exception e){
        super(e);
    }

}
