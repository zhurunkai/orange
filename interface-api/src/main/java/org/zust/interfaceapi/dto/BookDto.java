package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto implements Serializable {
        private Integer id;
        // 书名
        private String name;
        // 书的主人id
        private BookUserDto owner;
        // 书的封面图url
        private String cover;
        // 书的epub格式文件地址
        private String epubUrl;
        // 转化状态 —— 0代表正在转换，1代表已转换完成
        private Integer convertStatus;
        // 原始格式文件url
        private String originUrl;
        // 原始文件格式后缀（如pdf，txt等）
        private String originExt;
}
