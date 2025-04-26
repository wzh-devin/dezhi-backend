package com.devin.dezhi.mapper.v1.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.devin.dezhi.domain.v1.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 2025/4/25 18:26.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
