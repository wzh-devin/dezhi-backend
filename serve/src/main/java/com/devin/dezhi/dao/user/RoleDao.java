package com.devin.dezhi.dao.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.entity.user.Role;
import com.devin.dezhi.mapper.user.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * 2025/4/25 18:30.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {

    /**
     * 根据角色名获取角色信息.
     * @param role 角色名称
     * @return Role
     */
    public Role getRoleByName(final String role) {
        return lambdaQuery()
                .eq(Role::getRole, role)
                .one();
    }
}
