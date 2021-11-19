package com.vbobot.demo.spring.cloud.gateway.gateway.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.netty.ReactorNetty;

/**
 * @author Bobo
 * @date 2021/9/16
 */
@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        System.setProperty(ReactorNetty.ACCESS_LOG_ENABLED, "false");

        //https://blog.csdn.net/manzhizhen/article/details/115386684
//        System.setProperty(ReactorNetty.NATIVE, "false");

//        System.setProperty(ReactorNetty.IO_WORKER_COUNT, "16");
//        System.setProperty(ReactorNetty.IO_SELECT_COUNT, "16");

        System.setProperty("org.jboss.netty.epollBugWorkaround", "true");

        SpringApplication.run(GatewayServerApplication.class, args);
    }
}
