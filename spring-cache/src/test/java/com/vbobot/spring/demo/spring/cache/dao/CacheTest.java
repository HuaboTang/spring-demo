package com.vbobot.spring.demo.spring.cache.dao;

import com.vbobot.spring.demo.spring.cache.DemoSpringCacheApplication;
import com.vbobot.spring.demo.spring.cache.cache.LRUCacheUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.lang.management.ManagementFactory;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

/**
 * @author Bobo
 * @date 2021/4/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoSpringCacheApplication.class)
public class CacheTest {

    private static final PodamFactory podamFactory = new PodamFactoryImpl();
    private static final AtomicInteger id = new AtomicInteger(0);

    @Test
    public void testSetGet() throws Exception {
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
        for (int i=10; i>0; i--) {
            System.out.println("启动中" + i);
            Thread.sleep(1000);
        }

        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 50,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        for (int i=0; i<20; i++) {
            threadPoolExecutor.execute(new InnerRunnable());
        }
        threadPoolExecutor.awaitTermination(1, TimeUnit.DAYS);
    }

    public static class InnerRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    final int andIncrement = id.getAndIncrement();
                    System.out.println("put " + andIncrement);
                    final CacheEntity obj = CacheEntity.newInstance();
                    LRUCacheUtil.getLRUCacheInstance().put(andIncrement + "", obj);
                    LRUCacheUtil.getLRUCacheInstance().get(String.valueOf(id.get() - 10));
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Data
    public static class CacheEntity {
        private String fieldOne;
        private String fieldTow;
        private String fieldThree;
        private String fieldFour;
        private String fieldFive;
        private String field1;
        private String field2;
        private String field3;
        private String field4;
        private String field5;
        private String field6;


        public static CacheEntity newInstance() {
            return podamFactory.manufacturePojoWithFullData(CacheEntity.class);
        }
    }
}
