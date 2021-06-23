package org.zust.book.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zust.interfaceapi.dto.BookDto;
import org.zust.interfaceapi.dto.BookShelfDto;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: Linxy
 * @Date: 2021/6/6 11:03
 * @function: 用户的书目
 **/
@Entity
@Getter
@Setter
@Table(name = "book_chain")
@NoArgsConstructor
public class BookChain implements Serializable {

    public static final Integer ALIVE = 1;
    public static final Integer LIFELESS = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11)")
    private Integer id;

    @Column(columnDefinition = "varchar comment '书名'")
    private String name;

    @Column(columnDefinition = "int(11) comment '书的主人'")
    private Integer owner;

    @Column(columnDefinition = "int(11) comment '所属书架'")
    private Integer shelf;

    @Column(columnDefinition = "varchar comment '书的封面图url地址'")
    private String cover;

    @Column(columnDefinition = "int(11) comment '书的原始主人'")
    private Integer process;

    @Column(columnDefinition = "int(1) comment '0代表删除，1代表存在'")
    private Integer alive;

    @Column(columnDefinition = "varchar comment '书的原始url地址'")
    private Integer origin;

    public BookChain(String name, Integer owner, Integer shelf, String cover,  Integer origin) {
        this.name = name;
        this.owner = owner;
        this.shelf = shelf;
        this.cover = cover;
        this.process = 0;
        this.alive = ALIVE;
        this.origin = origin;
    }
}
