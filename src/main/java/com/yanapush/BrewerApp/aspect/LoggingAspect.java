package com.yanapush.BrewerApp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.yanapush.BrewerApp.*.*.*(..))")
    public Object aroundAllServiceMethodsAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        logger.info("starting method from service package");
        logger.info("begin of " + methodName);
        logger.info("begin of " + signature);
        logger.info("with arguments " + Arrays.toString(joinPoint.getArgs()));
        Object targetMethodResult = joinPoint.proceed();
        logger.info("end of " + methodName);
        logger.info("with result " + targetMethodResult);
        logger.info("ending method from service package");
        return targetMethodResult;
    }

    @AfterThrowing(pointcut = "execution(* com.yanapush.BrewerApp.*.*.*(..))", throwing = "exception")
    public void afterThrowingInAnyMethod(Exception exception) throws Throwable {
        logger.error("EXCEPTION WAS THROWN IN METHOD ");
        logger.error("Here it is " + exception);
    }
}
