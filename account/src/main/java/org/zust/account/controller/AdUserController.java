package org.zust.account.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;


@RestController
@RequestMapping("/account")
public class AdUserController {

    @Autowired
    AdUserService adUserService;

    //根据id获取广告主信息
    @GetMapping("/ad/{id}")
    public ResponseEntity<?>  findBookUserAllById(@PathVariable String id){
        ResType res = adUserService.findBookUserAllInformById(Integer.parseInt(id));
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

    //查找广告主账单
    @GetMapping("/ad/{id}/money/history")
    public ResponseEntity<?>  findBillById(@PathVariable String id){
        ResType res = adUserService.findAdUserBillById(Integer.parseInt(id));
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

}
