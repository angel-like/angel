package com.itheima.springboot.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception可以是自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception e){
        //这里做统一异常处理
        return ResponseEntity.status(e.hashCode()).body(e);

    }
}
