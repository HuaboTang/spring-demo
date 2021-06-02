package com.vbobot.spring.demo.feign.client.controller;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/5/25
 */
@RestController
public class PerformanceTestController {

    @GetMapping("/performance/test")
    public String get() throws InterruptedException {
        Thread.sleep(RandomUtils.nextInt(1500));
        return "success";
    }
}
