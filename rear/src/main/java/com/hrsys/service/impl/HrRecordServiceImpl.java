package com.hrsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hrsys.pojo.dto.HrRecordAddDTO;
import com.hrsys.pojo.dto.HrRecordSearchDTO;
import com.hrsys.pojo.dto.HrRecordUpdateDTO;
import com.hrsys.pojo.entity.HrRecord;
import com.hrsys.mapper.HrRecordMapper;
import com.hrsys.pojo.vo.HrRecordDetailVO;
import com.hrsys.pojo.vo.HrRecordListVO;
import com.hrsys.service.IHrRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 人力资源档案表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Service
@RequiredArgsConstructor
public class HrRecordServiceImpl extends MPJBaseServiceImpl<HrRecordMapper, HrRecord> implements IHrRecordService {

    private final HrRecordMapper hrRecordMapper;

    @Override
    public void add(HrRecordAddDTO hrRecordAddDTO) {
        HrRecord hrRecord = BeanUtil.copyProperties(hrRecordAddDTO, HrRecord.class);
        Long userId = hrRecordMapper.getLastId();
        int userIdLastTwoDigits = userId != null ? (int) (userId % 100 + 1) : 0;
        String recordId = generateArchiveNumber(2024, hrRecord.getOrgId1(), hrRecord.getOrgId2(), hrRecord.getOrgId3(), userIdLastTwoDigits);
        hrRecord.setRecordId(Long.parseLong(recordId));
        save(hrRecord);
    }

    @Override
    public void change(HrRecordUpdateDTO hrRecordUpdateDTO) {
        HrRecord hrRecord = BeanUtil.copyProperties(hrRecordUpdateDTO, HrRecord.class);
        updateById(hrRecord);
    }

    @Override
    public void delete(Long id) {
        lambdaUpdate().eq(HrRecord::getRecordId, id)
                .set(HrRecord::getStatus, "2")
                .update();
    }

    @Override
    public void recover(Long id) {
        lambdaUpdate().eq(HrRecord::getRecordId, id)
                .set(HrRecord::getStatus, "0")
                .update();
    }

    @Override
    public IPage<HrRecordListVO> search(HrRecordSearchDTO hrRecordSearchDTO) {
        Page<HrRecord> page = new Page<>(hrRecordSearchDTO.getCurrent(), hrRecordSearchDTO.getPageSize());
        IPage<HrRecordListVO> resultPage = hrRecordMapper.search(page, hrRecordSearchDTO);
        return resultPage;
    }

    @Override
    public HrRecordDetailVO getHrRecordById(Long id) {
        return hrRecordMapper.getHrRecordById(id);
    }

    private String generateArchiveNumber(int year, Long orgId1, Long orgId2, Long orgId3, int userIdLastTwoDigits) {
        return String.format("%04d%02d%02d%02d%02d", year, orgId1, orgId2, orgId3, userIdLastTwoDigits);
    }
}
