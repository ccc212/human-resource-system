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
