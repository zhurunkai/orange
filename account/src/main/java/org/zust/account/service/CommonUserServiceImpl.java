package org.zust.account.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.zust.account.dao.AdUserDao;
import org.zust.account.dao.BookUserDao;
import org.zust.account.entity.AdUserEntity;
import org.zust.account.entity.BookUserEntity;
import org.zust.interfaceapi.dto.AdUserDto;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.CommonUserService;
import org.zust.interfaceapi.utils.ResType;

@Service
@org.apache.dubbo.config.annotation.Service(timeout = 300000)
public class CommonUserServiceImpl implements CommonUserService {
    @Autowired
    private AdUserDao adUserDao;
    @Autowired
    private BookUserDao bookUserDao;
    //    校验token获取用户身份
    public ResType checkToken(String token) {
        if("".equals(token) || token == null) {
            return new ResType(401,101);
        }
        // 查询
        try {
            AdUserEntity adUserEntity = adUserDao.findByToken(token);
            if(adUserEntity != null) {
                AdUserDto adUserDto = adUserE2d(adUserEntity);
                return new ResType(adUserDto);
            }
            BookUserEntity bookUserEntity = bookUserDao.findByToken(token);
            if(bookUserEntity != null) {
                BookUserDto bookUserDto = bookUserE2d(bookUserEntity);
                return new ResType(bookUserDto);
            }
            return new ResType(401,102);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResType(401,103);
        }
    }

    public AdUserDto adUserE2d(AdUserEntity adUserEntity) {
        if(adUserEntity==null)
            return null;
        AdUserDto adUserDto=new AdUserDto();
        BeanUtils.copyProperties(adUserEntity,adUserDto);
        return  adUserDto;
    }
    public BookUserDto bookUserE2d(BookUserEntity bookUserEntity) {
        if(bookUserEntity==null)
            return null;
        BookUserDto bookUserDto=new BookUserDto();
        BeanUtils.copyProperties(bookUserEntity,bookUserDto);
        return  bookUserDto;
    }
}
