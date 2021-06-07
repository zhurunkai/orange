package org.zust.interfaceapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookTabDto {
    private Integer id;
    private BookDto book;
    private TabDto tab;
}
