package vbobot.spring.demo.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import vbobot.spring.demo.jpa.domain.Student;

/**
 * @author Liang.Zhuge
 * @date 22/02/2018
 */
public interface StudentRepository extends JpaRepository<Student, Integer>, QueryDslPredicateExecutor<Student> {
}
