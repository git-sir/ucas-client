package com.ucsmy.ucas.commons.aop.annotation_operation;

import com.ucsmy.ucas.commons.aop.annotation.SignValid;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SignValidOperation {

	@Around("execution (* com.ucsmy.ucas.manage.service.*.*(..)) && @annotation(signValid) && args(key)")
	public void aroundLogger(ProceedingJoinPoint pjp, SignValid signValid, String key) throws Throwable {
		System.err.println("进入signValid注解处理方法...");
		System.out.println("pjp = "+pjp);
		System.out.println("key = "+key);
		System.out.println(signValid.desc());
		Object retObj = pjp.proceed();
		System.out.println(retObj);
		System.err.println("结束signValid注解处理方法...");

        long start = System.currentTimeMillis();

	}

}
