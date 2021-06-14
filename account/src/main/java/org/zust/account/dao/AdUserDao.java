package org.zust.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zust.account.entity.AdUserEntity;
import org.zust.account.entity.BookUserEntity;

import java.util.List;


public interface AdUserDao extends JpaRepository<AdUserEntity,Integer> {
    public AdUserEntity findByPhone(String phone);
    public AdUserEntity findByToken(String token);
    public AdUserEntity findOneById(int id);
    @Query(value = "from AdUserEntity where id in ?1")
    public List<AdUserEntity> findAllByIds(List<Integer> ids);
}
