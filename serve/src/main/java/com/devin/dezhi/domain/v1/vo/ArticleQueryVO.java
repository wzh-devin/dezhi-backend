package com.devin.dezhi.domain.v1.vo;

import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.enums.StatusEnum;
import com.devin.dezhi.result.CommonQueryVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2025/7/20 17:34.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleQueryVO extends CommonQueryVO {

    /**
     * 标题.
     */
    private String title;

    /**
     * 状态.
     */
    private StatusEnum status;

    /**
     * 类别.
     */
    private String categoryName;

    /**
     * 删除标识.
     */
    private DelFlagEnum delFlag;
}
