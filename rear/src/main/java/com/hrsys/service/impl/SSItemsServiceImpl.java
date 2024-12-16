package com.hrsys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrsys.mapper.SSItemsMapper;

import com.hrsys.pojo.dao.SSItems;
import com.hrsys.service.SSItemsService;
import org.springframework.stereotype.Service;

@Service
public class SSItemsServiceImpl extends ServiceImpl<SSItemsMapper, SSItems> implements SSItemsService {
}
