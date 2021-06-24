package org.zust.interfaceapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEnDto implements Serializable {
    private Integer id;
    private Integer owner;
}
