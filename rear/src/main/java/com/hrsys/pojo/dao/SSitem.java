package com.hrsys.pojo.dao;

import lombok.Data;

import java.math.BigDecimal;

@Data
public
class SSitem {
    private Long itemID;            // 项目编号
    private Long standardId;        // 薪酬标准编号
    private String name;            // 项目名称
    private BigDecimal account;     // 项目金额

    public SSitem(String name, BigDecimal account) {
        this.name = name;
        this.account = account;
    }
}
