package com.devin.dezhi.domain.v1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 2025/6/1 22:32.
 *
 * <p>
 *     模型管理
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@ToString
@TableName("dz_model_manager")
public class ModelManager implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模型id.
     */
    @TableId("id")
    private Long id;

    /**
     * 模型提供商.
     */
    @TableField("provider")
    private String provider;

    /**
     * 模型名称.
     */
    @TableField("model")
    private String model;

    /**
     * 模型的base_url.
     */
    @TableField("base_url")
    private String baseUrl;

    /**
     * 模型的api_key.
     */
    @TableField("api_key")
    private String apiKey;

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
     * 初始化.
     */
    public void init() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
