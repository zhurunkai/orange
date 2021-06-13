package org.zust.interfaceapi.service;

import org.zust.interfaceapi.utils.ResType;

import java.util.List;
import java.util.Map;

public interface TabService {
    public ResType changeWeight(List list,Integer uid,Integer weight);
    public ResType findTagIdByName(Map param);
    public ResType findtagNameById(Map param);
}
