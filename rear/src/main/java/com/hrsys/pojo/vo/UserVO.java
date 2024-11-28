package com.hrsys.pojo.vo;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
public class UserVO implements Serializable {

    private Integer userId;

    private String username;

    private Integer roleId;

}
