package com.vbobot.spring.demo.feign.client.controller;

import com.vbobot.spring.demo.feign.api.FeignDemoFeign;
import com.vbobot.spring.demo.feign.client.exception.DemoFeignException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import lombok.Data;
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

    @GetMapping("/throw/demo/feign/exception")
    public String throwDemoFeignException(Param param,
            @RequestBody RequestBodyParam body) {
        log.info("Param:{}, body:{}", param, body);
        throw new DemoFeignException("dddd");
    }

    @GetMapping("/throw/null/pointer")
    public String onNullPointerException(Param param,
            @RequestBody RequestBodyParam body) {
        log.info("Param:{}, body:{}", param, body);
        throw new NullPointerException("123");
    }


    @PostMapping("/post")
    public String forMethodException() {
        return "post";
    }

    @Data
    public static class RequestBodyParam {
        private String b1;
        private String b2;
    }

    @Data
    public static class Param {
        private String p1;
        private String p2;
    }
}
