package com.vbobot.spring.demo.spring.boot.actuator;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Bobo
 * @date 2021/7/19
 */
@SpringBootApplication
public class DemoSpringBootActuatorApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSpringBootActuatorApplication.class)
            .web(WebApplicationType.SERVLET)
            .run(args);
    }
}
