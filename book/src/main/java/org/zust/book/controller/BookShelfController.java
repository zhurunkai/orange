package org.zust.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.dto.BookShelfDto;
import org.zust.interfaceapi.service.BookShelfService;
import org.zust.interfaceapi.utils.ResType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Linxy
 * @Date: 2021/6/7 11:13
 * @function:
 **/
@RestController
@RequestMapping("/book/shelf")
public class BookShelfController {

    @Autowired
    private BookShelfService bookShelfService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookShelfById(@PathVariable String id) {

        ResType res = bookShelfService.getBookShelfById(id);
        if (res.getStatus() == 200) {
            return ResponseEntity.ok(res.getData());
        }else {
            return ResponseEntity.status(res.getStatus()).body(res.getCode());
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllBookShelfByOwnerId(@RequestHeader("Authorization") String token){

        ResType res = bookShelfService.getBookShelfLists(token);
        return ResponseEntity.ok(res.getData());

    }

    @PostMapping
    // 书用户注册的时候，需要传is_Root = 1 来创建默认的根目录书架
    // 普通用户创建书架时，不需要传is_Root 或者 传is_Root = 0
    public ResponseEntity<?> addBookShelf(@RequestBody HashMap map) {
        ResType res = bookShelfService.addBookShelf(map);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookShelf(@PathVariable String id,
                                            @RequestBody HashMap map) {

        map.put("id",id);
        map.put("uid",1);

        ResType res = bookShelfService.updateBookShelfById(map);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }



}
