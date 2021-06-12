package org.zust.account.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.service.BookUserService;
import org.zust.interfaceapi.service.CommonUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/account")
@CrossOrigin
public class BookUserController {

    @Autowired
    BookUserService bookUserService;

    @Reference(check = false)
    private CommonUserService commonUserService;

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
    public ResponseEntity<?>  findBookUserAllById(@RequestHeader("Authorization") String token,@PathVariable String id){
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }
        ResType res = bookUserService.findBookUserAllInformById(Integer.parseInt(id));
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

    //根据用户id获取其标签
    @GetMapping("/book/{id}/tabs")
    public ResponseEntity<?>  getUserTabs(@RequestHeader("Authorization") String token,@PathVariable String id){
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }
        ResType res = bookUserService.findTabsByBuid(Integer.parseInt(id));
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

    //用户选择标签
    @PostMapping("/book/{id}/tabs")
    public  ResponseEntity<?>  chooseTabs(@RequestHeader("Authorization") String token,@PathVariable String id){
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }
        ResType res = bookUserService.chooseTags(Integer.parseInt(id));
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }




}





