package com.vbobot.demo.sleuth.feign.header;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/5/19
 */
@Slf4j
public class OryxFeignPassHeaderConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new MyRequestInterceptor();
    }

    public static class MyRequestInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate template) {
            HttpServletRequest request =
                    ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                            .getRequest();
            final Enumeration<String> headerNames = Optional.ofNullable(request).map(item -> item.getHeaderNames())
                    .orElse(null);
            if (headerNames == null) {
                return;
            }

            while (headerNames.hasMoreElements()) {
                final String key = headerNames.nextElement();
                template.header(key, request.getHeader(key));
            }
        }
    }
}
