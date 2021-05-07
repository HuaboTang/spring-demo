package com.vbobot.spring.demo.spring.cache.dao;

import com.vbobot.spring.demo.spring.cache.DemoSpringCacheApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Bobo
 * @date 2021/4/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoSpringCacheApplication.class)
public class CacheDemoEntityDaoTest {
    @Resource CacheDemoEntityDao cacheDemoEntityDao;

    @Test
    public void testGet() throws Exception {
        final CacheDemoEntity cacheDemoEntity = cacheDemoEntityDao.get(1);
        final CacheDemoEntity cacheDemoEntity1 = cacheDemoEntityDao.get(1);
    }
}
