package com.hrsys.pojo.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("ssitem")
public class SSitemDetailDao {
    @TableId(value = "item_id", type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long itemID;            // 项目编号
    private Long standardId;        // 薪酬标准编号
    private String name;            // 项目名称
    private BigDecimal account;     // 项目金额

    public SSitemDetailDao(String name, BigDecimal account) {
        this.name = name;
        this.account = account;
    }




}
