package vbobot.spring.demo.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import vbobot.spring.demo.jpa.domain.Student;

/**
 * @author Liang.Zhuge
 * @date 22/02/2018
 */
public interface StudentRepository extends JpaRepository<Student, Integer>, QuerydslPredicateExecutor<Student> {
}
