package com.hrsys.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.Result;
import com.hrsys.pojo.entity.Title;
import com.hrsys.service.ITitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 职称表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/title")
@RequiredArgsConstructor
@Api(tags = "职称管理")
@Validated
public class TitleController {

    private final ITitleService titleService;

    @PostMapping
    @ApiOperation(value = "添加职称")
    public Result<?> add(@RequestBody @Valid Title title) {
//        titleService.add(title);
        titleService.save(title);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除职称")
    public Result<?> delete(@PathVariable @NotNull(message = "id不能为空") Integer id) {
        titleService.removeById(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "修改职称")
    public Result<?> update(@RequestBody @Valid Title title) {
        titleService.updateById(title);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取职称列表")
    public Result<IPage<Title>> list(@RequestParam(defaultValue = "1") Integer current,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Title> page = new Page<>(current, pageSize);
        return Result.success(titleService.page(page));
    }

}
