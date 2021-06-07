package org.zust.account.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="salt")
public class SaltEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="phone")
    private String phone;
    @Column(name="salt")
    private String salt;
    @Column(name="datetime")
    private Date dateTime;
    @Column(name="captcha")
    private String captcha;

    public SaltEntity(String phone, String salt, String captcha) {
        this.phone = phone;
        this.salt = salt;
        this.dateTime = new Date();
        this.captcha = captcha;
    }

    public SaltEntity() {
    }
}
