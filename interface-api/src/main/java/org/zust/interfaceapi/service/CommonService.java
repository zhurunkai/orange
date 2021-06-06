package org.zust.interfaceapi.service;


import org.springframework.web.multipart.MultipartFile;

public interface CommonService {


    String uploadfile(MultipartFile file);
}
