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

    //获取验证码（读者广告商通用）
    @PostMapping("/captcha")
    public ResponseEntity<?> identfyCode(@RequestBody Map params)
    {
        ResType res = bookUserService.identifyCode(params);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }   
    
    //读书人登录注册
    @PostMapping("/book")
    public ResponseEntity<?> bookUserLoginRegister(@RequestBody Map params){
        ResType res = bookUserService.lrBookUser(params);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

    //根据id获取读书人信息
    @GetMapping("/book/{id}")
    public ResponseEntity<?>  findBookUserAllById(@PathVariable String id){
        ResType res = bookUserService.findBookUserAllInformById(Integer.parseInt(id));
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

}





