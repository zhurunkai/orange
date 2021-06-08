package org.zust.interfaceapi.service;

import org.springframework.web.servlet.tags.Param;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

public interface AdUserService {

    public ResType findAdUserAllInformById(int buId);
    public ResType findAdUserBillById(int id);
    public ResType findRecordsByAdId(int id);




}
