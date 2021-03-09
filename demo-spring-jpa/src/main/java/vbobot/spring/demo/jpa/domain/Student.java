package vbobot.spring.demo.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Liang.Zhuge
 * @date 22/02/2018
 */
@Entity
@Table(name = "student")
@Data
public class Student {

    @Id
    private Integer id;
    private String name;
}
