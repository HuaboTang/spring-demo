package com.vbobot.spring.demo.spring.cache.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LRUCacheUtil {

    private static RedisTemplateCache<String, Object> redisCache;

    private static LRUCache<String, Object> instance = new LRUCache<>(50000);

    private static LRUCache<String, Object> hasRedisCache;

    @Autowired
    private RedisTemplateCache<String, Object> redisTemplateCache;

    @PostConstruct
    public void beforeInit() {
        redisCache = redisTemplateCache;
    }

    public static LRUCache<String, Object> getLRUCacheInstance() {
        return instance;
    }

    public static LRUCache<String, Object> getLRuCacheInstanceWithRedis() {
        if (hasRedisCache == null) {
            hasRedisCache = new LRUCache<>(50000, redisCache);
        }
        return hasRedisCache;
    }
}
