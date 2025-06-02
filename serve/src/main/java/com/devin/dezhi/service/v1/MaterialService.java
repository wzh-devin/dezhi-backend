package com.devin.dezhi.service.v1;

import com.devin.dezhi.common.utils.r.PageResult;
import com.devin.dezhi.domain.v1.dto.FileInfoDTO;
import com.devin.dezhi.domain.v1.vo.FileInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 2025/6/1 23:06.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public interface MaterialService {

    /**
     * 文件上传.
     * @param material 文件信息
     * @return 文件id
     */
    String upload(MultipartFile material);

    /**
     * 文件列表.
     * @param fileInfoDTO 文件信息
     * @return 分页文件列表
     */
    PageResult<FileInfoVO> list(FileInfoDTO fileInfoDTO);

    /**
     * 删除文件.
     * @param pathList 文件列表
     */
    void delMaterial(List<String> pathList);
}
