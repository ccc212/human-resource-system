package com.hrsys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hrsys.pojo.dto.HrRecordAddDTO;
import com.hrsys.pojo.dto.HrRecordSearchDTO;
import com.hrsys.pojo.dto.HrRecordUpdateDTO;
import com.hrsys.pojo.entity.HrRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 人力资源档案表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
public interface IHrRecordService extends IService<HrRecord> {

    void add(HrRecordAddDTO hrRecordAddDTO);

    void change(HrRecordUpdateDTO hrRecordUpdateDTO);

    void delete(Long id);

    void recover(Long id);

    IPage<HrRecord> search(HrRecordSearchDTO hrRecordSearchDTO);
}
