package org.zust.account.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.BookUserDao;
import org.zust.account.dao.SaltDao;
import org.zust.account.entity.BookUserEntity;
import org.zust.account.entity.SaltEntity;
import org.zust.account.utils.IdentifyingCode;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Map;

@Service
public class BookUserServiceImpl implements BookUserService {
    @Autowired
    private SaltDao saltDao;
    @Autowired
    private BookUserDao bookUserDao;


    //读者登录所用验证码
    @Override
    public ResType bookidentifyCode(Map param) {
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

    @Override
    public ResType lrBookUser(Map param) {

        return null;
    }

    @Override
    public ResType findBookUserAllInformById(int buId) {
        try {
            BookUserEntity busere = bookUserDao.findById(buId).orElse(null);
            return new ResType(e2d(busere));
        }catch (Exception e) {
            e.printStackTrace();
            return new ResType(500,101);
        }
    }

    private BookUserDto e2d(BookUserEntity bookUserEntity) {
        if(bookUserEntity==null)
            return null;
        BookUserDto bookUserDto=new BookUserDto();
        BeanUtils.copyProperties(bookUserEntity,bookUserDto);
        return  bookUserDto;
    }
}
