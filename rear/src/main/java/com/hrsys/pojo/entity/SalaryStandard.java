package com.hrsys.pojo.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hrsys.pojo.dao.SSitem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;

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
     * 复核人
     */
    private String reviewer;
    /**
     * 薪酬项目列表
     */
    private List<SSitem> items = new ArrayList<>();

    /**
     * 复核状态（0：未复核，1：已复核）
     */
    private Enum reviewStatus;

    /**
     * 复核意见
     */
    private static final BigDecimal PENSION_RATE = new BigDecimal("0.08");
    private static final BigDecimal MEDICAL_RATE = new BigDecimal("0.02");
    private static final BigDecimal UNEMPLOYMENT_RATE = new BigDecimal("0.005");
    private static final BigDecimal HOUSING_RATE = new BigDecimal("0.08");
    private static final BigDecimal MEDICAL_BASE = new BigDecimal("3.00");

    /**
     * 校验薪酬标准是否通过
     */
    public boolean checkIsPass() {
        try {
            // 初始化薪酬项目并设置默认值
            initializeItems();

            // 检查并计算薪酬项目金额
            standardizeItems();

            // 保险金额计算
            calculateInsurance();

            return true; // 所有检查通过
        } catch (ArithmeticException | NullPointerException e) {
            // 捕获并记录常见异常
            System.err.println("发生算术或空指针异常: " + e.getMessage());
            return false;
        } catch (Exception e) {
            // 捕获其他未知异常
            System.err.println("发生未知异常: " + e.getMessage());
            return false;
        }
    }

    /**
     * 验证基础字段
     */


    /**
     * 初始化薪酬项目
     */
    private void initializeItems() {
        if (items == null || items.isEmpty()) {
            items = new ArrayList<>();
            items.add(new SSitem("基本工资", BigDecimal.ZERO));
            items.add(new SSitem("交通补助", BigDecimal.ZERO));
            items.add(new SSitem("午餐补助", BigDecimal.ZERO));
            items.add(new SSitem("通信补助", BigDecimal.ZERO));
            items.add(new SSitem("养老保险", BigDecimal.ZERO));
            items.add(new SSitem("医疗保险", BigDecimal.ZERO));
            items.add(new SSitem("失业保险", BigDecimal.ZERO));
            items.add(new SSitem("住房公积金", BigDecimal.ZERO));
        }
    }

    /**
     * 规范化项目金额
     * 此方法遍历所有的SSitem对象，确保每个项目的账户金额要么被设置为零（如果原本为空），
     * 要么被规范化为两位小数的格式，以确保数据的一致性和准确性
     */
    private void standardizeItems() {
        for (SSitem item : items) {
            // 检查项目账户金额是否为空
            if (item.getAccount() == null) {
                // 如果为空，则设置为零
                item.setAccount(BigDecimal.ZERO);
            } else {
                // 如果不为空，则规范化为两位小数的格式
                item.setAccount(item.getAccount().setScale(2, RoundingMode.HALF_UP));
            }
        }
    }

    /**
     * 计算保险金额
     */
    private void calculateInsurance() {
        BigDecimal baseSalary = getItemAmount("基本工资");

        setItemAmount("养老保险", baseSalary.multiply(PENSION_RATE).setScale(2, RoundingMode.HALF_UP));
        setItemAmount("医疗保险", baseSalary.multiply(MEDICAL_RATE).add(MEDICAL_BASE).setScale(2, RoundingMode.HALF_UP));
        setItemAmount("失业保险", baseSalary.multiply(UNEMPLOYMENT_RATE).setScale(2, RoundingMode.HALF_UP));
        setItemAmount("住房公积金", baseSalary.multiply(HOUSING_RATE).setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * 设置项目金额
     */
    public void setItemAmount(String name, BigDecimal amount) {
        for (SSitem item : items) {
            if (item.getName().equals(name)) {
                item.setAccount(amount);
                break;
            }
        }
    }

    /**
     * 获取项目金额
     */
    public BigDecimal getItemAmount(String name) {
        for (SSitem item : items) {
            if (item.getName().equals(name)) {
                return item.getAccount();
            }
        }
        return BigDecimal.ZERO;
    }

}
