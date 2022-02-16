package com.iot.migration.configuration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    public StopWatch stopWatch = new StopWatch();

    @Around("@annotation(com.iot.migration.configuration.LogExecutionTime)")
    public Object methodTimeLogger(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        StopWatch stopWatch = new StopWatch(className + "--->" + methodName);
        stopWatch.start(className + "--->" + methodName);
        Object result = joinPoint.proceed();
        stopWatch.stop();

        if(logger.isDebugEnabled()) {
            logger.debug(methodSignature.getDeclaringType().getSimpleName() // Class Name
                    + "." + methodSignature.getName() + " " // Method Name
                    + ":: " + stopWatch.getTotalTimeMillis() + " ms");
            //logger.debug(stopWatch.prettyPrint());
        }

        return result;

    }

}
