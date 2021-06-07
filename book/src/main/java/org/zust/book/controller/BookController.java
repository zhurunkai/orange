package org.zust.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.service.BookService;
import org.zust.interfaceapi.utils.ResType;

import javax.persistence.criteria.CriteriaBuilder;
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
    public ResponseEntity<?> getBook(@PathVariable String id) {
        ResType res = bookService.getBook(id);
        if (res.getStatus() == 200) {
            return ResponseEntity.ok(res.getData());
        }else {
            return ResponseEntity.status(res.getStatus()).body(res.getCode());
        }
    }
}
