package org.zust.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.TabDao;
import org.zust.account.entity.TabEntity;
import org.zust.interfaceapi.service.TabService;
import org.zust.interfaceapi.utils.ResType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@org.apache.dubbo.config.annotation.Service
public class TabServiceImpl implements TabService {

    @Autowired
    TabDao tabDao;


    @Override
    public ResType changeWeight(List list,Integer uid,Integer weight) {



        return null;
    }

    //通过tag名找id
    @Override
    public ResType findTagIdByName(Map params) {
        ArrayList tabs = (ArrayList) params.get("tabs");
        ArrayList nums = new ArrayList();
        //System.out.println("size ="+tabs.size());
        if(tabs == null){
            return new ResType(400,103);
        }
        for (Object tab : tabs) {
//            System.out.println(tab);
            TabEntity tabEntity = tabDao.findByName(String.valueOf(tab));
            nums.add(tabEntity.getId());
        }
        return new ResType(nums);
    }

    //根据tag名找id集合
    @Override
    public ResType findtagNameById(Map param) {
        ArrayList nums = (ArrayList) param.get("id");
        ArrayList tabs = new ArrayList();
        //System.out.println("size ="+tabs.size());
        if(nums == null){
            return new ResType(400,103);
        }
        for (Object tab : tabs) {
//            System.out.println(tab);
            TabEntity tabEntity = tabDao.findById((Integer) tab).orElse(null);
            tabs.add(tabEntity.getName());
        }
        return new ResType(tabs);
    }


}
