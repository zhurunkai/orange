package org.zust.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.book.entity.BookShelf;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author: Linxy
 * @Date: 2021/6/7 11:02
 * @function:
 **/
public interface BookShelfRepository extends JpaRepository<BookShelf,Integer> {

    ArrayList<BookShelf> findAllByOwner(Integer id);

    BookShelf findByNameAndOwner(String name,Integer id);
}
