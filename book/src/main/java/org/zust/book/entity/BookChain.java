package org.zust.book.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author: Linxy
 * @Date: 2021/6/6 11:03
 * @function: 用户的书目
 **/
@Entity
@Getter
@Setter
public class BookChain {
    @Id
    @Column(columnDefinition = "int(11)")
    private Integer id;

    @Column(columnDefinition = "varchar comment '书名'")
    private String name;

    @Column(columnDefinition = "varchar comment '书的主人'")
    private Integer owner;

    @Column(columnDefinition = "varchar comment '书的封面'")
    private String cover;

    

}
