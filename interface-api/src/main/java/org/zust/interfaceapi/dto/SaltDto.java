package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaltDto implements Serializable {
    private Integer id;
    private String phone;
    private String captcha;
    private String salt;
    private Date datetime;
}
