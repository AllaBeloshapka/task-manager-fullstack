package com.example.taskmanagerapi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect // Marks this class as an Aspect (AOP component)
@Component // Registers this class as a Spring Bean
public class LoggingAspect {

    // This advice runs BEFORE execution of any method
    // in the service package
    @Before("execution(* com.example.taskmanagerapi.service.*.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {

        // Get the name of the called method
        String methodName = joinPoint.getSignature().getName();

        // Log method execution to console
        System.out.println("👉 Method called: " + methodName);
    }
}