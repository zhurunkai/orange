package org.zust.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.account.entity.TabEntity;



public interface TabDao extends JpaRepository<TabEntity,Integer> {

}
