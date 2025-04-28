package com.devin.dezhi.service.generate.user;

import com.devin.dezhi.domain.v1.entity.user.User;
import com.devin.dezhi.domain.v1.entity.user.UserRole;
import com.devin.dezhi.domain.v1.vo.req.UserInfoReq;
import com.devin.dezhi.enums.FlagEnum;
import com.devin.dezhi.utils.PasswordEncrypt;
import com.devin.dezhi.utils.SnowFlake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 2025/4/27 2:18.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class UserEntityGenerate {

    private final SnowFlake snowFlake;

    private final PasswordEncrypt passwordEncrypt;

    /**
     * 构建用户实体.
     * @param userInfoReq 用户信息
     * @return User
     */
    public User generateRegisterUser(final UserInfoReq userInfoReq) {
        User user = new User();
        user.setId(snowFlake.nextId());
        user.setUsername(userInfoReq.getUsername());
        user.setPassword(passwordEncrypt.encrypt(userInfoReq.getPassword()));
        user.setEmail(userInfoReq.getEmail());
        user.setCreateUserId(user.getId());
        user.setUpdateUserId(user.getId());
        user.initDate();
        user.setDelFlag(FlagEnum.NORMAL.getFlag());

        return user;
    }

    /**
     * 构建用户更新实体.
     * @param userInfoReq 用户信息
     * @param uid 用户id
     * @return User
     */
    public User generateUpdateUser(final UserInfoReq userInfoReq, final Long uid) {
        User user = new User();
        user.setId(uid);
        user.setPassword(passwordEncrypt.encrypt(userInfoReq.getPassword()));
        return user;
    }

    /**
     * 构建用户角色关联实体.
     * @param uid 用户id
     * @param roleId 角色id
     * @return UserRole
     */
    public UserRole generateUserRole(final Long uid, final Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setId(snowFlake.nextId());
        userRole.setUserId(uid);
        userRole.setRoleId(roleId);
        userRole.initDate();

        return userRole;
    }
}
