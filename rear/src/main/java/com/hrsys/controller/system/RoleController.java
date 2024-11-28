package com.hrsys.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.Role;
import com.hrsys.pojo.entity.Result;
import com.hrsys.pojo.entity.Role;
import com.hrsys.service.IRoleService;
import com.hrsys.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Api(tags = "角色管理")
@Validated
public class RoleController {

    private final IRoleService roleService;

    @PostMapping
    @ApiOperation(value = "添加角色")
    public Result<?> add(@RequestBody @Valid Role role) {
        roleService.add(role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除角色")
    public Result<?> delete(@PathVariable @NotNull(message = "id不能为空") Long id) {
        roleService.delete(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "修改角色")
    public Result<?> update(@RequestBody @Valid Role role) {
        roleService.change(role);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取角色列表")
    public Result<IPage<Role>> list(@RequestParam(defaultValue = "1") Integer current,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Role> page = new Page<>(current, pageSize);
        return Result.success(roleService.page(page));
    }
}
