package com.itheima.exception;

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public Result handleException(Exception ex) {
        log.error("程序出错了", ex);
        return Result.error("出错，请联系管理员");
    }

//    @ExceptionHandler
//    public Result handleException1(MissingServletRequestParameterException ex){
//        String message = ex.getMessage();
//
//    }
}
