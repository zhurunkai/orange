package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUserDto implements Serializable {
    private Integer id;
    private String phone;
    // 头像url地址
    private String portrait;
    private Integer age;
    private String nickname;
    // "男"  "女"
    private String sex;
    // 登录凭证
    private String token;
}
