package com.hrsys.service.impl;

import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import com.hrsys.pojo.entity.Ethnicitie;
import com.hrsys.mapper.EthnicitieMapper;
import com.hrsys.service.IEthnicitieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 民族表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Service
public class EthnicitieServiceImpl extends ServiceImpl<EthnicitieMapper, Ethnicitie> implements IEthnicitieService {

    @Override
    public void add(Ethnicitie ethnicitie) {
        if (lambdaQuery().eq(Ethnicitie::getEthnicityId, ethnicitie.getEthnicityId()).one() != null) {
            throw new BizException(StatusCodeEnum.EXISTS);
        }
        save(ethnicitie);
    }

    @Override
    public void delete(Integer id) {
        if (lambdaQuery().eq(Ethnicitie::getEthnicityId, id).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        removeById(id);
    }

    @Override
    public void change(Ethnicitie ethnicitie) {
        if (lambdaQuery().eq(Ethnicitie::getEthnicityId, ethnicitie.getEthnicityId()).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        updateById(ethnicitie);
    }
}
