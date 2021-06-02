package com.vbobot.demo.sleuth.middle.service;

import com.vbobot.demo.sleuth.feign.header.EnableFeignAutoPassOryxHeader;
import com.vbobot.demo.sleuth.ground.service.GroundFeignApi;
import com.vbobot.demo.sleuth.middle.service.api.MiddleFeignApi;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/5/19
 */
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.vbobot.demo.sleuth.ground")
@RestController
@EnableFeignAutoPassOryxHeader
@SpringBootApplication
public class DemoSleuthMiddleServiceApplication implements MiddleFeignApi {
    @Resource GroundFeignApi groundFeignApi;

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSleuthMiddleServiceApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @Override
    @RequestMapping("/get")
    public String get() {
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String name = headerNames.nextElement();
            log.info("head: {}={}", name, request.getHeaders(name));
        }

        log.info("Ground invoke result:{}", groundFeignApi.get());

        return "middle";
    }
}
