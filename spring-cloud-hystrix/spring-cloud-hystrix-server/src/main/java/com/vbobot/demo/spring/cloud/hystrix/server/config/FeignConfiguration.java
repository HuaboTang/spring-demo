package com.vbobot.demo.spring.cloud.hystrix.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import feign.Feign;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.hystrix.HystrixFeign;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/4/28
 */
@Slf4j
@Configuration
public class FeignConfiguration {

    @Bean
    public ErrorDecoder demoErrorDecode() {
        return new DemoErrorDecode();
    }

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder(ErrorDecoder demoErrorDecode) {
        final HystrixFeign.Builder builder = HystrixFeign.builder();
        builder.errorDecoder(demoErrorDecode);
        return builder;
    }

    public static class DemoErrorDecode implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = new Exception();
            log.info("{}, {}", methodKey, response);
            return exception;
        }
    }

}
