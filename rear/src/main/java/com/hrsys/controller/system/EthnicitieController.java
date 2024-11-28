package com.hrsys.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.Ethnicitie;
import com.hrsys.pojo.entity.Ethnicitie;
import com.hrsys.pojo.entity.Result;
import com.hrsys.service.IEthnicitieService;
import com.hrsys.service.IEthnicitieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 民族表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/ethnicitie")
@RequiredArgsConstructor
@Api(tags = "民族管理")
@Validated
public class EthnicitieController {

    private final IEthnicitieService ethnicitieService;

    @PostMapping
    @ApiOperation(value = "添加民族")
    public Result<?> add(@RequestBody @Valid Ethnicitie ethnicitie) {
        ethnicitieService.save(ethnicitie);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除民族")
    public Result<?> delete(@PathVariable @NotNull(message = "id不能为空") Long id) {
        ethnicitieService.removeById(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "修改民族")
    public Result<?> update(@RequestBody @Valid Ethnicitie ethnicitie) {
        ethnicitieService.updateById(ethnicitie);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取民族列表")
    public Result<IPage<Ethnicitie>> list(@RequestParam(defaultValue = "1") Integer current,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Ethnicitie> page = new Page<>(current, pageSize);
        return Result.success(ethnicitieService.page(page));
    }
}
