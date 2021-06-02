package vbobot.spring.demo.jpa.domain.graphs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

/**
 * @author Bobo
 * @date 2021/6/1
 */
@Data
@Entity
@Table(name = "order_goods")
@ToString(exclude = "order")
public class OrderGoodsDO {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private OrderDO order;
}
