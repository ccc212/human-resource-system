package com.hrsys.mapper;

import com.hrsys.pojo.entity.SalaryStandard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 薪酬标准表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Mapper
public interface SalaryStandardMapper extends BaseMapper<SalaryStandard> {


    public  Long saveUsingGeneratedKeys(SalaryStandard salaryStandard);
}
