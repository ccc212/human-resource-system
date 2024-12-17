package com.hrsys.Service.impl;

import com.hrsys.Gobal.GlobalVariables;
import com.hrsys.mapper.SSitemDetailMapper;
import com.hrsys.mapper.SalaryStandardDaoMapper;
import com.hrsys.pojo.dao.SSItems;
import com.hrsys.pojo.dao.SSitemDetailDao;
import com.hrsys.pojo.dao.SalaryStandaryDao;
import com.hrsys.pojo.entity.SalaryStandard;
import com.hrsys.service.impl.SSimImpl;
import com.hrsys.util.TimeBasedIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
 // 确保每个测试后数据库回滚，避免污染
class SSimImplTest {

    @Autowired
    private SalaryStandardDaoMapper salaryStandardDaoMapper;

    @Autowired
    private SSitemDetailMapper ssitemDetailMapper;

    @Autowired
    private SSimImpl sSimImpl;

    private SalaryStandard salaryStandard;
    private List<SSitemDetailDao> items;
@Autowired
private GlobalVariables globalVariables;
    @BeforeEach
    void setUp() {
        // 准备 SalaryStandard 数据
        salaryStandard = new SalaryStandard();
        salaryStandard.setStandardId(TimeBasedIdGenerator.generateId());
        salaryStandard.setSalaryStandardName("Test Salary Standard");
        salaryStandard.setCreator("Test Creator");
        salaryStandard.setRegistrar("Test Registrar");
        salaryStandard.setReviewer("Test Reviewer");


        // 准备 SalaryStandard 关联的 items

        List<SSitemDetailDao> list= Arrays.asList(
                new SSitemDetailDao(TimeBasedIdGenerator.generateId(),salaryStandard.getStandardId(), "ada", BigDecimal.valueOf(1000)),
                new SSitemDetailDao(TimeBasedIdGenerator.generateId(),salaryStandard.getStandardId(), "adaS", BigDecimal.valueOf(1000)),
                new SSitemDetailDao(TimeBasedIdGenerator.generateId(),salaryStandard.getStandardId(), "adaC", BigDecimal.valueOf(1000))
        );

        salaryStandard.setItems(list);
    }

    @Test

    void testInsertSalaryStandard() {
        // 插入 SalaryStandard
        System.out.println(salaryStandard.getStandardId()+"++"+salaryStandard.getItems().get(0).getStandardId());
//       boolean t= sSimImpl.InsertSalaryStandard(salaryStandard,null);
//       if (!t){
//           fail("插入失败");
//       }
       // 验证 items 是否插入成功
       List<SalaryStandard>L= sSimImpl.getSalaryStandard(salaryStandard.getStandardId());
       if (L.size()==0){
              fail("没有数据");
       }
        for (SalaryStandard s:L)
        {
            System.out.println(s.toString());
        }
    }
    @Test
    void getAll(){
        List<SalaryStandard>L= sSimImpl.getAllSalaryStandard();
        if (L.size()==0){
            fail("没有数据");
        }
        for (SalaryStandard s:L)
        {
            System.out.println(s.toString());
        }
    }
        @Test
    void getGobalValue(){

          List<SSItems>  l= (List<SSItems>) globalVariables.get("ssItems");
            for (SSItems s:l)
            {
                System.out.println(s.toString());
            }
    }
}
