package vbobot.spring.demo.jpa;

import org.junit.Test;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Liang.Zhuge
 * @date 22/02/2018
 */
@Slf4j
public class QueryDslSubSelectExpressionTest extends TestWithSpring {
    @Resource private QueryDslSubSelectExpression queryDslSubSelectExpression;

    @Test
    public void testStudent() throws Exception {
        log.info("sdf");
        queryDslSubSelectExpression.students("测试", 30);
    }

}
