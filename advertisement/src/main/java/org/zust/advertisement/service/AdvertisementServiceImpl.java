package org.zust.advertisement.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.ThrowRecordsDao;
import org.zust.account.entity.ThrowRecordsEntity;
import org.zust.advertisement.dao.AdTabDao;
import org.zust.advertisement.dao.AdvertisementDao;
import org.zust.advertisement.entity.AdTabEntity;
import org.zust.advertisement.entity.AdvertisementEntity;
import org.zust.interfaceapi.dto.AdUserDto;
import org.zust.interfaceapi.dto.ThrowRecordsDto;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.service.AdvertisementService;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.utils.ResType;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.valueOf;

@Service
@org.apache.dubbo.config.annotation.Service(timeout = 300000)
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    AdvertisementDao advertisementDao;
    @Autowired
    AdTabDao adTabDao;

    @Reference(check = false)
    private AdUserService adUserService;

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
            if(adUserService.findRecordsByAdId(id).getStatus()!=200)
            {
                return new ResType(500,101);
            }
            System.out.println(adUserService.findRecordsByAdId(id).getData());
            List advertisementThrow = (ArrayList) adUserService.findRecordsByAdId(id).getData();
            return new ResType(advertisementThrow);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,108);
        }
    }


    public AdvertisementDto e2d(AdvertisementEntity advertisementEntity) {
        AdvertisementDto advertisementDto = new AdvertisementDto();
        BeanUtils.copyProperties(advertisementEntity, advertisementDto);
        advertisementDto.setOwner((AdUserDto) (adUserService.findAdUserAllInformById(advertisementEntity.getOwner()).getData()));
        return advertisementDto;
    }

    public AdvertisementDto pureE2d(AdvertisementEntity advertisementEntity) {
        AdvertisementDto advertisementDto = new AdvertisementDto();
        BeanUtils.copyProperties(advertisementEntity, advertisementDto);
        return advertisementDto;
    }

    // 根据tabid获得所有广告id
    @Override
    public ResType getAdvertisementByTabId(Integer tabId) {
        try {
            List<AdTabEntity> adTabEntities = adTabDao.findByTab(tabId);
            List<AdvertisementDto> advertisementDtos = new ArrayList<>();
            for (AdTabEntity adTabEntity : adTabEntities) {
                advertisementDtos.add((AdvertisementDto) getAdvertisement(adTabEntity.getAdvertisement()).getData());
            }
            return new ResType(advertisementDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,108);
        }


    }

    // 根据广告主id获得广告信息
    public ResType getAdvertisementByAdUser(Integer adUserId) {
        try {
            List<AdvertisementDto> advertisementDtos = new ArrayList<>();
            List<AdvertisementEntity> advertisementEntities = advertisementDao.findByOwner(adUserId);
            for (AdvertisementEntity advertisementEntity : advertisementEntities) {
                advertisementDtos.add(e2d(advertisementEntity));
            }
            return new ResType(advertisementDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,101);
        }
    }

    // 根据ids获得list
    public ResType getAdByIds(List<Integer> ids) {
        try {
            List<AdvertisementEntity> advertisementEntities = new ArrayList<>();
            for (Integer id : ids) {
                advertisementEntities.add(advertisementDao.findOneById(id));
            }
//            System.out.println(ids);
//            System.out.println(advertisementEntities);
            List<AdvertisementDto> advertisementDtos = new ArrayList<>();
            for (AdvertisementEntity advertisementEntity : advertisementEntities) {
                AdvertisementDto advertisementDto = pureE2d(advertisementEntity);
                advertisementDto.setOwner((AdUserDto) adUserService.findAdUserAllInformById(advertisementEntity.getOwner()).getData());
                advertisementDtos.add(advertisementDto);
            }
//            System.out.println(advertisementDtos);
//            List<AdUserDto> adUserDtos = (List<AdUserDto>) res.getData();
//            System.out.println(adUserDtos.size());
//            System.out.println(advertisementDtos.size());
//            for (int i = 0; i < advertisementDtos.size(); i++) {
//                advertisementDtos.get(i).setOwner(adUserDtos.get(i));
//            }
            return new ResType(advertisementDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,101);
        }
    }
}


