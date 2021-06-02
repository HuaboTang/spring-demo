package vbobot.spring.demo.jpa.repository;

import org.junit.Test;

import java.util.Optional;

import javax.annotation.Resource;

import vbobot.spring.demo.jpa.TestWithSpring;
import vbobot.spring.demo.jpa.domain.graphs.OrderDO;

/**
 * @author Bobo
 * @date 2021/6/1
 */
public class OrderRepositoryTest extends TestWithSpring {
    @Resource OrderRepository orderRepository;

    @Test
    public void testFindById() {
        final Optional<OrderDO> order = orderRepository.findById(1);
        order.ifPresent(System.out::println);
    }
}
