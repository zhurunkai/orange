package org.zust.account.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.AdUserDao;
import org.zust.account.dao.SaltDao;
import org.zust.account.dao.ThrowRecordsDao;
import org.zust.account.entity.AdUserEntity;
import org.zust.account.entity.SaltEntity;
import org.zust.account.entity.ThrowRecordsEntity;
import org.zust.account.utils.IdentifyingCode;
import org.zust.account.utils.RandomName;
import org.zust.account.utils.RandomProfile;
import org.zust.interfaceapi.dto.*;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.service.AdvertisementService;
import org.zust.interfaceapi.service.BookService;
import org.zust.interfaceapi.service.BookUserService;
import org.zust.interfaceapi.utils.ResType;
import org.zust.interfaceapi.utils.Utils;

import java.text.SimpleDateFormat;
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
    @Autowired
    private BookUserService bookUserService;
    @Autowired
    private SaltDao saltDao;


    @Override
    public ResType lrAdUser(Map param) {
        try {
            String phone = (String) param.get("phone");
            String salt = (String) param.get("salt");
            String captcha = (String) param.get("captcha");
            
            SaltEntity yanzheng = saltDao.findOneBy(phone, salt, captcha);

            if(yanzheng != null){
                AdUserEntity adUserEntity = adUserDao.findByPhone(phone);
                if (adUserEntity == null) {
                    Double money = 10000.00;
                    String randomName = RandomName.nameString();
                    String randomProfile = RandomProfile.profileString();
                    Double freeze = 0.00;
                    String token1 = IdentifyingCode.md5(phone + new Date().getTime());

                    AdUserEntity data = new AdUserEntity(money, phone, randomName, randomProfile, freeze, token1);
                    AdUserEntity save = adUserDao.save(data);

                    return new ResType(e2d(save));

                } else {
                    String token2 = IdentifyingCode.md5(phone + new Date().getTime());
                    AdUserEntity adUserEntity1 = adUserDao.findByPhone(phone);
                    adUserEntity1.setToken(token2);

                    AdUserEntity data = adUserEntity1;
                    AdUserEntity save = adUserDao.save(data);
                    return new ResType(e2d(adUserEntity1));
                }
            }else{
                return new ResType(500,111);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(400, 107);
        }
    }

    @Override
    public ResType findAdUserAllInformById(int auId) {
        try {
//            System.out.println("开始" + System.currentTimeMillis());
            AdUserEntity ausere = adUserDao.findOneById(auId);
//            System.out.println("结束" + System.currentTimeMillis());
            return new ResType(e2d(ausere));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 101);
        }
    }

    @Override
    public ResType findAdUserBillById(int id) {
        try {
            List<ThrowRecordsEntity> byOwner = throwRecordsDao.findByOwner(id);
            List<ThrowRecordsDto> list = new ArrayList<>();
            List<Integer> adids = new ArrayList<>();
            List<Integer> bids = new ArrayList<>();
            for (ThrowRecordsEntity throwRecordsEntity : byOwner) {
                adids.add(throwRecordsEntity.getAdvertisement());
                bids.add(throwRecordsEntity.getBook());
            }
            List<BookDto> bookDtos = new ArrayList<>();
            for (Integer bid : bids) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("id",String.valueOf(bid));
                bookDtos.add((BookDto) (bookService.getBook(map).getData()));
            }
            ResType adRes = advertisementService.getAdByIds(adids);
            if(adRes.getStatus()!=200) {
                return new ResType(500,108);
            }
            List<AdvertisementDto> advertisementDtos = (List<AdvertisementDto>) advertisementService.getAdByIds(adids).getData();
            System.out.println(advertisementDtos);
            for (AdvertisementDto advertisementDto : advertisementDtos) {
                System.out.println(advertisementDto);
            }
            for (int i = 0; i < byOwner.size(); i++) {
                ThrowRecordsDto throwRecordsDto = e2d(byOwner.get(i));
                throwRecordsDto.setAdvertisement(advertisementDtos.get(i));
                throwRecordsDto.setBook(bookDtos.get(i));
                list.add(throwRecordsDto);
            }
//            for (ThrowRecordsEntity t : byOwner) {
//
////                ResType book = bookService.getBook(map);
////                ResType ad = advertisementService.getAdvertisement(adid);
////                BookDto bookDto = (BookDto) book.getData();
////                AdvertisementDto advertisementDto = (AdvertisementDto) ad.getData();
////                AdUserDto adUserDto = (AdUserDto) au.getData();
//                ThrowRecordsDto throwRecordsDto = e2d(t);
//                throwRecordsDto.setBook(bookDto);
//                throwRecordsDto.setAdvertisement(advertisementDto);
//                throwRecordsDto.setOwner(adUserDto);
//                System.out.println(throwRecordsDto);
//                list.add(throwRecordsDto);
//            }
            return new ResType(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 101);
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
        try {
            List<ThrowRecordsEntity> byAdvertisement = throwRecordsDao.findByAdvertisement(id);
            ArrayList list = new ArrayList<>();
            for (ThrowRecordsEntity t : byAdvertisement) {

                String bid = Integer.toString(t.getBook());
                Integer adid = t.getAdvertisement();


                Integer ownerId = t.getOwner();

                HashMap<String, Object> map = new HashMap<>();
                map.put("id", bid);
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
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 101);
        }
    }

    @Override
    public ResType findAdsByAuId(int id) {
        try{

         ResType ad = advertisementService.getAdvertisementByAdUser(id);
         if(ad.getStatus()==200) {
             return new ResType(ad.getData());
         }
         return new ResType(500,101);

        }catch(Exception e){
            e.printStackTrace();
            return new ResType(500,101);
        }


    }

    private AdUserDto e2d(AdUserEntity adUserEntity) {
        if (adUserEntity == null)
            return null;
        AdUserDto adUserDto = new AdUserDto();
        BeanUtils.copyProperties(adUserEntity, adUserDto);
        return adUserDto;
    }

    private ThrowRecordsDto e2d(ThrowRecordsEntity throwRecordsEntity) {
        if (throwRecordsEntity == null)
            return null;
        ThrowRecordsDto throwRecordsDto = new ThrowRecordsDto();
        BeanUtils.copyProperties(throwRecordsEntity, throwRecordsDto);
        return throwRecordsDto;
    }

    // 某广告主所有广告最近7天每天的点击数
