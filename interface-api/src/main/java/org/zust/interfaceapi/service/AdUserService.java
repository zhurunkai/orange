package org.zust.interfaceapi.service;

import org.springframework.web.servlet.tags.Param;
import org.zust.interfaceapi.utils.ResType;

import java.util.List;
import java.util.Map;

public interface AdUserService {

    public ResType lrAdUser(Map param);
    public ResType findAdUserAllInformById(int buId);
    public ResType findAdUserBillById(int id);
    public ResType findRecordsByAdId(int id);
    public ResType findAdsByAuId(int id);
    public ResType getAd7DaysClickByAdUserId(Integer id);
    public ResType getAd7DaysShowByAdUserId(Integer id);
    public ResType getAd7DaysNumsByAdUserId(Integer id);
    public ResType getAd7DaysCostByAdUserId(Integer id);
    public ResType getAdBuserTabWeights(Integer id);
    public ResType getAdUserAllInfoByIds(List<Integer> ids);

}
