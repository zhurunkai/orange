package org.zust.book.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.dto.BookShelfDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookShelfService;
import org.zust.interfaceapi.service.CommonUserService;
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
    @Reference(check = false)
    private CommonUserService commonUserService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookShelfById(@RequestHeader("Authorization") String token,
                                              @PathVariable String id) {

        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        ResType res = bookShelfService.getBookShelfById(id);
        if (res.getStatus() == 200) {
            return ResponseEntity.ok(res.getData());
        }else {
            return ResponseEntity.status(res.getStatus()).body(res.getCode());
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllBookShelfByOwnerId(@RequestHeader("Authorization") String token){

        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        BookUserDto buser = (BookUserDto) tokenRes.getData();

        ResType res = bookShelfService.getBookShelfLists(buser.getId());
        return ResponseEntity.ok(res.getData());

    }

    @PostMapping
    // 书用户注册的时候，需要传is_Root = 1 来创建默认的根目录书架
    // 普通用户创建书架时，不需要传is_Root 或者 传is_Root = 0
    public ResponseEntity<?> addBookShelf(@RequestHeader("Authorization") String token,
                                          @RequestBody HashMap map) {

        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        BookUserDto buser = (BookUserDto) tokenRes.getData();
        map.put("owner",buser.getId());

        ResType res = bookShelfService.addBookShelf(map);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookShelf(@RequestHeader("Authorization") String token,
                                             @PathVariable String id,
                                            @RequestBody HashMap map) {


        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        BookUserDto buser = (BookUserDto) tokenRes.getData();
        map.put("owner",buser.getId());

        map.put("id",id);
        ResType res = bookShelfService.updateBookShelfById(map);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookShelf(@RequestHeader("Authorization") String token,
                                             @PathVariable String id) {

        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }
        HashMap<String, Object> map = new HashMap<>();
        BookUserDto buser = (BookUserDto) tokenRes.getData();
        map.put("owner",buser.getId());

        map.put("bsid",id);
        ResType res = bookShelfService.deleteBookShelfById(map);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body(res.getCode());
    }

}
