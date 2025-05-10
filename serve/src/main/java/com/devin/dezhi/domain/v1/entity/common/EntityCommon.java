package com.devin.dezhi.domain.v1.entity.common;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.Date;

/**
 * 2025/4/26 0:46.
 *
 * <p>
 *     实体公共字段
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
public class EntityCommon {

    /**
     * 创建时间.
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间.
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 初始化公共字段.
     */
    public void initDate() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }
}
