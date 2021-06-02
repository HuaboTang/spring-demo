package com.vbobot.demo.spring.cloud.hystrix.client.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

import java.util.function.Supplier;


public class GetUidCircuitBreakerCommand extends HystrixCommand<String> {

    private String uid;
    private Supplier<String> eventTrackingClient;

    /**
     * command key ，代表了一类 command，一般来说，代表了底层的依赖服务的一个接口，相同的依赖要使用相同的CommandKey名称。依赖隔离的根本就是对相同CommandKey的依赖做隔离.
     * command group ,command group 在逻辑上去组织起来一堆 command key 的调用、统计信息、成功次数、timeout 超时次数、失败次数等
     * ThreadPoolKey 一般来说，推荐根据一个服务区划分出一个线程池，最然在业务上都是相同的组，但是需要在资源上做隔离时，可以使用HystrixThreadPoolKey区分.
     * <p>
     * 设置线程池的大小，默认是 10。一般来说，用这个默认的 10 个线程大小就够了。
     * HystrixThreadPoolProperties.Setter().withCoreSize(int value);
     * <p>
     * 控制 queue 满了之后 reject 的 threshold，因为 maxQueueSize 不允许热修改，因此提供这个参数可以热修改，控制队列的最大大小。
     * HystrixThreadPoolProperties.Setter().withQueueSizeRejectionThreshold(int value);
     *
     * @param uid
     */
    public GetUidCircuitBreakerCommand(String uid, Supplier<String> consumer) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ThreadPool1TestGroup"))     // 服务分组
                .andCommandKey(HystrixCommandKey.Factory.asKey("uidCommandKey"))   // 服务标识
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("getUid"))   // 线程池名称
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter() // 线程池配置
                        // 默认是true
                        .withCircuitBreakerEnabled(true)
                        // 默认是false
                        .withCircuitBreakerForceOpen(false)
                        // 默认是false
                        .withCircuitBreakerForceClosed(false)
                        //(1)错误百分比超过5%
                        .withCircuitBreakerErrorThresholdPercentage(5)
                        //(2)10s 内调用次数10次，同时满足(1)(2)熔断打开
                        .withCircuitBreakerRequestVolumeThreshold(10)
                        //隔20s 之后，熔断器会尝试半开（关闭），重新放请求进来
                        .withExecutionTimeoutInMilliseconds(20000))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("demo-hystrix"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withMaxQueueSize(10)   //配置队列大小
                        .withCoreSize(2)    //配置线程池里线程数
                        .withQueueSizeRejectionThreshold(10))); // 设置等待队列大小

        this.uid = uid;
        this.eventTrackingClient = consumer;
    }

    @Override
    protected String run() throws Exception {
        System.out.println("================== GetUidCircuitBreakerCommand:");
        return eventTrackingClient.get();
    }

    @Override
    protected String getFallback() {
        return "fallback";
    }
}
