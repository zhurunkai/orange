package org.zust.advertisement.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.advertisement.dao.AdvertisementDao;
import org.zust.advertisement.entity.AdvertisementEntity;
import org.zust.interfaceapi.service.AdvertisementService;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    AdvertisementDao advertisementDao;


    @Override
    public ResType addAdvertisement(Map params) {
        String title = null;
        String url = null;
        String picture = null;
        try {
            title = (String)params.get("title");
            url = (String)params.get("url");
            picture = (String)params.get("picture");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(400,"参数错误");
        }
        try {
            AdvertisementEntity advertisementEntity = advertisementDao.save(new AdvertisementEntity(title,url,picture,1, 10000.0,"正在投放"));
            return new ResType(200,"成功",e2d(advertisementEntity));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,"数据库操作错误");
        }
    }


    public AdvertisementDto e2d(AdvertisementEntity advertisementEntity) {
        AdvertisementDto advertisementDto = new AdvertisementDto();
        BeanUtils.copyProperties(advertisementEntity, advertisementDto);
        return advertisementDto;
    }
//    public static EquipmentDto equipmentE2d(Equipment equipment) {
//        EquipmentDto equipmentDto = new EquipmentDto();
//        BeanUtils.copyProperties(equipment,equipmentDto);
//        return equipmentDto;
//    }
}


