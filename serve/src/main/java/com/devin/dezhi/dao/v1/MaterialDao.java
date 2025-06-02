package com.devin.dezhi.dao.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.dto.FileInfoDTO;
import com.devin.dezhi.domain.v1.entity.Material;
import com.devin.dezhi.enums.FlagEnum;
import com.devin.dezhi.mapper.v1.MaterialMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 2025/6/1 23:10.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class MaterialDao extends ServiceImpl<MaterialMapper, Material> {

    /**
     * 获取文件分页列表.
     *
     * @param fileInfoDTO 文件信息对象
     * @return 分页信息
     */
    public IPage<Material> getList(final FileInfoDTO fileInfoDTO) {
        Page<Material> page = new Page<>(fileInfoDTO.getPageNum(), fileInfoDTO.getPageSize());

        LambdaQueryWrapper<Material> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(Material::getDelFlag, fileInfoDTO.getStatus());

        if (StringUtils.hasLength(fileInfoDTO.getFileName())) {
            lambdaQueryWrapper.eq(Material::getName, fileInfoDTO.getFileName());
        }

        if (StringUtils.hasLength(fileInfoDTO.getFilePath())) {
            lambdaQueryWrapper.eq(Material::getUrl, fileInfoDTO.getFilePath());
        }

        if (StringUtils.hasLength(fileInfoDTO.getFileType())) {
            lambdaQueryWrapper.eq(Material::getFileTypeCode, fileInfoDTO.getFileType());
        }

        return page(page, lambdaQueryWrapper);
    }

    /**
     * 批量删除文件(逻辑删除).
     *
     * @param pathList 文件路径列表
     * @return 删除结果
     */
    public boolean delBatchByUrl(final List<String> pathList) {
        return lambdaUpdate()
                .in(Material::getUrl, pathList)
                .set(Material::getDelFlag, FlagEnum.DISABLED.getFlag())
                .update();
    }
}
