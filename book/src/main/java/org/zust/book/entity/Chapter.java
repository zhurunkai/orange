package org.zust.book.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * @author: Linxy
 * @Date: 2021/6/5 21:47
 * @function: 章节
 **/
@Entity
@Getter
@Setter
@Table(name = "chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11)")
    private Integer id;

    @Column(columnDefinition = "varchar comment '章节名'")
    private String name;

    @Column(columnDefinition = "int comment '章节数'")
    private Integer num;

    private String start;

    private String end;

    @Column(columnDefinition = "int comment '所属书的id'")
    private Integer book;
}
