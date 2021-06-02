package com.vbobot.demo.spring.cloud.hystrix.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Bobo
 * @date 2021/6/2
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(threadLocalRemoveInterceptor());
    }

    @Bean
    public ThreadLocalRemoveInterceptor threadLocalRemoveInterceptor() {
        return new ThreadLocalRemoveInterceptor();
    }
}
