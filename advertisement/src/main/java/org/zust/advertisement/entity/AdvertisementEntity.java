package org.zust.advertisement.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "advertisement",schema = "book_cloud")
public class AdvertisementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //广告标题
    @Column
    private String title;
    //广告落地页url
    @Column
    private String url;
    //广告图
    @Column
    private String picture;
    //广告商
    @Column
    private Integer owner;
    //广告预算
    @Column
    private Double budget;
    //广告状态
    @Column
    private String status;



}
