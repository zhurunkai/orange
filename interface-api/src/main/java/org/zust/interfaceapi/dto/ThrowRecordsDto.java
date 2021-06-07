package org.zust.interfaceapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ThrowRecordsDto {
    private Integer id;
    // 投放类型 "点击"或"查看"
    private String type;
    // 投放时间
    private Date datetime;
    // 单次花费
    private Double cost;
    // 投放到的书
    private BookDto book;
    // 投放的广告
    private AdvertisementDto advertisement;
    // 查看广告的读书用户
    private BookUserDto owner;
}
