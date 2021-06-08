package com.vbobot.demo.seata.server.one;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Bobo
 * @date 2021/6/8
 */
@Component
public class DemoTwoAdapter {

    @FeignClient(name = "demo-seata-two-server", contextId = "demoTwoFeign", url = "http://127.0.0.1:8081/")
    public interface DemoTwoFeign {

        @GetMapping("/commit")
        String commit();

        @GetMapping("/rollback")
        String rollback();
    }
}
