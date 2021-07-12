package com.vbobot.demo.spring.logger;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/7/7
 */
@Slf4j
@Component
public class LoggerPostConstructor {
    private static final Map<Long, AtomicInteger> costAndTimes = Maps.newHashMapWithExpectedSize(100000);

    public LoggerPostConstructor() {
//        try {
////            startUp();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void startUp() throws InterruptedException {
        log.info("Start up");

        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(200, 600, 0, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactoryBuilder()
                        .setNameFormat("task-%d")
                        .setDaemon(true)
                        .build());
        final AtomicLong allCost = new AtomicLong(0);
        final CountDownLatch countDownLatch = new CountDownLatch(500);
        for (int i = 0; i < 500; i++) {
            threadPoolExecutor.execute(() -> {
                final AtomicInteger atomicInteger = new AtomicInteger(2000);
                Runtime.getRuntime().addShutdownHook(new Thread(() -> atomicInteger.set(0)));
                while (atomicInteger.decrementAndGet() > 0) {
                    final long begin = System.currentTimeMillis();
                    log.info(atomicInteger.get() + ", java.lang.Thread.run( ):748 [1300ms]"
                            + "org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run( ):61 [1300ms]"
                            + "java.util.concurrent.ThreadPoolExecutor$Worker.run( ):624 [1300ms]"
                            + "显示已隐藏的90个条目"
                            + "ch.qos.logback.classic.Logger.appendLoopOnAppenders( ):270 [1300ms]"
                            + "ch.qos.logback.core.spi.AppenderAttachableImpl.appendLoopOnAppenders( ):51 [1300ms]"
                            + "ch.qos.logback.core.UnsynchronizedAppenderBase.doAppend( ):84 [1300ms]"
                            + "ch.qos.logback.core.OutputStreamAppender.append( ):102 [1300ms]"
                            + "ch.qos.logback.core.rolling.RollingFileAppender.subAppend( ):229 [100ms]"
                            + "ch.qos.logback.core.rolling.RollingFileAppender.subAppend( ):235 [1200ms]"
                            + "ch.qos.logback.core.OutputStreamAppender.subAppend( ):231 [1200ms]"
                            + "ch.qos.logback.core.OutputStreamAppender.writeBytes( ):197 [1200ms]"
                            + "java.util.concurrent.locks.ReentrantLock.lock( ):285 [1200ms]"
                            + "java.util.concurrent.locks.ReentrantLock$NonfairSync.lock( ):209 [1200ms]"
                            + "java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire( ):1199 [1200ms]"
                            + "java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued( ):870 [1200ms]"
                            + "java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt( ):836 [1200ms]"
                            + "java.util.concurrent.locks.LockSupport.park( ):175 [1200ms]"
                            + "sun.misc.Unsafe.park( ):-2 [1200ms]");
                    final long cost = System.currentTimeMillis() - begin;
                    System.out.println("cost:" + cost);
                    allCost.addAndGet(cost);
                    costAndTimes.compute(cost, (key, val) -> {
                        if (val == null) {
                            return new AtomicInteger(1);
                        } else {
                            val.incrementAndGet();
                            return val;
                        }
                    });
                }

                countDownLatch.countDown();
            });
        }

        if (countDownLatch.await(1, TimeUnit.HOURS)) {
            StringBuilder sb = new StringBuilder();
            costAndTimes.keySet().stream().sorted(Comparator.reverseOrder()).forEach(cost -> {
                sb.append(cost).append(":").append(costAndTimes.get(cost).get()).append(", ");
            });
            System.out.println(sb);
            System.out.println(allCost.get());
        }
    }
}
