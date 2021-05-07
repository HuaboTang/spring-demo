package com.vbobot.demo.spring.cloud.hystrix.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author Bobo
 * @date 2021/4/26
 */
@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
public class DemoSpringCloudHystrixServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSpringCloudHystrixServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
