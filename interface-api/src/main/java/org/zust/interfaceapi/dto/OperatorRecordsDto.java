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

    public static final Integer READ = 0;
    public static final Integer ADD = 1;
    public static final Integer DELETE = 2;
    public static final Integer COLLECT = 3;

    private Integer id;
    // 操作类型 "delete"或"add"或“read”或“collect”
    private String type;
    // 操作者用户
    private BookUserDto operator;
    // 操作的book_chain
    private BookChainDto book;
    private Date datetime;
}
