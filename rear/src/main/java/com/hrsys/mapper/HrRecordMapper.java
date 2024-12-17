package com.hrsys.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.hrsys.pojo.dto.HrRecordSearchDTO;
import com.hrsys.pojo.entity.HrRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrsys.pojo.vo.HrRecordListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 人力资源档案表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Mapper
public interface HrRecordMapper extends MPJBaseMapper<HrRecord> {

    List<HrRecordListVO> search(@Param("hrRecordSearchDTO") HrRecordSearchDTO hrRecordSearchDTO);

    HrRecordListVO getHrRecordById(Long id);

    Long getLastId();
}
