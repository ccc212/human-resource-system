package com.hrsys.service;

import com.hrsys.pojo.entity.Education;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hrsys.pojo.entity.Title;

/**
 * <p>
 * 学历表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
public interface IEducationService extends IService<Education> {

    void add(Education education);

    void delete(Integer id);

    void change(Education education);
}
