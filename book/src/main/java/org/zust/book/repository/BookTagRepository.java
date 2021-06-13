package org.zust.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.book.entity.BookTag;

/**
 * @author: Linxy
 * @Date: 2021/6/13 20:15
 * @function:
 **/
public interface BookTagRepository extends JpaRepository<BookTag,Integer> {
}
