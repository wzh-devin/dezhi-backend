/* --------------- 创建表 --------------- */
DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict
(
    id          int8         NOT NULL,
    code        VARCHAR(255) NOT NULL,
    type        VARCHAR(255) NOT NULL,
    name        VARCHAR(255),
    create_time timestamp    NOT NULL DEFAULT now(),
    update_time timestamp    NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);
COMMENT
ON TABLE sys_dict IS '系统字典表';
COMMENT
ON COLUMN sys_dict.id IS '系统字典id';
COMMENT
ON COLUMN sys_dict.code IS '字典code';
COMMENT
ON COLUMN sys_dict.type IS 'code类型';
COMMENT
ON COLUMN sys_dict.name IS 'code描述';
COMMENT
ON COLUMN sys_dict.create_time IS '创建时间';
COMMENT
ON COLUMN sys_dict.update_time IS '更新时间';
DROP TABLE IF EXISTS tb_article;
CREATE TABLE tb_article
(
    id          int8         NOT NULL,
    category_id int8,
    title       VARCHAR(255) NOT NULL,
    summary     VARCHAR(255),
    content     TEXT,
    content_md  TEXT,
    url         VARCHAR(255),
    is_stick    int2         NOT NULL DEFAULT 0,
    status      int2         NOT NULL DEFAULT 0,
    is_hot      int2         NOT NULL DEFAULT 0,
    is_ai       int2         NOT NULL DEFAULT 0,
    create_time timestamp    NOT NULL DEFAULT now(),
    update_time timestamp    NOT NULL DEFAULT now(),
    is_deleted  int2         NOT NULL,
    PRIMARY KEY (id)
);
COMMENT
ON TABLE tb_article IS '文章表';
COMMENT
ON COLUMN tb_article.id IS '文章id';
COMMENT
ON COLUMN tb_article.category_id IS '文章分类';
COMMENT
ON COLUMN tb_article.title IS '文章标题';
COMMENT
ON COLUMN tb_article.summary IS '文章简介';
COMMENT
ON COLUMN tb_article.content IS '文章内容';
COMMENT
ON COLUMN tb_article.content_md IS '文章内容（markdown格式）';
COMMENT
ON COLUMN tb_article.url IS '文章url地址';
COMMENT
ON COLUMN tb_article.is_stick IS '文章是否置顶（0：否；1：是）';
COMMENT
ON COLUMN tb_article.status IS '文章状态（0：草稿；1：发布）';
COMMENT
ON COLUMN tb_article.is_hot IS '是否热门文章（0：否；1：是）';
COMMENT
ON COLUMN tb_article.is_ai IS '是否ai创建（0：否；1：是）';
COMMENT
ON COLUMN tb_article.create_time IS '创建时间';
COMMENT
ON COLUMN tb_article.update_time IS '更新时间';
COMMENT
ON COLUMN tb_article.is_deleted IS '是否被删除（0：未删除；1：已删除）';
DROP TABLE IF EXISTS tb_article_tag;
CREATE TABLE tb_article_tag
(
    id          int8      NOT NULL,
    article_id  int8      NOT NULL,
    tag         int8      NOT NULL,
    create_time timestamp NOT NULL DEFAULT now(),
    update_time timestamp NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);
COMMENT
ON TABLE tb_article_tag IS '文章标签关联表';
COMMENT
ON COLUMN tb_article_tag.id IS '文章标签关联id';
COMMENT
ON COLUMN tb_article_tag.article_id IS '文章id';
COMMENT
ON COLUMN tb_article_tag.tag IS '标签id';
COMMENT
ON COLUMN tb_article_tag.create_time IS '创建时间';
COMMENT
ON COLUMN tb_article_tag.update_time IS '更新时间';
DROP TABLE IF EXISTS tb_category;
CREATE TABLE tb_category
(
    id          int8         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    color       VARCHAR(9)   NOT NULL,
    create_time timestamp    NOT NULL DEFAULT now(),
    update_time timestamp    NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);
COMMENT
ON TABLE tb_category IS '类别表';
COMMENT
ON COLUMN tb_category.id IS '分类id';
COMMENT
ON COLUMN tb_category.name IS '分类名称';
COMMENT
ON COLUMN tb_category.color IS '类别颜色';
COMMENT
ON COLUMN tb_category.create_time IS '创建时间';
COMMENT
ON COLUMN tb_category.update_time IS '更新时间';
DROP TABLE IF EXISTS tb_material;
CREATE TABLE tb_material
(
    id           int8         NOT NULL,
    name         VARCHAR(255) NOT NULL,
    md5          bpchar(32),
    size         int8         NOT NULL,
    file_type    VARCHAR(255) NOT NULL,
    storage_type VARCHAR(255) NOT NULL,
    url          VARCHAR(255),
    create_time  timestamp    NOT NULL DEFAULT now(),
    update_time  timestamp    NOT NULL DEFAULT now(),
    is_deleted   int2         NOT NULL,
    PRIMARY KEY (id)
);
COMMENT
ON TABLE tb_material IS '文件素材表';
COMMENT
ON COLUMN tb_material.id IS '素材id';
COMMENT
ON COLUMN tb_material.name IS '文件名称';
COMMENT
ON COLUMN tb_material.md5 IS '文件md5值';
COMMENT
ON COLUMN tb_material.size IS '文件大小';
COMMENT
ON COLUMN tb_material.file_type IS '文件类型';
COMMENT
ON COLUMN tb_material.storage_type IS '文件存储类型';
COMMENT
ON COLUMN tb_material.url IS '文件地址';
COMMENT
ON COLUMN tb_material.create_time IS '创建时间';
COMMENT
ON COLUMN tb_material.update_time IS '更新时间';
COMMENT
ON COLUMN tb_material.is_deleted IS '是否删除（0：未删除；1：已删除）';
DROP TABLE IF EXISTS tb_permission;
CREATE TABLE tb_permission
(
    id          int8         NOT NULL,
    permission  VARCHAR(60)  NOT NULL,
    remark      VARCHAR(255) NOT NULL,
    create_time timestamp    NOT NULL DEFAULT now(),
    update_time timestamp    NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);
