package com.hrsys.Service.impl;

import com.hrsys.pojo.dao.SSItems;
import com.hrsys.service.SSItemsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class SSItemsTest {

    @Autowired
    private SSItemsService ssItemsService;

    @Test
    public void testSave() {
        SSItems ssItems = new SSItems();
        ssItems.setItemName("Test Item");
        ssItems.setIsFixed(false);
        ssItems.setRate(new BigDecimal("1.5"));
        ssItemsService.save(ssItems);
    }
    @Test
    public void testUp() {
        SSItems ssItems = new SSItems();
        Long id = 1868668042445021186L;
        ssItems.setItemId(id);
        ssItems.setItemName("Test Item");
        ssItems.setIsFixed(false);
        ssItems.setRate(new BigDecimal("1.5"));
        boolean result= ssItemsService.updateById(ssItems);
//        检查结果
        System.out.println(result);
    }
    @Test
    public void setSsItemsService()
    {
        System.out.println(ssItemsService.list());
    }

}
