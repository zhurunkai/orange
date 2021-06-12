package org.zust.book.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.book.entity.Book;
import org.zust.book.entity.BookChain;
import org.zust.book.entity.BookShelf;
import org.zust.book.repository.BookChainRepository;
import org.zust.book.repository.BookShelfRepository;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.dto.BookShelfDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookShelfService;
import org.zust.interfaceapi.service.BookUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Linxy
 * @Date: 2021/6/7 11:03
 * @function:
 **/
@Service
@org.apache.dubbo.config.annotation.Service
public class BookShelfServiceImpl implements BookShelfService {

    @Reference(check = false)
    private BookUserService bookUserService;
    @Autowired
    private BookShelfRepository bookShelfRepository;
    @Autowired
    private BookChainRepository chainRepository;

    @Override
    public ResType addBookShelf(HashMap map) {

        String name = (String) map.get("name");
        Integer owner = (Integer) map.get("owner");
        String isRoot;

        try {
            isRoot = (String) map.get("is_root");
            int iroot = -1;
            if (isRoot==null)
                iroot = 0;
            else
                iroot = Integer.parseInt(isRoot);

            if (name==null || owner==null) return new ResType(400,103);

            BookShelf save = bookShelfRepository.save(new BookShelf(name, owner, iroot));
            return new ResType(e2d(save,owner));
        }catch (Exception e){
            e.printStackTrace();
            return new ResType(400,105);
        }

    }

    @Override
    public ResType deleteBookShelfById(HashMap map) {

        String bsid = (String) map.get("bsid");
        Integer owner = (Integer) map.get("owner");

        BookShelf defaultShelf = bookShelfRepository.findByDefaultShelf(owner);
        BookShelf targetShelf = bookShelfRepository.findById(Integer.parseInt(bsid)).orElse(null);
        if (targetShelf==null) return new ResType(400,102);

        // 默认书架不允许删除
        if (targetShelf.equals(defaultShelf)) return new ResType(400,110);
        List<BookChain> bookChain = chainRepository.findAllByShelf(targetShelf.getId());
        // 将待删除目录下的书添加到默认书架中
        for (BookChain chain : bookChain) {
            chain.setShelf(defaultShelf.getId());
            chainRepository.save(chain);
        }
        // 最后再删除待删除的数据
        bookShelfRepository.delete(targetShelf);
        return new ResType(200);
    }

    @Override
    public ResType updateBookShelfById(HashMap map) {

        String name = (String) map.get("name");
        Integer owner = (Integer) map.get("owner");
        String bsid = (String) map.get("id");

        try {
            if (bsid==null) return new ResType(400,101);

            // 判断该用户的书架里是否已使用该name
            BookShelf byNameAndOwner = bookShelfRepository.findByNameAndOwner(name, owner);
            if (byNameAndOwner!=null) return new ResType(400,106);

            BookShelf bookShelf = bookShelfRepository.findById(Integer.parseInt(bsid)).orElse(null);
            if (bookShelf!=null){
                bookShelf.setName(name);
                BookShelf save = bookShelfRepository.save(bookShelf);
                return new ResType(e2d(save,owner));
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
                return new ResType(e2d(bookShelf,bookShelf.getOwner()));
            }else {
                return new ResType(400,102);
            }
        }catch (Exception e){
            return new ResType(400,105);
        }




    }

    @Override
    public ResType getBookShelfLists(Integer uid) {
        ArrayList<BookShelf> list = bookShelfRepository.findAllByOwner(uid);

        ArrayList<BookShelfDto> returnList = new ArrayList<>();
        if (list!=null){
            for (BookShelf bookShelf : list) {
                returnList.add(e2d(bookShelf,uid));
            }
        }

        return new ResType(returnList);
    }

    public BookShelfDto e2d(BookShelf bookShelf,Integer uid) {
        BookShelfDto bookShelfDto = new BookShelfDto();
        ResType buRes = bookUserService.findBookUserAllInformById(uid);
        bookShelfDto.setOwner((BookUserDto) buRes.getData());
        BeanUtils.copyProperties(bookShelf, bookShelfDto);
        return bookShelfDto;
    }

}
