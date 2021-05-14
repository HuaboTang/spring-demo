package vbobot.spring.demo.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Bobo
 * @date 2021/5/7
 */
@Data
@Entity
@Table(
        name = "unique_key_domain",
        uniqueConstraints = @UniqueConstraint(columnNames = {"unique_key"})
)
@Accessors(chain = true)
public class UniqueKeyDomain {

    @Id
    private Integer id;

    @Column(name = "unique_key")
    private String uniqueKey;
}
