package com.vbobot.spring.demo.feign.server.controller;

import com.vbobot.spring.demo.feign.api.FeignDemoFeign;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 4/16/21
 */
@RestController
public class FeignDemoController implements FeignDemoFeign {

    @Override
    public String test() {
        return "Implementation";
    }

    @Override
    public String testNoDefault() {
        return "Implementation, no-default";
    }
}
