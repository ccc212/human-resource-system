package com.hrsys.controller.salary;


import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.pojo.dto.SSReviewDTO;
import com.hrsys.pojo.dto.SSSearchDTO;
import com.hrsys.pojo.entity.Result;
import com.hrsys.pojo.entity.SalaryStandard;
import com.hrsys.service.ISalaryStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
@RequiredArgsConstructor//自动注入构造类
@Api(tags = "薪酬标准管理")
@Validated
public class SalaryStandardController {

    private final ISalaryStandardService salaryStandardService;


    @PostMapping("/new")
    @Transactional
    @ApiOperation(value = "登记")
    public Result<?> add(@RequestBody @Valid SalaryStandard salaryStandard) {
        if (!salaryStandard.checkIsPass()) {
            return Result.error(StatusCodeEnum.PARAM_ERROR, "不符合规范的薪酬标准");
        }
        String ssID = salaryStandardService.createSalaryStandard(salaryStandard);
        return Result.success(ssID);
    }

    /*薪酬标准获取*/
    @GetMapping("/get")
    @ApiOperation(value = "获取")
    public Result<?> get() {
        return Result.success(salaryStandardService.list());
    }

    /*（未）薪酬标准获取*/
    @GetMapping("/get/{standardId}")
    @ApiOperation(value = "获取")
    public Result<?> get(@RequestParam Long standardId) {
        SalaryStandard salaryStandard = salaryStandardService.getById(standardId);
        if (salaryStandard == null) {
            return Result.error(StatusCodeEnum.PARAM_ERROR, "薪酬标准不存在");
        }
        return Result.success(salaryStandard);
    }

    /*薪酬标准复审*/
    @Transactional
    @PostMapping("/review")
    @ApiOperation(value = "复审")
    public Result<?> review(@RequestBody @Valid SSReviewDTO ssReviewDTO) {

        salaryStandardService.update();
        return Result.success();
    }

    @GetMapping("/search")
    @ApiOperation(value = "搜索")
    public Result<?> searchSS(@RequestBody SSSearchDTO ssSearchDTO) {
        List<SalaryStandard> list = salaryStandardService.searchSS(ssSearchDTO);
        if (list == null) {
            return Result.error(StatusCodeEnum.PARAM_ERROR, "搜索失败");
        }
        return Result.success(list);
    }
}
