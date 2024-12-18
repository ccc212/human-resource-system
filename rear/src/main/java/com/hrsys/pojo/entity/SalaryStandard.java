package com.hrsys.pojo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hrsys.enums.ReviewStatus;
import com.hrsys.pojo.dao.SSitemDetailDao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 薪酬标准表
 * </p>
 *
 * @author
 * @since 2024-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class SalaryStandard implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long standardId;

    /**
     * 薪酬标准名称
     */

    private String salaryStandardName;

    /**
     * 制定人
     */
    private String creator;

    /**
     * 登记人
     */
    private String registrar;
    /**
     * 复核人
     */
    private String reviewer;
    /**
     * 登记时间
     */
    private LocalDateTime registrationTime;


    private ReviewStatus reviewStatus;
    /**
     * 薪酬项目列表
     */
    private List<SSitemDetailDao> items = new ArrayList<>();

    private String  reviewComment;
    public void setRegistrationTime(LocalDateTime date) {
    this.registrationTime = date;

    }

    /**
     * 复核状态（0：未复核，1：已复核）
     */



}
