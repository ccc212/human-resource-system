package com.hrsys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.HrRecord;
import com.hrsys.pojo.entity.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrsys.pojo.vo.HrRecordListVO;
import com.hrsys.pojo.vo.PositionVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 职位表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Mapper
public interface PositionMapper extends BaseMapper<Position> {

    IPage<PositionVO> search(Page<Position> page, Integer categoryId);
}
