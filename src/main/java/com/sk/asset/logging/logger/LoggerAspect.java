package com.sk.asset.logging.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// AOP 설정
@Aspect
@Component
public class LoggerAspect {
	protected Log log = LogFactory.getLog(LoggerAspect.class);
	private static String name = "";

	// com.sk.asset.logging.controller와 com.sk.asset.logging.service.impl 패키지 하위의 메서드 실행 전후에 발생
	@Around("execution(* com.sk.asset.logging.controller.*.*(..)) || execution(* com.sk.asset.logging.service.impl.*.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {

		String type = joinPoint.getSignature().getDeclaringTypeName();

		if ( type.contains("Controller") ) {
			name = "Controller  \t:  ";
		}
		else if ( type.contains("Service") ) {
			name = "ServiceImpl  \t:  ";
		}

		log.debug(name + type + "." + joinPoint.getSignature().getName() + "()");

		return joinPoint.proceed();
	}
}
