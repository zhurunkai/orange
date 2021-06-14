package org.zust.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zust.account.entity.TabEntity;

import java.util.List;


public interface TabDao extends JpaRepository<TabEntity,Integer> {
    @Query("SELECT t from TabEntity t where t.name=?1")
    TabEntity  findByName(String name);

    @Query(value = "FROM TabEntity WHERE id=?1")
    TabEntity findxxById(Integer id);
    @Query(value = "from TabEntity where id in ?1")
    List<TabEntity> findAllByIds(List<Integer> ids);
    TabEntity findOneById(Integer id);
}
