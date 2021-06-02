package com.vbobot.demo.sleuth.feign.header;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Bobo
 * @date 2021/5/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
        OryxFeignPassHeaderConfiguration.class
})
public @interface EnableFeignAutoPassOryxHeader {
}
