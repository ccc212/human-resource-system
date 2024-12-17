package com.hrsys.pojo.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hrsys.enums.ReviewStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("salary_standard")
public class SalaryStandaryDao {
    private static final long serialVersionUID = 1L;
    @TableId(value = "standard_id")
    private Long standardId;
    @TableField("salary_standard_name")
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
    /**
     * 复核状态（0：未复核，1：已复核）
     */

    private ReviewStatus reviewStatus;
    /**
     * 薪酬项目列表
     */
    @TableField(exist = false)
    private List<SSitemDetailDao> items = new ArrayList<>();

    public SalaryStandaryDao(List<SSitemDetailDao>list, String salaryStandardName, String creator, String registrar, String reviewer, LocalDateTime registrationTime) {
        this.items = list;
        this.salaryStandardName = salaryStandardName;
        this.creator = creator;
        this.registrar = registrar;
        this.reviewer = reviewer;
        this.registrationTime = registrationTime;

        this.reviewStatus = ReviewStatus.PENDING; ;

    }  public SalaryStandaryDao(Long standardId,List<SSitemDetailDao>list, String salaryStandardName, String creator, String registrar, String reviewer, LocalDateTime registrationTime) {
        this.standardId = standardId;
        this.items = list;
        this.salaryStandardName = salaryStandardName;
        this.creator = creator;
        this.registrar = registrar;
        this.reviewer = reviewer;
        this.registrationTime = registrationTime;

        this.reviewStatus = ReviewStatus.PENDING; ;

    }
    public void review(ReviewStatus reviewStatus, String reviewer) {
        this.reviewStatus =reviewStatus;
    }

}
