package org.zust.book.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.book.entity.Book;
import org.zust.book.entity.BookChain;
import org.zust.book.repository.BookChainRepository;
import org.zust.book.repository.BookRepository;
import org.zust.interfaceapi.dto.BookChainDto;
import org.zust.interfaceapi.dto.BookDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookChainService;
import org.zust.interfaceapi.service.BookUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.HashMap;

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
    private BookChainRepository chainRepository;

    @Override
    public ResType getChainBySidAndCid(HashMap map) {

        String sid = (String) map.get("sid");
        String cid = (String) map.get("cid");
        if (sid==null || cid==null) return new ResType(400,101);

        try {
            BookChain chain = chainRepository.findById(Integer.parseInt(cid)).orElse(null);
            if (chain==null) return new ResType(400,111);

            BookChain bookChain = chainRepository.findByIdAndShelf(chain.getId(), Integer.parseInt(sid));
            if (bookChain==null) return new ResType(400,112);



        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResType updateChainLocation(HashMap map) {
        return null;
    }

    @Override
    public ResType updateChain(HashMap map) {
        return null;
    }

    @Override
    public ResType deleteChain(HashMap map) {
        return null;
    }

    @Override
    public ResType addReaderAccord(HashMap map) {
        return null;
    }

    @Override
    public ResType getAllChain(HashMap map) {
        return null;
    }

    @Override
    public ResType CollectChain(HashMap map) {
        return null;
    }

    public BookChainDto e2d(BookChain bookChain) {
        BookChainDto bookChainDto = new BookChainDto();
        Book book = bookRepository.findById(bookChain.getOrigin()).orElse(null);
        ResType buRes = bookUserService.findBookUserAllInformById(book.getOwner());
        
//        bookDto.setOwner((BookUserDto) buRes.getData());

        BeanUtils.copyProperties(bookChain, bookChainDto);
        return bookChainDto;
    }

}
