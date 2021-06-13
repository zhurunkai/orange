package org.zust.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.account.entity.TabWeightEntity;

import java.util.List;

public interface TabWeightDao extends JpaRepository<TabWeightEntity,Integer> {
      List<TabWeightEntity> findAllByUser(Integer id);

      TabWeightEntity findByUserAndTab(Integer uid,Integer tab);
}
