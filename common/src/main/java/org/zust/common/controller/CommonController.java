package org.zust.common.controller;


import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zust.interfaceapi.service.BookService;
import org.zust.interfaceapi.service.CommonService;
import org.zust.interfaceapi.utils.ResType;


@RestController
@RequestMapping("/")
public class CommonController {
    @Autowired
    CommonService commonService;

    @PostMapping("/file")
    @ResponseBody
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        ResType res = commonService.uploadfile(file);
        if(res.getStatus()==200) {
            return ResponseEntity.ok(res.getData());
        }
        return new ResponseEntity<Integer>(101, HttpStatus.valueOf(res.getStatus()));


    }

}