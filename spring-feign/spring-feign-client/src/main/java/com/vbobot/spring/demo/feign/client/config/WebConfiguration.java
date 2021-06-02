package com.vbobot.spring.demo.feign.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/5/20
 */
@Slf4j
@Configuration
public class WebConfiguration {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        final CommonsRequestLoggingFilter loggingFilter = new CustomerCommonsRequestLoggingFilter();
        loggingFilter.setMaxPayloadLength(1024);
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        return loggingFilter;
    }

    public static class CustomerCommonsRequestLoggingFilter extends CommonsRequestLoggingFilter {

        @Override
        protected boolean shouldLog(HttpServletRequest request) {
            return false;
        }

        @Override
        protected void beforeRequest(HttpServletRequest request, String message) {
            log.info(message);
        }

        @Override
        protected void afterRequest(HttpServletRequest request, String message) {
            log.info(message);
        }
    }
}
