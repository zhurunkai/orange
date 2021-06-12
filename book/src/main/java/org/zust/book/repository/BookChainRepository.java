package org.zust.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.book.entity.BookChain;
import org.zust.book.entity.BookShelf;

import java.util.List;

/**
 * @author: Linxy
 * @Date: 2021/6/8 17:12
 * @function:
 **/
public interface BookChainRepository extends JpaRepository<BookChain,Integer> {

    BookChain findByIdAndShelf(Integer cid,Integer sid);

    List<BookChain> findAllByShelf(Integer bsid);

}
