package org.zust.interfaceapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookChainDto {
    private Integer id;
    // 书名
    private String name;
    // 书的封面图url地址
    private String cover;
    // 所属书架
    private BookShelfDto shelf;
    // 拥有者
    private BookUserDto owner;
    private Integer process;
    private Integer alive;
    private BookDto origin;


}

