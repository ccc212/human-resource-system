package com.hrsys.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.Organization;
import com.hrsys.pojo.entity.Organization;
import com.hrsys.pojo.entity.Result;
import com.hrsys.service.IOrganizationService;
import com.hrsys.service.IOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 组织机构表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
@Api(tags = "组织机构管理")
@Validated
public class OrganizationController {

    private final IOrganizationService organizationService;

    @PostMapping
    @ApiOperation(value = "添加组织机构")
    public Result<?> add(@RequestBody @Valid Organization organization) {
        organizationService.add(organization);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除组织机构")
    public Result<?> delete(@PathVariable @NotNull(message = "id不能为空") Long id) {
        organizationService.delete(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "修改组织机构")
    public Result<?> update(@RequestBody @Valid Organization organization) {
        organizationService.change(organization);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取组织机构列表")
    public Result<IPage<Organization>> list(@RequestParam(defaultValue = "1") Integer current,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Organization> page = new Page<>(current, pageSize);
        return Result.success(organizationService.page(page));
    }
}
