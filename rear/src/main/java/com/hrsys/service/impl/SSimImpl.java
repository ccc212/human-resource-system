package com.hrsys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hrsys.enums.ReviewStatus;
import com.hrsys.mapper.SSitemDetailMapper;
import com.hrsys.mapper.SalaryStandardDaoMapper;
import com.hrsys.mapper.SsitemBaseMapper;
import com.hrsys.pojo.dao.SSItems;
import com.hrsys.pojo.dao.SSitemDetailDao;
import com.hrsys.pojo.dao.SalaryStandaryDao;
import com.hrsys.pojo.dto.SSReviewDTO;
import com.hrsys.pojo.entity.SalaryStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SSimImpl {
    @Autowired
    private SalaryStandardDaoMapper salaryStandardDaoMapper;
    @Autowired
    private SSitemDetailMapper ssitemDetailMapper;
    @Autowired
    private SsitemBaseMapper ssitemBaseMapper;

    public List<SalaryStandard> getSalaryStandard(Long id) {
        MPJLambdaWrapper<SalaryStandaryDao> wrapper = new MPJLambdaWrapper<SalaryStandaryDao>()
                .selectAll(SalaryStandaryDao.class).eq(SalaryStandaryDao::getStandardId, id)
                .selectCollection(SSitemDetailDao.class, SalaryStandaryDao::getItems)
                .leftJoin(SSitemDetailDao.class, SSitemDetailDao::getStandardId, SalaryStandaryDao::getStandardId);
        List<SalaryStandard> salaryStandard = salaryStandardDaoMapper.selectJoinList(SalaryStandard.class, wrapper);
        return salaryStandard;
    }
    public List<SalaryStandard> PendingSalaryStandard() {
        MPJLambdaWrapper<SalaryStandaryDao> wrapper = new MPJLambdaWrapper<SalaryStandaryDao>()
                .selectAll(SalaryStandaryDao.class)
                .eq(SalaryStandaryDao::getReviewStatus, ReviewStatus.PENDING)
                .selectCollection(SSitemDetailDao.class, SalaryStandaryDao::getItems)
                .leftJoin(SSitemDetailDao.class, SSitemDetailDao::getStandardId, SalaryStandaryDao::getStandardId);
        List<SalaryStandard> salaryStandard = salaryStandardDaoMapper.selectJoinList(SalaryStandard.class, wrapper);

        // 确保返回一个空列表而不是null
        if (salaryStandard == null) {
            System.out.println("查询结果为空");
        }

        return salaryStandard;
    }


    public List<SalaryStandard> getAllSalaryStandard() {
        MPJLambdaWrapper<SalaryStandaryDao> wrapper = new MPJLambdaWrapper<SalaryStandaryDao>()
                .selectAll(SalaryStandaryDao.class)
                .eq(SalaryStandaryDao::getReviewStatus, ReviewStatus.APPROVED)
                .selectCollection(SSitemDetailDao.class, SalaryStandaryDao::getItems)
                .leftJoin(SSitemDetailDao.class, SSitemDetailDao::getStandardId, SalaryStandaryDao::getStandardId);
        List<SalaryStandard> salaryStandard = salaryStandardDaoMapper.selectJoinList(SalaryStandard.class, wrapper);
        for (SalaryStandard salaryStandard1 : salaryStandard) {
         if (salaryStandard1.getReviewComment() == null){
                salaryStandard1.setReviewComment("无");
         }
            System.out.println(salaryStandard1.getReviewComment());
        }
        return salaryStandard;
    }

    public boolean insertSalaryStandard(SalaryStandard salaryStandard) {
        try {
            // 检查边界条件
            if (salaryStandard.getItems() == null) {
                System.out.println("输入数据不合法");
                return false;
            }

            SalaryStandaryDao salaryStandaryDao = new SalaryStandaryDao(
                    salaryStandard.getStandardId(),
                    salaryStandard.getItems(),
                    salaryStandard.getSalaryStandardName(),
                    salaryStandard.getCreator(),
                    salaryStandard.getRegistrar(),
                    salaryStandard.getReviewer(),
                    salaryStandard.getRegistrationTime()
            );
            List<SSitemDetailDao> sSitemDetailDaos = salaryStandard.getItems();

            salaryStandardDaoMapper.insert(salaryStandaryDao);
            for (SSitemDetailDao ssitemDetailDao : sSitemDetailDaos) {
                ssitemDetailMapper.insert(ssitemDetailDao);
            }
            return true; // 插入成功

        } catch (Exception e) {
            // 记录异常信息
            e.printStackTrace(); // 可以替换为日志框架
            return false; // 插入失败
        }
    }

    public boolean updateSalaryStandard(SalaryStandard salaryStandard, Long id) {
        System.out.println(salaryStandard.toString());
        try {
            SalaryStandaryDao salaryStandaryDao = new SalaryStandaryDao(
                    salaryStandard.getStandardId(),
                    salaryStandard.getItems(),
                    salaryStandard.getSalaryStandardName(),
                    salaryStandard.getCreator(),
                    salaryStandard.getRegistrar(),
                    salaryStandard.getReviewer(),
                    salaryStandard.getRegistrationTime(),
                    salaryStandard.getReviewStatus(),
                    salaryStandard.getReviewComment()
            );
            salaryStandardDaoMapper.updateById( salaryStandaryDao );
            List<SSitemDetailDao> sSitemDetailDaos = salaryStandard.getItems();

            // 先查询比对，没有的话插入，有的话更新
            SalaryStandard salaryStandary = getSalaryStandard(id).get(0);
            List<SSitemDetailDao> sSitemDetailDaos1 = salaryStandary.getItems();

            // 使用Map记录sSitemDetailDaos1中的itemID
            Map<Long, SSitemDetailDao> existingItems = new HashMap<>();
            for (SSitemDetailDao ssitemDetailDao1 : sSitemDetailDaos1) {
                existingItems.put(ssitemDetailDao1.getItemID(), ssitemDetailDao1);
            }

            for (SSitemDetailDao ssitemDetailDao : sSitemDetailDaos) {
                if (existingItems.containsKey(ssitemDetailDao.getItemID())) {
                    ssitemDetailMapper.updateById(ssitemDetailDao);
                } else {
                    ssitemDetailMapper.insert(ssitemDetailDao);
                }
            }
            return true; // 更新成功

        } catch (Exception e) {
            // 记录异常信息
            e.printStackTrace(); // 可以替换为日志框架
            return false; // 更新失败
        }
    }

    public boolean reviewSalaryStandard(SSReviewDTO ssReviewDTO) {
//        更新warrper
        UpdateWrapper<SalaryStandaryDao> wrapper = new UpdateWrapper<>();
        wrapper.eq("standard_id", ssReviewDTO.salaryStandardId);
        wrapper.set("review_status", ssReviewDTO.reviewStatus);
        wrapper.set("review_comment", ssReviewDTO.reviewMessage);
        int nums = salaryStandardDaoMapper.update(null, wrapper);
        if (nums == 0) {
            System.out.println("复审失败");
            return false;
        } else {
            return true;
        }
    }
}
