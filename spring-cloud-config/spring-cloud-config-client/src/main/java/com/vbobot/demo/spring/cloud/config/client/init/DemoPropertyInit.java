package com.vbobot.demo.spring.cloud.config.client.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Bobo
 * @date 2021/5/17
 */
@Component
public class DemoPropertyInit {
    @Value("${demo.url}") String demoUrl;

    @PostConstruct
    public void init() {
        System.out.println("Demo-url:" + demoUrl);
    }
}