COMMENT
ON TABLE tb_permission IS '权限表';
COMMENT
ON COLUMN tb_permission.id IS '权限id';
COMMENT
ON COLUMN tb_permission.permission IS '权限名称';
COMMENT
ON COLUMN tb_permission.remark IS '权限描述';
COMMENT
ON COLUMN tb_permission.create_time IS '创建时间';
COMMENT
ON COLUMN tb_permission.update_time IS '更新时间';
DROP TABLE IF EXISTS tb_role;
CREATE TABLE tb_role
(
    id          int8           NOT NULL,
    role        VARCHAR(60)    NOT NULL,
    remark      VARCHAR(255)   NOT NULL,
    create_time timestamp      NOT NULL DEFAULT now(),
    update_time TIMESTAMP(255) NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);
COMMENT
ON TABLE tb_role IS '角色表';
COMMENT
ON COLUMN tb_role.id IS '角色id';
COMMENT
ON COLUMN tb_role.role IS '角色名称';
COMMENT
ON COLUMN tb_role.remark IS '角色描述';
COMMENT
ON COLUMN tb_role.create_time IS '创建时间';
COMMENT
ON COLUMN tb_role.update_time IS '修改时间';
DROP TABLE IF EXISTS tb_role_permission;
CREATE TABLE tb_role_permission
(
    id            int8      NOT NULL,
    role_id       int8      NOT NULL,
    permission_id int8      NOT NULL,
    create_time   timestamp NOT NULL DEFAULT now(),
    update_time   timestamp NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);
COMMENT
ON TABLE tb_role_permission IS '角色权限关联表';
COMMENT
ON COLUMN tb_role_permission.id IS '角色权限关联表';
COMMENT
ON COLUMN tb_role_permission.role_id IS '角色id';
COMMENT
ON COLUMN tb_role_permission.permission_id IS '权限id';
COMMENT
ON COLUMN tb_role_permission.create_time IS '创建时间';
COMMENT
ON COLUMN tb_role_permission.update_time IS '更新时间';
DROP TABLE IF EXISTS tb_tag;
CREATE TABLE tb_tag
(
    id          int8         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    color       VARCHAR(9)   NOT NULL,
    create_time timestamp    NOT NULL DEFAULT now(),
    update_time timestamp    NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);
COMMENT
ON TABLE tb_tag IS '标签表';
COMMENT
ON COLUMN tb_tag.id IS '标签id';
COMMENT
ON COLUMN tb_tag.name IS '标签名称';
COMMENT
ON COLUMN tb_tag.color IS '标签颜色（hex）';
COMMENT
ON COLUMN tb_tag.create_time IS '创建时间';
COMMENT
ON COLUMN tb_tag.update_time IS '更新时间';
DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user
(
    id          int8         NOT NULL,
    username    VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    avatar      VARCHAR(255),
    create_time timestamp    NOT NULL DEFAULT now(),
    update_time timestamp    NOT NULL DEFAULT now(),
    is_deleted  int2         NOT NULL,
    PRIMARY KEY (id)
);
COMMENT
ON TABLE tb_user IS '用户表';
COMMENT
ON COLUMN tb_user.id IS '用户id';
COMMENT
ON COLUMN tb_user.username IS '用户名称';
COMMENT
ON COLUMN tb_user.password IS '用户密码';
COMMENT
ON COLUMN tb_user.email IS '用户邮箱';
COMMENT
ON COLUMN tb_user.avatar IS '用户头像';
COMMENT
ON COLUMN tb_user.create_time IS '创建时间';
COMMENT
ON COLUMN tb_user.update_time IS '更新时间';
COMMENT
ON COLUMN tb_user.is_deleted IS '是否被删除（0：未删除；1：已删除）';
DROP TABLE IF EXISTS tb_user_role;
CREATE TABLE tb_user_role
(
    id          int8      NOT NULL,
    user_id     int8      NOT NULL,
    role_id     int8      NOT NULL,
    create_time timestamp NOT NULL,
    update_time timestamp NOT NULL,
    PRIMARY KEY (id)
);
COMMENT
ON TABLE tb_user_role IS '用户角色关联表';
COMMENT
ON COLUMN tb_user_role.id IS '用户角色关联表';
COMMENT
ON COLUMN tb_user_role.user_id IS '用户id';
COMMENT
ON COLUMN tb_user_role.role_id IS '角色id';
COMMENT
ON COLUMN tb_user_role.create_time IS '创建时间';
COMMENT
ON COLUMN tb_user_role.update_time IS '更新时间';
