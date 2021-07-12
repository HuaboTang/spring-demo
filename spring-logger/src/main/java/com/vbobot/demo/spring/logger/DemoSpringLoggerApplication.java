package com.vbobot.demo.spring.logger;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/7/7
 */
@Slf4j
@SpringBootApplication
public class DemoSpringLoggerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSpringLoggerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        log.info("Test");
    }
}
