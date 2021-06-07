package org.zust.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.dto.BookShelfDto;
import org.zust.interfaceapi.service.BookShelfService;
import org.zust.interfaceapi.utils.ResType;

/**
 * @author: Linxy
 * @Date: 2021/6/7 11:13
 * @function:
 **/
@RestController
@RequestMapping(value = "/book/shelf",produces = "application/json;charset=utf-8")
public class BookShelfController {

    @Autowired
    private BookShelfService bookShelfService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookShelfById(@PathVariable Integer id) {

        ResType res = bookShelfService.getBookShelfById(id);
        if(res.getStatus()==200) {
            return new ResponseEntity<>((BookShelfDto)res.getData(), HttpStatus.valueOf(res.getStatus()));
        }
        return new ResponseEntity<>(res.getStatus(), HttpStatus.valueOf(res.getStatus()));
    }

}
