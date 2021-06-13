package org.zust.advertisement.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "ad_tab")
public class AdTabEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //标签id
    @Column
    private Integer tab;
    //广告id
    @Column
    private Integer advertisement;
}
