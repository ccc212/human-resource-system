package com.hrsys.pojo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
@TableName("salary_standard")
public class SalaryStandard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 薪酬标准编号
     */
    @TableId(value = "standard_id", type = IdType.AUTO)
    private Long standardId;

    /**
     * 薪酬标准名称
     */
    private String name;

    /**
     * 制定人
     */
    private String creator;

    /**
     * 登记人
     */
    private String registrar;

    /**
     * 登记时间
     */
    private LocalDateTime registrationTime;

    /**
     * 基本工资
     */
    private BigDecimal baseSalary;

    /**
     * 交通补助
     */
    private BigDecimal transportationAllowance;

    /**
     * 午餐补助
     */
    private BigDecimal lunchAllowance;

    /**
     * 通信补助
     */
    private BigDecimal communicationAllowance;

    /**
     * 养老保险
     */
    private BigDecimal pensionInsurance;

    /**
     * 医疗保险
     */
    private BigDecimal medicalInsurance;

    /**
     * 失业保险
     */
    private BigDecimal unemploymentInsurance;

    /**
     * 住房公积金
     */
    private BigDecimal housingFund;

    /**
     * 复核状态（0：未复核，1：已复核）
     */
    private String reviewStatus;

    /**
     * 复核意见
     */
    private String reviewComment;


}
