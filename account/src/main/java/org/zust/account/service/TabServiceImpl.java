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
    public Boolean changeWeight(List list,Integer uid,Integer weight) {
        for (Object o : list) {
            TabWeightEntity tabWeight = tabWeightDao.findByUserAndTab(uid, (Integer) o);
            tabWeight.setWeight(tabWeight.getWeight()+weight);
            tabWeightDao.save(tabWeight);
        }
        return true;
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

    @Override
    public Boolean isTagExist(Integer uid, Integer tab) {

        // 如果用户没有此书的id，则在tag_weight上加上
        // 有的话，则加30权重
        TabWeightEntity tabWeight = tabWeightDao.findByUserAndTab(uid, tab);
        if (tabWeight==null){
            TabWeightEntity tabWeightEntity = new TabWeightEntity();
            tabWeightEntity.setTab(tab);
            tabWeightEntity.setUser(uid);
            tabWeightEntity.setWeight(100);
            tabWeightDao.save(tabWeightEntity);
        }else {
            tabWeight.setWeight(tabWeight.getWeight()+30);
            tabWeightDao.save(tabWeight);
        }

        return true;
    }

    //根据tagid找名字
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
            TabEntity tabEntity = tabDao.findById( (Integer) tab).orElse(null);
            tabs.add(tabEntity.getName());
        }
        return new ResType(tabs);
    }


}
