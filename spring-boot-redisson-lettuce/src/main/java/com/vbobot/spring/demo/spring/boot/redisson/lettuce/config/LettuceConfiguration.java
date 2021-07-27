package com.vbobot.spring.demo.spring.boot.redisson.lettuce.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * @author Bobo
 * @date 2021/7/26
 */
@Configuration
@ConditionalOnBean(LettuceConnectionFactory.class)
public class LettuceConfiguration {

    public LettuceConfiguration(LettuceConnectionFactory factory, LettuceProperties lettuceProperties) {
        factory.setShareNativeConnection(lettuceProperties.isShareNativeConnection());
    }
}
