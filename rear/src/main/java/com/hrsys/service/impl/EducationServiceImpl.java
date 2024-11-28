package com.hrsys.service.impl;

import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import com.hrsys.pojo.entity.Education;
import com.hrsys.mapper.EducationMapper;
import com.hrsys.pojo.entity.Education;
import com.hrsys.service.IEducationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学历表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Service
public class EducationServiceImpl extends ServiceImpl<EducationMapper, Education> implements IEducationService {

    @Override
    public void add(Education education) {
        if (lambdaQuery().eq(Education::getEducationId, education.getEducationId()).one() != null) {
            throw new BizException(StatusCodeEnum.EXISTS);
        }
        save(education);
    }

    @Override
    public void delete(Integer id) {
        if (lambdaQuery().eq(Education::getEducationId, id).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        removeById(id);
    }

    @Override
    public void change(Education education) {
        if (lambdaQuery().eq(Education::getEducationId, education.getEducationId()).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        updateById(education);
    }
}
