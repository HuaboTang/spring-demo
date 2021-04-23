package com.vbobot.spring.demo.feign.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Bobo
 * @date 4/16/21
 */
@EnableEurekaClient
@SpringBootApplication
public class FeignServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FeignServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
