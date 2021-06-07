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

import java.util.ArrayList;
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

        String name = (String) map.get("name");
        Integer isRoot = (Integer) map.get("is_root");
        if (isRoot==null) isRoot = 1;
        Integer ownerId = (Integer) map.get("owner");

        if (name==null || ownerId==null) return new ResType(400);

        BookShelf save = bookShelfRepository.save(new BookShelf(name, ownerId, isRoot));
        return new ResType(200,e2d(save));

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

        BookShelf bookShelf = bookShelfRepository.findById(id).orElse(null);
        if (bookShelf!=null){
            return new ResType(200,e2d(bookShelf));
        }else {
            return new ResType(400);
        }


    }

    @Override
    public ResType getBookShelfLists(String Token) {
        ArrayList<BookShelf> list = bookShelfRepository.findAllByOwner(1);

        ArrayList<BookShelfDto> returnList = new ArrayList<>();
        if (list!=null){
            for (BookShelf bookShelf : list) {
                returnList.add(e2d(bookShelf));
            }
        }

        return new ResType(200,returnList);
    }

    public BookShelfDto e2d(BookShelf bookShelf) {
        BookShelfDto bookShelfDto = new BookShelfDto();
        bookShelfDto.setOwner(new BookUserDto());
        BeanUtils.copyProperties(bookShelf, bookShelfDto);
        return bookShelfDto;
    }

}
