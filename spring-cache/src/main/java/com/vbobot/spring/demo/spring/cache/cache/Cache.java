package com.vbobot.spring.demo.spring.cache.cache;

public interface Cache<K extends Comparable, V> {
    V get(K obj);
    //breakDownRateOneToHundred表示0-100的击穿率，比如=5，表示5%的请求即使get到了数据，也返回null
    V get(K obj, int breakDownRateZeroToHundred);
    void put(K key, V obj);
    void put(K key, V obj, long validTimeMs);
    void remove(K key);
    Pair[] getAll();
    int size();
}

