package com.itheima.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect //当前类为切面类
@Slf4j
public class RecordTimeAspect {

    @Pointcut("execution(* com.itheima.service.impl.*.*(..))")
    public void pt(){}

    @Before("pt()")
    public void before(JoinPoint joinPoint){
        log.info("before...");
        //1. 获取目标对象
        Object target = joinPoint.getTarget();
//        Object target = joinPoint.getTarget();
        log.info("获取目标对象: {}" , target);
        //2.获取目标类
        String className = joinPoint.getTarget().getClass().getName();
        log.info("获取目标类: {}" , className);
        //3.获取目标方法
        String methodName = joinPoint.getSignature().getName();
        log.info("获取目标方法: {}" , methodName);
        //4.获取目标方法参数
        Object[] args = joinPoint.getArgs();
        log.info("获取目标方法参数: {}" , Arrays.toString(args));


    }

    @Around("pt()")
    public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {
        //记录方法执行开始时间
        long begin = System.currentTimeMillis();

        //执行原始方法
        Object result = pjp.proceed();

        //记录方法执行结束时间
        long end = System.currentTimeMillis();

        //计算方法执行耗时
        log.info("方法 {} 执行耗时: {}毫秒",pjp.getSignature(), end-begin);
        return result;
    }
}