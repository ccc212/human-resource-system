package com.hrsys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrsys.mapper.RecordMapper;
import com.hrsys.pojo.dao.RecordDao;
import com.hrsys.service.RecordService;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, RecordDao> implements RecordService {
    // 可以在这里实现自定义的业务方法
}
