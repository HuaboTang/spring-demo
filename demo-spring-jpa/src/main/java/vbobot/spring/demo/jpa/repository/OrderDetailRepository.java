package vbobot.spring.demo.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vbobot.spring.demo.jpa.domain.graphs.OrderDetailDO;

/**
 * @author Bobo
 * @date 2021/6/1
 */
@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetailDO, Integer> {
}
