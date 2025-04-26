package com.devin.dezhi.dao.v1.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.entity.user.UserRole;
import com.devin.dezhi.mapper.v1.user.UserRoleMapper;
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
public class UserRoleDao extends ServiceImpl<UserRoleMapper, UserRole> {

    /**
     * 根据用户id查询用户角色关联列表.
     * @param uid uid
     * @return List
     */
    public List<UserRole> getByUserId(final Long uid) {
        return lambdaQuery()
                .eq(UserRole::getUserId, uid)
                .list();
    }

    /**
     * 根据用户id删除用户角色关联列表信息.
     * @param uid uid
     * @return boolean
     */
    public boolean removeByUserId(final Long uid) {
        return lambdaUpdate()
                .eq(UserRole::getUserId, uid)
                .remove();
    }
}
