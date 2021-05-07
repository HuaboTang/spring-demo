package com.vbobot.demo.spring.cloud.hystrix.server.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.vbobot.demo.spring.cloud.hystrix.api.HystrixDemoFeign;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/4/26
 */
@Slf4j
@RestController
public class HystrixDemoController implements HystrixDemoFeign {

    @HystrixCommand(fallbackMethod = "failOfTest", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
    })
    @Override
    public String test() throws Exception {
        final long sleep = (int) (Math.random() * 1000);
        System.out.println(Thread.currentThread().getName() + ", sleep:" + sleep);
        Thread.sleep(sleep);
        return "test";
    }

    @HystrixCommand(fallbackMethod = "failOfThrowException")
    @Override
    public String throwException() {
        throw new HystrixBadRequestException("111", new DemoException("123123123123123"));
    }

    @Override
    public String throwExceptionNoHystrix() throws Exception {
        throw new Exception("throw-exception-no-hystrix");
    }

    @Override
    public String triggerHystrix() {
        throw new RuntimeException("triggerHystrix");
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    @Override
    public String triggerByTimeout() {
        int v = (int) (10000 * Math.random());
        System.out.println(v);
        try {
            Thread.sleep(v);
        } catch (InterruptedException e) {
            log.warn("{}, {}", v, e.getMessage());
        }
        return "tiggerByTimeout";
    }

    public String failOfThrowException(Throwable e) {
        log.error("hystrix, on exception:{}", e.getMessage(), e);
        return "failOfThrowException";
    }

    @ResponseStatus
    public String failOfTest() {
        return "fallback method";
    }

    public static class DemoException extends HystrixBadRequestException {

        public DemoException(String message) {
            super(message);
        }
    }

}
