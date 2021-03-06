package org.zust.book.service.impl;

import org.apache.commons.io.FileUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.zust.book.entity.Book;
import org.zust.book.entity.BookChain;
import org.zust.book.entity.BookShelf;
import org.zust.book.entity.BookTag;
import org.zust.book.repository.BookChainRepository;
import org.zust.book.repository.BookRepository;
import org.zust.book.repository.BookTagRepository;
import org.zust.interfaceapi.dto.BookChainDto;
import org.zust.interfaceapi.dto.BookDto;
import org.zust.interfaceapi.dto.BookShelfDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.*;
import org.zust.interfaceapi.utils.BookUtils;
import org.zust.interfaceapi.utils.FileUtil;
import org.zust.interfaceapi.utils.ResType;
import org.zust.interfaceapi.utils.Upload;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:33
 * @function:
 **/
@Service
@org.apache.dubbo.config.annotation.Service(timeout = 300000)
public class BookServiceImpl implements BookService {

    @Reference(check = false)
    private BookUserService bookUserService;
    @Reference(check = false)
    private TabService tabService;
    @Reference(check = false)
    private RecommendService recommendService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookTagRepository bookTagRepository;
    @Autowired
    private BookChainRepository chainRepository;

    @Override
    public ResType getBook(HashMap map) {

        String id = (String) map.get("id");
        if (id==null) return new ResType(400,101);

        try {
            Book book = bookRepository.findOneById(Integer.parseInt(id));
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

        String origin_url = (String) map.get("origin_url");
        String name = (String) map.get("name");
        Integer owner = (Integer) map.get("owner");
        Integer bookshelf = (Integer) map.get("bookshelf");
        String cover = (String) map.get("cover");

        // ????????????????????????
        String origin_ext = origin_url.substring(origin_url.lastIndexOf(".")).toLowerCase();

        try {
            // ?????????????????????????????????
            // ???????????????D??????????????????????????????temp_file?????????????????????download
            HashMap downloader = FileUtil.ThreadDownloader(origin_url);
            Boolean downloaderStatus = (Boolean) downloader.get("status");
            // 0?????????????????????-1??????????????????
            if (downloaderStatus){
                System.out.println("?????????????????????"+downloaderStatus);

                // ?????????????????????????????????
                // ??????book
                Book book = new Book();
                book.setName(name);
                book.setOwner(owner);
                book.setOriginUrl(origin_url);
                book.setOriginExt(origin_ext);
                book.setConvertStatus(0);
                if (cover!=null) book.setCover(cover);
                Book bookSave = bookRepository.save(book);

                // ??????book?????????tag
                ResType res = tabService.findTagIdByName(map);
                if (res.getStatus()==400&&res.getCode()==103) return new ResType(400,103);
                ArrayList data = (ArrayList) res.getData();
                for (Object tag : data) {
                    BookTag bookTag = new BookTag();
                    bookTag.setBook(bookSave.getId());
                    bookTag.setTab((Integer) tag);
                    bookTagRepository.save(bookTag);
                    tabService.isTagExist(owner,bookTag.getTab());
                }

                // ??????bookChain
                BookChain bookChain = new BookChain();
                bookChain.setName(name);
                bookChain.setOwner(owner);
                bookChain.setOrigin(bookSave.getId());
                bookChain.setAlive(1);
                bookChain.setShelf(bookshelf);
                bookChain.setProcess(0);
                if (cover!=null) bookChain.setCover(cover);
                BookChain chainSave = chainRepository.save(bookChain);

                // ?????????
                // ???.mobi???????????????????????????.epub??????
                if (origin_ext.equals(".mobi") || origin_ext.equals(".azw3") || origin_ext.equals(".txt")){
                    String fileName = (String) downloader.get("fileName");
                    // ???????????????????????????convert
                    boolean convert = BookUtils.convert("D:/book/download" + fileName + origin_ext, "D:/book/convert" + fileName + ".epub");
                }else if (origin_ext.equals(".epub")){
                    book.setConvertStatus(1);
                    book.setEpubUrl(origin_url);
                    String fileName = (String) downloader.get("fileName");
                    File file = new File("D:/book/download"+fileName+origin_ext);
                    BookUtils.getEpub(file,fileName);
                    String coverName = fileName + "_cover.jpg";
                    String coverUrl = Upload.fileQcloud(new File("D:/book/convert" + coverName ), coverName);

                    // ??????????????????
                    book.setCover(coverUrl);
                    bookRepository.save(book);
                    bookChain.setCover(coverUrl);
                    chainRepository.save(bookChain);
                }else if (origin_ext.equals(".pdf")){
                    book.setConvertStatus(1);
                    book.setEpubUrl(origin_url);
                    bookRepository.save(book);
                }

                if (chainSave!=null) return new ResType(e2d(chainSave,bookSave,owner));

            }

            return new ResType(500,104);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResType checkConvert(HashMap map) {

        Integer uid = (Integer) map.get("owner");
        String chain = (String) map.get("chain");

        if (chain==null) return new ResType(400,101);

        try {
            BookChain bookChain = chainRepository.findOneById(Integer.parseInt(chain));

            if (bookChain!=null){
                Book book = bookRepository.findOneById(bookChain.getOrigin());
                String originUrl = book.getOriginUrl();
                String fileName = originUrl.substring(originUrl.lastIndexOf("/"));
                String[] fname = fileName.split("\\.");

                String epubName = fname[0] + ".epub";
                System.out.println(epubName);
                File file = new File("D:/book/convert" + epubName);
                System.out.println(file.exists());
                if (file.exists()) {
                    if (book.getConvertStatus()==1) return new ResType(400,109);

                    // ??????????????????
                    // ?????????????????????????????????epub?????????
                    book.setConvertStatus(1);
                    String epubUrl = Upload.fileQcloud(file, epubName);
                    if (epubUrl.equals(500)) return new ResType(500,107);
                    book.setEpubUrl(epubUrl);
                    bookRepository.save(book);

                    // ???????????????
                    // ?????????txt?????????????????????
                    if (book.getOriginExt().equals(".txt")) return new ResType(e2d(bookChain,book,uid));

                    BookUtils.getEpub(file,fname[0]);
                    String coverName = fname[0] + "_cover.jpg";
                    String coverUrl = Upload.fileQcloud(new File("D:/book/convert" + coverName ), coverName);
                    if (coverUrl.equals(500)) return new ResType(500,107);

                    // ??????????????????
                    book.setCover(coverUrl);
                    bookRepository.save(book);
                    bookChain.setCover(coverUrl);
                    chainRepository.save(bookChain);

                    return new ResType(e2d(bookChain,book,uid));

                }else {
                    // ??????????????????
                    return new ResType(400,108);
                }

            }else {
                return new ResType(400,102);
            }

        }catch (NumberFormatException e){
            return new ResType(400,105);
        }


    }

    @Override
    public ResType findBookDtobyIds(List<Integer> ids) {

        try{
            List<Book> bookList = bookRepository.findAllByIds(ids);
            ArrayList<BookDto> list = new ArrayList<>();
            for (Book book : bookList) {
                list.add(e2d(book));
            }
            return new ResType(list);

        }catch (Exception e){
            e.printStackTrace();
            return new ResType(500,101);
        }

    }

    @Override
    public ResType recommendByUser(Integer userId) {

        ResType resType = recommendService.userBasedCF(userId);

        return resType;
    }

    @Override
    public ResType recommendByItem(Integer userId) {
        System.out.println("recommentserveice");
        System.out.println(recommendService);
        ResType resType = recommendService.itemBasedCF(userId);
        System.out.println("restype");
        System.out.println(resType);


        return resType;
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


    public ResType getBookIdsByTabIds(List<Integer> tabIds) {
        try {
            List<Integer> bookIds = bookTagRepository.getBookIdsByTabIds(tabIds);
//            ??????bookids??????bookentitys
            return new ResType(bookIds);
//            List<Book> bookEns = bookRepository.findAllByIds(bookIds);
//            List<BookDto> bookDtos = new ArrayList<>();
//            for (Book bookEn : bookEns) {
//                bookDtos.add(e2d(bookEn));
//            }
//            return
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,108);
        }
    }

    public ResType getBookIdByMostAdd(Integer num) {
        try {
            List<Integer> ids = chainRepository.getMostAddBookIds(num);
            return new ResType(ids);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,108);
        }
    }

    public ResType bookIdsToBookDtos(List<Integer> bookIds) {
        try {
            List<Book> books = bookRepository.findAllByIds(bookIds);
            List<BookDto> bookDtos = new ArrayList<>();
            for (Book book : books) {
                bookDtos.add(e2d(book));
            }
            return new ResType(bookDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,108);
        }
    }
}
