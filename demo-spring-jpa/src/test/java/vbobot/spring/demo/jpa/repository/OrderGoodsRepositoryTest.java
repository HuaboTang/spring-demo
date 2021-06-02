package vbobot.spring.demo.jpa.repository;

import org.junit.Test;

import java.util.Optional;

import javax.annotation.Resource;

import vbobot.spring.demo.jpa.TestWithSpring;
import vbobot.spring.demo.jpa.domain.graphs.OrderGoodsDO;

/**
 * @author Bobo
 * @date 2021/6/1
 */
public class OrderGoodsRepositoryTest extends TestWithSpring {
    @Resource OrderGoodsRepository orderGoodsRepository;

    @Test
    public void testFindById() {
        final Optional<OrderGoodsDO> oGoods = orderGoodsRepository.findById(1);
        oGoods.ifPresent(System.out::println);
    }
}
