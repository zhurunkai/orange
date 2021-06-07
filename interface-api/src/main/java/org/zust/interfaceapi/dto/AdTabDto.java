package org.zust.interfaceapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdTabDto {
    private Integer id;
    // 标签
    private TabDto tab;
    // 广告
    private AdvertisementDto advertisement;
}
