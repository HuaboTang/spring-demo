package com.vbobot.demo.seata.server.two.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import io.seata.rm.datasource.DataSourceProxy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataSourceConfig {

    @Value("${seata.enable:true}") Boolean seataEnable;
    @Value("${seata.enableAutoDataSourceProxy:true}") Boolean enableAutoDataSourceProxy;
    @Value("${seata.enable-auto-data-source-proxy:true}") Boolean enableAutoDataSourceProxy2;

    @PostConstruct
    public void init() {
        log.info("datasource-config: {}, {}, {}", seataEnable, enableAutoDataSourceProxy, enableAutoDataSourceProxy2);
    }

//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
    public DataSourceWrapper dataSourceWrapper() {
        return new DataSourceWrapper();
    }

    public static class DataSourceWrapper implements BeanPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) {
            if (bean instanceof DataSource) {
                return new DataSourceProxy((DataSource)bean);
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }
    }
}
