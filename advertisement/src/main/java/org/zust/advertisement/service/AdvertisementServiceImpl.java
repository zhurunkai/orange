package org.zust.advertisement.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.advertisement.dao.AdTabDao;
import org.zust.advertisement.dao.AdvertisementDao;
import org.zust.advertisement.entity.AdTabEntity;
import org.zust.advertisement.entity.AdvertisementEntity;
import org.zust.interfaceapi.dto.AdUserDto;
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.service.AdvertisementService;
<<<<<<< HEAD
=======
import org.zust.interfaceapi.dto.AdvertisementDto;
import org.zust.interfaceapi.service.TabService;
>>>>>>> 2383fedd687d01ec3db18424145f8d1869c329aa
import org.zust.interfaceapi.utils.ResType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@org.apache.dubbo.config.annotation.Service(timeout = 300000)
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    AdvertisementDao advertisementDao;
    @Autowired
    AdTabDao adTabDao;

    @Reference(check = false)
    TabService tabService;

    @Reference(check = false)
    private AdUserService adUserService;

    @Override
    public ResType addAdvertisement(Map params) {
        String title = null;
        String url = null;
        String picture = null;
        Double budget = 0.0;
        List<String> tabs = new ArrayList<>();
        try {
            title = (String)params.get("title");
            url = (String)params.get("url");
            picture = (String)params.get("picture");
            budget = new Double((String)params.get("budget"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(400,104);
        }
        try {
            AdvertisementEntity advertisementEntity = advertisementDao.save(new AdvertisementEntity(title,url,picture,((AdUserDto)params.get("user")).getId(), budget,"????????????"));
            ResType res1 = adUserService.cost(budget,((AdUserDto)params.get("user")).getId());
            if(res1.getStatus()!=200) {
                return res1;
            }
            ResType res = tabService.findTagIdByName(params);
            if(res.getStatus()!=200) {
                return res;
            }
            List<Integer> tabIds = (List<Integer>) (res.getData());
            for (Integer tabId : tabIds) {
                adTabDao.save(new AdTabEntity(tabId,advertisementEntity.getId()));
            }
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

    // ??????tabid??????????????????id
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

    // ???????????????id??????????????????
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

    // ??????ids??????list
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


