package com.tprm.spi.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ThirdpartyServiceLoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThirdpartyServiceLoggingAspect.class);

    @Before("execution(* com.tprm.spi.service.ThirdPartyService.*(..))")
    public void logBeforeServiceMethodCall(JoinPoint joinPoint) {
        LOGGER.info("Calling method:{}.{} with arguments: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.tprm.spi.service.ThirdPartyService.*(..))", returning = "result")
    public void logAfterServiceMethodReturn(JoinPoint joinPoint, Object result) {
        LOGGER.info("Method {}.{}, returned: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                result);
    }
}
