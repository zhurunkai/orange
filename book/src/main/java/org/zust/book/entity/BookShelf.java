package org.zust.book.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.zust.interfaceapi.dto.BookUserDto;

import javax.persistence.*;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:11
 * @function: 书架
 **/
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bookshelf")
public class BookShelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11)")
    private Integer id;

    @Column(columnDefinition = "varchar comment '书架名'")
    private String name;

    @Column(columnDefinition = "int(11) comment '书架的拥有者'")
    private Integer owner;

    @Column(columnDefinition = "int(1) comment '是否为根目录，1是根目录，0不是根目录'")
    private Integer isRoot;

    public BookShelf(String name, Integer owner, Integer isRoot) {
        this.name = name;
        this.owner = owner;
        this.isRoot = isRoot;
    }
}
