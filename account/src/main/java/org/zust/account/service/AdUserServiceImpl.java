package org.zust.account.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.AdUserDao;
import org.zust.account.dao.ThrowRecordsDao;
import org.zust.account.entity.AdUserEntity;
import org.zust.account.entity.ThrowRecordsEntity;
import org.zust.interfaceapi.dto.AdUserDto;
import org.zust.interfaceapi.dto.ThrowRecordsDto;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdUserServiceImpl implements AdUserService {

    @Autowired
    private AdUserDao adUserDao;

    @Autowired
    private ThrowRecordsDao throwRecordsDao;


    @Override
    public ResType findBookUserAllInformById(int auId) {
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
                list.add(e2d(t));
            }
            System.out.println(byOwner);
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
