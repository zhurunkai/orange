package org.zust.book.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.book.entity.Book;
import org.zust.book.entity.BookShelf;
import org.zust.book.repository.BookRepository;
import org.zust.interfaceapi.dto.BookDto;
import org.zust.interfaceapi.dto.BookShelfDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookService;
import org.zust.interfaceapi.utils.ResType;

import java.util.HashMap;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:33
 * @function:
 **/
@Service
@org.apache.dubbo.config.annotation.Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public ResType getBook(String id) {

        if (id==null) return new ResType(400,101);

        try {
            Book book = bookRepository.findById(Integer.parseInt(id)).orElse(null);
            if (book!=null){
                return new ResType(e2d(book));
            }else {
                return new ResType(400,102);
            }
        }catch (Exception e){
            return new ResType(400,105);
        }




    }

    @Override
    public ResType addBook(HashMap map) {
        return null;
    }

    public BookDto e2d(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setOwner(new BookUserDto());
        BeanUtils.copyProperties(book, bookDto);
        return bookDto;
    }

}
