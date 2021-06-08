package org.zust.account.service;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.BookUserDao;
import org.zust.account.dao.SaltDao;
import org.zust.account.entity.BookUserEntity;
import org.zust.account.entity.SaltEntity;
import org.zust.account.utils.IdentifyingCode;
import org.zust.account.utils.RandomName;
import org.zust.account.utils.RandomProfile;
import org.zust.interfaceapi.dto.BookUserDto;
import org.zust.interfaceapi.service.BookUserService;
import org.zust.interfaceapi.utils.ResType;

import java.util.Date;
import java.util.Map;

@Service
public class BookUserServiceImpl implements BookUserService {
    @Autowired
    private SaltDao saltDao;
    @Autowired
    private BookUserDao bookUserDao;


    //读者登录所用验证码
    @Override
    public ResType identifyCode(Map param) {
        Map saltdata = IdentifyingCode.execute((String)param.get("phone"));
        if((int)saltdata.get("status") == 0) {
            return new ResType(400,101);
        }
        String salt = (String)saltdata.get("salt");
        String randomcode =(String) saltdata.get("randomCode");
        String phone = (String) param.get("phone");

        SaltEntity data = new SaltEntity(phone,salt,randomcode);
        SaltEntity save = saltDao.save(data);

        return new ResType(save);
    }

    @Override
    public ResType lrBookUser(Map param) {

        try{
            String phone = (String) param.get("phone");
            BookUserEntity bookUserEntity = bookUserDao.findByPhone(phone);
            if(bookUserEntity == null){
                String randomProfile = RandomProfile.profileString();
                Integer age = 0;
                String randomName = RandomName.nameString();
                String sex = "男";
                String token1 = IdentifyingCode.md5(phone+new Date().getTime());

                BookUserEntity data =new BookUserEntity(phone,randomProfile,age,randomName,sex,token1);
                BookUserEntity save = bookUserDao.save(data);

                return new ResType(e2d(save));

            }else{
                String token2 = IdentifyingCode.md5(phone+new Date().getTime());
                BookUserEntity bookUserEntity1 = bookUserDao.findByPhone(phone);
                bookUserEntity1.setToken(token2);
                return new ResType(e2d(bookUserEntity1));
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResType(400,107);
        }


    }

    //通过id查找读书人的所有信息
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

    private BookUserEntity d2e (BookUserDto bookUserDto){
        if(bookUserDto==null)
            return null;
        BookUserEntity bookUserEntity=new BookUserEntity();
        BeanUtils.copyProperties(bookUserDto,bookUserEntity);
        return bookUserEntity;
    }
}
