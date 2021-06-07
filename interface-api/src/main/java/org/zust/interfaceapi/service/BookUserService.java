package org.zust.interfaceapi.service;

import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

public interface BookUserService {
    public ResType bookidentifyCode (Map param);
    public ResType lrBookUser(Map param);
    public ResType findBookUserAllInformById(int buId);
}
