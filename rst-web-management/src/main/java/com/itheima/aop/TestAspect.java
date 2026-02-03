//package com.itheima.aop;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//@Component
//@Slf4j
//@Aspect
//public class TestAspect {
//
//    //前置通知，目标方法运行前运行
//    @Before("execution(* com.itheima.service.impl.*.*(..))")
//    public void before(){
//        log.info("before......");
//    }
//
//    //环绕通知，目标方法运行前和运行后都运行
//    @Around("execution(* com.itheima.service.impl.*.*(..))")
//    public void around(ProceedingJoinPoint pjp) throws Throwable {
//        log.info("around......before");
//
//        Object proceed = pjp.proceed();
//
//        log.info("around......after");
//
//    }
//
//    //后置通知，目标方法运行后运行，无论目标方法是否异常
//    @After("execution(* com.itheima.service.impl.*.*(..))")
//    public void after(){
//        log.info("after......");
//    }
//
//    //返回通知，目标方法运行后运行，只有目标方法正常运行时才运行
//    @AfterReturning("execution(* com.itheima.service.impl.*.*(..))")
//    public void afterReturning(){
//        log.info("afterReturning......");
//    }
//
//    //异常通知，目标方法运行后运行，只有目标方法异常时才运行
//    @AfterThrowing("execution(* com.itheima.service.impl.*.*(..))")
//    public void afterThrowing(){
//        log.info("afterThrowing......");
//    }
//
//}
