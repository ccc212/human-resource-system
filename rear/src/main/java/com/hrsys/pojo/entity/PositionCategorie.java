package com.hrsys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 职位分类表
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Data
@AllArgsConstructor
public class PositionCategorie implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 职位分类ID
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;

    /**
     * 职位分类名称
     */
    @NotBlank(message = "职位分类名称不能为空")
    private String categoryName;


}
