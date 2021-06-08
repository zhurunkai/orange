package org.zust.interfaceapi.service;


import org.springframework.web.multipart.MultipartFile;
import org.zust.interfaceapi.utils.ResType;

public interface CommonService {

    ResType uploadfile(MultipartFile file);
}
