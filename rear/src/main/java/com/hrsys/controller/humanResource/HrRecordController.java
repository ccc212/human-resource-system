package com.hrsys.controller.humanResource;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.enums.FilePathEnum;
import com.hrsys.pojo.dto.HrRecordAddDTO;
import com.hrsys.pojo.dto.HrRecordSearchDTO;
import com.hrsys.pojo.dto.HrRecordUpdateDTO;
import com.hrsys.pojo.entity.HrRecord;
import com.hrsys.pojo.entity.HrRecord;
import com.hrsys.pojo.entity.Result;
import com.hrsys.service.IHrRecordService;
import com.hrsys.service.IHrRecordService;
import com.hrsys.strategy.storage.StorageStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 * 人力资源档案表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/hr-record")
@RequiredArgsConstructor
@Api(tags = "人力资源档案管理")
@Validated
public class HrRecordController {

    private final IHrRecordService hrRecordService;
    private final StorageStrategyContext storageStrategyContext;

    @PostMapping("/avatar")
    @ApiOperation(value = "上传头像")
    public Result<String> uploadAvatar(@RequestParam MultipartFile avatar) {
        return Result.success(storageStrategyContext.executeUploadStrategy(FilePathEnum.AVATER.getPath(), avatar));
    }

    @PostMapping("/log")
    @ApiOperation(value = "登记")
    public Result<?> add(@RequestBody @Valid HrRecordAddDTO hrRecordAddDTO) {
        hrRecordService.add(hrRecordAddDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public Result<?> delete(@PathVariable @NotNull Long id) {
        hrRecordService.delete(id);
        return Result.success();
    }

    @DeleteMapping("/recover/{id}")
    @ApiOperation(value = "恢复已删除")
    public Result<?> recover(@PathVariable @NotNull Long id) {
        hrRecordService.recover(id);
        return Result.success();
    }

    @PutMapping("/update")
    @ApiOperation(value = "变更/复核")
    public Result<?> update(@RequestBody @Valid HrRecordUpdateDTO hrRecordUpdateDTO) {
        hrRecordService.change(hrRecordUpdateDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取人力资源档案列表")
    public Result<IPage<HrRecord>> list(@RequestParam(defaultValue = "1") Integer current,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam Boolean checkIsPass,
                                        @RequestParam Boolean isDeleted) {
        Page<HrRecord> page = new Page<>(current, pageSize);
        Page<HrRecord> result;
        if (checkIsPass == null) {
            result = hrRecordService.page(page);
        } else if (isDeleted) {
            result = hrRecordService.page(page, new LambdaUpdateWrapper<HrRecord>().eq(HrRecord::getStatus, "2"));
        } else {
            result = hrRecordService.page(page, new LambdaUpdateWrapper<HrRecord>().eq(HrRecord::getStatus, checkIsPass ? "1" : "0"));
        }
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取人力资源档案")
    public Result<HrRecord> get(@PathVariable @NotNull Long id) {
        return Result.success(hrRecordService.getById(id));
    }

    // 条件查询
    @GetMapping("/search")
    @ApiOperation(value = "条件查询")
    public Result<IPage<HrRecord>> search(HrRecordSearchDTO hrRecordSearchDTO) {
        return Result.success(hrRecordService.search(hrRecordSearchDTO));
    }

}
