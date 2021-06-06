package org.zust.interfaceapi.dto;

import java.util.Date;

public class OperatorRecordsDto {
    private Integer id;
    // 操作类型 "delete"或"add"
    private String type;
    // 操作者用户
    private BookUserDto operator;
    // 操作的book_chain
    private BookChainDto book;
    private Date datetime;
}
