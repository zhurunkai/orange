package org.zust.advertisement.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "ad_tab")
public class AdTabEntity  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //标签id
    @Column
    private Integer tab;
    //广告id
    @Column
    private Integer advertisement;

    public AdTabEntity(Integer tab, Integer advertisement) {
        this.tab = tab;
        this.advertisement = advertisement;
    }
}
