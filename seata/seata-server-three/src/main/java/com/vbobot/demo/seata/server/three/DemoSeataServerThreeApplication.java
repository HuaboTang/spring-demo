package com.vbobot.demo.seata.server.three;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/6/7
 */
@RestController
@SpringBootApplication
public class DemoSeataServerThreeApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSeataServerThreeApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
