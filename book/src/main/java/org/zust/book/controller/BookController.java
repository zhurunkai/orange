package org.zust.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zust.interfaceapi.service.BookService;

/**
 * @author: Linxy
 * @Date: 2021/6/6 22:38
 * @function:
 **/
@CrossOrigin
@RestController
@RequestMapping(value = "/captcha",produces = "application/json;charset=utf-8")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping()
    public
}
