package com.vbobot.demo.sleuth.client;

import com.vbobot.demo.sleuth.middle.service.api.MiddleFeignApi;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/5/19
 */
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.vbobot.demo.sleuth.middle")
@RestController
@SpringBootApplication
public class DemoSleuthClientApplication {
    @Resource MiddleFeignApi middleFeignApi;

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSleuthClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @GetMapping()
    public String get() {
        log.info("Invoke result:{}", middleFeignApi.get());
        return "client";
    }
}
