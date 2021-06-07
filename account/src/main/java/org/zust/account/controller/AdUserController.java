package org.zust.account.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AdUserController {

    @Autowired
    AdUserService adUserService;

    @PostMapping("/captcha")
    public ResponseEntity<?> addAdvertisement(@RequestBody Map params)
    {
        ResType res = adUserService.adidentifyCode(params);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));
    }
}
