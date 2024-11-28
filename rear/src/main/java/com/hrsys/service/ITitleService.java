package com.hrsys.service;

import com.hrsys.pojo.entity.Title;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 职称表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
public interface ITitleService extends IService<Title> {

    void add(Title title);

    void delete(Long id);

    void change(Title title);
}
