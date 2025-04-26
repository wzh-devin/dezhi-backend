package com.devin.dezhi.enums.rpac;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 2025/4/25 23:26.
 *
 * <p></p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN("admin", "超级管理员"),
    PRO_USER("pro_user", "Pro用户"),
    USER("user", "普通用户");

    /**
     * 角色名称.
     */
    private final String role;

    /**
     * 角色描述.
     */
    private final String remark;
}
