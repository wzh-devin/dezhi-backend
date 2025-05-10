package com.devin.dezhi.service.generate.common;

import com.devin.dezhi.domain.v1.entity.user.User;
import com.devin.dezhi.domain.v1.vo.resp.LoginResp;
import com.devin.dezhi.domain.v1.vo.resp.PermissionResp;
import com.devin.dezhi.domain.v1.vo.resp.RoleResp;
import com.devin.dezhi.domain.v1.vo.resp.UserInfoResp;
import org.springframework.beans.BeanUtils;
import java.util.List;

/**
 * 2025/4/26 19:21.
 *
 * <p>
 *     通用响应实体生成器
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
public class RespEntityGenerate {

    /**
     * 登录响应实体生成器.
     * @param token token
     * @return LoginResp
     */
    public static LoginResp loginResp(final String token) {
        LoginResp loginResp = new LoginResp();
        loginResp.setToken(token);
        return loginResp;
    }

    /**
     * 构建用户信息响应实体.
     * @param user 用户信息
     * @param roles 角色信息
     * @param permissions 权限信息
     * @param createUser 创建人
     * @param updateUser 更新人
     * @return UserInfoResp
     */
    public static UserInfoResp generateUserInfoResp(final User user, final List<RoleResp> roles, final List<PermissionResp> permissions, final String createUser, final String updateUser) {
        UserInfoResp userInfoResp = new UserInfoResp();
        BeanUtils.copyProperties(user, userInfoResp);
        userInfoResp.setUid(user.getId());
        userInfoResp.setRoles(roles);
        userInfoResp.setPermissions(permissions);
        userInfoResp.setCreateUser(createUser);
        userInfoResp.setUpdateUser(updateUser);
        return userInfoResp;
    }
}
