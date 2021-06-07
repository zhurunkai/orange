package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TabWeightDto {
    private Integer id;
    // 读书用户
    private BookUserDto user;
    // 标签
    private TabDto tab;
    // 权重，初始权重为100
    private Integer weight;
}
