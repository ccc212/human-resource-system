package com.hrsys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 人力资源档案表
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Data
@AllArgsConstructor
public class HrRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 档案ID
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;

    /**
     * I级机构ID
     */
    private Long orgId1;

    /**
     * II级机构ID
     */
    private Long orgId2;

    /**
     * III级机构ID
     */
    private Long orgId3;

    /**
     * 职位分类ID
     */
    private Long categoryId;

    /**
     * 职位ID
     */
    private Long positionId;

    /**
     * 职称ID
     */
    private Long titleId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别（0：男，1：女）
     */
    private String gender;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 住址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 出生地
     */
    private String birthplace;

    /**
     * 出生日期
     */
    private LocalDate birthdate;

    /**
     * 民族ID
     */
    private Long ethnicityId;

    /**
     * 宗教信仰
     */
    private String religion;

    /**
     * 政治面貌
     */
    private String politicalAffiliation;

    /**
     * 身份证号码
     */
    private String idNumber;

    /**
     * 社会保障号码
     */
    private String socialSecurityNumber;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 学历ID
     */
    private Long educationId;

    /**
     * 专业
     */
    private String major;

    /**
     * 薪酬标准ID
     */
    private Long salaryStandardId;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 账号
     */
    private String accountNumber;

    /**
     * 特长
     */
    private String skills;

    /**
     * 爱好
     */
    private String hobbies;

    /**
     * 个人履历
     */
    private String personalHistory;

    /**
     * 家庭关系信息
     */
    private String familyInfo;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 员工相片url
     */
    private String photoUrl;

    /**
     * 登记人
     */
    private String registrar;

    /**
     * 登记时间
     */
    private LocalDateTime registrationTime;

    /**
     * 状态（0：未复核，1：已复核，2：已删除）
     */
    private String status;


}
