package vbobot.spring.demo.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vbobot.spring.demo.jpa.domain.UniqueKeyDomain;

/**
 * @author Bobo
 * @date 2021/5/7
 */
@Repository
public interface UniqueKeyDomainRepository extends CrudRepository<UniqueKeyDomain, Integer> {
}
