package org.zust.interfaceapi.service;

import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

public interface BookUserService {
    public ResType identifyCode (Map param);
    public ResType lrBookUser(Map param);
    public ResType findBookUserAllInformById(int buId);
    public ResType findTabsByBuid(int id);
    public ResType chooseTags(int id,Map param);
    public ResType findTabWeightByBuid(int id);
    public ResType findTabById(Integer id);
    public ResType getRecommendAd(Integer id,Integer bookId);
    public ResType clickAd(Integer id,Integer bookId,Integer adId);
    public ResType getBookRecommendByTab(Integer id);
}
