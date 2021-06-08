package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatorRecordsDto implements Serializable {
    private Integer id;
    // 操作类型 "delete"或"add"
    private String type;
    // 操作者用户
    private BookUserDto operator;
    // 操作的book_chain
    private BookChainDto book;
    private Date datetime;
}
