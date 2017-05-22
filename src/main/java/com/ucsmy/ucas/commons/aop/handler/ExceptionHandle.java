package com.ucsmy.ucas.commons.aop.handler;

import com.ucsmy.ucas.commons.aop.exception.ServiceException;
import com.ucsmy.ucas.commons.aop.exception.result.AosResult;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ucs_xiaokailin on 2017/4/27.
 */
@ControllerAdvice
public class ExceptionHandle {

    private Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler
    @ResponseBody
    public AosResult handle(Exception e){
        if(e instanceof ServiceException){
            ServiceException serviceException = (ServiceException)e;
            log.info("业务异常",e);
            return AosResult.retFailureMsg(serviceException.getMessage());
        }else{
            log.info("系统异常",e);
            return AosResult.retFailureMsg("未知错误");
        }
    }
}
