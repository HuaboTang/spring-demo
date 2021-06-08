package com.vbobot.demo.seata.server.two;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bobo
 * @date 2021/6/8
 */
@Slf4j
@Service
public class DemoSeataTwoService {
    @Resource DemoSeataTwoEntityRepository demoSeataTwoEntityRepository;

    @GlobalTransactional(rollbackFor = Exception.class, name = "ory-demo")
    public String commit() {
        final DemoSeataTwoEntity entity = new DemoSeataTwoEntity();
        final String name = System.currentTimeMillis() + "";
        entity.setName(name);
        demoSeataTwoEntityRepository.save(entity);
        log.info("Commit, name:{}", name);
        return "commit";
    }

    @GlobalTransactional(rollbackFor = Exception.class, name = "rollback")
    public String rollback() {
        final DemoSeataTwoEntity entity = new DemoSeataTwoEntity();
        final String name = System.currentTimeMillis() + "";
        entity.setName(name);
        demoSeataTwoEntityRepository.save(entity);
        log.info("Rollback, name:{}", name);
        throw new NullPointerException("Test");
    }
}
