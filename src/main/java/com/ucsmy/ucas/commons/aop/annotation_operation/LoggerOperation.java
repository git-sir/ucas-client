package com.ucsmy.ucas.commons.aop.annotation_operation;

import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.ucas.commons.aop.annotation.Logger;
import com.ucsmy.ucas.config.log4j2.LogCommUtil;
import com.ucsmy.ucas.config.log4j2.LogOuputTarget;
import com.ucsmy.ucas.manage.entity.ManageLogInfo;
import com.ucsmy.ucas.manage.service.ManageLogInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggerOperation {

	private final org.apache.logging.log4j.Logger log = LogManager.getLogger();

	@Autowired
	private ManageLogInfoService manageLogInfoService;

	/**
	 * 拦截service层的@Logger
	 * @param pjp
	 * @param logger
	 * @return
	 * @throws Throwable
	 */
	@Around("execution (* com.ucsmy.ucas.manage.service..*.*(..)) && @annotation(logger)")
	public Object aroundService(ProceedingJoinPoint pjp, Logger logger) throws Throwable {
		String sessionId = LogCommUtil.getServiceSessionId();
		Signature signature = pjp.getSignature();
		// 日志信息
		StringBuilder logInfo = new StringBuilder();
		logInfo.append(LogCommUtil.LINE_SEPERATOR).append("【操作方法】").append(LogCommUtil.getMethodName(signature));
		logInfo.append(LogCommUtil.LINE_SEPERATOR).append("【操作名称】").append(logger.operationName());
		logInfo.append(LogCommUtil.LINE_SEPERATOR).append("【操作类型】").append(logger.operationType());
		logInfo.append(LogCommUtil.LINE_SEPERATOR).append("【IP信息】").append(LogCommUtil.getIpAddress());
		logInfo.append(LogCommUtil.LINE_SEPERATOR).append("【操作员信息】").append(LogCommUtil.getUserInfo());
		// 入参信息
		if (logger.printInput()) {
			logInfo.append(LogCommUtil.getInputParamList(signature, pjp.getArgs()));
		}
		ThreadContext.put(sessionId, logInfo.toString());
		// 是否输出SQL
		ThreadContext.put(sessionId + "SQL", String.valueOf(logger.printSQL()));
		Object retObj = pjp.proceed();
		// 输出数据
		StringBuilder info = new StringBuilder(ThreadContext.get(sessionId));
		if (logger.printOutput()) {
			info.append(LogCommUtil.getOutputParam(signature, retObj));
		}
		log.info(info.toString());
		LogCommUtil.removeThreadContext(sessionId);
		// 是否输出到数据库
		if (LogOuputTarget.DATABASE.equals(logger.outputTarget())) {
			ManageLogInfo manageLogInfo = LogCommUtil.getManageLogInfo(info.toString());
			manageLogInfoService.addManageLogInfo(manageLogInfo);
		}
        return retObj;
	}

	/**
	 * 拦截service层使用了@Logger的抛异常的方法，打印抛异常前的日志信息
	 */
	@Pointcut("execution (* com.ucsmy.ucas.manage.service..*.*(..)) && @annotation(com.ucsmy.ucas.commons.aop.annotation.Logger)")
	private void exceptionPointCut(){};

	@AfterThrowing(pointcut = "exceptionPointCut()")
	public void handlerServiceException(JoinPoint joinPoint) {
		String sessionId = LogCommUtil.getServiceSessionId();
		if (StringUtils.isNotEmpty(ThreadContext.get(sessionId))) {
			log.info(ThreadContext.get(sessionId));
			Signature signature = joinPoint.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method method = methodSignature.getMethod();
			if (method.isAnnotationPresent(Logger.class)) {
				Logger logger = method.getAnnotation(Logger.class);
				if (LogOuputTarget.DATABASE.equals(logger.outputTarget())) {
					ManageLogInfo manageLogInfo = LogCommUtil.getManageLogInfo(ThreadContext.get(sessionId));
					manageLogInfoService.addManageLogInfo(manageLogInfo);
				}
			}
			LogCommUtil.removeThreadContext(sessionId);
		}
	}

}
