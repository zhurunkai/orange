package org.zust.book.entity;

import lombok.Getter;
import lombok.Setter;
import org.zust.interfaceapi.dto.BookDto;
import org.zust.interfaceapi.dto.TabDto;

import javax.persistence.*;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:16
 * @function: 书的标签
 **/
@Entity
@Getter
@Setter
@Table(name = "book_tab")
public class BookTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11)")
    private Integer id;

    @Column(columnDefinition = "int(11) comment '所属书的id'")
    private Integer book;

    @Column(columnDefinition = "int(11) comment '所属标签的id'")
    private Integer tab;

}
