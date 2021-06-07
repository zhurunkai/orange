package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUserDto {
    public Integer id;
    // 资金
    private Double money;
    private String phone;
    private String nickname;
    // 头像url地址
    private String portrait;
    // 冻结资金
    private Double freeze;
    // 登录凭证
    private String token;
}
