package org.zust.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.account.entity.AdUserEntity;
import org.zust.account.entity.BookUserEntity;


public interface AdUserDao extends JpaRepository<AdUserEntity,Integer> {
    public AdUserEntity findByPhone(String phone);
    public AdUserEntity findByToken(String token);
    public AdUserEntity findOneById(int id);
}
