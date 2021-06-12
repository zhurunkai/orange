package org.zust.interfaceapi.service;

import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

public interface BookUserService {
    public ResType identifyCode (Map param);
    public ResType lrBookUser(Map param);
    public ResType findBookUserAllInformById(int buId);
    public ResType findTabsByBuid(int id);
    public ResType chooseTags(int id);
}
