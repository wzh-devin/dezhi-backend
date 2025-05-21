package com.devin.dezhi.domain.v1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.devin.dezhi.domain.v1.entity.common.EntityCommon;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/5/22 1:55.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("tb_menu")
@EqualsAndHashCode(callSuper = true)
public class Menu extends EntityCommon {

    /**
     * 菜单id.
     */
    @TableId
    private Long id;

    /**
     * 菜单key.
     */
    @TableField("key")
    private String key;

    /**
     * 菜单名称.
     */
    @TableField("name")
    private String name;

    /**
     * 菜单路径.
     */
    @TableField("path")
    private String path;

    /**
     * 父级菜单id.
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 创建人id.
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 更新人id.
     */
    @TableField("update_user_id")
    private Long updateUserId;

    /**
     * 删除标志.
     */
    @TableField("del_flag")
    private Integer delFlag;
}
