package vbobot.spring.demo.jpa;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
@Transactional()
public class TestWithSpring {
    @Resource private ApplicationContext ac;

    <T> T getBean(Class<T> clazz) {
        return ac.getBean(clazz);
    }

    @SuppressWarnings(value="unchecked")
    <T> T unwrap(T t) {
        if (AopUtils.isAopProxy(t) && t instanceof Advised) {
            try {
                return (T) ((Advised) t).getTargetSource().getTarget();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    protected void setField(Object target, Object fieldValue, Class<?> type) {
        ReflectionTestUtils.setField(unwrap(target), null, fieldValue, type);
    }

    protected void revert(Object target, Class<?> type) {
        setField(target, getBean(type), type);
    }
}
