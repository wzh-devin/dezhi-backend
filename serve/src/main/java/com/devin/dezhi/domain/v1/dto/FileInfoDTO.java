package com.devin.dezhi.domain.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 2025/6/2 0:21.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "文件信息请求实体")
public class FileInfoDTO {
    @Schema(description = "文件id")
    private Long id;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "文件状态：0 草稿；1 正常")
    private Integer status;

    @Schema(description = "页码")
    private Integer pageNum;

    @Schema(description = "每页数量")
    private Integer pageSize;
}
