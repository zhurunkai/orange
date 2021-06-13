package org.zust.advertisement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.advertisement.entity.AdTabEntity;
import org.zust.advertisement.entity.AdvertisementEntity;

import java.util.List;

public interface AdTabDao extends JpaRepository<AdTabEntity,Integer> {
    List<AdTabEntity> findByTab(Integer id);
}
