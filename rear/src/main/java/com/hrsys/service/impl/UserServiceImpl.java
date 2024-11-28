package com.hrsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import com.hrsys.mapper.UserMapper;
import com.hrsys.pojo.dto.UserAssignRoleDTO;
import com.hrsys.pojo.dto.UserLoginDTO;
import com.hrsys.pojo.dto.UserRegisterDTO;
import com.hrsys.pojo.entity.User;
import com.hrsys.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        User existUser = lambdaQuery().eq(User::getUsername, username).one();

        if (existUser != null) {
            throw new BizException(StatusCodeEnum.USERNAME_EXIST);
        }

        User user = BeanUtil.copyProperties(userRegisterDTO, User.class);
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        save(user);
    }

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        User user = lambdaQuery().eq(User::getUsername, username).one();
        if (user == null) {
            throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            throw new BizException(StatusCodeEnum.PASSWORD_ERROR);
        }

        return user;
    }

    @Override
    public void assignRole(UserAssignRoleDTO userAssignRoleDTO) {
        User user = BeanUtil.copyProperties(userAssignRoleDTO, User.class);
        if (lambdaQuery().eq(User::getUserId, userAssignRoleDTO.getUserId()).one() == null) {
            throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
        }
        updateById(user);
    }

    @Override
    public void removeUser(Integer id) {
        if (lambdaQuery().eq(User::getUserId, id).one() == null) {
            throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
        }
        removeById(id);
    }
}
