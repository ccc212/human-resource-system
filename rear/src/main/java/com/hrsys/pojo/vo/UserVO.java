package com.hrsys.pojo.vo;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
public class UserVO implements Serializable {

    private Long userId;

    private String username;

    private Long roleId;

}
