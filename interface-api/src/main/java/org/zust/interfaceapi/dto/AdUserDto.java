package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUserDto implements Serializable {
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
