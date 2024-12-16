package com.hrsys.pojo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class basicSalaryItem {
    private Long id; // 薪酬项目ID
    private String name; // 项目名称
    private Boolean isFixed; // 是否固定金额
    private String description; // 描述
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
