package com.vbobot.spring.demo.spring.boot.redisson.lettuce;

import java.util.Set;
import javax.annotation.Resource;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bobo
 * @date 2021/7/26
 */
@EnableConfigurationProperties
@SpringBootApplication
@RestController
public class SpringBootRedissonLettuceDemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootRedissonLettuceDemoApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @GetMapping("")
    public String get() {
        final Set<String> keys = redisTemplate.keys("*");
        System.out.println(keys);
        return "test";
    }
}
