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

        if (id==null)return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("101");

        ResType res = bookShelfService.getBookShelfById(Integer.parseInt(id));
        if (res.getStatus() == 200) {
            return ResponseEntity.ok(res.getData());
        }else {
            return ResponseEntity.status(res.getStatus()).body("102");
        }

    }

    @GetMapping
    public ResponseEntity<?> getAllBookShelfByOwnerId(@RequestHeader("Authorization") String token){

        ResType res = bookShelfService.getBookShelfLists(token);
        return ResponseEntity.ok(res.getData());

    }

    @PostMapping
    public ResponseEntity<?> addAdvertisement(@RequestBody HashMap map)
    {
        ResType res = bookShelfService.addBookShelf(map);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return ResponseEntity.status(res.getStatus()).body("102");
    }


}
