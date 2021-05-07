package com.vbobot.spring.demo.spring.cache.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

@Component
public class RedisTemplateCache<K extends Comparable, V> implements RedisCache<K, V> {

    @Resource
    private RedisTemplate<K, V> redisTemplate;

    @Override
    public V get(K obj) {
        return redisTemplate.opsForValue().get(obj);
    }

    @Override
    public void put(K key, V obj, long validTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, obj, validTime, timeUnit);
    }

    @Override
    public void remove(K key) {
        redisTemplate.delete(key);
    }
}
