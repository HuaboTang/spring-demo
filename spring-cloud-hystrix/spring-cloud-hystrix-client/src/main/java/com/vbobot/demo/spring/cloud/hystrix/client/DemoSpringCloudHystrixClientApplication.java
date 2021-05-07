package com.vbobot.demo.spring.cloud.hystrix.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Bobo
 * @date 2021/4/26
 */
@EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.vbobot.demo.spring.cloud.hystrix.api")
@SpringBootApplication
public class DemoSpringCloudHystrixClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSpringCloudHystrixClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .build()
                .run(args);
    }
}