//    public ResType getAd7DaysClickByAdUserId(Integer id) {
//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return
//        }
//    }
    public ResType getAd7DaysClickByAdUserId(Integer id) {
        try {
            List<Map<String, Integer>> list = new ArrayList<>();
            for (int i = 1; i < 8; i++) {
                Map<String, Integer> map = new HashMap<>();
                Date startTime = Utils.getBeforeKDay0Date(0, new Date(), i);
                Date endTime = Utils.getBeforeKDay0Date(1, new Date(), i);
                map.put((startTime.getMonth() + 1) + "." + startTime.getDate(), throwRecordsDao.get7DaysClickNums(1, startTime, endTime));
                list.add(map);
            }
            Collections.reverse(list);
            return new ResType(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 101);
        }
    }

    // 某广告主所有广告最近7天每天的投放次数
    public ResType getAd7DaysNumsByAdUserId(Integer id) {
        try {
            List<Map<String, Integer>> list = new ArrayList<>();
            for (int i = 1; i < 8; i++) {
                Map<String, Integer> map = new HashMap<>();
                Date startTime = Utils.getBeforeKDay0Date(0, new Date(), i);
                Date endTime = Utils.getBeforeKDay0Date(1, new Date(), i);
                map.put((startTime.getMonth() + 1) + "." + startTime.getDate(), throwRecordsDao.get7DaysNums(1, startTime, endTime));
                list.add(map);
            }
            Collections.reverse(list);
            return new ResType(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 101);
        }
    }

    // 某广告主所有广告最近7天每天的查看数
    public ResType getAd7DaysShowByAdUserId(Integer id) {
        try {
            List<Map<String, Integer>> list = new ArrayList<>();
            for (int i = 1; i < 8; i++) {
                Map<String, Integer> map = new HashMap<>();
                Date startTime = Utils.getBeforeKDay0Date(0, new Date(), i);
                Date endTime = Utils.getBeforeKDay0Date(1, new Date(), i);
                map.put((startTime.getMonth() + 1) + "." + startTime.getDate(), throwRecordsDao.get7DaysShowNums(1, startTime, endTime));
                list.add(map);
            }
            Collections.reverse(list);
            return new ResType(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 101);
        }
    }

    // 某广告主所有广告最近7天每天的扣费数
    public ResType getAd7DaysCostByAdUserId(Integer id) {
        try {
            List<Map<String, Double>> list = new ArrayList<>();
            for (int i = 1; i < 8; i++) {
                Map<String, Double> map = new HashMap<>();
                Date startTime = Utils.getBeforeKDay0Date(0, new Date(), i);
                Date endTime = Utils.getBeforeKDay0Date(1, new Date(), i);
                System.out.println(startTime);
                System.out.println(endTime);
                Double cost = throwRecordsDao.get7DaysCostNums(1, startTime, endTime);
                if (cost == null) {
                    cost = 0.0;
                }
                map.put((startTime.getMonth() + 1) + "." + startTime.getDate(), cost);
                System.out.println(map);
                list.add(map);
            }
            Collections.reverse(list);
            return new ResType(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500, 101);
        }
    }

    // 获取某位广告主所有广告投放到的读书人的标签权重和
    public ResType getAdBuserTabWeights(Integer id) {
        try {
            Map<String,Integer> weightMap = new HashMap<>();
            List<ThrowRecordsEntity> throwRecordsEntities = throwRecordsDao.findThrowByAd(id);
            for (ThrowRecordsEntity throwRecordsEntity : throwRecordsEntities) {
                ResType res = calcBuserWeight(id,weightMap);
                if(res.getStatus()==200) {
                    weightMap = (Map<String, Integer>) res.getData();
                }
            }
            return new ResType(weightMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,101);
        }
    }

    private ResType calcBuserWeight(Integer buserId,Map<String,Integer> map) {
        ResType findRes = bookUserService.findTabWeightByBuid(buserId);
        if(findRes.getStatus()==200) {
            List<TabWeightDto> tabWeightDtos = (List<TabWeightDto>) findRes.getData();
            for (TabWeightDto tabWeightDto : tabWeightDtos) {
                if(map.keySet().contains(tabWeightDto.getTab().getName())) {
                    map.put(tabWeightDto.getTab().getName(),map.get(tabWeightDto.getTab().getName())+tabWeightDto.getWeight());
                } else {
                    map.put(tabWeightDto.getTab().getName(),tabWeightDto.getWeight());
                }
            }
            return new ResType(map);
        }
        return new ResType(500,108);
    }

    public ResType getAdUserAllInfoByIds(List<Integer> ids) {
        try {
            List<AdUserEntity> adUserEntities =  adUserDao.findAllByIds(ids);
            List<AdUserDto> adUserDtos = new ArrayList<>();
            for (AdUserEntity adUserEntity : adUserEntities) {
                adUserDtos.add(e2d(adUserEntity));
            }
            return new ResType(adUserDtos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,101);
        }
    }
}
