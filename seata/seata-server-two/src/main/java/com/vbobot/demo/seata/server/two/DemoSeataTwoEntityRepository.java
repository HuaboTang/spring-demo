package com.vbobot.demo.seata.server.two;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bobo
 * @date 2021/6/8
 */
@Repository
public interface DemoSeataTwoEntityRepository extends CrudRepository<DemoSeataTwoEntity, Integer> {
}
