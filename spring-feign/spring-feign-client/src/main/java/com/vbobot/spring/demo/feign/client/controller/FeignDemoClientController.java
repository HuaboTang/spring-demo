package com.vbobot.spring.demo.feign.client.controller;

import com.vbobot.spring.demo.feign.api.FeignDemoFeign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 4/16/21
 */
@Slf4j
@RestController
@RequestMapping("/feign/demo")
public class FeignDemoClientController {
    @Resource FeignDemoFeign feignDemoFeign;

    /**
     * 接口的default方法，会直接本地调整，不触发远程调用
     * ReflectiveFeign.java:59
     */
    @GetMapping("/test")
    public String test() {
        final String test = feignDemoFeign.test();
        log.info("Result:{}", test);
        return test;
    }

    @GetMapping("/test/no/default")
    public String testNoDefault() {
        final String noDefault = feignDemoFeign.testNoDefault();
        log.info("Result:{}", noDefault);
        return noDefault;
    }
}
