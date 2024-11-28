package com.hrsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.dto.HrRecordAddDTO;
import com.hrsys.pojo.dto.HrRecordSearchDTO;
import com.hrsys.pojo.dto.HrRecordUpdateDTO;
import com.hrsys.pojo.entity.HrRecord;
import com.hrsys.mapper.HrRecordMapper;
import com.hrsys.service.IHrRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrsys.strategy.storage.StorageStrategyContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
public class HrRecordServiceImpl extends ServiceImpl<HrRecordMapper, HrRecord> implements IHrRecordService {

    private final StorageStrategyContext storageStrategyContext;
    private final HrRecordMapper hrRecordMapper;

    @Override
    public void add(HrRecordAddDTO hrRecordAddDTO) {
        HrRecord hrRecord = BeanUtil.copyProperties(hrRecordAddDTO, HrRecord.class);
        Long userId = hrRecordAddDTO.getUserId();
        int userIdLastTwoDigits = userId != null ? (int) (userId % 100) : 0;
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
    public IPage<HrRecord> search(HrRecordSearchDTO hrRecordSearchDTO) {
        Integer current = hrRecordSearchDTO.getCurrent();
        Integer pageSize = hrRecordSearchDTO.getPageSize();
        Long orgId1 = hrRecordSearchDTO.getOrgId1();
        Long orgId2 = hrRecordSearchDTO.getOrgId2();
        Long orgId3 = hrRecordSearchDTO.getOrgId3();
        Long categoryId = hrRecordSearchDTO.getCategoryId();
        Long positionId = hrRecordSearchDTO.getPositionId();
        LocalDateTime begin = hrRecordSearchDTO.getBegin();
        LocalDateTime end = hrRecordSearchDTO.getEnd();

        Page<HrRecord> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<HrRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(orgId1 != null, HrRecord::getOrgId1, orgId1)
                    .eq(orgId2 != null, HrRecord::getOrgId2, orgId2)
                    .eq(orgId3 != null, HrRecord::getOrgId3, orgId3)
                    .eq(categoryId != null, HrRecord::getCategoryId, categoryId)
                    .eq(positionId != null, HrRecord::getPositionId, positionId)
                    .ge(begin != null, HrRecord::getRegistrationTime, begin)
                    .le(end != null, HrRecord::getRegistrationTime, end);
        return hrRecordMapper.selectPage(page, queryWrapper);
    }

    private String generateArchiveNumber(int year, Long orgId1, Long orgId2, Long orgId3, int userIdLastTwoDigits) {
        return String.format("%04d%02d%02d%02d%02d", year, orgId1, orgId2, orgId3, userIdLastTwoDigits);
    }
}
