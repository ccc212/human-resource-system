package com.hrsys.service;

import com.hrsys.pojo.entity.PositionCategorie;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 职位分类表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
public interface IPositionCategorieService extends IService<PositionCategorie> {

    void add(PositionCategorie positionCategorie);

    void delete(Long id);

    void change(PositionCategorie positionCategorie);
}
