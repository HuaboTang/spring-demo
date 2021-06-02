package com.vbobot.demo.sleuth.ground.service;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/5/19
 */
@Slf4j
@RestController
@EnableFeignClients
@SpringBootApplication
public class DemoSleuthGroundServiceApplication implements GroundFeignApi {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSleuthGroundServiceApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @Override
    public String get() {
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String name = headerNames.nextElement();
            log.info("head: {}={}", name, request.getHeaders(name));
        }

        return "ground";
    }
}
