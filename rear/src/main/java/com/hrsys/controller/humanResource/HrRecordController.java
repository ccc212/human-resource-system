package com.hrsys.controller.humanResource;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hrsys.pojo.dto.HrRecordAddDTO;
import com.hrsys.pojo.dto.HrRecordSearchDTO;
import com.hrsys.pojo.dto.HrRecordUpdateDTO;
import com.hrsys.pojo.entity.HrRecord;
import com.hrsys.pojo.entity.Result;
import com.hrsys.pojo.vo.HrRecordListVO;
import com.hrsys.service.IHrRecordService;
import com.hrsys.service.IMinIOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    private final IMinIOService minIOService;

    @PostMapping("/avatar")
    @ApiOperation(value = "上传头像")
    public Result<String> uploadAvatar(@RequestParam MultipartFile avatar) {
        return Result.success(minIOService.uploadFile(avatar));
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

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取人力资源档案")
    public Result<HrRecordListVO> get(@PathVariable @NotNull Long id) {
        return Result.success(hrRecordService.getHrRecordById(id));
    }

    @GetMapping("/search")
    @ApiOperation(value = "条件查询")
    public Result<List<HrRecordListVO>> search(HrRecordSearchDTO hrRecordSearchDTO) {
        return Result.success(hrRecordService.search(hrRecordSearchDTO));
    }

}
