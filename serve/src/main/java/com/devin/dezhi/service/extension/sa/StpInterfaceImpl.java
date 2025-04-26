package com.devin.dezhi.service.extension.sa;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.json.JSONUtil;
import com.devin.dezhi.common.utils.RedisUtil;
import com.devin.dezhi.constant.RedisKey;
import com.devin.dezhi.dao.v1.user.PermissionDao;
import com.devin.dezhi.dao.v1.user.RoleDao;
import com.devin.dezhi.dao.v1.user.RolePermissionDao;
import com.devin.dezhi.dao.v1.user.UserRoleDao;
import com.devin.dezhi.domain.v1.entity.user.Permission;
import com.devin.dezhi.domain.v1.entity.user.Role;
import com.devin.dezhi.domain.v1.entity.user.RolePermission;
import com.devin.dezhi.domain.v1.entity.user.UserRole;
import com.devin.dezhi.enums.BusinessErrorEnum;
import com.devin.dezhi.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 2025/4/26 17:42.
 *
 * <p>
 *     Sa-Token 缓存权限校验扩展类
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final RedisUtil redisUtil;

    private final PermissionDao permissionDao;

    private final RolePermissionDao rolePermissionDao;

    private final UserRoleDao userRoleDao;

    private final RoleDao roleDao;

    @Override
    public List<String> getPermissionList(final Object uid, final String loginType) {
        // 查询缓存，如果没有则查询数据库
        String permissionListStr = redisUtil.get(RedisKey.generateRedisKey(RedisKey.USER_PERMISSION, uid));
        List<String> permissions = JSONUtil.toList(permissionListStr, String.class);

        if (permissions.isEmpty()) {
            // 查询用户的角色列表
            List<Long> roleIds = getRoleIds((Long) uid);

            // 查询权限列表信息
            List<RolePermission> rolePermissions = rolePermissionDao.getByRoleIds(roleIds);

            // 获取权限id集合
            List<Long> permissionIds = rolePermissions.stream()
                    .map(RolePermission::getPermissionId)
                    .toList();

            permissions = permissionDao.listByIds(permissionIds)
                    .stream()
                    .map(Permission::getPermission)
                    .toList();
        }

        return permissions;
    }

    @Override
    public List<String> getRoleList(final Object uid, final String loginType) {
        // 查询缓存中是否存在此用户的角色信息列表
        String roleListStr = redisUtil.get(RedisKey.generateRedisKey(RedisKey.USER_ROLE, uid));
        List<String> roles = JSONUtil.toList(roleListStr, String.class);

        if (roles.isEmpty()) {
            // 获取角色id集合
            List<Long> roleIds = getRoleIds((Long) uid);

            // 查询角色列表信息
            roles = roleDao.listByIds(roleIds)
                    .stream()
                    .map(Role::getRole)
                    .toList();
        }

        return roles;
    }

    /**
     * 根据用户uid获取角色id集合.
     * @param uid 用户id
     * @return List
     */
    private List<Long> getRoleIds(final Long uid) {
        // 查询数据库中的角色列表
        List<UserRole> userRoles = userRoleDao.getByUserId(uid);

        AssertUtil.isNotEmpty(userRoles, String.format(BusinessErrorEnum.BUSINESS_ERROR.getErrMsg(), "用户没有角色信息，请联系管理员进行设置！！！"));

        // 获取角色id集合
        return userRoles.stream()
                .map(UserRole::getRoleId)
                .toList();
    }
}
