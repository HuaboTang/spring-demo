package com.vbobot.demo.spring.cloud.hystrix.client.config;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        log.info("thread-local, interceptor:{}", ThreadLocalRemoveInterceptor.THEAD_LOCAL.get());
        log.info("thread-local, get, ali:{}", ThreadLocalRemoveInterceptor.ALI_THREAD_LOCAL.get());
    }
}
