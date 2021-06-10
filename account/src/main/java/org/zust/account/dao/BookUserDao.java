package org.zust.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zust.account.entity.BookUserEntity;

public interface BookUserDao extends JpaRepository<BookUserEntity,Integer> {
    public BookUserEntity findByPhone(String phone);
    public BookUserEntity findByToken(String token);
}
