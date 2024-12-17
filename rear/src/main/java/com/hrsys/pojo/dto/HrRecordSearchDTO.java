package com.hrsys.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HrRecordSearchDTO {

    private Integer current = 1;

    private Integer pageSize = 10;

    private Long orgId1;

    private Long orgId2;

    private Long orgId3;

    private Long categoryId;

    private Long positionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}