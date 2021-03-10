package com.vbobot.spring.demo.task.init;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Liang.Zhuge
 * @date 3/9/21
 */
@Slf4j
@Component
public class NewThreadInitialize {

    @PostConstruct
    public void init() {
        log.info("Thread-init");
        new Thread(() -> {
            int i = 0;
            while (i < 100) {
                try {
                    log.info("Thread-Loop, i:{}", i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.warn("Thread-interrupted, i:{}", i);
                }
                i++;
            }
        }).start();

        log.info("Thread-init finish");
    }
}
