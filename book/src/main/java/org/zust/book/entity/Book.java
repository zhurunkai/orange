package org.zust.book.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: Linxy
 * @Date: 2021/6/5 21:32
 * @function: 系统的书目
 **/
@Entity
@Getter
@Setter
@Table()
public class Book {

    @Id
    @Column(columnDefinition = "int(11)")
    private Integer id;

    @Column(columnDefinition = "varchar comment '书名'")
    private String name;

    @Column(columnDefinition = "varchar comment '书的主人'")
    private Integer owner;

    @Column(columnDefinition = "varchar comment '书的封面'")
    private String cover;


    private String epubUrl;

    @Column(columnDefinition = "int comment '0代表正在转换，1代表已转换完成'")
    private Integer convertStatus;

    @Column(columnDefinition = "varchar comment '书所在的url'")
    private String originUrl;


    private String originExt;





}
