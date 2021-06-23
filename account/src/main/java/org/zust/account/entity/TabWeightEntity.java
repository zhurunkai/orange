package org.zust.account.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="tab_weight")
@NoArgsConstructor
public class TabWeightEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="user")
    private Integer user;
    @Column(name="tab")
    private Integer tab;
    @Column(name="weight")
    private Integer weight;


    public TabWeightEntity(Integer user, Integer tab, Integer weight) {
        this.user = user;
        this.tab = tab;
        this.weight = weight;
    }
}
