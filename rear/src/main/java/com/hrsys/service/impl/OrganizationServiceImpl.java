package com.hrsys.service.impl;

import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import com.hrsys.pojo.entity.Organization;
import com.hrsys.mapper.OrganizationMapper;
import com.hrsys.service.IOrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织机构表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements IOrganizationService {

    @Override
    public void add(Organization organization) {
        if (lambdaQuery().eq(Organization::getOrgId, organization.getOrgId()).one() != null) {
            throw new BizException(StatusCodeEnum.EXISTS);
        }
        save(organization);
    }

    @Override
    public void delete(Long id) {
        if (lambdaQuery().eq(Organization::getOrgId, id).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        removeById(id);
    }

    @Override
    public void change(Organization organization) {
        if (lambdaQuery().eq(Organization::getOrgId, organization.getOrgId()).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        updateById(organization);
    }
}
