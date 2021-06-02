package com.vbobot.spring.demo.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/5/7
 */
@Configuration
@Slf4j
public class TaskConfiguration {

    /**
     * 默认@Async处理bean
     */
    @Bean
    public TaskExecutor demoTaskExecutor() {
        final int maxPoolSize = 10;

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setThreadNamePrefix("thread-");
        executor.setQueueCapacity(3 * maxPoolSize);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        log.info("Init thread pool task executor");

        return executor;
    }

    /**
     * 默认@Schedule处理Bean
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setRejectedExecutionHandler(
                (r, executor) -> log.warn("Rejected scheduler, {}", r));
        threadPoolTaskScheduler.setErrorHandler(
                (e) -> log.error("schedule execute error:{}", e.getMessage(), e));
        return threadPoolTaskScheduler;
    }
}
