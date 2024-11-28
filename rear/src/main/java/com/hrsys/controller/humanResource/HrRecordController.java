package com.hrsys.controller.humanResource;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.HrRecord;
import com.hrsys.pojo.entity.HrRecord;
import com.hrsys.pojo.entity.Result;
import com.hrsys.service.IHrRecordService;
import com.hrsys.service.IHrRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @PostMapping("/check")
    @ApiOperation(value = "复核")
    public Result<?> check(@RequestParam @NotNull Integer recordId, @RequestParam @NotNull Boolean checkIsPass) {
        hrRecordService.update(new LambdaUpdateWrapper<HrRecord>()
                .eq(HrRecord::getRecordId, recordId)
                .set(HrRecord::getStatus, checkIsPass ? "1" : "0"));
        return Result.success();
    }

    @PostMapping
    @ApiOperation(value = "登记")
    public Result<?> add(@RequestBody @Valid HrRecord hrRecord) {
        hrRecordService.save(hrRecord);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public Result<?> delete(@PathVariable @NotNull Integer id) {
        hrRecordService.removeById(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "变更")
    public Result<?> update(@RequestBody @Valid HrRecord hrRecord) {
        hrRecordService.updateById(hrRecord);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取人力资源档案列表")
    public Result<IPage<HrRecord>> list(@RequestParam(defaultValue = "1") Integer current,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<HrRecord> page = new Page<>(current, pageSize);
        return Result.success(hrRecordService.page(page));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取人力资源档案")
    public Result<HrRecord> get(@PathVariable @NotNull Integer id) {
        return Result.success(hrRecordService.getById(id));
    }
}
