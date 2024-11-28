package com.hrsys.pojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HrRecordUpdateDTO {

    /**
     * 档案ID
     */
    private Long recordId;

    /**
     * 用户ID
     */
    private Long userId;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationTime;

    /**
     * 状态（0：未复核，1：已复核，2：已删除）
     */
    private String status;
}
