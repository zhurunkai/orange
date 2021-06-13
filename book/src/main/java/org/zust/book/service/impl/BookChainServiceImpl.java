package org.zust.book.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.book.entity.Book;
import org.zust.book.entity.BookChain;
import org.zust.book.entity.BookShelf;
import org.zust.book.repository.BookChainRepository;
import org.zust.book.repository.BookRepository;
import org.zust.book.repository.BookShelfRepository;
import org.zust.interfaceapi.dto.BookChainDto;
import org.zust.interfaceapi.dto.BookDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookChainService;
import org.zust.interfaceapi.service.BookService;
import org.zust.interfaceapi.service.BookUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Linxy
 * @Date: 2021/6/10 19:28
 * @function:
 **/
@Service
public class BookChainServiceImpl implements BookChainService {

    @Reference(check = false)
    private BookUserService bookUserService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookShelfRepository shelfRepository;
    @Autowired
    private BookChainRepository chainRepository;

    @Override
    public ResType getChainBySidAndCid(HashMap map) {

        String sid = (String) map.get("sid");
        String cid = (String) map.get("cid");
        Integer owner = (Integer) map.get("owner");
        if (sid==null || cid==null) return new ResType(400,101);

        try {
            BookChain chain = chainRepository.findById(Integer.parseInt(cid)).orElse(null);
            if (chain==null) return new ResType(400,111);

            BookChain bookChain = chainRepository.findByIdAndShelf(chain.getId(), Integer.parseInt(sid));
            if (bookChain==null) return new ResType(400,112);

            Book book = bookRepository.findById(bookChain.getOwner()).orElse(null);

            return new ResType(e2d(bookChain,book,owner));

        }catch (Exception e){
            e.printStackTrace();
            return new ResType(400,105);
        }

    }

    @Override
    public ResType updateChainLocation(HashMap map) {

        String sid = (String) map.get("sid");
        String cid = (String) map.get("cid");
        String shelf = (String) map.get("shelf");
        Integer owner = (Integer) map.get("owner");
        if (sid==null || cid==null ||shelf==null) return new ResType(400,101);

        try {
            BookChain chain = chainRepository.findById(Integer.parseInt(cid)).orElse(null);
            if (chain==null) return new ResType(400,111);

            BookChain bookChain = chainRepository.findByIdAndShelf(chain.getId(), Integer.parseInt(sid));
            if (bookChain==null) return new ResType(400,112);

            BookShelf exShelf = shelfRepository.findByIdAndOwner(Integer.parseInt(shelf), owner);
            if (exShelf==null) return new ResType(400,113);

            bookChain.setShelf(exShelf.getId());
            BookChain save = chainRepository.save(bookChain);

            Book book = bookRepository.findById(bookChain.getOwner()).orElse(null);

            return new ResType(e2d(save,book,owner));

        }catch (Exception e){
            e.printStackTrace();
            return new ResType(400,105);
        }

    }

    @Override
    public ResType updateChain(HashMap map) {
        String sid = (String) map.get("sid");
        String cid = (String) map.get("cid");
        String name = (String) map.get("name");
        String cover = (String) map.get("cover");
        Integer process = (Integer) map.get("process");
        Integer shelf = (Integer) map.get("shelfId");
        Integer alive = (Integer) map.get("alive");
        Integer owner = (Integer) map.get("owner");

        if (sid==null || cid==null) return new ResType(400,101);

        try {
            BookChain chain = chainRepository.findById(Integer.parseInt(cid)).orElse(null);
            if (chain==null) return new ResType(400,111);

            BookChain bookChain = chainRepository.findByIdAndShelf(chain.getId(), Integer.parseInt(sid));
            if (bookChain==null) return new ResType(400,112);

            if (name!=null) bookChain.setName(name);
            if (cover!=null) bookChain.setCover(cover);
            if (process!=null) bookChain.setProcess(process);
            if (shelf!=null){
                BookShelf exShelf = shelfRepository.findByIdAndOwner(shelf, owner);
                if (exShelf!=null) bookChain.setShelf(shelf);
            }
            bookChain.setAlive(alive);
            BookChain save = chainRepository.save(bookChain);

            Book book = bookRepository.findById(bookChain.getOwner()).orElse(null);
            return new ResType(e2d(save,book,owner));

        }catch (Exception e){
            e.printStackTrace();
            return new ResType(400,105);
        }

    }

    @Override
    public ResType deleteChain(HashMap map) {
        return null;
    }

    @Override
    public ResType addReaderAccord(HashMap map) {

        String cid = (String) map.get("chainId");
        Integer owner = (Integer) map.get("owner");
        if (cid==null) return new ResType(400,101);




        return null;
    }

    @Override
    public ResType getAllChain(HashMap map) {

        String sid = (String) map.get("id");
        Integer owner = (Integer) map.get("owner");
        if (sid==null) return new ResType(400,101);

        BookShelf exShelf = shelfRepository.findByIdAndOwner(Integer.parseInt(sid), owner);
        if (exShelf==null) return new ResType(400,113);

        List<BookChain> chainList = chainRepository.findAllByShelf(exShelf.getId());
        ArrayList<BookChainDto> returnList = new ArrayList<>();
        if (chainList!=null){
            for (BookChain bookChain : chainList) {
                Book book = bookRepository.findById(bookChain.getOwner()).orElse(null);
                returnList.add(e2d(bookChain,book,owner));
            }
        }

        return new ResType(returnList);
    }

    @Override
    public ResType CollectChain(HashMap map) {
        return null;
    }

    public BookDto e2d(Book book) {
        BookDto bookDto = new BookDto();
        ResType buRes = bookUserService.findBookUserAllInformById(book.getOwner());
        bookDto.setOwner((BookUserDto) buRes.getData());
        BeanUtils.copyProperties(book, bookDto);
        return bookDto;
    }

    public BookChainDto e2d(BookChain chain, Book book,Integer uid) {
        BookChainDto chainDto = new BookChainDto();
        chainDto.setOrigin(e2d(book));
        ResType buRes = bookUserService.findBookUserAllInformById(uid);
        chainDto.setOwner((BookUserDto) buRes.getData());
        BeanUtils.copyProperties(chain, chainDto);
        return chainDto;
    }

}
