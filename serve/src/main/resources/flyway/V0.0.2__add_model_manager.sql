/* --------------- 创建表 --------------- */
DROP TABLE IF EXISTS dz_model_manager;
CREATE TABLE dz_model_manager
(
    id          int8           NOT NULL,
    provider    VARCHAR(255)   NOT NULL,
    model       VARCHAR(255)   NOT NULL,
    base_url    VARCHAR(255),
    api_key     VARCHAR(255)   NOT NULL,
    create_time TIMESTAMP(255) NOT NULL DEFAULT NOW(),
    update_time TIMESTAMP(255) NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);
COMMENT
ON TABLE dz_model_manager IS '得智模型接入管理';
COMMENT
ON COLUMN dz_model_manager.id IS '模型id';
COMMENT
ON COLUMN dz_model_manager.provider IS '模型提供商';
COMMENT
ON COLUMN dz_model_manager.model IS '模型名称';
COMMENT
ON COLUMN dz_model_manager.base_url IS '模型的base_url';
COMMENT
ON COLUMN dz_model_manager.api_key IS '模型的api_key';
COMMENT
ON COLUMN dz_model_manager.create_time IS '创建时间';
COMMENT
ON COLUMN dz_model_manager.update_time IS '更新时间';