package org.zust.common.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zust.interfaceapi.service.CommonService;


@RestController
@RequestMapping("/file")
public class CommonController {
    @Autowired
    CommonService commonService;

    @PostMapping("/file")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        commonService.uploadfile(file);
        return("ok");
    }

}