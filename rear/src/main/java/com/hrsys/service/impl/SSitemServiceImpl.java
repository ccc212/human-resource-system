package com.hrsys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrsys.mapper.SSitemMapper;
import com.hrsys.pojo.dao.SSitemDetailDao;
import com.hrsys.service.SSitemService;
import org.springframework.stereotype.Service;

@Service
public class SSitemServiceImpl extends ServiceImpl<SSitemMapper, SSitemDetailDao> implements SSitemService {
    // 可以在这里实现自定义的业务方法
}
