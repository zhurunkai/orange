package org.zust.book.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: Linxy
 * @Date: 2021/6/14 8:57
 * @function:
 **/
@Entity
@Getter
@Setter
public class OperateRecords  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11)")
    private Integer id;

    @Column(columnDefinition = "varchar comment '操作类型'")
    private String type;

    @Column(columnDefinition = "int comment '操作员'")
    private Integer operator;

    @Column(columnDefinition = "int comment '书chain id'")
    private Integer bookChain;

    @CreatedDate
    @Column(columnDefinition = "datetime comment '操作时间'")
    private Date datetime;

    public OperateRecords() {
        this.datetime = new Date();
    }
}
