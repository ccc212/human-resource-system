package com.hrsys.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.PositionCategorie;
import com.hrsys.pojo.entity.PositionCategorie;
import com.hrsys.pojo.entity.Result;
import com.hrsys.service.IPositionCategorieService;
import com.hrsys.service.IPositionCategorieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 职位分类表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/position-categorie")
@RequiredArgsConstructor
@Api(tags = "职位分类管理")
@Validated
public class PositionCategorieController {

    private final IPositionCategorieService positionCategorieService;

    @PostMapping
    @ApiOperation(value = "添加职位分类")
    public Result<?> add(@RequestBody @Valid PositionCategorie positionCategorie) {
        positionCategorieService.save(positionCategorie);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除职位分类")
    public Result<?> delete(@PathVariable @NotNull(message = "id不能为空") Integer id) {
        positionCategorieService.removeById(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "修改职位分类")
    public Result<?> update(@RequestBody @Valid PositionCategorie positionCategorie) {
        positionCategorieService.updateById(positionCategorie);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取职位分类列表")
    public Result<IPage<PositionCategorie>> list(@RequestParam(defaultValue = "1") Integer current,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PositionCategorie> page = new Page<>(current, pageSize);
        return Result.success(positionCategorieService.page(page));
    }
}
