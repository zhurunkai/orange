package org.zust.interfaceapi.dto;

public class AdvertisementDto {
    private Integer id;
    // 广告标题
    private String title;
    // 广告链接
    private String url;
    // 广告图
    private String picture;
    // 投放广告的广告主用户
    private AdUserDto owner;
    // 预算
    private Double budget;
    // 广告投放状态，"正在投放"或"已结束"
    private String status;
}
