# 数据库初始化
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

-- 创建库
create database if not exists template_init_db;

-- 切换库
use template_init_db;

-- 用户表
create table if not exists user
(
    id            bigint auto_increment comment 'id' primary key,
    userAccount   varchar(256)                           not null comment '账号',
    userPassword  varchar(512)                           not null comment '密码',
    unionId       varchar(256)                           null comment '微信开放平台id',
    mpOpenId      varchar(256)                           null comment '公众号openId',
    userName      varchar(256)                           null comment '用户昵称',
    userAvatar    varchar(1024)                          null comment '用户头像',
    userProfile   varchar(512)                           null comment '用户简介',
    userRole      varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    scene         varchar(128)                           null comment '场景码',
    vipExpireTime datetime                               null comment '会员过期时间',
    createTime    datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

create index idx_userId
    on template_init_db.post (userId);

-- 周边表
create table if not exists `peripheral_info`
(
    `id`            bigint auto_increment comment '主键' primary key,
    `name`          varchar(256)                       not null comment '名称',
    `cover`         varchar(1024) comment '图片',
    `price`         int comment '价格（分）',
    `stock`         int comment '库存',
    `type`          varchar(128)                       null comment '周边类型',
    `replenishLink` varchar(512) comment '进货链接',
    `purchaseLink`  varchar(512) comment '用户购买链接',
    `status`        int      default 0                 not null comment '是否公开浏览，0: 关闭，1: 启用，默认关闭',
    `permission`    varchar(4096) comment '存储所有权限的 JSON 结构',
    `userId`        bigint                             not null comment '创建用户 id',
    `createTime`    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime`    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete`      tinyint  default 0                 not null comment '是否删除(0-未删, 1-已删)'
) comment '周边';
-- 给type字段添加普通索引
CREATE INDEX idx_type ON peripheral_info(type);

-- 申请记录表
CREATE TABLE IF NOT EXISTS `apply_records`
(
    `id`                bigint              AUTO_INCREMENT PRIMARY KEY COMMENT '主键，用于唯一标识每条申请记录',
    `peripheralId`      bigint              COMMENT '关联到周边信息表中的 id 字段，表示申请的是哪个周边',
    `peripheralName`    VARCHAR(256)        COMMENT '周边名称',
    `applicantId`       bigint              COMMENT '关联到用户表中的 id 字段，表示申请者的用户ID',
    `adminId`           bigint              COMMENT '审核人id',
    `applicantUserName` VARCHAR(512)        COMMENT '关联到用户表中的 userName 字段，表示申请者的用户名',
    `applicationTime`   DATETIME            DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '记录申请的时间',
    `status`            int                 COMMENT '申请状态（0：未申请，1：审核中，2：通过审核，3：审核不通过）',
    `content`           VARCHAR(4096)       COMMENT '申请内容（xxx申请xxx周边一件）',
    `reason`            VARCHAR(4096)       COMMENT '审核通过或者不通过的理由',
    `isDelete`          TINYINT             default 0 COMMENT '是否删除，0: 未删除，1: 已删除，默认未删除',
    `createTime`        DATETIME            DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '记录创建时间',
    `updateTime`        DATETIME            DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '记录最后更新时间'
) COMMENT '申请记录表';


