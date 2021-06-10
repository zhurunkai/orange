package org.zust.account.controller;


import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.service.CommonUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/account")
public class AdUserController {

    @Autowired
    AdUserService adUserService;

    @Reference(check = false)
    private CommonUserService commonUserService;


    //广告主登录注册
    @PostMapping("/ad")
    public ResponseEntity<?> adUserLoginRegister(@RequestBody Map params){
        ResType res = adUserService.lrAdUser(params);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

    //根据id获取广告主信息
    @GetMapping("/ad/{id}")
    public ResponseEntity<?>  findBookUserAllById(@RequestHeader("Authorization") String token,  @PathVariable String id){
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }
        ResType res = adUserService.findAdUserAllInformById(Integer.parseInt(id));
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

    //根据广告主id查找广告主账单
    @GetMapping("/ad/{id}/money/history")
    public ResponseEntity<?>  findBillById(@RequestHeader("Authorization") String token, @PathVariable String id){
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }
        ResType res = adUserService.findAdUserBillById(Integer.parseInt(id));
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

    //根据广告id获取投放记录
    @GetMapping("/ad/{id}/throw")
    public ResponseEntity<?> findRecordsById(@RequestHeader("Authorization") String token, @PathVariable String id){
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }
        ResType res = adUserService.findRecordsByAdId(Integer.parseInt(id));
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }

}
