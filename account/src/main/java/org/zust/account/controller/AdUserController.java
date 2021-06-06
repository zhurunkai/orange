package org.zust.account.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zust.interfaceapi.dto.AdUserDto;
import org.zust.interfaceapi.service.AdUserService;

@RestController
@RequestMapping("/account")
public class AdUserController {

    @Autowired
    AdUserService adUserService;

    @PostMapping("/captcha")
    public AdUserDto identifyingCode(AdUserDto adUserDto){


    }

}
