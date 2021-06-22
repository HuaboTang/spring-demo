package com.vbobot.demo.seata.server.one;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/6/8
 */
@Slf4j
@Configuration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        final String xid = RootContext.getXID();
        if (StringUtils.isNotBlank(xid)) {
            log.info("Feign-invoke-pass-xid:{}", xid);
            template.header(RootContext.KEY_XID, xid);
        }
    }
}
