package com.vbobot.demo.seata.server.one;

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
public class SeataOneService {
    @Resource DemoOneEntityRepository demoOneEntityRepository;
    @Resource DemoTwoAdapter.DemoTwoFeign demoTwoFeign;

    @GlobalTransactional(rollbackFor = Exception.class, name = "commit")
    public String commit() {
        final DemoOneEntity entity = new DemoOneEntity();
        entity.setName(System.currentTimeMillis() + "");
        demoOneEntityRepository.save(entity);
        return "commit";
    }

    @GlobalTransactional(rollbackFor = Exception.class, name = "ory-demo")
    public String rollbackAtInvoker() {
        final DemoOneEntity demoOneEntity = new DemoOneEntity();
        final String name = System.currentTimeMillis() + "";
        log.info("Rollback, name:{}", name);
        demoOneEntity.setName(name);
        demoOneEntityRepository.save(demoOneEntity);

        demoTwoFeign.commit();

        throw new NullPointerException("test");
    }

    @GlobalTransactional(rollbackFor = Exception.class, name = "rollback-at-callee")
    public String rollbackAtCallee() {
        final DemoOneEntity demoOneEntity = new DemoOneEntity();
        final String name = System.currentTimeMillis() + "";
        log.info("Rollback, name:{}", name);
        demoOneEntity.setName(name);
        demoOneEntityRepository.save(demoOneEntity);

        demoTwoFeign.rollback();

        return "rollbackAtCallee";
    }
}
