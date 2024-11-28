package com.hrsys.service.impl;

import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import com.hrsys.pojo.entity.PositionCategorie;
import com.hrsys.mapper.PositionCategorieMapper;
import com.hrsys.service.IPositionCategorieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职位分类表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Service
public class PositionCategorieServiceImpl extends ServiceImpl<PositionCategorieMapper, PositionCategorie> implements IPositionCategorieService {

    @Override
    public void add(PositionCategorie positionCategorie) {
        if (lambdaQuery().eq(PositionCategorie::getCategoryId, positionCategorie.getCategoryId()).one() != null) {
            throw new BizException(StatusCodeEnum.EXISTS);
        }
        save(positionCategorie);
    }

    @Override
    public void change(PositionCategorie positionCategorie) {
        if (lambdaQuery().eq(PositionCategorie::getCategoryId, positionCategorie.getCategoryId()).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        updateById(positionCategorie);
    }

    @Override
    public void delete(Long id) {
        if (lambdaQuery().eq(PositionCategorie::getCategoryId, id).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        removeById(id);
    }
}
