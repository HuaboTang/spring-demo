package com.vbobot.spring.demo.spring.cache.cache;

import java.util.concurrent.TimeUnit;

public interface RedisCache<K extends Comparable, V> {
    V get(K obj);
    void put(K key, V obj, long validTime, TimeUnit timeUnit);
    void remove(K key);
}
