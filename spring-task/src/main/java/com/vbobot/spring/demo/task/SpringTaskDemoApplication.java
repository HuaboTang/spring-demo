package com.vbobot.spring.demo.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;

/**
 * @author Liang.Zhuge
 * @date 3/9/21
 */
@SpringBootApplication
public class SpringTaskDemoApplication {
    private static final CountDownLatch COUNT_DOWN = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        final SpringApplication springApplication = new SpringApplication(SpringTaskDemoApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);

        Runtime.getRuntime().addShutdownHook(new Thread(COUNT_DOWN::countDown));

        COUNT_DOWN.await();
    }

    @Bean
    public TaskExecutor initTaskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("init-task-executor-");
        return executor;
    }
}
