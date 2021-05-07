package com.vbobot.demo.spring.cloud.hystrix.client.controller;

import com.vbobot.demo.spring.cloud.hystrix.api.HystrixDemoFeign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Bobo
 * @date 2021/4/26
 */
@RestController
public class HystrixClientController {
    @Resource HystrixDemoFeign hystrixDemoFeign;

    @GetMapping("/test")
    public String test() throws Exception {
        return hystrixDemoFeign.test();
    }

    @GetMapping("/throw/exception")
    public String throwException() throws Exception {
        return hystrixDemoFeign.throwException();
    }

    @GetMapping("/throw/exception/no/hystrix")
    public String throwExceptionNoHystrix() throws Exception {
        return hystrixDemoFeign.throwExceptionNoHystrix();
    }

    @GetMapping("/trigger/hystrix")
    public String triggerHystrix() {
        return hystrixDemoFeign.triggerHystrix();
    }

    @GetMapping("/trigger/timeout")
    public String triggerByTimeout() {
        return hystrixDemoFeign.triggerByTimeout();
    }
}
