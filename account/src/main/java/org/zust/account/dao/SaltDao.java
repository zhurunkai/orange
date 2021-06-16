package org.zust.account.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zust.account.entity.SaltEntity;


public interface SaltDao extends JpaRepository<SaltEntity,Integer> {
    @Query("from SaltEntity s where s.phone=?1 and s.salt=?2 and s.captcha=?3")
    SaltEntity findOneBy(String phone,String salt,String captcha);
}
