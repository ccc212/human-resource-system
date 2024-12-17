package com.hrsys.service.impl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hrsys.mapper.SSitemDetailMapper;
import com.hrsys.mapper.SalaryStandardDaoMapper;
import com.hrsys.mapper.SsitemBaseMapper;
import com.hrsys.pojo.dao.SSItems;
import com.hrsys.pojo.dao.SSitemDetailDao;
import com.hrsys.pojo.dao.SalaryStandaryDao;
import com.hrsys.pojo.entity.SalaryStandard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class SSimImpl {
    @Autowired
    private SalaryStandardDaoMapper salaryStandardDaoMapper;
    @Autowired
    private SSitemDetailMapper ssitemDetailMapper;
    @Autowired
    private SsitemBaseMapper    ssitemBaseMapper;
    public List<SalaryStandard> getSalaryStandard(Long id) {

        MPJLambdaWrapper<SalaryStandaryDao> wrapper = new MPJLambdaWrapper<SalaryStandaryDao>()
                .selectAll(SSitemDetailDao.class).eq(SalaryStandaryDao::getStandardId, id)
                .selectCollection(SSitemDetailDao.class, SalaryStandaryDao::getItems)
                .leftJoin(SSitemDetailDao.class,  SSitemDetailDao::getStandardId,SalaryStandaryDao::getStandardId);
        List<SalaryStandard> salaryStandard= salaryStandardDaoMapper.selectJoinList(SalaryStandard.class,wrapper);

    return salaryStandard;
    }
    public List<SalaryStandard> getAllSalaryStandard() {

        MPJLambdaWrapper<SalaryStandaryDao> wrapper = new MPJLambdaWrapper<SalaryStandaryDao>()
                .selectAll(SalaryStandaryDao.class)
                .selectCollection(SSitemDetailDao.class, SalaryStandaryDao::getItems)
                .leftJoin(SSitemDetailDao.class,  SSitemDetailDao::getStandardId,SalaryStandaryDao::getStandardId);
        List<SalaryStandard> salaryStandard= salaryStandardDaoMapper.selectJoinList(SalaryStandard.class,wrapper);

    return salaryStandard;
    }
    public boolean insertSalaryStandard(SalaryStandard salaryStandard) {
        try {
            // 检查边界条件
            if (salaryStandard.getItems() == null ) {
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



}
