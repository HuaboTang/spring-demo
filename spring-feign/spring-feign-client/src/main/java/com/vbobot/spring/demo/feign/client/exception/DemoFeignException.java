package com.vbobot.spring.demo.feign.client.exception;

/**
 * @author Bobo
 * @date 2021/5/20
 */
public class DemoFeignException extends RuntimeException {

    public DemoFeignException(String message) {
        super(message);
    }
}
