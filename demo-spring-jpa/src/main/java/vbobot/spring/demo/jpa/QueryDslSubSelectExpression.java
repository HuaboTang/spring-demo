package vbobot.spring.demo.jpa;

import com.google.common.collect.Lists;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import vbobot.spring.demo.jpa.domain.QScore;
import vbobot.spring.demo.jpa.domain.QStudent;
import vbobot.spring.demo.jpa.domain.Student;
import vbobot.spring.demo.jpa.repository.StudentRepository;

/**
 * @author Liang.Zhuge
 * @date 22/02/2018
 */
@Service
public class QueryDslSubSelectExpression {
    @Resource private StudentRepository studentRepository;

    List<Student> students(String subjectName, Integer score) {
        QScore qScore = QScore.score;
        JPQLQuery<Integer> subQuery = JPAExpressions.selectFrom(qScore)
                .where(qScore.subject.name.eq(subjectName), qScore.value.goe(score))
                .select(qScore.studentId);
        BooleanExpression exp = QStudent.student.id.in(subQuery);
        exp = exp.or(QStudent.student.name.eq("aaa"));
        exp = exp.and(QStudent.student.name.eq("bbb"));
        exp = exp.and(QStudent.student.name.like("name").or(QStudent.student.id.eq(123)));
        Iterable<Student> all = studentRepository.findAll(exp);
        return Lists.newCopyOnWriteArrayList(all);
    }
}
