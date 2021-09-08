package com.vbobot.demo.seata.server.two.config;

import static io.seata.spring.annotation.datasource.AutoDataSourceProxyRegistrar.BEAN_NAME_SEATA_AUTO_DATA_SOURCE_PROXY_CREATOR;
import static io.seata.spring.annotation.datasource.AutoDataSourceProxyRegistrar.BEAN_NAME_SEATA_DATA_SOURCE_BEAN_POST_PROCESSOR;

import io.seata.spring.annotation.datasource.SeataAutoDataSourceProxyCreator;
import io.seata.spring.annotation.datasource.SeataDataSourceBeanPostProcessor;
import io.seata.spring.boot.autoconfigure.properties.SeataProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DataSourceConfig {

    @Bean(BEAN_NAME_SEATA_AUTO_DATA_SOURCE_PROXY_CREATOR)
    @ConditionalOnMissingBean(SeataAutoDataSourceProxyCreator.class)
    public SeataAutoDataSourceProxyCreator seataAutoDataSourceProxyCreator(
            SeataProperties seataProperties) {
        return new SeataAutoDataSourceProxyCreator(seataProperties.isUseJdkProxy(),
                seataProperties.getExcludesForAutoProxying(), seataProperties.getDataSourceProxyMode());
    }

    @Bean(BEAN_NAME_SEATA_DATA_SOURCE_BEAN_POST_PROCESSOR)
    @ConditionalOnMissingBean(SeataDataSourceBeanPostProcessor.class)
    public SeataDataSourceBeanPostProcessor seataDataSourceBeanPostProcessor(SeataProperties seataProperties) {
        return new SeataDataSourceBeanPostProcessor(seataProperties.getExcludesForAutoProxying(), seataProperties.getDataSourceProxyMode());
    }

//    @Resource
//    ApplicationContext applicationContext;
//
//    @Bean
//    @ConditionalOnMissingBean
//    public DataSourceWrapper dataSourceWrapper() {
//        return new DataSourceWrapper();
//    }
//
//    public static class DataSourceWrapper implements BeanPostProcessor {
//
//        @Override
//        public Object postProcessBeforeInitialization(Object bean, String beanName) {
//            return bean;
//        }
//
//        @Override
//        public Object postProcessAfterInitialization(Object bean, String beanName)
//                throws BeansException {
//            if (bean instanceof DataSource && !(bean instanceof DataSourceProxy)) {
//                System.out.println(beanName);
//                return new DataSourceProxy((DataSource) bean);
//            }
//            return bean;
//        }
//    }
}
