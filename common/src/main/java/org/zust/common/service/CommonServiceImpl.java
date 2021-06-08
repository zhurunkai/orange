package org.zust.common.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.zust.interfaceapi.service.CommonService;
import org.zust.interfaceapi.utils.ResType;
import org.zust.interfaceapi.utils.Upload;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public ResType uploadfile(MultipartFile file) {
        if (file.isEmpty()) {
            return new ResType(500,106);
            //"上传失败，请选择文件"
        }

        String fileName = file.getOriginalFilename();
        String filePath = "D://cs/";
        Integer random = (int) (Math.random() * 10000);
        String trueFile =filePath + fileName;
        File dest = new File(filePath + random+ new Date().getTime() + fileName);

        try {
            file.transferTo(dest);
            String realFileName = random+ new Date().getTime() +fileName;
            String qcloud = Upload.fileQcloud(dest,realFileName);
            return new ResType(qcloud);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResType(500,107);
            //"上传失败！"
        }

    }
}

