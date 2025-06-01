package com.devin.dezhi.service.v1;

import org.springframework.web.multipart.MultipartFile;

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
}
