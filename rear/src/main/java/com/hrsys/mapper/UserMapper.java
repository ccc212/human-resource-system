package com.hrsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrsys.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-11-21
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
