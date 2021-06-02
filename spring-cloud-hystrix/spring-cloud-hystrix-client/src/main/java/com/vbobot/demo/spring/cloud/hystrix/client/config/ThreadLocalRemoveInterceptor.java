package com.vbobot.demo.spring.cloud.hystrix.client.config;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Liang.Zhuge
 * @date 2020/5/27
 */
@Slf4j
public class ThreadLocalRemoveInterceptor implements HandlerInterceptor {
    protected static final ThreadLocal<Integer> THEAD_LOCAL = new InheritableThreadLocal<>();
    protected static final ThreadLocal<Integer> ALI_THREAD_LOCAL = new TransmittableThreadLocal<>();

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final int random = ATOMIC_INTEGER.incrementAndGet();
        log.info("thread-local, set:{}", random);
        THEAD_LOCAL.set(random);
        ALI_THREAD_LOCAL.set(random);
        HystrixRequestContext.initializeContext();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        THEAD_LOCAL.remove();
        ALI_THREAD_LOCAL.remove();
    }
}
