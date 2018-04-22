package com.jblog.common.aspect;

import com.jblog.common.bean.EasyUIResult;
import com.jblog.common.exception.BusinessException.BlogNotFoundException;
import com.jblog.common.exception.BusinessException.BusinessException;
import com.jblog.common.exception.UnCheckedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManagerControllerAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerControllerAop.class);

    public Object handlerControllerMethod(ProceedingJoinPoint proceedingJoinPoint) {
        long startTime = System.currentTimeMillis();

        EasyUIResult result;
        try {
            result = (EasyUIResult) proceedingJoinPoint.proceed();
            LOGGER.info(proceedingJoinPoint.getSignature() + "use time" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            result = handlerException(proceedingJoinPoint, e);
        }
        return result;
    }

    private EasyUIResult handlerException(ProceedingJoinPoint proceedingJoinPoint, Throwable e) {
        EasyUIResult result = new EasyUIResult();

        if(e instanceof BusinessException) {
            if(e instanceof BlogNotFoundException) {
                setErrorMsgAndLog(result, "该博客已经被删除！");
            } else {
                setErrorMsgAndLog(result, e.getLocalizedMessage());
            }
        } else {
            result.setMsg(e.getLocalizedMessage());
            result.setCode(EasyUIResult.FAIL);
        }
        return result;
    }

    private void setErrorMsgAndLog(EasyUIResult result, String errorMsg) {
        LOGGER.error(errorMsg);
        result.setMsg(errorMsg);
        result.setCode(EasyUIResult.FAIL);
    }
}