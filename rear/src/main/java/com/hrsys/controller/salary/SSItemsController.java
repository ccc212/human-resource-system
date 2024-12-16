package com.hrsys.controller.salary;

import com.hrsys.enums.StatusCodeEnum;
import com.hrsys.pojo.dao.SSItems;
import com.hrsys.service.SSItemsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "薪酬项目管理")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ssitems")
public class SSItemsController {

    @Autowired
    private SSItemsService ssItemsService;

    // 增加一个项目
    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody SSItems item) {
        try {
            boolean savedItem = ssItemsService.save(item);
            if (savedItem) {
                return new ResponseEntity<>(item, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to save item", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 获取所有项目
    @GetMapping
    public ResponseEntity<?> getAllItems() {
        try {
            List<SSItems> items = ssItemsService.list();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID获取项目
    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        try {
            SSItems item = ssItemsService.getById(id);
            if (item != null) {
                return new ResponseEntity<>(item, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 更新项目
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody SSItems item) {
        try {

            item.setItemId(id);
            System.out.println(item.toString());
            boolean isUpdated = ssItemsService.updateById(item);
            if (isUpdated) {
                SSItems updatedItem = ssItemsService.getById(id);
                if (updatedItem != null) {
                    return new ResponseEntity<>(updatedItem, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Item not found after update", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("Failed to update item", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除项目
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        try {
            boolean isDeleted = ssItemsService.removeById(id);
            if (isDeleted) {
                return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
