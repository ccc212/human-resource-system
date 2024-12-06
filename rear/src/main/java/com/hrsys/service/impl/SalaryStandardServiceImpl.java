package com.hrsys.service.impl;

import com.hrsys.pojo.entity.SalaryStandard;
import com.hrsys.mapper.SalaryStandardMapper;
import com.hrsys.service.ISalaryStandardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 薪酬标准表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Service
public class SalaryStandardServiceImpl extends ServiceImpl<SalaryStandardMapper, SalaryStandard> implements ISalaryStandardService {

        public void createSalaryStandard(SalaryStandard salaryStandard) {

                baseMapper.insert(salaryStandard);
        }
}
