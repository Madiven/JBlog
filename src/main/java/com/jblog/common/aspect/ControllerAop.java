package com.jblog.common.aspect;

import com.jblog.common.bean.EasyUIResult;
import com.jblog.common.bean.Result;
import com.jblog.common.exception.BusinessException.BlogNotFoundException;
import com.jblog.common.exception.BusinessException.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAop.class);

    public Object handlerControllerMethod(ProceedingJoinPoint proceedingJoinPoint) {
        long startTime = System.currentTimeMillis();

        Result result;
        try {
            result = (Result) proceedingJoinPoint.proceed();
            LOGGER.info(proceedingJoinPoint.getSignature() + "use time" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            result = handlerException(proceedingJoinPoint, e);
        }
        return result;
    }

    private Result handlerException(ProceedingJoinPoint proceedingJoinPoint, Throwable e) {
        LOGGER.error(e.getLocalizedMessage());

        return new Result(false);
    }
}
