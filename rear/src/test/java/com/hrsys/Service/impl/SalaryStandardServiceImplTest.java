//package com.hrsys.service.impl;
//
//import com.hrsys.mapper.SSitemDetailMapper;
//import com.hrsys.mapper.SalaryStandardDaoMapper;
//import com.hrsys.pojo.dao.SSitemDetailDao;
//import com.hrsys.pojo.dao.SalaryStandaryDao;
//import com.hrsys.pojo.entity.SalaryStandard;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional // 确保每个测试后数据库回滚，避免污染
//class SSimImplTest {
//
//    @Autowired
//    private SalaryStandardDaoMapper salaryStandardDaoMapper;
//
//    @Autowired
//    private SSitemDetailMapper ssitemDetailMapper;
//
//    @Autowired
//    private SSimImpl sSimImpl;
//
//    private SalaryStandard salaryStandard;
//    private List<SSitemDetailDao> items;
//
//    @BeforeEach
//    void setUp() {
//        // 准备 SalaryStandard 数据
//        salaryStandard = new SalaryStandard();
//        salaryStandard.setSalaryStandardName("Test Salary Standard");
//        salaryStandard.setCreator("Test Creator");
//        salaryStandard.setRegistrar("Test Registrar");
//        salaryStandard.setReviewer("Test Reviewer");
//        salaryStandard.setRegistrationTime(new Date());
//
//        // 准备 SalaryStandard 关联的 items
//        items = Arrays.asList(
//                new SSitemDetailDao(null, "Test Item 1", false, 1.5);
//        );
//
//        salaryStandard.setItems(items);
//    }
//
//    @Test
//    void testInsertSalaryStandard() {
//        // 插入 SalaryStandard
//        sSimImpl.InsertSalaryStandard(salaryStandard);
//
//        // 验证数据库中是否插入成功
//        List<SalaryStandard> standards = salaryStandardDaoMapper.selectJoinList(null, null);
//        assertFalse(standards.isEmpty(), "SalaryStandard 插入失败");
//
//        // 验证 items 是否插入成功
//        for (SSitemDetailDao item : items) {
//            assertNotNull(item.getId(), "Item ID 未生成，插入失败");
//        }
//    }
//}
