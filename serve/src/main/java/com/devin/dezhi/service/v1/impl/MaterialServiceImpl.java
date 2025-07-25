package com.devin.dezhi.service.v1.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devin.dezhi.common.utils.MinioTemplate;
import com.devin.dezhi.constant.ErrMsgConstant;
import com.devin.dezhi.dao.v1.MaterialDao;
import com.devin.dezhi.dao.v1.SysDictDao;
import com.devin.dezhi.domain.v1.entity.Material;
import com.devin.dezhi.domain.v1.vo.FileInfoQueryVO;
import com.devin.dezhi.enums.DelFlagEnum;
import com.devin.dezhi.model.FileInfo;
import com.devin.dezhi.enums.StorageTypeEnum;
import com.devin.dezhi.exception.BusinessException;
import com.devin.dezhi.exception.FileException;
import com.devin.dezhi.exception.VerifyException;
import com.devin.dezhi.service.generate.common.EntityGenerate;
import com.devin.dezhi.service.v1.MaterialService;
import com.devin.dezhi.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * 2025/6/1 23:06.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private static final String DICT_TYPE_FILE = "FILE";

    private final MinioTemplate minioTemplate;

    private final SysDictDao sysDictDao;

    private final EntityGenerate entityGenerate;

    private final MaterialDao materialDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String upload(final MultipartFile material) {
        try (InputStream is = material.getInputStream()) {
            // 获取文件后缀
            String suffix = FileUtil.getSuffix(material.getOriginalFilename());
            if (!checkFileType(suffix)) {
                throw new VerifyException(ErrMsgConstant.FILE_TYPE_ERROR);
            }

            // 生成文件名
            String fileMd5 = minioTemplate.generateMd5(material.getBytes());
            String fileName = fileMd5 + "." + suffix;

            // 如果文件已经存在，则直接返回文件地址
            if (minioTemplate.hasFile(fileName)) {
                return minioTemplate.generatePublicReadUrl(fileName);
            }

            // 构建文件信息
            FileInfo fileInfo = FileInfo.builder()
                    .inputStream(is)
                    .fileName(fileName)
                    .originalFileName(material.getOriginalFilename())
                    .size(material.getSize())
                    .suffix(suffix)
                    .contentType(material.getContentType())
                    .md5(fileMd5)
                    .build();

            // 上传文件
            String url = minioTemplate.uploadFile(fileInfo);
            AssertUtil.isNotEmpty(url, ErrMsgConstant.FILE_URL_ERROR);

            // 将信息保存至数据库
            materialDao.save(entityGenerate.generateMaterial(fileInfo, StorageTypeEnum.MINIO.name(), url));

            return url;

        } catch (FileException e) {
            throw new FileException(e.getMessage());
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (VerifyException e) {
            throw new VerifyException(e.getMessage());
        } catch (Exception e) {
            throw new FileException(ErrMsgConstant.FILE_ERROR);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delMaterial(final List<Long> ids) {
        // 对数据库中的文件进行逻辑删除
        // TODO 后续30天之后删除文件
        materialDao.delBatchLogicByIds(ids);
    }

    @Override
    public Page<Material> page(final FileInfoQueryVO fileInfoQueryVO) {
        // 获取分页列表
        return materialDao.getList(fileInfoQueryVO);
    }

    @Override
    public void clearRecycle() {
        // 获取回收站所有的文件MD5
        List<Material> materialList = materialDao.getListByDelFlag(DelFlagEnum.IS_DELETED.getFlag());

        List<String> keys = materialList.stream().map(material -> {
            String md5 = material.getMd5();
            String suffix = material.getFileType().toLowerCase();
            return md5 + "." + suffix;
        }).toList();

        minioTemplate.delBatchFile(keys);

        // 物理删除文件
        materialDao.delBatchByMd5(materialList.stream().map(Material::getMd5).toList());
    }

    @Override
    public void recoverMaterial(final List<Long> ids) {
        materialDao.updateFlagByIds(ids, DelFlagEnum.NORMAL);
    }

    /**
     * 检查文件类型.
     *
     * @param suffix 文件后缀
     * @return Boolean
     */
    private boolean checkFileType(final String suffix) {
        // 获取文件类型所支持的枚举类
        List<String> codes = sysDictDao.getCodeByType(DICT_TYPE_FILE);

        return Objects.nonNull(suffix) && codes.contains(suffix.toUpperCase());
    }
}
