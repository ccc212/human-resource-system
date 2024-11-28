package com.hrsys.controller.salary;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrsys.pojo.entity.HrRecord;
import com.hrsys.pojo.entity.Result;
import com.hrsys.pojo.entity.SalaryPayment;
import com.hrsys.pojo.entity.Title;
import com.hrsys.service.ISalaryPaymentService;
import com.hrsys.service.ISalaryStandardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 薪酬发放登记表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/salary-payment")
@RequiredArgsConstructor
@Api(tags = "薪酬发放登记管理")
@Validated
public class SalaryPaymentController {

    private final ISalaryPaymentService salaryPaymentService;

    @GetMapping("/list")
    @ApiOperation(value = "获取薪酬发放登记列表")
    public Result<IPage<SalaryPayment>> list(@RequestParam(defaultValue = "1") Integer current,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SalaryPayment> page = new Page<>(current, pageSize);
        return Result.success(salaryPaymentService.page(page));
    }

    @PostMapping
    @ApiOperation(value = "提交")
    public Result<?> submit(@RequestParam @NotNull Integer paymentId,
                            @RequestParam @NotNull Boolean checkIsPass) {
        salaryPaymentService.update(new LambdaUpdateWrapper<SalaryPayment>()
                .eq(SalaryPayment::getPaymentId, paymentId)
                .set(SalaryPayment::getStatus, checkIsPass ? "1" : "0"));
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public Result<?> delete(@PathVariable @NotNull Integer id) {
        salaryPaymentService.removeById(id);
        return Result.success();
    }

}
