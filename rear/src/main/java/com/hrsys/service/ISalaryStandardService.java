package com.hrsys.service;

import com.hrsys.pojo.dto.SSReviewDTO;
import com.hrsys.pojo.dto.SSSearchDTO;
import com.hrsys.pojo.entity.SalaryStandard;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 薪酬标准表 服务类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
public interface ISalaryStandardService extends IService<SalaryStandard> {
   public String createSalaryStandard(SalaryStandard salaryStandard);


   public String reviewSS(SSReviewDTO ssReviewDTO);
   public List<SalaryStandard>searchSS(SSSearchDTO ssSearchDTO);
}
