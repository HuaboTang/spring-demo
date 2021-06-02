package vbobot.spring.demo.jpa.repository;

import org.junit.Test;

import java.util.Optional;

import javax.annotation.Resource;

import vbobot.spring.demo.jpa.TestWithSpring;
import vbobot.spring.demo.jpa.domain.graphs.OrderDetailDO;

/**
 * @author Bobo
 * @date 2021/6/1
 */
public class OrderDetailRepositoryTest extends TestWithSpring {
    @Resource OrderDetailRepository orderDetailRepository;

    @Test
    public void testFindById() throws Exception {
        final Optional<OrderDetailDO> oDetail = orderDetailRepository.findById(1);
        oDetail.ifPresent(System.out::println);
    }
}
