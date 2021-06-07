package org.zust.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.service.BookService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:38
 * @function:
 **/
@CrossOrigin
@RestController
@RequestMapping(value = "/book",produces = "application/json;charset=utf-8")
public class BookController {

    @Autowired
    private BookService bookService;

//    @GetMapping
//    public ResponseEntity<?> getBook(@RequestBody Map params)
//    {
//
//        if(res.getStatus()==200) {
//            return new ResponseEntity<AdvertisementDto>((AdvertisementDto)res.getData(), HttpStatus.valueOf(res.getStatus()));
//        }
//        return new ResponseEntity<String>((String)res.getMsg(), HttpStatus.valueOf(res.getStatus()));
//    }
}
