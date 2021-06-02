package com.vbobot.demo.sleuth.middle.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Bobo
 * @date 2021/5/19
 */
@FeignClient(value = "sleuth-middle-service", url = "http://127.0.0.1:8081/")
public interface MiddleFeignApi {

    @GetMapping("/get")
    String get();
}
