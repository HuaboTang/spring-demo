package com.vbobot.spring.demo.spring.cache.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/4/29
 */
@Repository
public class CacheDemoEntityDao {

    @Cacheable(key = "'demo:spring:cache:' + #p0", cacheNames = "Demo")
    public CacheDemoEntity get(Integer id) {
        System.out.println(id);
        return new CacheDemoEntity()
                .setA("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .setB("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
                .setC("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc")
                .setD("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd")
                .setE("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
    }
}
