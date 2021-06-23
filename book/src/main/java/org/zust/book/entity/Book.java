package org.zust.book.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: Linxy
 * @Date: 2021/6/5 21:32
 * @function: 系统的书目
 **/
@Entity
@Getter
@Setter
@Table(name = "book")
public class Book  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11)")
    private Integer id;

    @Column(columnDefinition = "varchar comment '书名'")
    private String name;

    @Column(columnDefinition = "varchar comment '书的主人'")
    private Integer owner;

    @Column(columnDefinition = "varchar comment '书的封面'")
    private String cover;

    @Column(columnDefinition = "varchar comment '书的epub格式文件地址'")
    private String epubUrl;

    @Column(columnDefinition = "int comment '0代表正在转换，1代表已转换完成'")
    private Integer convertStatus;

    @Column(columnDefinition = "varchar comment '原始格式文件url'")
    private String originUrl;

    @Column(columnDefinition = "varchar comment '原始文件格式后缀（如pdf，txt等）'")
    private String originExt;



}
