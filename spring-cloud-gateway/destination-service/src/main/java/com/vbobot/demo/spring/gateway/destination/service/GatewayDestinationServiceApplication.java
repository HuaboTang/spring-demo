package com.vbobot.demo.spring.gateway.destination.service;

import java.util.concurrent.atomic.AtomicInteger;
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
    private final AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        SpringApplication.run(GatewayDestinationServiceApplication.class, args);
    }

    @GetMapping("/reset")
    public String reset() {
        count.set(0);
        return "reset";
    }

    @GetMapping("/sleep")
    public String sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count.incrementAndGet());
        return "sleep";
    }

    @GetMapping("/get")
    public String get() {
        return "success";
    }
}
