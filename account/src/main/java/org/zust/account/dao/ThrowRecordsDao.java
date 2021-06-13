package org.zust.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zust.account.entity.ThrowRecordsEntity;

import java.util.Date;
import java.util.List;

public interface ThrowRecordsDao extends JpaRepository<ThrowRecordsEntity,Integer> {
    public List<ThrowRecordsEntity> findByOwner(int ownerId);
    public List<ThrowRecordsEntity> findByAdvertisement(int adId);
    @Query(value="select count(t) from ThrowRecordsEntity t where t.owner=?1 and t.datetime>?2 and t.datetime<?3 and t.type='点击'")
    public Integer get7DaysClickNums(Integer auid, Date startTime,Date endTime);
    @Query(value="select count(t) from ThrowRecordsEntity t where t.owner=?1 and t.datetime>?2 and t.datetime<?3")
    public Integer get7DaysNums(Integer auid, Date startTime,Date endTime);
    @Query(value="select count(t) from ThrowRecordsEntity t where t.owner=?1 and t.datetime>?2 and t.datetime<?3 and t.type='查看'")
    public Integer get7DaysShowNums(Integer auid, Date startTime,Date endTime);
    @Query(value="select sum(t.cost) from ThrowRecordsEntity t where t.owner=?1 and t.datetime>?2 and t.datetime<?3")
    public Double get7DaysCostNums(Integer auid, Date startTime,Date endTime);
    @Query(nativeQuery = true,value = "select * from throw_records where advertisement in (select id from advertisement where owner=?1)")
    public List<ThrowRecordsEntity> findThrowByAd(int adUserId);
}
