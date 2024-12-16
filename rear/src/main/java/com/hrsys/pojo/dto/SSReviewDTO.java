package com.hrsys.pojo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SSReviewDTO {
    public Long reviewId;
    public String reviewMessage;
    public Date reviewTime;
    public String reviewStatus;
}
