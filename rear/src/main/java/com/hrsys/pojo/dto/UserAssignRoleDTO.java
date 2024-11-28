package com.hrsys.pojo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class UserAssignRoleDTO {

    @NotNull(message = "用户id不能为空")
    private Long userId;

    @NotNull(message = "角色id不能为空")
    private Long roleId;

}
