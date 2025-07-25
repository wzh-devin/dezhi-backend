package com.devin.dezhi.dao.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.entity.Material;
import com.devin.dezhi.domain.v1.vo.FileInfoQueryVO;
import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.mapper.v1.MaterialMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

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
     * @param fileInfoVO 文件信息对象
     * @return 分页信息
     */
    public Page<Material> getList(final FileInfoQueryVO fileInfoVO) {
        Page<Material> page = new Page<>(fileInfoVO.getPageNum(), fileInfoVO.getPageSize());

        LambdaQueryWrapper<Material> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(Material::getIsDeleted, DelFlagEnum.of(fileInfoVO.getStatus()));

        if (StringUtils.hasLength(fileInfoVO.getName())) {
            lambdaQueryWrapper.eq(Material::getName, fileInfoVO.getName());
        }

        if (StringUtils.hasLength(fileInfoVO.getUrl())) {
            lambdaQueryWrapper.eq(Material::getUrl, fileInfoVO.getUrl());
        }

        if (StringUtils.hasLength(fileInfoVO.getFileType())) {
            lambdaQueryWrapper.eq(Material::getFileType, fileInfoVO.getFileType());
        }

        if (StringUtils.hasLength(fileInfoVO.getStorageType())) {
            lambdaQueryWrapper.eq(Material::getStorageType, fileInfoVO.getStorageType());
        }

        lambdaQueryWrapper.orderByDesc(Material::getCreateTime);

        return page(page, lambdaQueryWrapper);
    }

    /**
     * 批量删除文件(逻辑删除).
     *
     * @param pathList 文件路径列表
     */
    public void delBatchByUrl(final List<String> pathList) {
        lambdaUpdate()
                .in(Material::getUrl, pathList)
                .set(Material::getIsDeleted, DelFlagEnum.IS_DELETED.getFlag())
                .update();
    }

    /**
     * 批量逻辑删除文件.
     * @param ids 文件id列表
     */
    public void delBatchLogicByIds(final List<Long> ids) {
        lambdaUpdate()
                .in(Material::getId, ids)
                .set(Material::getIsDeleted, DelFlagEnum.IS_DELETED.getFlag())
                .update();
    }

    /**
     * 根据删除标志获取文件列表.
     * @param flag 删除标志
     * @return 文件列表
     */
    public List<Material> getListByDelFlag(final Integer flag) {
        return lambdaQuery()
                .eq(Material::getIsDeleted, flag)
                .list();
    }

    /**
     * 根据md5批量删除文件(物理删除).
     * @param keys md5列表
     */
    public void delBatchByMd5(final List<String> keys) {
        if (keys.isEmpty()) {
            return;
        }
        lambdaUpdate()
                .in(Material::getMd5, keys)
                .remove();
    }

    /**
     * 根据id批量更新删除标志.
     * @param ids id列表
     * @param delFlagEnum 删除标志枚举
     */
    public void updateFlagByIds(final List<Long> ids, final DelFlagEnum delFlagEnum) {
        lambdaUpdate()
                .in(Material::getId, ids)
                .set(Material::getIsDeleted, delFlagEnum.getFlag())
                .update();
    }
}
