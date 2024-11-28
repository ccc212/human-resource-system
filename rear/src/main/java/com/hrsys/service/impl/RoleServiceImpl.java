package com.hrsys.service.impl;

import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import com.hrsys.pojo.entity.Role;
import com.hrsys.mapper.RoleMapper;
import com.hrsys.pojo.entity.Role;
import com.hrsys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public void add(Role role) {
        if (lambdaQuery().eq(Role::getRoleId, role.getRoleId()).one() != null) {
            throw new BizException(StatusCodeEnum.EXISTS);
        }
        save(role);
    }

    @Override
    public void delete(Long id) {
        if (lambdaQuery().eq(Role::getRoleId, id).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        removeById(id);
    }

    @Override
    public void change(Role role) {
        if (lambdaQuery().eq(Role::getRoleId, role.getRoleId()).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        updateById(role);
    }
}
