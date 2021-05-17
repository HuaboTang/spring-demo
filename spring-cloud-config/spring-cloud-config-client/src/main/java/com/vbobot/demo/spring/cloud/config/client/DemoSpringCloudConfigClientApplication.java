package com.vbobot.demo.spring.cloud.config.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Bobo
 * @date 2021/5/17
 */
@SpringBootApplication
public class DemoSpringCloudConfigClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSpringCloudConfigClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
