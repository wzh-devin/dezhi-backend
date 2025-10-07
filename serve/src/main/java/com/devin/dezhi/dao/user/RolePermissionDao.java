package com.devin.dezhi.dao.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.entity.user.RolePermission;
import com.devin.dezhi.mapper.user.RolePermissionMapper;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 2025/4/25 18:30.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class RolePermissionDao extends ServiceImpl<RolePermissionMapper, RolePermission> {

    /**
     * 根据角色id列表查询权限列表id.
     * @param roleIds 角色id列表
     * @return 权限列表id
     */
    public List<RolePermission> getByRoleIds(final List<Long> roleIds) {
        return lambdaQuery()
                .in(RolePermission::getRoleId, roleIds)
                .list();
    }
}
