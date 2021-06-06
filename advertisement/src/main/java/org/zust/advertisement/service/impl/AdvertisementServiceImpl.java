package org.zust.advertisement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.advertisement.dao.AdvertisementDao;
import org.zust.advertisement.service.AdvertisementService;
import org.zust.interfaceapi.dto.AdvertisementDto;

import java.util.Map;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    AdvertisementDao advertisementDao;


    @Override
    public AdvertisementDto addAdvertisement(Map params) {
        String title = (String)params.get("title");
        String url = (String)params.get("url");
        String picture = (String)params.get("picture");
        String budget = (String)params.get("budget");
        String status = (String)params.get("status");

        return null;
    }
}


