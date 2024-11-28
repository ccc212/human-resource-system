package com.hrsys.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.Education;
import com.hrsys.pojo.entity.Result;
import com.hrsys.pojo.entity.Education;
import com.hrsys.service.IEducationService;
import com.hrsys.service.IEducationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 学历表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/education")
@RequiredArgsConstructor
@Api(tags = "学历管理")
@Validated
public class EducationController {

    private final IEducationService educationService;

    @PostMapping
    @ApiOperation(value = "添加学历")
    public Result<?> add(@RequestBody @Valid Education education) {
        educationService.save(education);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除学历")
    public Result<?> delete(@PathVariable @NotNull(message = "id不能为空") Long id) {
        educationService.removeById(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "修改学历")
    public Result<?> update(@RequestBody @Valid Education education) {
        educationService.updateById(education);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取学历列表")
    public Result<IPage<Education>> list(@RequestParam(defaultValue = "1") Integer current,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Education> page = new Page<>(current, pageSize);
        return Result.success(educationService.page(page));
    }
}
