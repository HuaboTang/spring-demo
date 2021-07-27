package com.vbobot.spring.demo.spring.boot.redisson.lettuce;

import com.google.common.collect.Lists;
import java.util.List;
import jodd.util.MathUtil;
import jodd.util.RandomString;
import lombok.Data;

/**
 * @author Bobo
 * @date 2021/7/27
 */
@Data
public class CacheEntity {
    private String fieldOne;
    private Integer fieldTwo;
    private List<String> fieldThree;

    public static CacheEntity randomEntity() {
        final CacheEntity cacheEntity = new CacheEntity();
        cacheEntity.setFieldOne(randomStr());
        cacheEntity.setFieldTwo(MathUtil.randomInt(10, 100000));
        cacheEntity.setFieldThree(Lists.newArrayList(randomStr(), randomStr(), randomStr()));
        return cacheEntity;
    }

    private static String randomStr() {
        return RandomString.get().randomNumeric(10);
    }
}
