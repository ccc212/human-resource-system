package com.hrsys.pojo.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HrRecordDetailVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long recordId;
    private String orgName1;
    private String orgName2;
    private String orgName3;
    private String positionName;
    private String categoryName;
    private String titleName;
    private String name;
    private String gender; // 性别（0：男，1：女）
    private String email;
    private String phone;
    private String qq;
    private String mobile;
    private String address;
    private String postalCode;
    private String nationality;
    private String birthplace;
    private LocalDate birthdate;
    private String ethnicityName;
    private String religion;
    private String politicalAffiliation;
    private String idNumber;
    private String socialSecurityNumber;
    private Integer age;
    private String educationName;
    private String major;
    private String salaryStandardName;
    private String bankName;
    private String accountNumber;
    private String skills;
    private String hobbies;
    private String personalHistory;
    private String familyInfo;
    private String remarks;
    private String photoUrl;
    private String registrar;
    private LocalDateTime registrationTime;
}
