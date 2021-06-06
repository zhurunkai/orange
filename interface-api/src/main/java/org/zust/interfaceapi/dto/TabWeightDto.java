package org.zust.interfaceapi.dto;

public class TabWeightDto {
    private Integer id;
    // 读书用户
    private BookUserDto user;
    // 标签
    private TabDto tab;
    // 权重，初始权重为100
    private Integer weight;
}
