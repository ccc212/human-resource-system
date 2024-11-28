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

/**
 * <p>
 * 学历表
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学历ID
     */
    @TableId(value = "education_id", type = IdType.AUTO)
    private Integer educationId;

    /**
     * 学历名称
     */
    @NotBlank(message = "学历名称不能为空")
    private String educationName;


}
