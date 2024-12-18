package com.hrsys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.exception.BizException;
import com.hrsys.mapper.PositionMapper;
import com.hrsys.pojo.entity.Position;
import com.hrsys.pojo.vo.PositionVO;
import com.hrsys.service.IPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Service
@RequiredArgsConstructor
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    private final PositionMapper positionMapper;

    @Override
    public void add(Position position) {
        if (lambdaQuery().eq(Position::getPositionId, position.getPositionId()).one() != null) {
            throw new BizException(StatusCodeEnum.EXISTS);
        }
        save(position);
    }

    @Override
    public void delete(Long id) {
        if (lambdaQuery().eq(Position::getPositionId, id).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        removeById(id);
    }

    @Override
    public void change(Position position) {
        if (lambdaQuery().eq(Position::getPositionId, position.getPositionId()).one() == null) {
            throw new BizException(StatusCodeEnum.NOT_EXISTS);
        }
        updateById(position);
    }

    @Override
    public IPage<PositionVO> search(Integer current, Integer pageSize, Integer categoryId) {
        Page<Position> page = new Page<>(current, pageSize);
        IPage<PositionVO> resultPage = positionMapper.search(page, categoryId);
        return resultPage;
    }

}
