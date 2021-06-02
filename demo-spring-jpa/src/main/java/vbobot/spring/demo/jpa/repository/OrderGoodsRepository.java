package vbobot.spring.demo.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vbobot.spring.demo.jpa.domain.graphs.OrderGoodsDO;

/**
 * @author Bobo
 * @date 2021/6/1
 */
@Repository
public interface OrderGoodsRepository extends CrudRepository<OrderGoodsDO, Integer> {
}
