package com.vbobot.spring.demo.spring.cache.dao;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Bobo
 * @date 2021/4/29
 */
@Data
@Accessors(chain = true)
public class CacheDemoEntity implements Serializable {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
}
