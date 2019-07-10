package com.revature.project02.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.revature.project02.TruckMasterRunner;

@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger logger = LogManager.getLogger(TruckMasterRunner.class.getName());
	
	@Before("  within(com.revature.project02.services.*) ||"
			+ "within(com.revature.project02.services.serviceImpls.*) ||"
			+ "within(com.revature.project02.controllers.*)")
	public void logServiceMethodCalls(JoinPoint jp) {
		
		logger.info("Method call - " + jp.getSignature());
	}
}
