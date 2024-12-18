package com.hrsys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 职位表
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 职位ID
     */
    @TableId(value = "position_id", type = IdType.AUTO)
    private Long positionId;

    /**
     * 职位名称
     */
    @NotBlank(message = "职位名称不能为空")
    private String positionName;

    /**
     * 职位分类ID
     */
    @NotNull(message = "职位分类id不能为空")
    private Long categoryId;

}
