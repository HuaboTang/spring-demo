package com.vbobot.spring.demo.feign.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Bobo
 * @date 4/16/21
 */
@FeignClient(name = "demo-spring-feign-server", contextId = "feignDemo")
@RequestMapping("/demo/feign")
public interface FeignDemoFeign {

    @GetMapping("/test")
    default String test() {
        return "default";
    }

    @GetMapping("/test/no/default")
    String testNoDefault();
}
