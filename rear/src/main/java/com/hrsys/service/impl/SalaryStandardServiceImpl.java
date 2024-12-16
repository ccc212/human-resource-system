package com.hrsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hrsys.pojo.dto.SSReviewDTO;
import com.hrsys.pojo.dto.SSSearchDTO;
import com.hrsys.pojo.entity.SalaryStandard;
import com.hrsys.mapper.SalaryStandardMapper;
import com.hrsys.service.ISalaryStandardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

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
    @Override
    public String createSalaryStandard(SalaryStandard salaryStandard) {
        if (!salaryStandard.checkIsPass()) {
            return "不符合规范的薪酬标准";
        }
        SalaryStandard ss_to_insert = new SalaryStandard();
        BeanUtil.copyProperties(salaryStandard, ss_to_insert);
        baseMapper.saveUsingGeneratedKeys(ss_to_insert);
        Long generatedDetailsId = ss_to_insert.getStandardId();
        return generatedDetailsId.toString();
    }

    @Override
    public String reviewSS(SSReviewDTO ssReviewDTO) {
        UpdateWrapper<SalaryStandard> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("standard_id", ssReviewDTO.getReviewId())
                     .set("review_status", ssReviewDTO.getReviewStatus())
                     .set("review_message", ssReviewDTO.getReviewMessage())
                     .set("review_time", ssReviewDTO.getReviewTime());

        int nums=baseMapper.update(null,updateWrapper);
        if(nums==0){
            return "复审失败";
        }
        else {
            return "复审成功";
        }
    }

/* 登记时间查询条件包括起止时间，结束时间，在登记时间大于等于开始时间小于等于结束时间的记录进行匹配。
 */
    @Override
    public List<SalaryStandard> searchSS(SSSearchDTO ssd) {
        LambdaQueryWrapper<SalaryStandard> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//null字段会导致报错，所以要判断是否为null
        if (ssd.getSSID() != null) {
            lambdaQueryWrapper.like(SalaryStandard::getStandardId, ssd.getSSID());
        }

        if (ssd.getKeyword() != null) {
            lambdaQueryWrapper.like(SalaryStandard::getName, ssd.getKeyword())
                    .like(SalaryStandard::getCreator, ssd.getKeyword())
                    .like(SalaryStandard::getRegistrar, ssd.getKeyword())
                    .like(SalaryStandard::getReviewer, ssd.getKeyword());
        }

        if (ssd.getStartTime() != null) {
            lambdaQueryWrapper.ge(SalaryStandard::getRegistrationTime, ssd.getStartTime());
        }

        if (ssd.getEndTime() != null) {
            lambdaQueryWrapper.le(SalaryStandard::getRegistrationTime, ssd.getEndTime());
        }

        return baseMapper.selectList(lambdaQueryWrapper);
    }
}
