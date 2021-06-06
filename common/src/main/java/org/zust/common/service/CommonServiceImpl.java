package org.zust.common.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zust.interfaceapi.service.CommonService;

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
        File dest = new File(filePath + fileName);
        System.out.println("zheshism ");
        try {

            file.transferTo(dest);


            return "上传成功";

        } catch (IOException e) {
            System.out.println("shibaile");
            e.printStackTrace();
        }
        return "上传失败！";
    }
}

