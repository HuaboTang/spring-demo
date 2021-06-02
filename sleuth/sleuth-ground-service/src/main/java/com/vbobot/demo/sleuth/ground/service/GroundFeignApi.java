package com.vbobot.demo.sleuth.ground.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Bobo
 * @date 2021/5/19
 */
@FeignClient(value = "sleuth-ground-service", url = "http://127.0.0.1:8080")
public interface GroundFeignApi {

    @GetMapping("/get")
    String get();
}
