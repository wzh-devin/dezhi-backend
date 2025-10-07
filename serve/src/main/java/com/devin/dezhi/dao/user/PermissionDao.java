package com.devin.dezhi.dao.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.entity.user.Permission;
import com.devin.dezhi.mapper.user.PermissionMapper;
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
public class PermissionDao extends ServiceImpl<PermissionMapper, Permission> {
}
