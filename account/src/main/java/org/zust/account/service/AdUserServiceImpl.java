package org.zust.account.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.AdUserDao;
import org.zust.account.dao.ThrowRecordsDao;
import org.zust.account.entity.AdUserEntity;
import org.zust.account.entity.ThrowRecordsEntity;
import org.zust.account.utils.IdentifyingCode;
import org.zust.account.utils.RandomName;
import org.zust.account.utils.RandomProfile;
import org.zust.interfaceapi.dto.*;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.service.AdvertisementService;
import org.zust.interfaceapi.service.BookService;
import org.zust.interfaceapi.utils.ResType;

import java.util.*;

@Service
@org.apache.dubbo.config.annotation.Service
public class AdUserServiceImpl implements AdUserService {

//    @Reference
//    private BookService bookService;
//
//    @Reference
//    private AdvertisementService advertisementService;
//
    @Reference(check = false)
    private AdvertisementService advertisementService;

    @Reference(check = false)
    private BookService bookService;

    @Autowired
    private AdUserDao adUserDao;

    @Autowired
    private ThrowRecordsDao throwRecordsDao;


    @Override
    public ResType lrAdUser(Map param) {
        try{
            String phone = (String) param.get("phone");
            AdUserEntity adUserEntity = adUserDao.findByPhone(phone);
            if(adUserEntity == null){
                Double money = 10000.00;
                String randomName = RandomName.nameString();
                String randomProfile = RandomProfile.profileString();
                Double freeze =0.00;
                String token1 = IdentifyingCode.md5(phone+new Date().getTime());

                AdUserEntity data =new AdUserEntity(money,phone,randomName,randomProfile,freeze,token1);
                AdUserEntity save = adUserDao.save(data);

                return new ResType(e2d(save));

            }else{
                String token2 = IdentifyingCode.md5(phone+new Date().getTime());
                AdUserEntity adUserEntity1 = adUserDao.findByPhone(phone);
                adUserEntity1.setToken(token2);

                AdUserEntity data = adUserEntity1;
                AdUserEntity save = adUserDao.save(data);
                return new ResType(e2d(adUserEntity1));
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResType(400,107);
        }
    }

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


                String bid = Integer.toString(t.getBook());
                HashMap<String, Object> map = new HashMap<>();
                map.put("id",bid);
                Integer adid = t.getAdvertisement();

                System.out.println(adid);

                ResType book = bookService.getBook(map);
                ResType ad = advertisementService.getAdvertisement(adid);
                ResType au = findAdUserAllInformById(id);

                System.out.println(book);



                BookDto bookDto = (BookDto) book.getData();
//                bookDto.setOwner();
                AdvertisementDto advertisementDto = (AdvertisementDto) ad.getData();
                AdUserDto adUserDto = (AdUserDto) au.getData();
                System.out.println(adUserDto);

                ThrowRecordsDto throwRecordsDto = e2d(t);
                throwRecordsDto.setBook(bookDto);
                throwRecordsDto.setAdvertisement(advertisementDto);
                throwRecordsDto.setOwner(adUserDto);
                System.out.println(throwRecordsDto);
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

                String bid = Integer.toString(t.getBook());
                Integer adid = t.getAdvertisement();


                Integer ownerId = t.getOwner();

                HashMap<String, Object> map = new HashMap<>();
                map.put("id",bid);
                ResType book = bookService.getBook(map);
                ResType ad = advertisementService.getAdvertisement(adid);
                ResType au = findAdUserAllInformById(ownerId);

                BookDto bookDto = (BookDto) book.getData();
                AdvertisementDto advertisementDto = (AdvertisementDto) ad.getData();
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

    // 某广告主所有广告最近7天每天的点击数
    public ResType getAd7DaysClickByAdUserId(Integer id) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return
        }
    }
}
