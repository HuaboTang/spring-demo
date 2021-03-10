package com.vbobot.spring.demo.task.init;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Liang.Zhuge
 * @date 3/9/21
 */
@Slf4j
@Component
public class ThreadByExecutorInitialize {
    @Resource TaskExecutor initTaskExecutor;

    @PostConstruct
    public void init() {
        log.info("Init");
        initTaskExecutor.execute(() -> {
            int i = 0;
            while (i < 100) {
                try {
                    log.info("Loop, i:{}", i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // 使用kill -15 pid 等关闭服务时，将调用到下catch块中的代码
                    log.warn("Interrupted, i:{}", i);
                }
                i++;
            }
        });

        log.info("Init finish");
    }
}
