package com.cloudbees.test.ticket.ticket.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.cloudbees.test.ticket.ticket.controller..*(..)) || execution(* com.cloudbees.test.ticket.ticket.service..*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        if (joinPoint == null)
            return;
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* com.cloudbees.test.ticket.ticket.controller..*(..)) || execution(* com.cloudbees.test.ticket.ticket.service..*(..))", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        if (joinPoint == null)
            return;
        logger.info("Exiting method: {} with result: {}", joinPoint.getSignature().toShortString(), result);
    }
}
