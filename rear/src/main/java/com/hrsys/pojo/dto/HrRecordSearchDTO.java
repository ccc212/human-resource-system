package com.hrsys.pojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HrRecordSearchDTO {

    private Integer current = 1;

    private Integer pageSize = 10;

    private Long orgId1;

    private Long orgId2;

    private Long orgId3;

    private Long categoryId;

    private Long positionId;

    private LocalDateTime begin;

    private LocalDateTime end;
}