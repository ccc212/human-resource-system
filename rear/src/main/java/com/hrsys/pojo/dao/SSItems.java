package com.hrsys.pojo.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("ss_items")
public class SSItems {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long itemId;
    private String itemName;
    private Boolean isFixed;
    private BigDecimal rate;
}
