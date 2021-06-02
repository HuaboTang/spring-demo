package com.vbobot.demo.sleuth.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/5/19
 */
@Slf4j
@Configuration
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new MyRequestInterceptor();
    }

    public static class MyRequestInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate template) {
            template.header("oryx-uid", "123456");
        }
    }
}
