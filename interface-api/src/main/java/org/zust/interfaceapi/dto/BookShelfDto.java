package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookShelfDto implements Serializable {
    private Integer id;
    private String name;
    // 书架的拥有者
    private BookUserDto owner;
    // 是否为根目录，1是根目录，0不是根目录
    private Integer isRoot;
}
