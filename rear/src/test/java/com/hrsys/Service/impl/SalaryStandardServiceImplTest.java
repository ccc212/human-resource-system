package com.hrsys.Service.impl;

import com.hrsys.mapper.SalaryStandardMapper;
import com.hrsys.pojo.entity.SalaryStandard;
import com.hrsys.service.impl.SalaryStandardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
// 在测试类中
public class SalaryStandardServiceImplTest {

    @Mock
    private SalaryStandardMapper salaryStandardMapper;

    @InjectMocks
    private SalaryStandardServiceImpl salaryStandardService;

    @Test
    void createSalaryStandard_ValidSalaryStandard_ReturnsGeneratedId() {
        // 创建测试数据
        SalaryStandard salaryStandard = new SalaryStandard();
        salaryStandard.setName("Test Standard");
        salaryStandard.setCreator("Test Creator");
        salaryStandard.setRegistrar("Test Registrar");
        salaryStandard.setBaseSalary(BigDecimal.valueOf(5000));
        salaryStandard.setTransportationAllowance(BigDecimal.valueOf(100.00));
        salaryStandard.setLunchAllowance(BigDecimal.valueOf(200.00));
        salaryStandard.setCommunicationAllowance(BigDecimal.valueOf(500.00));
        salaryStandard.setPensionInsurance(BigDecimal.valueOf(400.00));
        salaryStandard.setMedicalInsurance(BigDecimal.valueOf(103.00));
        salaryStandard.setUnemploymentInsurance(BigDecimal.valueOf(25.00));
        salaryStandard.setHousingFund(BigDecimal.valueOf(400.00));

        // 模拟 saveUsingSelectKey 方法
//        when(salaryStandardMapper.saveUsingSelectKey(salaryStandard)).thenReturn(1L); // 假设返回的 ID 是 1

        // 调用服务方法
        int generatedId = Integer.parseInt(salaryStandardService.createSalaryStandard(salaryStandard));
        System.out.println(generatedId);
        // 验证结果
        assertNotNull(generatedId);

        verify(salaryStandardMapper).saveUsingGeneratedKeys(salaryStandard);
    }
}
