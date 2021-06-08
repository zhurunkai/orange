package org.zust.advertisement.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.ThrowRecordsDao;
import org.zust.account.entity.ThrowRecordsEntity;
import org.zust.advertisement.dao.AdvertisementDao;
import org.zust.advertisement.entity.AdvertisementEntity;
import org.zust.interfaceapi.dto.ThrowRecordsDto;
import org.zust.interfaceapi.service.AdvertisementService;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.utils.ResType;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

import static java.lang.Integer.valueOf;

@Service
@org.apache.dubbo.config.annotation.Service
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
            return new ResType(400,104);
        }
        try {
            AdvertisementEntity advertisementEntity = advertisementDao.save(new AdvertisementEntity(title,url,picture,1, 10000.0,"正在投放"));
            return new ResType(e2d(advertisementEntity));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,102);
        }
    }

    @Override
    public ResType getAdvertisement(Integer id) {
        try{
            AdvertisementEntity advertisementEntity = advertisementDao.findById(id).orElse(null);
            return new ResType(e2d(advertisementEntity));
        }catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,101);
        }
    }

    @Override
    public ResType changeAdvertisementStatus(Integer id,String status) {
        try{
            AdvertisementEntity advertisement = advertisementDao.findById(id).orElse(null);
            advertisement.setStatus(status);
            advertisement = advertisementDao.save(advertisement);
            return new ResType(e2d(advertisement));
        }catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,103);
        }

    }

    @Override
    public ResType getAdvertisementThrow(Integer id){
        try{
//            AdvertisementEntity advertisement = advertisementDao.findById(id).orElse(null);
            return new ResType();
        }catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,103);
        }
    }


    public AdvertisementDto e2d(AdvertisementEntity advertisementEntity) {
        AdvertisementDto advertisementDto = new AdvertisementDto();
        BeanUtils.copyProperties(advertisementEntity, advertisementDto);
        return advertisementDto;
    }

}


