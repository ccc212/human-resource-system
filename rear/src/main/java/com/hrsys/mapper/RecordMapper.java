package com.hrsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.hrsys.pojo.dao.RecordDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<RecordDao> {
}
