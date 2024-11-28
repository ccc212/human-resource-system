package com.hrsys.pojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HrRecordAddDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * I级机构ID
     */
    @NotNull(message = "I级机构不能为空")
    private Long orgId1;

    /**
     * II级机构ID
     */
    @NotNull(message = "II级机构不能为空")
    private Long orgId2;

    /**
     * III级机构ID
     */
    @NotNull(message = "III级机构不能为空")
    private Long orgId3;

    /**
     * 职位分类ID
     */
    @NotNull(message = "职位分类不能为空")
    private Long categoryId;

    /**
     * 职位ID
     */
    @NotNull(message = "职位不能为空")
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
    @NotBlank(message = "性别不能为空")
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
    @NotNull(message = "民族不能为空")
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
    @NotNull(message = "学历不能为空")
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
