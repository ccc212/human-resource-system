package com.hrsys.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hrsys.Gobal.GlobalVariables;
import com.hrsys.mapper.SSItemsMapper;
import com.hrsys.pojo.dao.SSItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private GlobalVariables globalVariables;

    @Autowired
    SSItemsMapper ssItemsMapper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 从数据库加载数据
        QueryWrapper<SSItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_fixed", true);
        List<SSItems>ssItems= ssItemsMapper.selectList(queryWrapper);
        globalVariables.set("ssItems", ssItems);

        }

    }

