package com.devin.dezhi.enums.rpac;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 2025/4/25 23:20.
 *
 * <p>
 *     权限枚举类
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum PermissionEnum {

    USER_ADD("user:add", "添加用户"),
    USER_DELETE("user:delete", "删除用户"),
    USER_EDITE("user:edit", "修改用户"),
    ARTICLE_ADD("article:add", "添加文章"),
    ARTICLE_DELETE("article:delete", "删除文章"),
    ARTICLE_EDIT("article:edit", "修改文章"),
    ARTICLE_RELEASE("article:release", "发布文章");

    /**
     * 权限名称.
     */
    private final String permission;

    /**
     * 权限信息.
     */
    private final String remark;
}
