package org.zust.account.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Service;
import org.zust.account.dao.AdUserDao;
import org.zust.account.dao.ThrowRecordsDao;
import org.zust.account.entity.AdUserEntity;
import org.zust.account.entity.ThrowRecordsEntity;
import org.zust.interfaceapi.dto.*;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.service.AdvertisementService;
import org.zust.interfaceapi.service.BookService;
import org.zust.interfaceapi.utils.ResType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdUserServiceImpl implements AdUserService {

//    @Reference
//    private BookService bookService;
//
//    @Reference
//    private AdvertisementService advertisementService;
//

    @Autowired
    private AdUserDao adUserDao;

    @Autowired
    private ThrowRecordsDao throwRecordsDao;


    @Override
    public ResType findAdUserAllInformById(int auId) {
        try {
            AdUserEntity ausere = adUserDao.findById(auId).orElse(null);
            return new ResType(e2d(ausere));
        }catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,101);
        }
    }

    @Override
    public ResType findAdUserBillById(int id) {
        try{
            List<ThrowRecordsEntity> byOwner = throwRecordsDao.findByOwner(id);
            ArrayList list = new ArrayList<>();
            for (ThrowRecordsEntity t : byOwner) {

//                ResType book = ;
//                Restype ad =   ;
                ResType au = findAdUserAllInformById(id);

                BookDto bookDto = new BookDto();
                AdvertisementDto advertisementDto = new AdvertisementDto();
                AdUserDto adUserDto = (AdUserDto) au.getData();
                System.out.println(adUserDto);

                ThrowRecordsDto throwRecordsDto = e2d(t);
                throwRecordsDto.setBook(bookDto);
                throwRecordsDto.setAdvertisement(advertisementDto);
                throwRecordsDto.setOwner(adUserDto);
                list.add(throwRecordsDto);
            }
            return new ResType(list);
        }catch (Exception e){
            e.printStackTrace();
            return new ResType(500,101);
        }
    }

    @Override
    public ResType findRecordsByAdId(int id) {
//        List<ThrowRecordsEntity> throwRecordsEntities = throwRecordsDao.findByAdvertisement(id);
//        List<AdUserEntity> data = new ArrayList<>();
//        for (ThrowRecordsEntity t: throwRecordsEntities) {
//                Integer ownerId = t.getOwner();
//                AdUserEntity adUserEntity = adUserDao.findById(ownerId).orElse(null);
//                data.add(adUserEntity);
//        }
//            ResType<List<AdUserEntity>> resType = new ResType<>();
//            resType.setData(data);
//            resType.setStatus(200);
//            return resType;
        try{
            List<ThrowRecordsEntity> byAdvertisement = throwRecordsDao.findByAdvertisement(id);
            ArrayList list = new ArrayList<>();
            for (ThrowRecordsEntity t : byAdvertisement) {

//                ResType book = ;
//                Restype ad =   ;
                Integer ownerId = t.getOwner();

                ResType au = findAdUserAllInformById(ownerId);

                BookDto bookDto = new BookDto();
                AdvertisementDto advertisementDto = new AdvertisementDto();
                AdUserDto adUserDto = (AdUserDto) au.getData();

                ThrowRecordsDto throwRecordsDto = e2d(t);
                throwRecordsDto.setBook(bookDto);
                throwRecordsDto.setAdvertisement(advertisementDto);
                throwRecordsDto.setOwner(adUserDto);
                list.add(throwRecordsDto);
            }
//            System.out.println(byOwner);
            return new ResType(list);
        }catch (Exception e){
            e.printStackTrace();
            return new ResType(500,101);
        }
    }

    private AdUserDto e2d(AdUserEntity adUserEntity) {
        if(adUserEntity==null)
            return null;
        AdUserDto adUserDto=new AdUserDto();
        BeanUtils.copyProperties(adUserEntity,adUserDto);
        return  adUserDto;
    }

    private ThrowRecordsDto e2d(ThrowRecordsEntity throwRecordsEntity) {
        if(throwRecordsEntity==null)
            return null;
        ThrowRecordsDto throwRecordsDto=new ThrowRecordsDto();
        BeanUtils.copyProperties(throwRecordsEntity,throwRecordsDto);
        return  throwRecordsDto;
    }
}
