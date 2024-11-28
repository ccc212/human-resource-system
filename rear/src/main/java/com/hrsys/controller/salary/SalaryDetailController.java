package com.hrsys.controller.salary;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.HrRecord;
import com.hrsys.pojo.entity.Result;
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
 * 薪酬发放明细表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/salary-detail")
@RequiredArgsConstructor
@Api(tags = "薪酬发放明细管理")
@Validated
public class SalaryDetailController {

    private final IHrRecordService hrRecordService;

    @PostMapping
    @ApiOperation(value = "添加薪酬发放明细")
    public Result<?> add(@RequestBody @Valid HrRecord hrRecord) {
        hrRecordService.save(hrRecord);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除薪酬发放明细")
    public Result<?> delete(@PathVariable @NotNull(message = "id不能为空") Long id) {
        hrRecordService.removeById(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "修改薪酬发放明细")
    public Result<?> update(@RequestBody @Valid HrRecord hrRecord) {
        hrRecordService.updateById(hrRecord);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取薪酬发放明细列表")
    public Result<IPage<HrRecord>> list(@RequestParam(defaultValue = "1") Integer current,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<HrRecord> page = new Page<>(current, pageSize);
        return Result.success(hrRecordService.page(page));
    }
}
