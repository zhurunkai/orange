package org.zust.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.book.entity.BookShelf;

import java.util.Optional;

/**
 * @author: Linxy
 * @Date: 2021/6/7 11:02
 * @function:
 **/
public interface BookShelfRepository extends JpaRepository<BookShelf,Integer> {

    BookShelf findBookShelfById(Integer id);

}
