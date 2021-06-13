package org.zust.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zust.account.dao.TabDao;
import org.zust.account.dao.TabWeightDao;
import org.zust.account.entity.TabEntity;
import org.zust.account.entity.TabWeightEntity;
import org.zust.interfaceapi.service.TabService;
import org.zust.interfaceapi.utils.ResType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@org.apache.dubbo.config.annotation.Service(timeout = 300000)
public class TabServiceImpl implements TabService {

    @Autowired
    TabDao tabDao;
    @Autowired
    private TabWeightDao tabWeightDao;


    @Override
    public ResType changeWeight(List list) {
        return null;
    }

    @Override
    public ResType findTagIdByName(Map param) {
        ArrayList tabs = (ArrayList) param.get("tabs");
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

    @Override
    public Boolean isTagExist(Integer uid, Integer tab) {

        TabWeightEntity tabWeight = tabWeightDao.findByUserAndTab(uid, tab);
        if (tabWeight==null){
            TabWeightEntity tabWeightEntity = new TabWeightEntity();
            tabWeightEntity.setTab(tab);
            tabWeightEntity.setUser(uid);
            tabWeightEntity.setWeight(100);
            tabWeightDao.save(tabWeightEntity);
        }

        return true;
    }


}
