package com.hrsys.pojo.dto;

import lombok.Getter;

@Getter
public class PositionListDTO {
    Integer current = 1;
    Integer pageSize = 10;
    Integer categoryId;
}
