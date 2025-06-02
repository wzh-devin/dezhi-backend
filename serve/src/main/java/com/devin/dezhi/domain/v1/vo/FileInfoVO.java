package com.devin.dezhi.domain.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 2025/6/2 16:45.
 *
 * <p>
 * 文件信息
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "登录信息响应")
public class FileInfoVO {
    @Schema(description = "文件id")
    private Long id;

    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "文件地址")
    private String url;

    @Schema(description = "文件大小")
    private Long size;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "存储类型")
    private String storageType;

    @Schema(description = "上传时间")
    private Date uploadTime;
}
