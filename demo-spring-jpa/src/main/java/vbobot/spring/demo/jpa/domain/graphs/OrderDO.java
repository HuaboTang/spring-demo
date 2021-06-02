package vbobot.spring.demo.jpa.domain.graphs;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

/**
 * @author Bobo
 * @date 2021/6/1
 */
@Data
@Entity
@Table(name = "order_info")
@ToString(exclude = {"goods", "orderDetail"})
public class OrderDO {

    @Id
    private Integer id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private List<OrderGoodsDO> goods;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "order")
    private OrderDetailDO orderDetail;
}
