package com.vbobot.demo.spring.cloud.hystrix.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Bobo
 * @date 2021/4/26
 */
@EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.vbobot.demo.spring.cloud.hystrix.api")
@SpringBootApplication
public class DemoSpringCloudHystrixClientApplication {
//    static {
//        HystrixConcurrencyStrategy target = new ThreadLocalHystrixConcurrencyStrategy();
//        HystrixConcurrencyStrategy strategy = HystrixPlugins.getInstance().getConcurrencyStrategy();
//        if (!(strategy instanceof ThreadLocalHystrixConcurrencyStrategy)) {
//            // Welcome to singleton hell...
//
//            HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins
//                    .getInstance().getCommandExecutionHook();
//            HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance()
//                    .getEventNotifier();
//            HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance()
//                    .getMetricsPublisher();
//            HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance()
//                    .getPropertiesStrategy();
//
//            HystrixPlugins.reset();
//            HystrixPlugins.getInstance().registerConcurrencyStrategy(target);
//            HystrixPlugins.getInstance()
//                    .registerCommandExecutionHook(commandExecutionHook);
//            HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
//            HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
//            HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
//        }
//    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoSpringCloudHystrixClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .build()
                .run(args);
    }
}
