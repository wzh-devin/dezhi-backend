package com.devin.dezhi.domain.v1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.devin.dezhi.domain.v1.entity.common.EntityCommon;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/6/1 22:32.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("tb_material")
@EqualsAndHashCode(callSuper = true)
public class Material extends EntityCommon {

    /**
     * 主键ID.
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 文件名.
     */
    @TableField(value = "name")
    private String name;

    /**
     * 文件md5.
     */
    @TableField(value = "md5")
    private String md5;

    /**
     * 文件大小.
     */
    @TableField(value = "size")
    private Long size;

    /**
     * 文件类型.
     */
    @TableField(value = "file_type_code")
    private String fileTypeCode;

    /**
     * 文件存储类型.
     */
    @TableField(value = "storage_type_code")
    private String storageTypeCode;

    /**
     * 文件存储路径.
     */
    @TableField(value = "url")
    private String url;

    /**
     * 创建者ID.
     */
    @TableField(value = "create_user_id")
    private Long createUserId;

    /**
     * 修改者ID.
     */
    @TableField(value = "update_user_id")
    private Long updateUserId;

    /**
     * 删除标志(0: 已删除；1: 未删除).
     */
    @TableField(value = "del_flag")
    private Integer delFlag;
}
