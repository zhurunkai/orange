package org.zust.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.account.entity.ThrowRecordsEntity;

import java.util.List;

public interface ThrowRecordsDao extends JpaRepository<ThrowRecordsEntity,Integer> {
    public List<ThrowRecordsEntity> findByOwner(int ownerid);
}
