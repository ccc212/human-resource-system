package com.hrsys.pojo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 薪酬发放明细表
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("salary_detail")
public class SalaryDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 明细ID
     */
    @TableId(value = "details_id", type = IdType.AUTO)
    private Long detailsId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 薪酬标准ID
     */
    private Long standardId;

    /**
     * 奖励金额
     */
    private BigDecimal bonus;

    /**
     * 应扣奖金
     */
    private BigDecimal deductions;

    /**
     * 薪酬总额
     */
    private BigDecimal totalAmount;


}
