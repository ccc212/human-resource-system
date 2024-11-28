package com.hrsys.controller.salary;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.SalaryStandard;
import com.hrsys.pojo.entity.Result;
import com.hrsys.pojo.entity.SalaryStandard;
import com.hrsys.service.ISalaryStandardService;
import com.hrsys.service.ISalaryStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 薪酬标准表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/salary-standard")
@RequiredArgsConstructor
@Api(tags = "薪酬标准管理")
@Validated
public class SalaryStandardController {

    private final ISalaryStandardService salaryStandardService;

    @PostMapping("/check")
    @ApiOperation(value = "复核")
    public Result<?> check(@RequestParam @NotNull Long standardId, @RequestParam @NotNull Boolean checkIsPass) {
        salaryStandardService.update(new LambdaUpdateWrapper<SalaryStandard>()
                .eq(SalaryStandard::getStandardId, standardId)
                .set(SalaryStandard::getReviewStatus, checkIsPass ? "1" : "0"));
        return Result.success();
    }

    @PostMapping
    @ApiOperation(value = "登记")
    public Result<?> add(@RequestBody @Valid SalaryStandard salaryStandard) {
        salaryStandardService.save(salaryStandard);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public Result<?> delete(@PathVariable @NotNull(message = "id不能为空") Long id) {
        salaryStandardService.removeById(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "变更")
    public Result<?> update(@RequestBody @Valid SalaryStandard salaryStandard) {
        salaryStandardService.updateById(salaryStandard);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取薪酬标准列表")
    public Result<IPage<SalaryStandard>> list(@RequestParam(defaultValue = "1") Integer current,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SalaryStandard> page = new Page<>(current, pageSize);
        return Result.success(salaryStandardService.page(page));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取薪酬标准档案")
    public Result<SalaryStandard> get(@PathVariable @NotNull Long id) {
        return Result.success(salaryStandardService.getById(id));
    }
}
