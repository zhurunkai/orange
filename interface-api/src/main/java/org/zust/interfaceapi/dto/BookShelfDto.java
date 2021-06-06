package org.zust.interfaceapi.dto;

public class BookShelfDto {
    private Integer id;
    private String name;
    // 书架的拥有者
    private BookUserDto owner;
    // 是否为根目录，1是根目录，0不是根目录
    private Integer isRoot;
}
