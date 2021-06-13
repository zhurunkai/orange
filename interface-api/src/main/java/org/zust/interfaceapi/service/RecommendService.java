package org.zust.interfaceapi.service;

import org.zust.interfaceapi.utils.ResType;

public interface RecommendService {
    public ResType userBasedCF(Integer id);
    public ResType itemBasedCF(Integer id);
    public ResType adRecommendByUserTab(Integer id);
}
