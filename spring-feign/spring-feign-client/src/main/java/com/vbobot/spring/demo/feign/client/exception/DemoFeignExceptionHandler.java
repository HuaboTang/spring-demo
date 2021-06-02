package com.vbobot.spring.demo.feign.client.exception;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/5/20
 */
@Slf4j
@RestControllerAdvice
public class DemoFeignExceptionHandler {

    @ExceptionHandler(DemoFeignException.class)
    public String onDemoFeignException(DemoFeignException e, HttpServletRequest request) {
        log.warn("Request-warn, uri:{}, params:{}, error:{}",
                request.getRequestURI(),
                request.getQueryString(),
                e.getMessage());
        return "onDemoFeignException";
    }

    @ExceptionHandler(Exception.class)
    public String onException(Exception e, HttpServletRequest request) {
        log.error("Request-error, uri:{}, params:{}, error:{}",
                request.getRequestURI(),
                request.getQueryString(),
                e.getMessage(), e);
        return "onException";
    }

    @ExceptionHandler({
            ClientAbortException.class,
            HttpRequestMethodNotSupportedException.class
    })
    public String onHttpException(Exception e, HttpServletRequest request) {
        log.warn("Request-warn, uri:{}, params:{}, error:{}",
                request.getRequestURI(),
                request.getQueryString(),
                e.getMessage());
        return "onHttpException";
    }
}
