package com.hrsys.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long positionId;
    private String positionName;
    private Long categoryId;
    private String categoryName;
}
