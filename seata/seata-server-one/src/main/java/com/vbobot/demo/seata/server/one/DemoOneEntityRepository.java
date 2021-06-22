package com.vbobot.demo.seata.server.one;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/6/8
 */
@Repository
public interface DemoOneEntityRepository extends CrudRepository<DemoOneEntity, Integer> {
}
