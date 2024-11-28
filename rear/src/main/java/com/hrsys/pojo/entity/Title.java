package com.hrsys.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 职称表
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Title implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 职称ID
     */
    @TableId(value = "title_id", type = IdType.AUTO)
    private Integer titleId;

    /**
     * 职称
     */
    @NotBlank(message = "职称不能为空")
    private String titleName;


}
