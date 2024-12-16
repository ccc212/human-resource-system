package com.hrsys.Service.impl;

import com.hrsys.enums.OperationType;
import com.hrsys.mapper.RecordMapper;
import com.hrsys.pojo.dao.RecordDao;
import com.hrsys.service.impl.RecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RecordServiceTest {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private RecordServiceImpl recordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRecord() {
        RecordDao record =new RecordDao(1L, "Alice", 1L, LocalDateTime.now(), OperationType.REGISTRATION, "New record");


        boolean result = recordService.save(record);
        assertTrue(result);

        List<RecordDao> records = recordMapper.selectList(null);
        assertEquals(1, records.size());
        assertEquals("Alice", records.get(0).getOperator());
    }

    @Test
    void testGetAllRecords() {
        List<RecordDao> records = new ArrayList<>();
        records.add(new RecordDao(1L, "Alice", 1L, LocalDateTime.now(), OperationType.REGISTRATION, "New record"));
        records.add(new RecordDao(2L, "Bob", 2L, LocalDateTime.now(), OperationType.UPDATE, "Update record"));

        when(recordMapper.selectList(null)).thenReturn(records);

        List<RecordDao> result = recordService.list();

        verify(recordMapper, times(1)).selectList(null);
    }

    @Test
    void testGetRecordById() {
        RecordDao record = new RecordDao(1L, "Alice", 1L, LocalDateTime.now(), OperationType.REGISTRATION, "New record");
        when(recordMapper.selectById(1L)).thenReturn(record);

        RecordDao result = recordService.getById(1L);
        assertNotNull(result);
        assertEquals("Alice", result.getOperator());
        verify(recordMapper, times(1)).selectById(1L);
    }

    @Test
    void testUpdateRecord() {
        RecordDao record = new RecordDao(1L, "Alice", 1L, LocalDateTime.now(), OperationType.REGISTRATION, "New record");
        when(recordMapper.updateById(any(RecordDao.class))).thenReturn(1);

        boolean result = recordService.updateById(record);
        assertTrue(result);
        verify(recordMapper, times(1)).updateById(any(RecordDao.class));
    }

    @Test
    void testDeleteRecord() {
        when(recordMapper.deleteById(1L)).thenReturn(1);

        boolean result = recordService.removeById(1L);
        assertTrue(result);
        verify(recordMapper, times(1)).deleteById(1L);
    }
}
