package com.vbobot.spring.demo.spring.boot.redisson.lettuce.config;

import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bobo
 * @date 2021/7/26
 */
@Configuration
public class PoolRedissonAutoConfiguration {

    @Bean
    public RedissonAutoConfigurationCustomizer poolRedissonAutoConfigurationCustomizer() {
        return configuration -> configuration.setNettyThreads(10);
    }
}
