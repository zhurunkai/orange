package org.zust.advertisement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.advertisement.entity.AdvertisementEntity;

import java.util.List;

public interface AdvertisementDao extends JpaRepository<AdvertisementEntity,Integer> {
    public List<AdvertisementEntity> findByOwner(Integer adUserId);
}
