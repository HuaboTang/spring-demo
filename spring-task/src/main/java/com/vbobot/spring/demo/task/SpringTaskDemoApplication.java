package com.vbobot.spring.demo.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Liang.Zhuge
 * @date 3/9/21
 */
@SpringBootApplication
public class SpringTaskDemoApplication {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(SpringTaskDemoApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Bean
    public TaskExecutor initTaskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("init-task-executor-");
        return executor;
    }
}
