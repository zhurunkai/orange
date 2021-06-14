package org.zust.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.book.entity.Book;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:24
 * @function:
 **/
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findOneById(Integer id);
}
