package com.hrsys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hrsys.pojo.dto.UserAssignRoleDTO;
import com.hrsys.pojo.dto.UserLoginDTO;
import com.hrsys.pojo.dto.UserRegisterDTO;
import com.hrsys.pojo.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-21
 */
public interface IUserService extends IService<User> {

    void register(UserRegisterDTO userRegisterDTO);

    User login(UserLoginDTO userLoginDTO);

    void assignRole(UserAssignRoleDTO userAssignRoleDTO);

    void removeUser(Integer id);
}
