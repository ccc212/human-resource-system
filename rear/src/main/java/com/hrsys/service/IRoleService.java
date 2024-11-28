package com.hrsys.service;

import com.hrsys.pojo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hrsys.pojo.entity.Role;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
public interface IRoleService extends IService<Role> {

    void add(Role role);

    void delete(Long id);

    void change(Role role);
}
