package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdTabDto {
    private Integer id;
    // 标签
    private TabDto tab;
    // 广告
    private AdvertisementDto advertisement;
}
