package org.zust.account.auser.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name="buser")
public class BookUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="phone")
    private String phone;
    @Column(name="portrait")
    private String portrait;
    @Column(name="age")
    private Integer age;
    @Column(name="nickname")
    private String nickname;
    @Column(name="sex")
    private String sex;
    @Column(name="token")
    private String token;

}
