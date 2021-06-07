package org.zust.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.SaltDao;
import org.zust.account.entity.SaltEntity;
import org.zust.account.utils.IdentifyingCode;
import org.zust.interfaceapi.service.AdUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

@Service
public class AdUserServiceImpl implements AdUserService {

    @Autowired
    private SaltDao saltDao;


    @Override
    public ResType adidentifyCode(Map param) {
        Map saltdata = IdentifyingCode.execute((String)param.get("phone"));
        if((int)saltdata.get("status") == 0) {
            return new ResType(400,101);
        }
        String salt = (String)saltdata.get("salt");
        String randomcode =(String) saltdata.get("randomCode");
        String phone = (String) param.get("phone");

        SaltEntity data = new SaltEntity(phone,salt,randomcode);
        System.out.println(data);
        SaltEntity save = saltDao.save(data);
        System.out.println(save);

        return new ResType(save);


    }
}
