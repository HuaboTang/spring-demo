package com.vbobot.demo.spring.eureka.ribbon.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/6/23
 */
@RestController
public class ServiceController {

    @GetMapping("")
    public String get() {
        return "get";
    }
}
