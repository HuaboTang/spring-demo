package vbobot.spring.demo.jpa.repository;

import org.junit.Test;
import org.springframework.test.annotation.Commit;

import javax.annotation.Resource;

import vbobot.spring.demo.jpa.TestWithSpring;
import vbobot.spring.demo.jpa.domain.UniqueKeyDomain;

/**
 * @author Bobo
 * @date 2021/5/7
 */
public class UniqueKeyDomainRepositoryTest extends TestWithSpring {
    @Resource UniqueKeyDomainRepository uniqueKeyDomainRepository;

    @Test
    @Commit
    public void testSave() {
        uniqueKeyDomainRepository.save(new UniqueKeyDomain().setId(1).setUniqueKey("1"));
        uniqueKeyDomainRepository.save(new UniqueKeyDomain().setId(2).setUniqueKey("1"));
        System.out.println(uniqueKeyDomainRepository.findById(1));
    }
}
