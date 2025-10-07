package com.devin.dezhi.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("dz_material")
public class Material implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 素材id.
     */
    @TableId("id")
    private Long id;

    /**
     * 文件名称.
     */
    @TableField("name")
    private String name;

    /**
     * 文件md5值.
     */
    @TableField("md5")
    private String md5;

    /**
     * 文件大小.
     */
    @TableField("size")
    private Long size;

    /**
     * 文件类型.
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 文件存储类型.
     */
    @TableField("storage_type")
    private String storageType;

    /**
     * 文件地址.
     */
    @TableField("url")
    private String url;

    /**
     * 创建时间.
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间.
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除（0：未删除；1：已删除）.
     */
    @TableField("is_deleted")
    private Integer isDeleted;

    /**
     * 初始化.
     */
    public void init() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
