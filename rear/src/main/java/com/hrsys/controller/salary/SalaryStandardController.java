package com.hrsys.controller.salary;


import com.hrsys.Gobal.GlobalVariables;
import com.hrsys.enums.ReviewStatus;
import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.pojo.dao.SSItems;
import com.hrsys.pojo.dao.SSitemDetailDao;
import com.hrsys.pojo.dto.SSReviewDTO;
import com.hrsys.pojo.dto.SSSearchDTO;
import com.hrsys.pojo.entity.Result;
import com.hrsys.pojo.entity.SalaryStandard;
import com.hrsys.service.ISalaryStandardService;
import com.hrsys.service.impl.SSimImpl;
import com.hrsys.util.TimeBasedIdGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 薪酬标准表 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@RestController
@RequestMapping("/api/salarystrandary")
@RequiredArgsConstructor//自动注入构造类
@Api(tags = "薪酬标准管理")
@Validated
@CrossOrigin
public class SalaryStandardController {

    @Autowired
    private final GlobalVariables globalVariables;
    private final SSimImpl sSim;
    @GetMapping("/getAll")
    @ApiOperation("获取所有薪酬标准")
    public ResponseEntity<?> getAllSalaryStandard() {
        List<SalaryStandard> salaryStandard = sSim.getAllSalaryStandard();
        return ResponseEntity.ok(salaryStandard);
    }
    @GetMapping("/getPending/")
    @ApiOperation("获取待审核薪酬标准")
    public ResponseEntity<?> getPendingSalaryStandard() {
        List<SalaryStandard> salaryStandard = sSim.PendingSalaryStandard();


        return ResponseEntity.ok(salaryStandard);
    }
    @PutMapping("/review")
    @ApiOperation("审核薪酬标准")
    public ResponseEntity<?> reviewSalaryStandard(@RequestBody @Valid SSReviewDTO ssReviewDTO) {
        boolean flag = sSim.reviewSalaryStandard(ssReviewDTO);
        System.out.println("新的审核");
        if (flag) {
            return ResponseEntity.ok(StatusCodeEnum.SUCCESS);
        } else {
            return new ResponseEntity<>("审核失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/fixed-items")
    @ApiOperation("获取固定薪酬项目")
    public ResponseEntity<List<SSItems>> getFixedItems() {
        Map<String, SSItems> fixed_items = (Map) globalVariables.get("fixed_ssItems");
//        fixed_items转换成list
        List<SSItems> fixedItems = fixed_items.values().stream().toList();
        return ResponseEntity.ok(fixedItems);
    }
    @GetMapping("/unfixed-items")
    @ApiOperation("获取非固定薪酬项目")
    public ResponseEntity<List<SSItems>> getUnFixedItems() {
        Map<String, SSItems> fixed_items = (Map) globalVariables.get("unfixed_ssItems");
//        fixed_items转换成list
        List<SSItems> fixedItems = fixed_items.values().stream().toList();
        return ResponseEntity.ok(fixedItems);
    }
    @PutMapping("/update/{SalaryStandId}")
    @ApiOperation("更新薪酬标准基本信息")
    public ResponseEntity<?> updateSalaryStandard(@RequestBody @Valid SalaryStandard salaryStandard, @PathVariable Long SalaryStandId) {

      salaryStandard.setReviewStatus(ReviewStatus.PENDING);
      salaryStandard.setRegistrationTime(LocalDateTime.now());
       boolean flag= sSim.updateSalaryStandard(salaryStandard, SalaryStandId);
        if (flag) {
            return ResponseEntity.ok(StatusCodeEnum.SUCCESS);
        } else {
            return new ResponseEntity<>("更新失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/add")
    @ApiOperation("新增薪酬标准")
    public ResponseEntity<?> addSalaryStandard(@RequestBody  SalaryStandardDTO salaryStandard1) {

        System.out.println(salaryStandard1.toString());

        SalaryStandard salaryStandard = new SalaryStandard();
        salaryStandard.setSalaryStandardName(salaryStandard1.getSalaryStandardName());
        salaryStandard.setCreator(salaryStandard1.getCreator());

        salaryStandard.setReviewStatus(salaryStandard1.getReviewStatus());
        List<Item> items = salaryStandard1.getItems();
        Long SSid= TimeBasedIdGenerator.generateId();
        List<SSitemDetailDao> sSitemDetailDaos = items.stream().map(item -> {
            SSitemDetailDao ssitemDetailDao = new SSitemDetailDao();
            ssitemDetailDao.setItemID(item.getItemId());
            ssitemDetailDao.setStandardId(SSid);
            ssitemDetailDao.setName(item.getItemName());
            ssitemDetailDao.setAccount(item.getAccount());
            return ssitemDetailDao;
        }).toList();
        salaryStandard.setItems(sSitemDetailDaos);
      try {
          salaryStandard.setStandardId(SSid);
          Map<String, SSItems> fixed_items = (Map) globalVariables.get("fixed_ssItems");

          // 获取基本工资
          BigDecimal baseSalaryAccount = getBaseSalaryAccount(fixed_items, sSitemDetailDaos);
          if (baseSalaryAccount == null) {

              return new ResponseEntity<>("基本工资不存在", HttpStatus.NOT_FOUND);
          }
          // 验证每个项目的金额是否符合基本工资的比率
          if (!validateItemAccounts(fixed_items, sSitemDetailDaos, baseSalaryAccount)) {

              return new ResponseEntity<>("项目金额不符合基本工资的比率", HttpStatus.CONFLICT);
          }
          // 插入数据
          boolean result = sSim.insertSalaryStandard(salaryStandard);
          return  ResponseEntity.ok(StatusCodeEnum.SUCCESS);
      }catch (Exception e) {
          // 记录异常信息
          e.printStackTrace(); // 可以替换为日志框架
          return new ResponseEntity<>("插入失败", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

//    获取基本工资
    private BigDecimal getBaseSalaryAccount(Map<String, SSItems> fixed_items, List<SSitemDetailDao> sSitemDetailDaos) {
        BigDecimal baseSalaryAccount = new BigDecimal(1);
        for (SSitemDetailDao item : sSitemDetailDaos) {
            if (item.getName().equals("基本工资")) {
                baseSalaryAccount = item.getAccount();
                return baseSalaryAccount;
            }


        }
        return null;
    }
    //    比较rate是否合法
    private boolean validateItemAccounts(Map<String, SSItems> fixed_items, List<SSitemDetailDao> sSitemDetailDaos, BigDecimal baseSalaryAccount) {
        for (SSitemDetailDao item : sSitemDetailDaos) {
            if (fixed_items.containsKey(item.getName())) {
                BigDecimal rate = fixed_items.get(item.getName()).getRate();
                if (item.getAccount().compareTo(baseSalaryAccount.multiply(rate)) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
@Data
class SalaryStandardDTO {

    private String salaryStandardName;
    private String creator;
   private List<Item> items;
    private ReviewStatus reviewStatus;
}
@Data
class Item {
    private Long itemId;
    private String itemName;
    private BigDecimal account;
    private boolean isFixed;
    private BigDecimal rate;
};