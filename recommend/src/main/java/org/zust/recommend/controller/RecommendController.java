package org.zust.recommend.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zust.interfaceapi.service.CommonUserService;
import org.zust.interfaceapi.service.RecommendService;
import org.zust.interfaceapi.utils.ResType;

import java.util.List;

@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    private RecommendService recommendService;
    @Reference(check = false)
    private CommonUserService commonUserService;
    @GetMapping("/user/{id}")
    public ResponseEntity<?> userBasedCF(@RequestHeader("Authorization") String token, @PathVariable String id) {
        ResType tokenRes = commonUserService.checkToken(token);
        if(tokenRes.getStatus()!=200) {
            return ResponseEntity.status(tokenRes.getStatus()).body(tokenRes.getCode());
        }

        Integer intId;
        try {
            intId = Integer.parseInt(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(105);
        }
        try {
            return ResponseEntity.ok(recommendService.userBasedCF(intId).getData());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(108);
        }
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<?> itemBasedCF(@PathVariable String id) {
        Integer intId;
        try {
            intId = Integer.parseInt(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(105);
        }
        System.out.println(intId);
        try {
            return ResponseEntity.ok(recommendService.itemBasedCF(intId).getData());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(108);
        }
    }

//    @GetMapping("/tab/{id}")
//    public ResponseEntity<?> userTabRecommend(String id) {
//
//    }
}
