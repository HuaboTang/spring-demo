package com.vbobot.spring.demo.feign.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Bobo
 * @date 4/16/21
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.vbobot.spring.demo.feign.api")
@SpringBootApplication
public class FeignClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(FeignClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
