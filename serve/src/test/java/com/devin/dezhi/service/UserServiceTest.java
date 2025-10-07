package com.devin.dezhi.service;

import com.devin.dezhi.common.utils.RedisUtil;
import com.devin.dezhi.constant.RedisKey;
import com.devin.dezhi.dao.v1.user.UserDao;
import com.devin.dezhi.domain.v1.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Set;

/**
 * 2025/4/25 18:35.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testGetUserInfo() {
        List<User> users = userDao.list();
        log.info("users: {}", users);
    }

    @Test
    public void testRedis() {
        Set<String> keys = redisUtil.keys(RedisKey.generateRedisKey(RedisKey.LOGIN_INFO, "*"));
        log.info("keys: {}", keys);
//        redisUtil.delete(keys);
    }
}
