package com.hrsys.pojo.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HrRecordListVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long recordId;
    private String orgName1;
    private String orgName2;
    private String orgName3;
    private String positionName;
    private String name;
    private String gender; // 性别（0：男，1：女）
    private String status; // 状态（0：未复核，1：已复核，2：已删除）
}
