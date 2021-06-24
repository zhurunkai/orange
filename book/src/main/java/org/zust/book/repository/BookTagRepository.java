package org.zust.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zust.book.entity.Book;
import org.zust.book.entity.BookTag;

import java.util.List;

/**
 * @author: Linxy
 * @Date: 2021/6/13 20:15
 * @function:
 **/
public interface BookTagRepository extends JpaRepository<BookTag,Integer> {

    BookTag findOneById(Integer id);

    List<BookTag> findAllByBook(Integer bid);
    @Query(value = "SELECT b.book FROM BookTag b WHERE b.tab in ?1")
    List<Integer> getBookIdsByTabIds(List<Integer> ids);

}
