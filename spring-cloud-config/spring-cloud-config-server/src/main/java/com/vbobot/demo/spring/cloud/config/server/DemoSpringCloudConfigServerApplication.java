package com.vbobot.demo.spring.cloud.config.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Bobo
 * @date 2021/5/17
 */
@EnableConfigServer
@SpringBootApplication
public class DemoSpringCloudConfigServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSpringCloudConfigServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
