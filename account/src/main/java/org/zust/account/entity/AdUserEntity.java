package org.zust.account.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="auser")
@NoArgsConstructor
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
    private String nickname;
    @Column(name="portrait")
    private String portrait;
    @Column(name="freeze")
    private Double freeze;
    @Column(name="token")
    private String token;

    public AdUserEntity(Double money, String phone, String nickname, String portrait, Double freeze, String token) {
        this.money = money;
        this.phone = phone;
        this.nickname = nickname;
        this.portrait = portrait;
        this.freeze = freeze;
        this.token = token;
    }
}
