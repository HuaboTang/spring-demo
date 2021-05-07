package com.vbobot.demo.spring.cloud.hystrix.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Bobo
 * @date 2021/4/26
 */
@FeignClient(name = "demo-spring-cloud-hystrix-server", contextId = "hystrixDemo")
public interface HystrixDemoFeign {

    @GetMapping("/test")
    String test() throws Exception;

    @GetMapping("/throw/exception")
    String throwException() throws Exception;

    @GetMapping("/throw/exception/no/hystrix")
    String throwExceptionNoHystrix() throws Exception;

    @GetMapping("/trigger/hystrix")
    String triggerHystrix();

    @GetMapping("/trigger/timeout")
    String triggerByTimeout();
}
