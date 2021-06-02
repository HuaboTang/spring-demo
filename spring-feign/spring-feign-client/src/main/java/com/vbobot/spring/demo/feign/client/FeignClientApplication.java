package com.vbobot.spring.demo.feign.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Bobo
 * @date 4/16/21
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.vbobot.spring.demo.feign.api")
@SpringBootApplication
public class FeignClientApplication {
    @Value("${server.tomcat.max-threads}") Integer maxThreads;

    public static void main(String[] args) {
        final ConfigurableApplicationContext run = new SpringApplicationBuilder(FeignClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);

        System.out.println("Tomcat max-threads:" + run.getEnvironment().getProperty("server.tomcat.max-threads"));
    }
}
