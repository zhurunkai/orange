package org.zust.interfaceapi.service;

import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

public interface AdvertisementService {
    public ResType addAdvertisement(Map params);
    public ResType getAdvertisement(Integer id);
    public ResType changeAdvertisementStatus(Integer id,String status);
    public ResType getAdvertisementThrow(Integer id);
    public ResType getAdvertisementByTabId(Integer tabId);



}
