package com.vbobot.demo.spring.eureka.ribbon.eureka.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Bobo
 * @date 2021/6/23
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaRibbonEurekaServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaRibbonEurekaServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
