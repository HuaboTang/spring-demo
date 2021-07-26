package com.vbobot.spring.demo.spring.boot.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/7/19
 */
@Configuration
public class ActuatorHealthDownConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HealthDownResponseBodyAdvice customerAdvice() {
        return new HealthDownResponseBodyAdvice();
    }

    @Slf4j
    @RestControllerAdvice
    public static class HealthDownResponseBodyAdvice implements ResponseBodyAdvice<Health> {

        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            return true;
        }

        @Override
        public Health beforeBodyWrite(Health body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
            final Status status = Optional.ofNullable(body).map(Health::getStatus).orElse(null);
            if (Status.DOWN == status) {
                body.getDetails().forEach((key, val) -> {
                    if (val instanceof Health && ((Health) val).getStatus() == Status.DOWN) {
                        log.warn("Health-down:{}", val);
                    }
                });
            }
            return body;
        }
    }
}
