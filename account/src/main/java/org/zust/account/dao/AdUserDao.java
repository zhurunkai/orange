package org.zust.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.account.entity.AdUserEntity;

public interface AdUserDao extends JpaRepository<AdUserEntity,Integer> {
}
