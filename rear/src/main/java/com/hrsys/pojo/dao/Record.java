package com.hrsys.pojo.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hrsys.enums.OperationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
class Record {
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;            // 记录编号
    private String operator;          // 操作人
    private LocalDateTime operateTime; // 操作时间
    private OperationType operationType; // 操作类型
    private String comment;           // 操作备注
    public Record(String operator, OperationType operationType, String comment) {
        this.operator = operator;
        this.operationType = operationType;
        this.comment = comment;

    }
}
