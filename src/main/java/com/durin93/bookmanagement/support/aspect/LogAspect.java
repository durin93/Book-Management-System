package com.durin93.bookmanagement.support.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.durin93.bookmanagement.web.*.*(..))")
    public void web(){}

    @Pointcut("execution(* com.durin93.bookmanagement.service.*.*(..))")
    public void service(){}


    @AfterReturning(pointcut="web() || service())" , returning="ret")
    public void logExecutionTime(JoinPoint joinPoint,  Object ret){
        logger.info(joinPoint.toString() + " success method");
        logger.info(ret.toString() + " return object");

    }

}
