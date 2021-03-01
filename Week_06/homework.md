### 基于电商交易场景(用户、商品、订单)，设计一套简单的表结构，提交DDL 的 SQL 文件到 Github(后面2周的作业依然要是用到这个表结构)。

```
create table `t_user`(
    `id` bigint unsigned not null auto_increment,
    `nickname` varchar(64) not null comment '姓名',
    `mobile` char(11) not null comment '手机号',
    `status` tinyint unsigned not null default 1,
    `avatar` varchar(256) default null,
    `password` varchar(128) not null comment '密码',
    `created_time` datetime not null,
    `updated_time` datetime not null,
    `created_id` bigint not null,
    `updated_id` bigint not null,
    primary key (`id`)
)comment '用户表';

create table `t_goods` (
    `id` bigint unsigned not null auto_increment,
    `title` varchar(256) not null,
    `desc` varchar(512) not null,
    `image_url` varchar(512) not null,
    `created_time` datetime not null,
    `updated_time` datetime not null,
    `created_id` bigint not null,
    `updated_id` bigint not null,
    primary key (`id`)
) comment '商品表';

create table `t_sku`(
    `id` bigint unsigned not null auto_increment,
    `goods_id` bigint unsigned not null ,
    `stock` int unsigned  not null,
    `price` int unsigned not null ,
    `created_time` datetime not null,
    `updated_time` datetime not null,
    `created_id` bigint not null,
    `updated_id` bigint not null,
    primary key (`id`)
)comment '商品库存表';

create table `t_order` (
    `id` bigint unsigned not null auto_increment,
    `order_no` varchar(32) not null comment '订单号',
		`price` int unsigned not null comment '订单金额',
    `order_status` tinyint unsigned not null comment '0:已取消 1:已完成',
    `pay_status` tinyint not null comment '0: 未支付 1:已经支付 2:已退款',
    `fail_reason` varchar(64) default '' comment '失败原因',
    `created_time` datetime not null,
    `updated_time` datetime not null,
    `created_id` bigint not null,
    `updated_id` bigint not null,
    primary key (`id`)
)comment  '订单表';

create table `t_order_detail`(
    `id` bigint unsigned not null auto_increment,
    `order_no` varchar(32) not null,
    `goods_id` bigint unsigned not null,
    `sku_id` bigint unsigned not null ,
    `user_id` bigint unsigned not null,
    `gods_title` varchar(256) not null,
    `goods_desc` varchar(512) not null,
    `goods_image` varchar(512) not null,
    `goods_price` int unsigned  not null,
    `created_time` datetime not null,
    `created_id` bigint not null,
    primary key (`id`)
)comment '订单详情表';
```

