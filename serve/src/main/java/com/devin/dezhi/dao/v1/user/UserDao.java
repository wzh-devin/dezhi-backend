package com.devin.dezhi.dao.v1.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.devin.dezhi.domain.v1.entity.user.User;
import com.devin.dezhi.domain.v1.vo.req.UserInfoReq;
import com.devin.dezhi.mapper.v1.user.UserMapper;
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

        // 动态生成查询条件
        if (Objects.nonNull(userInfoReq.getUserName())) {
            lambdaQueryWrapper.eq(User::getUsername, userInfoReq.getUserName());
        }
        if (Objects.nonNull(userInfoReq.getPassword())) {
            lambdaQueryWrapper.eq(User::getPassword, userInfoReq.getPassword());
        }
        if (Objects.nonNull(userInfoReq.getEmail())) {
            lambdaQueryWrapper.eq(User::getEmail, userInfoReq.getEmail());
        }

        return getOne(lambdaQueryWrapper);
    }
}
