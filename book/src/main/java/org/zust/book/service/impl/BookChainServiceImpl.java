package org.zust.book.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.book.entity.*;
import org.zust.book.repository.*;
import org.zust.interfaceapi.dto.BookChainDto;
import org.zust.interfaceapi.dto.BookDto;
import org.zust.interfaceapi.dto.BookEnDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookChainService;
import org.zust.interfaceapi.service.BookUserService;
import org.zust.interfaceapi.service.TabService;
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
@org.apache.dubbo.config.annotation.Service(timeout = 300000)
public class BookChainServiceImpl implements BookChainService {

    @Reference(check = false)
    private BookUserService bookUserService;
    @Reference(check = false)
    private TabService tabService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookShelfRepository shelfRepository;
    @Autowired
    private BookChainRepository chainRepository;
    @Autowired
    private BookTagRepository bookTagRepository;
    @Autowired
    private OperatorRecordRepository recordRepository;

    @Override
    public ResType getChainBySidAndCid(HashMap map) {

        String sid = (String) map.get("sid");
        String cid = (String) map.get("cid");
        Integer owner = (Integer) map.get("owner");
        if (sid==null || cid==null) return new ResType(400,101);

        try {
            BookChain chain = chainRepository.findOneById(Integer.parseInt(cid));
            if (chain==null) return new ResType(400,111);

            BookChain bookChain = chainRepository.findByIdAndShelf(chain.getId(), Integer.parseInt(sid));
            if (bookChain==null) return new ResType(400,112);

            Book book = bookRepository.findOneById(bookChain.getOrigin());

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
            BookChain chain = chainRepository.findOneById(Integer.parseInt(cid));
            if (chain==null) return new ResType(400,111);

            BookChain bookChain = chainRepository.findByIdAndShelf(chain.getId(), Integer.parseInt(sid));
            if (bookChain==null) return new ResType(400,112);

            BookShelf exShelf = shelfRepository.findByIdAndOwner(Integer.parseInt(shelf), owner);
            if (exShelf==null) return new ResType(400,113);

            bookChain.setShelf(exShelf.getId());
            BookChain save = chainRepository.save(bookChain);

            Book book = bookRepository.findOneById(bookChain.getOrigin());

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
            BookChain chain = chainRepository.findOneById(Integer.parseInt(cid));
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

            Book book = bookRepository.findOneById(bookChain.getOrigin());
            return new ResType(e2d(save,book,owner));

        }catch (Exception e){
            e.printStackTrace();
            return new ResType(400,105);
        }

    }

    @Override
    public ResType deleteChain(HashMap map) {

        String sid = (String) map.get("sid");
        String cid = (String) map.get("cid");
        Integer alive = (Integer) map.get("alive");
        Integer owner = (Integer) map.get("owner");
        if (sid==null || cid==null ||alive==null) return new ResType(400,101);

        try {
            BookChain bookChain = chainRepository.findByIdAndOwner(Integer.parseInt(cid),owner);
            if (bookChain==null) return new ResType(400,112);

            Book book = bookRepository.findOneById(bookChain.getOrigin());

            if (bookChain.getAlive().equals(alive)){
                if (alive==1)
                    return new ResType(400,115);
                else
                    return new ResType(400,116);
            }


            if (alive==0){
                //进行删除操作
                bookChain.setAlive(alive);
                chainRepository.save(bookChain);

                List<BookTag> bookTagList = bookTagRepository.findAllByBook(book.getId());
                ArrayList<Integer> idList = new ArrayList<>();
                if (bookTagList!=null){
                    for (BookTag bookTag : bookTagList) {
                        idList.add(bookTag.getTab());
                    }
                }

                tabService.changeWeight(idList,owner,-30);

            }else if (alive==1){
                //进行恢复操作
                bookChain.setAlive(alive);
                chainRepository.save(bookChain);

                List<BookTag> bookTagList = bookTagRepository.findAllByBook(book.getId());
                ArrayList<Integer> idList = new ArrayList<>();
                if (bookTagList!=null){
                    for (BookTag bookTag : bookTagList) {
                        idList.add(bookTag.getTab());
                    }
                }

                tabService.changeWeight(idList,owner,+30);

            }else {
                //无效操作
                return new ResType(400,117);
            }

            return new ResType(200);

        }catch (Exception e){
            e.printStackTrace();
            return new ResType(400,105);
        }

    }

    @Override
    public ResType addReaderAccord(HashMap map) {

        String cid = (String) map.get("chainId");
        Integer owner = (Integer) map.get("owner");
        if (cid==null) return new ResType(400,101);

        try {
            BookChain chain = chainRepository.findOneById(Integer.parseInt(cid));
            if (chain==null) return new ResType(400,111);

            OperateRecords record = new OperateRecords();
            record.setBookChain(chain.getId());
            record.setType("read");
            record.setOperator(owner);
            OperateRecords save = recordRepository.save(record);

            // 取出该书对应的标签id
            List<BookTag> tags = bookTagRepository.findAllByBook(chain.getOrigin());
            ArrayList<Integer> list = new ArrayList<>();
            for (BookTag tag : tags) {
                list.add(tag.getTab());
            }
            // 修改用户喜好权重
            tabService.changeWeight(list, owner, +3);

            return new ResType(save);

        }catch (Exception e){
            e.printStackTrace();
            return new ResType(400,105);
        }
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
                Book book = bookRepository.findOneById(bookChain.getOrigin());
                returnList.add(e2d(bookChain,book,owner));
            }
        }

        return new ResType(returnList);
    }

    @Override
    public ResType CollectChain(HashMap map) {

        String sid = (String) map.get("sid");
        ArrayList<Integer> ids = (ArrayList<Integer>) map.get("ids");
        Integer owner = (Integer) map.get("owner");
        if (sid==null) return new ResType(400,101);

        BookShelf Shelf = shelfRepository.findByIdAndOwner(Integer.parseInt(sid), owner);
        if (Shelf==null) return new ResType(400,113);

        ArrayList<BookChainDto> returnList = new ArrayList<>();
        // 该用户批量添加chain记录
        if (ids!=null){
            for (Integer id : ids) {
                Book book = bookRepository.findOneById(id);
                if (book==null) return new ResType(400,114);
                BookChain chain = new BookChain(book.getName(),owner,Integer.parseInt(sid),book.getCover(),id);
                BookChain save = chainRepository.save(chain);
                List<BookTag> bookTags = bookTagRepository.findAllByBook(id);
                // 处理标签
                if (bookTags!=null){
                    for (BookTag bookTag : bookTags) {
                        tabService.isTagExist(owner,bookTag.getTab());
                    }
                }
                returnList.add(e2d(save,book,owner));
            }
        }
        return new ResType(returnList);
    }

    public ResType getAllChains() {
        try {
            List<BookChain> bookChains = chainRepository.findAll();
            List<BookEnDto> bookChainDtos = new ArrayList<>();
            for (BookChain bookChain : bookChains) {

                bookChainDtos.add(new BookEnDto(bookChain.getId(),bookChain.getOwner()));
            }
            return new ResType(bookChainDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,108);
        }
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
