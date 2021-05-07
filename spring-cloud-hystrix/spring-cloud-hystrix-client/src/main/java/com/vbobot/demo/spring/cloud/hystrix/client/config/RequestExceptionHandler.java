package com.vbobot.demo.spring.cloud.hystrix.client.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/4/28
 */
@Slf4j
@RestControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Resp onException(Exception e) {
        log.error("Exception handler, catch exception:{}", e.getMessage(), e);
        return new Resp().setCode(-1).setMsg(e.getMessage());
    }

    @Data
    @Accessors(chain = true)
    public static class Resp {
        private int code;
        private String msg;
    }
}
