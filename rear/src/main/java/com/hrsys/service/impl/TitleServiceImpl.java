package com.hrsys.service.impl;

import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import com.hrsys.pojo.entity.Title;
import com.hrsys.mapper.TitleMapper;
import com.hrsys.service.ITitleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职称表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Service
public class TitleServiceImpl extends ServiceImpl<TitleMapper, Title> implements ITitleService {

    @Override
    public void add(Title title) {
        if (lambdaQuery().eq(Title::getTitleId, title.getTitleId()).one() != null) {
            throw new BizException(StatusCodeEnum.TITLE_ID_EXISTS);
        }
        save(title);
    }
}
