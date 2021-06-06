package org.zust.common.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.zust.interfaceapi.service.CommonService;
import org.zust.interfaceapi.utils.Upload;

import java.io.File;
import java.io.IOException;

@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public String uploadfile(MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println('n');
            return "上传失败，请选择文件";
        }
        System.out.println("haha");
        String fileName = file.getOriginalFilename();
        String filePath = "D://cs/";
        String trueFile =filePath + fileName;
        File dest = new File(filePath + fileName);

        try {
            file.transferTo(dest);
            String qcloud = Upload.fileQcloud(dest,fileName);
            return "上传成功";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败！";
    }
}

