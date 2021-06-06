package org.zust.account.auser.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="auser")
public class AdUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="money")
    private Double money;
    @Column(name="phone")
    private String phone;
    @Column(name="nickname")
    private String nickName;
    @Column(name="portrait")
    private String portrait;
    @Column(name="freeze")
    private Double freeze;
    @Column(name="token")
    private String token;
}
