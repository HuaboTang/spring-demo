package vbobot.spring.demo.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import vbobot.spring.demo.jpa.domain.graphs.OrderDO;

/**
 * @author Bobo
 * @date 2021/6/1
 */
@Repository
public interface OrderRepository extends CrudRepository<OrderDO, Integer> {

//    @EntityGraph(attributePaths={"orderDetail", "goods"})
    @Override
    Optional<OrderDO> findById(Integer integer);
}
