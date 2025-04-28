package com.devin.dezhi.dao.v1.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.entity.user.User;
import com.devin.dezhi.domain.v1.vo.req.UserInfoReq;
import com.devin.dezhi.enums.FlagEnum;
import com.devin.dezhi.mapper.v1.user.UserMapper;
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
     * @param userInfoReq 用户信息
     * @return User
     */
    public User getByReq(final UserInfoReq userInfoReq) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 正常状态
        lambdaQueryWrapper.eq(User::getDelFlag, FlagEnum.NORMAL.getFlag());

        // 动态生成查询条件
        if (Objects.nonNull(userInfoReq.getUsername())) {
            lambdaQueryWrapper.eq(User::getUsername, userInfoReq.getUsername());
        }
        if (Objects.nonNull(userInfoReq.getPassword())) {
            lambdaQueryWrapper.eq(User::getPassword, userInfoReq.getPassword());
        }
        if (Objects.nonNull(userInfoReq.getEmail())) {
            lambdaQueryWrapper.eq(User::getEmail, userInfoReq.getEmail());
        }

        return getOne(lambdaQueryWrapper);
    }

    /**
     * 通过用户id逻辑删除用户信息.
     * @param uid 用户id
     * @return boolean
     */
    public boolean logicRemoveById(final Long uid) {
        return lambdaUpdate()
                .eq(User::getId, uid)
                .set(User::getDelFlag, FlagEnum.DISABLED.getFlag())
                .update();
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
}
