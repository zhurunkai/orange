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
import org.zust.book.repository.BookChainRepository;
import org.zust.book.repository.BookRepository;
import org.zust.interfaceapi.dto.BookChainDto;
import org.zust.interfaceapi.dto.BookDto;
import org.zust.interfaceapi.dto.BookShelfDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookService;
import org.zust.interfaceapi.service.CommonService;
import org.zust.interfaceapi.utils.BookUtils;
import org.zust.interfaceapi.utils.FileUtil;
import org.zust.interfaceapi.utils.ResType;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.acl.Owner;
import java.util.Date;
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
    @Autowired
    private BookChainRepository chainRepository;

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

        String origin_url = (String) map.get("origin_url");
        String name = (String) map.get("name");
        Integer owner = (Integer) map.get("owner");
        Integer bookshelf = (Integer) map.get("bookshelf");

        // 拿到文件的后缀名
        String origin_ext = origin_url.substring(origin_url.lastIndexOf(".")).toLowerCase();

        try {
            // 将链接下载到指定路径下
            // 默认下载到D盘，下载暂存文件夹为temp_file，下载文件夹为download
            HashMap downloader = FileUtil.ThreadDownloader(origin_url);
            Boolean downloaderStatus = (Boolean) downloader.get("status");
            // 0代表下载成功，-1代表下载失败
            if (downloaderStatus){
                System.out.println("是否下载成功："+downloaderStatus);

                // 下载成功，保存到数据库
                Book book = new Book();
                book.setName(name);
                book.setOwner(owner);
                book.setOriginUrl(origin_url);
                book.setOriginExt(origin_ext);
                book.setConvertStatus(0);
                Book bookSave = bookRepository.save(book);

                BookChain bookChain = new BookChain();
                bookChain.setName(name);
                bookChain.setOwner(owner);
                bookChain.setOrigin(bookSave.getId());
                bookChain.setAlive(1);
                bookChain.setShelf(bookshelf);
                bookChain.setProcess(0);
                BookChain chainSave = chainRepository.save(bookChain);

                // 将.mobi等格式的文件转换为.epub格式
                if (origin_ext.equals(".mobi")){
                    String fileName = (String) downloader.get("fileName");
                    // 默认转化的文件夹为convert
                    BookUtils.convert("D:/book/download" + fileName +".mobi", "D:/book/convert" + fileName + ".epub");
                }
//

                if (chainSave!=null) return new ResType(e2d(chainSave,bookSave));

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
    public ResType checkConvert(String chain) {

        if (chain==null) return new ResType(400,101);

        try {
            BookChain bookChain = chainRepository.findById(Integer.parseInt(chain)).orElse(null);

            if (bookChain!=null){
                Book book = bookRepository.findById(bookChain.getOrigin()).orElse(null);
                String originUrl = book.getOriginUrl();
                String fileName = originUrl.substring(originUrl.lastIndexOf("/"));
                String[] fname = fileName.split("\\.");
                System.out.println("fileName = "+fname[0]);

                File file = new File("D:/book/convert" + fname[0] + ".epub");
                System.out.println(file.toString());
                if (file.exists()) {
                    // 文件转化成功，取出封面图
                    boolean epub = BookUtils.getEpub(file,fname[0]);
                    System.out.println("是否获取封面："+epub);

//                    System.out.println("yes");
                }else {
                    // 文件还没转好
                    return new ResType(400,108);
                }

//                if (book!=null){
//                    return new ResType(e2d(book));
//                }else {
//                    return new ResType(400,102);
//                }


            }

            return new ResType("ok");

        }catch (NumberFormatException e){
            return new ResType(400,105);
        }


    }

    public BookDto e2d(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setOwner(new BookUserDto());
        BeanUtils.copyProperties(book, bookDto);
        return bookDto;
    }

    public BookChainDto e2d(BookChain chain, Book book) {
        BookChainDto chainDto = new BookChainDto();
        chainDto.setOrigin(e2d(book));
        chainDto.setOwner(new BookUserDto());
        BeanUtils.copyProperties(chain, chainDto);
        return chainDto;
    }

}
