package com.hrsys.pojo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 薪酬发放登记表
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@TableName("salary_payment")
public class SalaryPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 薪酬发放单ID
     */
    @TableId(value = "payment_id", type = IdType.AUTO)
    private Integer paymentId;

    /**
     * 明细ID
     */
    private Integer detailsId;

    /**
     * I级机构ID
     */
    private Integer orgId1;

    /**
     * II级机构ID
     */
    private Integer orgId2;

    /**
     * III级机构ID
     */
    private Integer orgId3;

    /**
     * 登记人
     */
    private String registrar;

    /**
     * 登记时间
     */
    private LocalDateTime registrationTime;

    /**
     * 发放人数
     */
    private Integer totalPeople;

    /**
     * 基本薪资总额
     */
    private BigDecimal totalBaseSalary;

    /**
     * 状态（0：未提交，1：已提交，2：已删除）
     */
    private String status;
}
