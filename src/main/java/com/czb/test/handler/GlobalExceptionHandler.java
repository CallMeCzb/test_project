package com.czb.test.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({NullPointerException.class})    //申明捕获那个异常类
    public Map ExceptionDemo(Exception e) {
        Map<String,String> map = new HashMap<>();
        map.put("ex","异常信息");
        return map;
    }

}

