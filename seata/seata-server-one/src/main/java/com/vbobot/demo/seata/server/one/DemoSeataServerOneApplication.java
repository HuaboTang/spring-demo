package com.vbobot.demo.seata.server.one;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Bobo
 * @date 2021/6/7
 */
@EnableFeignClients
@RestController
@SpringBootApplication
public class DemoSeataServerOneApplication {
    @Resource SeataOneService seataOneService;

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSeataServerOneApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @GetMapping("/commit")
    public String commit() {
        return seataOneService.commit();
    }

    @GetMapping("/rollback/at/invoker")
    public String rollbackAtInvoker() {
        try {
            return seataOneService.rollbackAtInvoker();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "rollback-at-invoker";
    }

    @GetMapping("/rollback/at/callee")
    public String rollbackAtCallee() {
        return seataOneService.rollbackAtCallee();
    }
}
