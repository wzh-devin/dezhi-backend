package com.devin.dezhi.service.generate.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.devin.dezhi.domain.v1.entity.Material;
import com.devin.dezhi.domain.v1.entity.user.User;
import com.devin.dezhi.domain.v1.vo.FileInfoVO;
import com.devin.dezhi.domain.v1.vo.user.LoginVO;
import com.devin.dezhi.domain.v1.vo.user.PermissionVO;
import com.devin.dezhi.domain.v1.vo.user.RoleVO;
import com.devin.dezhi.domain.v1.vo.user.UserInfoVO;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 2025/4/26 19:21.
 *
 * <p>
 * 通用响应实体生成器
 * </p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public class RespEntityGenerate {

    /**
     * 登录响应实体生成器.
     *
     * @param token token
     * @return LoginResp
     */
    public static LoginVO loginResp(final String token) {
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        return loginVO;
    }

    /**
     * 构建用户信息响应实体.
     *
     * @param user        用户信息
     * @param roles       角色信息
     * @param permissions 权限信息
     * @param createUser  创建人
     * @param updateUser  更新人
     * @return UserInfoResp
     */
    public static UserInfoVO generateUserInfoResp(final User user, final List<RoleVO> roles, final List<PermissionVO> permissions, final String createUser, final String updateUser) {
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        userInfoVO.setUid(user.getId());
        userInfoVO.setRoles(roles);
        userInfoVO.setPermissions(permissions);
        userInfoVO.setCreateUser(createUser);
        userInfoVO.setUpdateUser(updateUser);
        return userInfoVO;
    }

    /**
     * 文件信息响应实体生成器.
     *
     * @param material    文件信息
     * @return FileInfoVO
     */
    public static FileInfoVO generateFileInfoVO(final Material material) {
        FileInfoVO fileInfo = new FileInfoVO();
        fileInfo.setId(material.getId());
        fileInfo.setName(material.getName());
        fileInfo.setSize(material.getSize());
        fileInfo.setUrl(material.getUrl());
        fileInfo.setStorageType(material.getStorageTypeCode());
        fileInfo.setFileType(material.getFileTypeCode());
        fileInfo.setUploadTime(material.getCreateTime());
        return fileInfo;
    }
}
