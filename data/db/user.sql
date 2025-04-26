create schema if not exists dezhi_blog_master;

create table if not exists tb_user
(
    id             bigint                  not null
    primary key,
    username       varchar(255)            not null,
    email          varchar(100)            not null,
    password       varchar(60)             not null,
    avatar         varchar,
    create_user_id bigint                  not null,
    create_time    timestamp default now() not null,
    update_user_id bigint                  not null,
    update_time    timestamp default now() not null,
    del_flag       smallint                not null
    );

comment on table tb_user is '用户表';

comment on column tb_user.id is '用户id';

comment on column tb_user.username is '用户名';

comment on column tb_user.email is '邮箱';

comment on column tb_user.password is '用户密码';

comment on column tb_user.avatar is '用户头像';

comment on column tb_user.create_user_id is '创建人id';

comment on column tb_user.create_time is '创建时间';

comment on column tb_user.update_user_id is '更新人id';

comment on column tb_user.update_time is '更新时间';

comment on column tb_user.del_flag is '删除标识（0: 已删除，1：未删除）';

create table if not exists tb_role
(
    id          bigint                  not null
    primary key,
    role        varchar(60)             not null
    unique,
    create_time timestamp default now() not null,
    update_time timestamp default now() not null,
    remark      varchar
    );

comment on table tb_role is '角色表';

comment on column tb_role.id is '角色id';

comment on column tb_role.role is '角色信息';

comment on column tb_role.create_time is '创建时间';

comment on column tb_role.update_time is '更新时间';

comment on column tb_role.remark is '角色说明';

create table if not exists tb_permission
(
    id          bigint                  not null
    primary key,
    permission  varchar(60)             not null
    unique,
    create_time timestamp default now() not null,
    update_time timestamp default now() not null,
    remark      varchar
    );

comment on table tb_permission is '权限表';

comment on column tb_permission.permission is '权限信息';

comment on column tb_permission.create_time is '创建时间';

comment on column tb_permission.update_time is '更新时间';

comment on column tb_permission.remark is '权限说明';

create table if not exists tb_user_role
(
    id          bigint                  not null
    primary key,
    user_id     bigint                  not null,
    role_id     bigint                  not null,
    create_time timestamp default now() not null,
    update_time timestamp default now() not null
    );

comment on table tb_user_role is '用户角色关联表';

comment on column tb_user_role.id is '用户角色关联表id';

comment on column tb_user_role.user_id is '用户id';

comment on column tb_user_role.role_id is '角色id';

comment on column tb_user_role.create_time is '创建时间';

comment on column tb_user_role.update_time is '更新时间';

create table if not exists tb_role_permission
(
    id            bigint                  not null
    primary key,
    role_id       bigint                  not null,
    permission_id bigint                  not null,
    create_time   timestamp default now() not null,
    update_time   timestamp default now() not null
    );

comment on table tb_role_permission is '角色权限关联表';

comment on column tb_role_permission.id is '角色权限关联表id';

comment on column tb_role_permission.role_id is '角色id';

comment on column tb_role_permission.permission_id is '权限id';

comment on column tb_role_permission.create_time is '创建时间';

comment on column tb_role_permission.update_time is '更新时间';

