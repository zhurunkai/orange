package org.zust.advertisement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.advertisement.entity.AdvertisementEntity;

public interface AdvertisementDao extends JpaRepository<AdvertisementEntity,Integer> {

}
