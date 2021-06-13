package org.zust.interfaceapi.service;

import org.zust.interfaceapi.utils.ResType;

import java.util.List;
import java.util.Map;

public interface TabService {
    public Boolean changeWeight(List list,Integer uid,Integer weight);
    public ResType findTagIdByName(Map param);
    public Boolean isTagExist(Integer uid,Integer tab);
    public ResType findtagNameById(Map param);
}
