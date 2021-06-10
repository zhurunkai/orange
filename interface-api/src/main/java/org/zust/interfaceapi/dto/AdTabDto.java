package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdTabDto implements Serializable {
    private Integer id;
    // 标签
    private TabDto tab;
    // 广告
    private AdvertisementDto advertisement;
}
