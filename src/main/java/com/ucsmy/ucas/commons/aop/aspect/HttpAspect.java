package com.ucsmy.ucas.commons.aop.aspect;

import com.ucsmy.commons.utils.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ucs_xiaokailin on 2017/4/27.
 */
@Aspect
@Component
public class HttpAspect {

    private Logger log = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.ucsmy.ucas.manage.web.*.*(..))")
    public void log(){
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.debug("doBefore");
        //url
        log.debug("url={}",request.getRequestURL());
        //method
        log.debug("method={}",request.getMethod());
        //ip
        log.debug("ip={}",request.getRemoteAddr());
        //类方法
        log.debug("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        //类方法参数
        log.debug("args={}",joinPoint.getArgs());
    }

    @After("log()")
    public void doAfter(){
        log.debug("doAfter");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturing(Object object) throws Exception{
        log.debug("doAfterReturing");
        log.info("response={}", JsonUtils.formatObjectToJson(object));
    }
}
