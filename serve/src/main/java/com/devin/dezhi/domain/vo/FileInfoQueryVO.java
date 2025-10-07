package com.devin.dezhi.domain.vo;

import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.result.CommonQueryVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Schema(description = "文件信息")
@EqualsAndHashCode(callSuper = true)
public class FileInfoQueryVO extends CommonQueryVO {
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

    @Schema(description = "文件状态")
    private DelFlagEnum status;

    @Schema(description = "上传时间")
    private Date uploadTime;
}
