package org.zust.interfaceapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDto {
    private Integer id;
    // 章节名
    private String name;
    // 章节数
    private Integer num;
    // 章节起始点标记
    private String start;
    // 章节终止点标记
    private String end;
    // 所属书
    private BookDto book;
}

