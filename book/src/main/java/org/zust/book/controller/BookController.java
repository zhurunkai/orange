package org.zust.book.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.*;
import org.zust.interfaceapi.utils.ResType;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:38
 * @function:
 **/
@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Reference(check = false)
    private CommonUserService commonUserService;
    @Reference(check = false)
    private TabService tabService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookChainService chainService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@RequestHeader("Authorization") String token,
                                     @PathVariable String id) {

        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);

        ResType res = bookService.getBook(map);
        if (res.getStatus() == 200) {
            return ResponseEntity.ok(res.getData());
        }else {
            return ResponseEntity.status(res.getStatus()).body(res.getCode());
        }
    }

    @PostMapping("/shelf/{id}/chain/origin")
    public ResponseEntity<?> addBook(@RequestHeader("Authorization") String token
                                        ,@PathVariable String id
                                         ,@RequestBody HashMap map) {
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        BookUserDto buser = (BookUserDto) tokenRes.getData();
        map.put("owner",buser.getId());
        map.put("bookshelf",Integer.parseInt(id));

        ResType res = bookService.addBook(map);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }

    @GetMapping("/shelf/{sid}/chain/{cid}/origin")
    public ResponseEntity<?> checkConvert(@RequestHeader("Authorization") String token,
                                          @PathVariable String sid,
                                          @PathVariable String cid) {

        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        HashMap<String, Object> map = new HashMap<>();
        BookUserDto buser = (BookUserDto) tokenRes.getData();
        map.put("owner",buser.getId());
        map.put("chain",cid);

        ResType res = bookService.checkConvert(map);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }

    @PostMapping("/operate/read")
    public ResponseEntity<?> addBookRecord(@RequestHeader("Authorization") String token
                                            ,@RequestBody HashMap map) {
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        BookUserDto buser = (BookUserDto) tokenRes.getData();
        map.put("owner",buser.getId());
        ResType res = chainService.addReaderAccord(map);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }

    @GetMapping("/recommend/user")
    public ResponseEntity<?> recommendByUser(
            @RequestHeader("Authorization") String token) {

        System.out.println(commonUserService);
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        BookUserDto buser = (BookUserDto) tokenRes.getData();
        ResType res = bookService.recommendByUser(buser.getId());

        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }

    @GetMapping("/recommend/item")
    public ResponseEntity<?> recommendByItem(
            @RequestHeader("Authorization") String token) {

        System.out.println(commonUserService);
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        BookUserDto buser = (BookUserDto) tokenRes.getData();
        ResType res = bookService.recommendByItem(buser.getId());

        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }
}
