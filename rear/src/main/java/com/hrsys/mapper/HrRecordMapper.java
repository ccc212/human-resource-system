package com.hrsys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseMapper;
import com.hrsys.pojo.dto.HrRecordSearchDTO;
import com.hrsys.pojo.entity.HrRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrsys.pojo.vo.HrRecordDetailVO;
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

    IPage<HrRecordListVO> search(Page<HrRecord> page, @Param("hrRecordSearchDTO") HrRecordSearchDTO hrRecordSearchDTO);

    HrRecordDetailVO getHrRecordById(Long id);

    Long getLastId();
}
