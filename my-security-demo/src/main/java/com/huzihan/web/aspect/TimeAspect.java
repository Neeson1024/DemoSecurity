package com.huzihan.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class TimeAspect {

    @Around("execution(* com.huzihan.web.Controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint point) throws Throwable {
        System.out.println("time aspect start");

        long start = new Date().getTime();

        Object obj = point.proceed();

        System.out.println("time aspect 耗时:" + (new Date().getTime() - start));

        System.out.println("time aspect end");
        return obj;
    }
}
