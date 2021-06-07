package org.zust.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookId2BuserIdDto {
    private Integer bookId;
    private Integer BuserId;
}
