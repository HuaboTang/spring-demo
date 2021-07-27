package com.vbobot.spring.demo.spring.boot.redisson.lettuce.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bobo
 * @date 2021/7/26
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis.lettuce")
@Data
public class LettuceProperties {

    private boolean shareNativeConnection;
}
