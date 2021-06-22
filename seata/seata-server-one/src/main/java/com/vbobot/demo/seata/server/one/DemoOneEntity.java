package com.vbobot.demo.seata.server.one;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Bobo
 * @date 2021/6/8
 */
@Data
@Entity
@Table(name = "demo_one_entity")
public class DemoOneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
