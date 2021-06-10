package org.zust.book.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.service.BookService;
import org.zust.interfaceapi.service.CommonService;
import org.zust.interfaceapi.utils.ResType;

import javax.persistence.criteria.CriteriaBuilder;
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

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@RequestHeader("Authorization") String token,
                                     @PathVariable String id) {
        ResType res = bookService.getBook(id);
        if (res.getStatus() == 200) {
            return ResponseEntity.ok(res.getData());
        }else {
            return ResponseEntity.status(res.getStatus()).body(res.getCode());
        }
    }

    @PostMapping("/shelf/{id}/chain/origin")
    public ResponseEntity<?> addBookShelf(@RequestHeader("Authorization") String token
                                        ,@PathVariable String id
                                         ,@RequestBody HashMap map) {
        // 从token里解析出用户，再存入用户id
        map.put("owner",1);
        map.put("bookshelf",Integer.parseInt(id));
        ResType res = bookService.addBook(map);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }

    @GetMapping("/shelf/{id}/chain/origin")
    public ResponseEntity<?> checkConvert(@RequestHeader("Authorization") String token,
                                          @PathVariable String id) {

        ResType res = bookService.checkConvert(id);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }


}
