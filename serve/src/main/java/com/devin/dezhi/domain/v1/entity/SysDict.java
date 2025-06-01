package com.devin.dezhi.domain.v1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.devin.dezhi.domain.v1.entity.common.EntityCommon;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/6/1 23:02.
 *
 * <p>
 * 系统字典表
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("sys_dict")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends EntityCommon {

    /**
     * 系统表id.
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 字典编码.
     */
    @TableField(value = "code")
    private String code;

    /**
     * 字典类型.
     */
    @TableField(value = "type")
    private String type;

    /**
     * 字典名称.
     */
    @TableField(value = "name")
    private String name;
}
