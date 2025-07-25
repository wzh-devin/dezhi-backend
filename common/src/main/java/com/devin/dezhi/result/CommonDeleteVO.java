package com.devin.dezhi.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 2025/7/25 21:55.
 *
 * <p>
 * 通用删除VO
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "通用删除VO")
public class CommonDeleteVO {

    /**
     * 批量删除id列表.
     */
    @Schema(description = "id列表")
    private List<Long> idList;
}
