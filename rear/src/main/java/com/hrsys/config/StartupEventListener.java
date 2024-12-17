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
import java.util.Map;
import java.util.stream.Collectors;

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
        List<SSItems>fixed_ssItems= ssItemsMapper.selectList(queryWrapper);
        QueryWrapper<SSItems> queryWrapper2 = new QueryWrapper<>();
        queryWrapper.eq("is_fixed", false);
        List<SSItems>unfixed_ssItems= ssItemsMapper.selectList(queryWrapper2);



        Map<String, SSItems> ssItemsMap = fixed_ssItems.stream()
                .collect(Collectors.toMap(SSItems::getItemName, item -> item));

        globalVariables.set("fixed_ssItems", ssItemsMap);
        Map<String, SSItems> ssItemsMap2 = unfixed_ssItems.stream()
                .collect(Collectors.toMap(SSItems::getItemName, item -> item));

        globalVariables.set("unfixed_ssItems", ssItemsMap2);


    }
}

