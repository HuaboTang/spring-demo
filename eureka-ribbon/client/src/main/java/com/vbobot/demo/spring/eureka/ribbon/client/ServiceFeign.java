package com.vbobot.demo.spring.eureka.ribbon.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Bobo
 * @date 2021/6/23
 */
@FeignClient(name = "demo-eureka-ribbon-service", contextId = "demo")
public interface ServiceFeign {

    @GetMapping("")
    String get();
}
