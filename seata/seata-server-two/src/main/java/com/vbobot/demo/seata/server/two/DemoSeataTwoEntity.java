package com.vbobot.demo.seata.server.two;

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
@Table(name = "demo_seata_two")
public class DemoSeataTwoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
