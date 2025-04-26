package com.devin.dezhi.service.generator;

import com.devin.dezhi.dao.v1.user.PermissionDao;
import com.devin.dezhi.dao.v1.user.RoleDao;
import com.devin.dezhi.dao.v1.user.RolePermissionDao;
import com.devin.dezhi.dao.v1.user.UserDao;
import com.devin.dezhi.dao.v1.user.UserRoleDao;
import com.devin.dezhi.domain.v1.entity.user.Permission;
import com.devin.dezhi.domain.v1.entity.user.Role;
import com.devin.dezhi.domain.v1.entity.user.RolePermission;
import com.devin.dezhi.domain.v1.entity.user.User;
import com.devin.dezhi.domain.v1.entity.user.UserRole;
import com.devin.dezhi.enums.rpac.PermissionEnum;
import com.devin.dezhi.enums.rpac.RoleEnum;
import com.devin.dezhi.utils.AssertUtil;
import com.devin.dezhi.utils.PasswordEncrypt;
import com.devin.dezhi.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 2025/4/25 22:58.
 *
 * <p>
 *     测试生成postgresql数据
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@SpringBootTest
public class GeneratorPgDataTest {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private PasswordEncrypt encrypt;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 生成管理员账号
     */
    @Test
    public void generateAdminData() {
        User user = new User();
        user.setId(snowFlake.nextId());
        user.setUsername("admin");
        user.setEmail("wzh.devin@gmail.com");
        user.setPassword(encrypt.encrypt("123456"));
        user.setCreateUserId(user.getId());
        user.setUpdateUserId(user.getId());
        user.setDelFlag(1);
        user.initDate();

        boolean insertResult = userDao.save(user);
        AssertUtil.isTrue(insertResult, "生成管理员账号失败");
    }

    @Test
    public void generateUserRoleData() {
        User user = userDao.getById(1113544547259908096L);
        Role role = roleDao.getRoleByName(RoleEnum.ADMIN.getRole());

        UserRole userRole = new UserRole();
        userRole.setId(snowFlake.nextId());
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRole.initDate();

        boolean insertResult = userRoleDao.save(userRole);
        AssertUtil.isTrue(insertResult, "生成用户角色关联数据失败");
    }

    /**
     * 生成权限数据
     */
    @Test
    public void generatePermissionData() {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(getPermission(PermissionEnum.USER_ADD));
        permissions.add(getPermission(PermissionEnum.USER_DELETE));
        permissions.add(getPermission(PermissionEnum.USER_EDITE));
        permissions.add(getPermission(PermissionEnum.ARTICLE_ADD));
        permissions.add(getPermission(PermissionEnum.ARTICLE_DELETE));
        permissions.add(getPermission(PermissionEnum.ARTICLE_EDIT));
        permissions.add(getPermission(PermissionEnum.ARTICLE_RELEASE));

        boolean insertResult = permissionDao.saveBatch(permissions);
        AssertUtil.isTrue(insertResult, "生成权限数据失败");
    }

    /**
     * 生成角色数据
     */
    @Test
    public void generateRoleData() {
        List<Role> roles = new ArrayList<>();
        roles.add(getRole(RoleEnum.ADMIN));
        roles.add(getRole(RoleEnum.PRO_USER));
        roles.add(getRole(RoleEnum.USER));

        boolean insertResult = roleDao.saveBatch(roles);
        AssertUtil.isTrue(insertResult, "生成角色数据失败");
    }

    /**
     * 生成角色权限关联数据
     */
    @Test
    public void generateRolePermissionData() {

        // 角色列表
        List<Role> roles = roleDao.list();

        // 权限列表
        List<Permission> permissions = permissionDao.list();

        // 角色权限关联列表
        List<RolePermission> rolePermissions = new ArrayList<>();

        roles.forEach(role -> {

            // 如果是管理员，则添加所有权限
            if (RoleEnum.ADMIN.getRole().equals(role.getRole())) {
                rolePermissions.addAll(getRolePermission(role, permissions));
            }

            // 如果是Pro用户或者普通用户，则添加部分权限
            if (RoleEnum.PRO_USER.getRole().equals(role.getRole())) {
                Set<String> filterSet = new HashSet<>();
                filterSet.add(PermissionEnum.USER_ADD.getPermission());
                filterSet.add(PermissionEnum.USER_EDITE.getPermission());
                filterSet.add(PermissionEnum.ARTICLE_ADD.getPermission());
                filterSet.add(PermissionEnum.ARTICLE_EDIT.getPermission());
                filterSet.add(PermissionEnum.ARTICLE_RELEASE.getPermission());
                rolePermissions.addAll(getRolePermission(role, filterPermission(permissions, filterSet)));
            }
            if (RoleEnum.USER.getRole().equals(role.getRole())) {
                Set<String> filterSet = new HashSet<>();
                filterSet.add(PermissionEnum.ARTICLE_ADD.getPermission());
                filterSet.add(PermissionEnum.ARTICLE_EDIT.getPermission());
                rolePermissions.addAll(getRolePermission(role, filterPermission(permissions, filterSet)));
            }
        });

        boolean insertResult = rolePermissionDao.saveBatch(rolePermissions);
        AssertUtil.isTrue(insertResult, "生成角色权限关联数据失败");
    }

    /**
     * 创建权限对象
     * @param permissionEnum 权限枚举
     * @return 权限对象
     */
    private Permission getPermission(final PermissionEnum permissionEnum) {
        Permission permission = new Permission();
        permission.setId(snowFlake.nextId());
        permission.setPermission(permissionEnum.getPermission());
        permission.setRemark(permissionEnum.getRemark());
        return permission;
    }

    /**
     * 创建角色对象
     * @param roleEnum 角色枚举
     * @return 角色对象
     */
    private Role getRole(final RoleEnum roleEnum) {
        Role role = new Role();
        role.setId(snowFlake.nextId());
        role.setRole(roleEnum.getRole());
        role.setRemark(roleEnum.getRemark());
        role.initDate();
        return role;
    }

    /**
     * 创建角色权限关联列对象列表
     * @param role 角色对象
     * @param permissions 权限对象列表
     * @return 角色权限关联列对象列表
     */
    private List<RolePermission> getRolePermission(final Role role, final List<Permission> permissions) {
        return permissions.stream().map(permission -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setId(snowFlake.nextId());
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permission.getId());
            rolePermission.initDate();
            return rolePermission;
        }).toList();
    }

    /**
     * 过滤出筛选出需要的权限
     * @param permissions 权限列表
     * @param conditions 需要筛选出来的条件
     * @return 过滤后的权限列表
     */
    private List<Permission> filterPermission(final List<Permission> permissions, final Set<String> conditions) {
        return permissions.stream()
                .filter(permission -> conditions.contains(permission.getPermission()))
                .toList();
    }
}
