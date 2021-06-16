package org.zust.advertisement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zust.advertisement.entity.AdvertisementEntity;

import java.util.List;

public interface AdvertisementDao extends JpaRepository<AdvertisementEntity,Integer> {
    public List<AdvertisementEntity> findByOwner(Integer adUserId);
    @Query(value = "from AdvertisementEntity where id in ?1")
    public List<AdvertisementEntity> findAllByIds(List<Integer> ids);
    public AdvertisementEntity findOneById(Integer id);
}
