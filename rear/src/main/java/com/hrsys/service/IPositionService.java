package com.hrsys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hrsys.pojo.entity.Position;
import com.hrsys.pojo.vo.PositionVO;

/**
 * <p>
 * 职位表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
public interface IPositionService extends IService<Position> {

    void add(Position position);

    void delete(Long id);

    void change(Position position);

    IPage<PositionVO> search(Integer current, Integer pageSize, Integer categoryId);
}
