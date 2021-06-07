package org.zust.book.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.book.entity.Book;
import org.zust.book.entity.BookShelf;
import org.zust.book.repository.BookShelfRepository;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.dto.BookShelfDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookShelfService;
import org.zust.interfaceapi.utils.ResType;

import java.util.HashMap;

/**
 * @author: Linxy
 * @Date: 2021/6/7 11:03
 * @function:
 **/
@Service
public class BookShelfServiceImpl implements BookShelfService {

    @Autowired
    private BookShelfRepository bookShelfRepository;

    @Override
    public ResType addBookShelf(HashMap map) {
        return null;
    }

    @Override
    public ResType deleteBookShelfById(HashMap map) {
        return null;
    }

    @Override
    public ResType updateBookShelfById(HashMap map) {
        return null;
    }

    @Override
    public ResType getBookShelfById(Integer id) {

        if (id==null) return new ResType(101);

        BookShelf bookShelf = bookShelfRepository.findBookShelfById(id);

        if (bookShelf!=null){
            return new ResType(200,e2d(bookShelf));
        }else {
            return new ResType(102); // 11表示
        }
    }

    @Override
    public ResType getBookShelfLists(String Token) {
        return null;
    }

    public BookShelfDto e2d(BookShelf bookShelf) {
        BookShelfDto bookShelfDto = new BookShelfDto();
        bookShelfDto.setOwner(new BookUserDto());
        BeanUtils.copyProperties(bookShelf, bookShelfDto);
        return bookShelfDto;
    }

}
