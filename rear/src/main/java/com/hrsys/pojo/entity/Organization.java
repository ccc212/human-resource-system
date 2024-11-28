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
 * 组织机构表
 * </p>
 *
 * @author 
 * @since 2024-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构ID
     */
    @TableId(value = "org_id", type = IdType.AUTO)
    private Integer orgId;

    /**
     * 机构名称
     */
    @NotBlank(message = "组织机构名称不能为空")
    private String orgName;

    @NotNull(message = "父级机构ID不能为空")
    private Integer parentId;


}
