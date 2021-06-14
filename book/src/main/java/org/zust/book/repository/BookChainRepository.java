package org.zust.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zust.book.entity.Book;
import org.zust.book.entity.BookChain;
import org.zust.book.entity.BookShelf;

import java.util.List;

/**
 * @author: Linxy
 * @Date: 2021/6/8 17:12
 * @function:
 **/
public interface BookChainRepository extends JpaRepository<BookChain,Integer> {

    @Query("from BookChain bc where bc.alive=1 and bc.id=?1")
    BookChain findOneById(Integer id);

    BookChain findByIdAndOwner(Integer cid,Integer owner);

    @Query("from BookChain bc where bc.alive=1 and bc.id=?1 and bc.shelf=?2")
    BookChain findByIdAndShelf(Integer cid,Integer sid);

    @Query("from BookChain bc where bc.alive=1 and bc.shelf=?1")
    List<BookChain> findAllByShelf(Integer bsid);

}
