package org.zust.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.zust.account.dao.TabDao;
import org.zust.account.entity.TabEntity;
import org.zust.interfaceapi.service.TabService;
import org.zust.interfaceapi.utils.ResType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class TabServiceImpl implements TabService {

    @Autowired
    TabDao tabDao;


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


}
