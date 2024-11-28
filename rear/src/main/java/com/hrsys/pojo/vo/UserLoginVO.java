package com.hrsys.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class UserLoginVO implements Serializable {

    private Integer userId;

    private String token;
}
