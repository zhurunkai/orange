package org.zust.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zust.book.entity.Book;

import java.util.List;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:24
 * @function:
 **/
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findOneById(Integer id);

    @Query("from Book b where b.id in ?1")
    List<Book> findAllByIds(List<Integer> ids);
}
