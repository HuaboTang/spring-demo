package vbobot.spring.demo.jpa.domain.graphs;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Bobo
 * @date 2021/6/1
 */
@Data
@Entity
@Table(name = "order_detail")
public class OrderDetailDO {

    @Id
    private Integer id;
    private String detail;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private OrderDO order;
}
