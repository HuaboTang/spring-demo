package com.vbobot.demo.spring.eureka.ribbon.service;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Bobo
 * @date 2021/6/23
 */
@SpringBootApplication
@EnableEurekaClient
public class EurekaRibbonServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaRibbonServiceApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
