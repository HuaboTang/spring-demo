package com.vbobot.spring.demo.spring.cache;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Bobo
 * @date 2021/4/29
 */
@SpringBootApplication
public class DemoSpringCacheApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSpringCacheApplication.class)
                .web(WebApplicationType.NONE)
                .build()
                .run(args);
    }
}
