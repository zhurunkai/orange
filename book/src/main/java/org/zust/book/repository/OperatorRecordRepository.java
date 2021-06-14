package org.zust.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.book.entity.OperateRecords;

/**
 * @author: Linxy
 * @Date: 2021/6/14 9:34
 * @function:
 **/
public interface OperatorRecordRepository extends JpaRepository<OperateRecords,Integer> {

    OperateRecords findOneById(Integer id);

}
