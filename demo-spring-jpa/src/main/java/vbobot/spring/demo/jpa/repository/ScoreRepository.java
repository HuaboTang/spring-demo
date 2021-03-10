package vbobot.spring.demo.jpa.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vbobot.spring.demo.jpa.domain.Score;

/**
 * @author Liang.Zhuge
 * @date 22/02/2018
 */
@Repository
public interface ScoreRepository extends CrudRepository<Score, Integer>, QuerydslPredicateExecutor<Score> {
}
