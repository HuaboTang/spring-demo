package com.vbobot.demo.spring.cloud.hystrix.client.controller;

import com.vbobot.demo.spring.cloud.hystrix.api.HystrixDemoFeign;
import com.vbobot.demo.spring.cloud.hystrix.client.config.GetUidCircuitBreakerCommand;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Resource;

/**
 * @author Bobo
 * @date 2021/4/26
 */
@RestController
public class HystrixClientController {
    @Resource HystrixDemoFeign hystrixDemoFeign;

    @GetMapping("/test")
    public String test() throws Exception {
        return hystrixDemoFeign.test();
    }

    @GetMapping("/throw/exception")
    public String throwException() throws Exception {
        return hystrixDemoFeign.throwException();
    }

    @GetMapping("/throw/exception/no/hystrix")
    public String throwExceptionNoHystrix() throws Exception {

        List<String> list = null;

        list.forEach(System.out::println);

        return hystrixDemoFeign.throwExceptionNoHystrix();
    }

    @GetMapping("/trigger/hystrix")
    public String triggerHystrix() {
        return hystrixDemoFeign.triggerHystrix();
    }

    @GetMapping("/trigger/timeout")
    public String triggerByTimeout() {
        final Supplier<String> stringSupplier = () -> {
            return hystrixDemoFeign.triggerByTimeout();
        };
        final GetUidCircuitBreakerCommand command = new GetUidCircuitBreakerCommand("uid", stringSupplier);
        return command.execute();
    }
}
