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
        String isRoot;
        String ownerId;

        try {
            isRoot = (String) map.get("is_root");
            int iroot = -1;
            if (isRoot==null)
                iroot = 0;
            else
                iroot = Integer.parseInt(isRoot);

            ownerId = (String) map.get("owner");
            if (name==null || ownerId==null) return new ResType(400,103);

            // 需要调用cgg那边通过用户id查找用户的接口
            Integer oid = Integer.parseInt(ownerId);
            //

            BookShelf save = bookShelfRepository.save(new BookShelf(name, oid, iroot));
            return new ResType(e2d(save));
        }catch (Exception e){
            e.printStackTrace();
            return new ResType(400,105);
        }






    }

    @Override
    public ResType deleteBookShelfById(HashMap map) {
        return null;
    }

    @Override
    public ResType updateBookShelfById(HashMap map) {

        String name = (String) map.get("name");
        Integer uid = (Integer) map.get("uid");
        String bsid = (String) map.get("id");

        try {
            if (bsid==null) return new ResType(400,101);

            // 判断该用户的书架里是否已使用该name
            BookShelf byNameAndOwner = bookShelfRepository.findByNameAndOwner(name, uid);
            if (byNameAndOwner!=null) return new ResType(400,106);

            BookShelf bookShelf = bookShelfRepository.findById(Integer.parseInt(bsid)).orElse(null);
            if (bookShelf!=null){
                bookShelf.setName(name);
                BookShelf save = bookShelfRepository.save(bookShelf);
                return new ResType(e2d(save));
            }else {
                return new ResType(400,102);
            }
        }catch (Exception e){
            return new ResType(400,105);
        }

    }

    @Override
    public ResType getBookShelfById(String id) {

        if (id==null) return new ResType(400,101);

        try {

            BookShelf bookShelf = bookShelfRepository.findById(Integer.parseInt(id)).orElse(null);
            if (bookShelf!=null){
                return new ResType(e2d(bookShelf));
            }else {
                return new ResType(400,102);
            }
        }catch (Exception e){
            return new ResType(400,105);
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

        return new ResType(returnList);
    }

    public BookShelfDto e2d(BookShelf bookShelf) {
        BookShelfDto bookShelfDto = new BookShelfDto();
        bookShelfDto.setOwner(new BookUserDto());
        BeanUtils.copyProperties(bookShelf, bookShelfDto);
        return bookShelfDto;
    }

}
