package org.zust.account.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.account.entity.SaltEntity;


public interface SaltDao extends JpaRepository<SaltEntity,Integer> {

}
