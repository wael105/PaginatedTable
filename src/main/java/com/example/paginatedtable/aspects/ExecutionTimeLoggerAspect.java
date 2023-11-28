package com.example.paginatedtable.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeLoggerAspect {

    @Around("execution(* com.example.paginatedtable.services.StudentsService.*(..)) ||" +
            "execution(* com.example.paginatedtable.services.StudentConverter.*(..)) ||" +
            "execution(* com.example.paginatedtable.services.StudentTsvWriter.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        log.info(
                "{} executed in {} ms",
                joinPoint.getSignature().toShortString(),
                executionTime
        );

        return result;
    }
}