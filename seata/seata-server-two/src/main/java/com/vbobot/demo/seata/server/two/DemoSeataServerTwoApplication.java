package com.vbobot.demo.seata.server.two;

import io.seata.spring.boot.autoconfigure.SeataDataSourceAutoConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.seata.core.context.RootContext;

/**
 * @author Bobo
 * @date 2021/6/7
 */
@RestController
@EnableJpaRepositories
@SpringBootApplication
@ImportAutoConfiguration(SeataDataSourceAutoConfiguration.class)
public class DemoSeataServerTwoApplication {
    @Resource DemoSeataTwoService demoSeataTwoService;

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSeataServerTwoApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @GetMapping("/commit")
    public String commit() {
        System.out.println(RootContext.getXID());
        return demoSeataTwoService.commit();
    }

    @GetMapping("/rollback")
    public String rollback() {
        try {
            demoSeataTwoService.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "rollback";
    }
}
