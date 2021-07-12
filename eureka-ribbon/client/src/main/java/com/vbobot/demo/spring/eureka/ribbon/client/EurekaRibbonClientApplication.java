package com.vbobot.demo.spring.eureka.ribbon.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Bobo
 * @date 2021/6/23
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.vbobot.demo.spring.eureka.ribbon")
@RestController
public class EurekaRibbonClientApplication {
    @Resource ServiceFeign serviceFeign;

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaRibbonClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @GetMapping("")
    public String get() {
        return serviceFeign.get();
    }
}
