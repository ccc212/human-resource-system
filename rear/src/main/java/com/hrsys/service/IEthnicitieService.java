package com.hrsys.service;

import com.hrsys.pojo.entity.Ethnicitie;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hrsys.pojo.entity.Title;

/**
 * <p>
 * 民族表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
public interface IEthnicitieService extends IService<Ethnicitie> {

    void add(Ethnicitie ethnicitie);

    void delete(Integer id);

    void change(Ethnicitie ethnicitie);
}
