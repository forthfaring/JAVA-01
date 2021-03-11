/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : sys

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 11/03/2021 21:54:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL,
  `desc` varchar(512) NOT NULL,
  `image_url` varchar(512) NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `created_id` bigint(20) NOT NULL,
  `updated_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL COMMENT '订单号',
  `price` int(10) unsigned NOT NULL COMMENT '订单金额',
  `order_status` tinyint(3) unsigned NOT NULL COMMENT '0:已取消 1:已完成',
  `pay_status` tinyint(4) NOT NULL COMMENT '0: 未支付 1:已经支付 2:已退款',
  `fail_reason` varchar(64) DEFAULT '' COMMENT '失败原因',
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `created_id` bigint(20) NOT NULL,
  `updated_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for t_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) NOT NULL,
  `goods_id` bigint(20) unsigned NOT NULL,
  `sku_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `gods_title` varchar(256) NOT NULL,
  `goods_desc` varchar(512) NOT NULL,
  `goods_image` varchar(512) NOT NULL,
  `goods_price` int(10) unsigned NOT NULL,
  `created_time` datetime NOT NULL,
  `created_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';

-- ----------------------------
-- Table structure for t_sku
-- ----------------------------
DROP TABLE IF EXISTS `t_sku`;
CREATE TABLE `t_sku` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) unsigned NOT NULL,
  `stock` int(10) unsigned NOT NULL,
  `price` int(10) unsigned NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `created_id` bigint(20) NOT NULL,
  `updated_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品库存表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `nickname` varchar(64) NOT NULL COMMENT '姓名',
  `mobile` char(11) NOT NULL COMMENT '手机号',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `avatar` varchar(256) DEFAULT NULL,
  `password` varchar(128) NOT NULL COMMENT '密码',
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  `created_id` bigint(20) NOT NULL,
  `updated_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
