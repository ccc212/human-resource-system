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
    private Integer standardId;

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

    /**
     * 构建薪酬标准对象
     *
     * @param name 薪酬标准名称，不能为空
     * @param creator 制定人，不能为空
     * @param registrar 登记人，不能为空
     * @param baseSalary 基本工资
     * @param transportationAllowance 交通补贴
     * @param lunchAllowance 午餐补贴
     * @param communicationAllowance 通讯补贴
     * @return 返回构建的SalaryStandard对象
     * @throws IllegalArgumentException 如果薪酬标准名称、制定人或登记人为空，则抛出此异常
     */
    public static SalaryStandard build(String name, String creator, String registrar, BigDecimal baseSalary,
                                       BigDecimal transportationAllowance, BigDecimal lunchAllowance, BigDecimal communicationAllowance) {
        // 验证薪酬标准名称不为空
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("薪酬标准名称不能为空");
        }
        // 验证制定人不为空
        if (creator == null || creator.isEmpty()) {
            throw new IllegalArgumentException("制定人不能为空");
        }
        // 验证登记人不为空
        if (registrar == null || registrar.isEmpty()) {
            throw new IllegalArgumentException("登记人不能为空");
        }

        // 计算五险一金
        BigDecimal pensionInsurance = baseSalary.multiply(new BigDecimal("0.08")).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal medicalInsurance = baseSalary.multiply(new BigDecimal("0.02")).add(new BigDecimal("3")).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal unemploymentInsurance = baseSalary.multiply(new BigDecimal("0.005")).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal housingFund = baseSalary.multiply(new BigDecimal("0.08")).setScale(2, BigDecimal.ROUND_HALF_UP);

        // 为补贴设置默认值
        if (transportationAllowance == null) {
            transportationAllowance = BigDecimal.ZERO;
        }
        if (lunchAllowance == null) {
            lunchAllowance = BigDecimal.ZERO;
        }
        if (communicationAllowance == null) {
            communicationAllowance = BigDecimal.ZERO;
        }

        // 构建并返回SalaryStandard对象
        return new SalaryStandard()
                .setName(name)
                .setCreator(creator)
                .setRegistrar(registrar)
                .setRegistrationTime(LocalDateTime.now())
                .setBaseSalary(baseSalary)
                .setTransportationAllowance(transportationAllowance)
                .setLunchAllowance(lunchAllowance)
                .setCommunicationAllowance(communicationAllowance)
                .setPensionInsurance(pensionInsurance)
                .setMedicalInsurance(medicalInsurance)
                .setUnemploymentInsurance(unemploymentInsurance)
                .setHousingFund(housingFund);
    }

}
