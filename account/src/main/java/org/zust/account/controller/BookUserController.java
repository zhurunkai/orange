package org.zust.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.service.BookUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class BookUserController {

    @Autowired
    BookUserService bookUserService;

    @PostMapping("/captcha")
    public ResponseEntity<?> identfyCode(@RequestBody Map params1)
    {
        ResType res = bookUserService.bookidentifyCode(params1);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }   
    
    @PostMapping("/book")
    public ResponseEntity<?> bookUserLoginRegister(@RequestBody Map params2){
        ResType res = bookUserService.bookidentifyCode(params2);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?>  findBookUserAllById(@PathVariable String id){
        ResType res = bookUserService.findBookUserAllInformById(Integer.parseInt(id));
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

}





