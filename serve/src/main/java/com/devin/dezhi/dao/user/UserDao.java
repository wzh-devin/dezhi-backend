package com.devin.dezhi.dao.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.entity.user.User;
import com.devin.dezhi.domain.vo.user.UserInfoQueryVO;
import com.devin.dezhi.mapper.user.UserMapper;
import jakarta.validation.constraints.Email;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * 2025/4/25 18:30.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

    /**
     * 根据用户获取用户.
     * @param userInfoQueryVO 用户信息
     * @return User
     */
    public User getByVO(final UserInfoQueryVO userInfoQueryVO) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 动态生成查询条件
        if (Objects.nonNull(userInfoQueryVO.getUsername())) {
            lambdaQueryWrapper.eq(User::getUsername, userInfoQueryVO.getUsername());
        }
        if (Objects.nonNull(userInfoQueryVO.getPassword())) {
            lambdaQueryWrapper.eq(User::getPassword, userInfoQueryVO.getPassword());
        }
        if (Objects.nonNull(userInfoQueryVO.getEmail()) && !userInfoQueryVO.getEmail().isBlank()) {
            lambdaQueryWrapper.eq(User::getEmail, userInfoQueryVO.getEmail());
        }

        return getOne(lambdaQueryWrapper);
    }

    /**
     * 根据邮箱获取用户.
     * @param email 邮箱
     * @return User
     */
    public User getByEmail(@Email final String email) {
        return lambdaQuery()
                .eq(User::getEmail, email)
                .one();
    }

    /**
     * 根据用户id获取用户名.
     * @param uid 用户id
     * @return String
     */
    public String getUsernameById(final Long uid) {
        return lambdaQuery()
                .eq(User::getId, uid)
                .select(User::getUsername)
                .one()
                .getUsername();
    }
}
