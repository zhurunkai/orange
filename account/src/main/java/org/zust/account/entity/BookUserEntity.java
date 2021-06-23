package org.zust.account.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="buser")
@NoArgsConstructor
public class BookUserEntity  implements Serializable {
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

    public BookUserEntity(String phone, String portrait, Integer age, String nickname, String sex, String token) {
        this.phone = phone;
        this.portrait = portrait;
        this.age = age;
        this.nickname = nickname;
        this.sex = sex;
        this.token = token;
    }

}
