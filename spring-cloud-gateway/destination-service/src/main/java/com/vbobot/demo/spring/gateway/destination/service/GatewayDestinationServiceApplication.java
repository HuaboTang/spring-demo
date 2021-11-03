package com.vbobot.demo.spring.gateway.destination.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/9/16
 */
@RestController
@SpringBootApplication
public class GatewayDestinationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayDestinationServiceApplication.class, args);
    }

    @GetMapping("/get")
    public String get() {
        return "success";
    }
}
