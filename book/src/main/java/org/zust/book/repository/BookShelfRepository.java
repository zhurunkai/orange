package org.zust.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    BookShelf findByIdAndOwner(Integer sid,Integer uid);

    BookShelf findByNameAndOwner(String name,Integer id);

    @Query("select bs from BookShelf bs where bs.owner=?1 and bs.isRoot=1")
    BookShelf findByDefaultShelf(Integer uid);
}
