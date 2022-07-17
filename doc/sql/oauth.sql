/*
 Navicat Premium Data Transfer

 Source Server         : lemon-oauth
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Schema         : lemon

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 20/05/2022 17:44:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lemon_client_info
-- ----------------------------
DROP TABLE IF EXISTS `lemon_client_info`;
CREATE TABLE `lemon_client_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务名称',
  `secret` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密钥',
  `info` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务介绍',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_key_service_name`(`client_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '服务信息表，记录微服务的id，名称，密文，用来做服务认证' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of lemon_client_info
-- ----------------------------
INSERT INTO `lemon_client_info` VALUES (1, 'pay-service', '$2a$10$LjOQwjNv.4cO0uftZkvZzOfhpXQxqU.XrHL5Ut6m3G4OXBkQQQdBe', '支付微服务', '2022-06-12 15:55:11', '2022-06-12 15:55:11');
INSERT INTO `lemon_client_info` VALUES (2, 'order-service', '$2a$10$LjOQwjNv.4cO0uftZkvZzOfhpXQxqU.XrHL5Ut6m3G4OXBkQQQdBe', '订单微服务', '2022-06-12 15:55:11', '2022-06-12 15:55:11');
INSERT INTO `lemon_client_info` VALUES (3, 'goods-service', '$2a$10$LjOQwjNv.4cO0uftZkvZzOfhpXQxqU.XrHL5Ut6m3G4OXBkQQQdBe', '商品微服务', '2022-06-12 15:55:11', '2022-06-12 15:55:11');
INSERT INTO `lemon_client_info` VALUES (4, 'coupon-service', '$2a$10$LjOQwjNv.4cO0uftZkvZzOfhpXQxqU.XrHL5Ut6m3G4OXBkQQQdBe', '优惠券微服务', '2022-06-12 15:55:11', '2022-06-12 15:55:11');
INSERT INTO `lemon_client_info` VALUES (5, 'gateway-service', '$2a$10$LjOQwjNv.4cO0uftZkvZzOfhpXQxqU.XrHL5Ut6m3G4OXBkQQQdBe', '网关微服务', '2022-06-12 15:55:11', '2022-06-12 15:55:11');
INSERT INTO `lemon_client_info` VALUES (6, 'oauth-service', '$2a$10$LjOQwjNv.4cO0uftZkvZzOfhpXQxqU.XrHL5Ut6m3G4OXBkQQQdBe', '授权微服务', '2022-06-12 15:55:11', '2022-06-12 15:55:11');
INSERT INTO `lemon_client_info` VALUES (7, 'file-service', '$2a$10$LjOQwjNv.4cO0uftZkvZzOfhpXQxqU.XrHL5Ut6m3G4OXBkQQQdBe', '文件微服务', '2022-06-14 11:11:00', '2022-06-14 11:11:00');

-- ----------------------------
-- Table structure for lemon_group
-- ----------------------------
DROP TABLE IF EXISTS `lemon_group`;
CREATE TABLE `lemon_group`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组名称，例如：搬砖者',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组信息：例如：搬砖的人',
  `level` tinyint(4) NOT NULL DEFAULT 3 COMMENT '分组级别 1：root 2：guest 3：user  root（root、guest分组只能存在一个)',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_del`(`name`, `delete_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lemon_group
-- ----------------------------
INSERT INTO `lemon_group` VALUES (1, 'root', '超级用户组', 1, '2021-10-28 22:16:42.459', '2021-10-28 22:16:42.459', NULL);
INSERT INTO `lemon_group` VALUES (2, 'guest', '游客组', 2, '2021-10-28 22:16:42.460', '2021-10-28 22:16:42.460', NULL);

-- ----------------------------
-- Table structure for lemon_group_permission
-- ----------------------------
DROP TABLE IF EXISTS `lemon_group_permission`;
CREATE TABLE `lemon_group_permission`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `group_id` int(10) UNSIGNED NOT NULL COMMENT '分组id',
  `permission_id` int(10) UNSIGNED NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `group_id_permission_id`(`group_id`, `permission_id`) USING BTREE COMMENT '联合索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for lemon_log
-- ----------------------------
DROP TABLE IF EXISTS `lemon_log`;
CREATE TABLE `lemon_log`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `message` varchar(450) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `username` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status_code` int(11) NULL DEFAULT NULL,
  `method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lemon_permission
-- ----------------------------
DROP TABLE IF EXISTS `lemon_permission`;
CREATE TABLE `lemon_permission`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称，例如：访问首页',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限所属模块，例如：人员管理',
  `mount` tinyint(1) NOT NULL DEFAULT 1 COMMENT '0：关闭 1：开启',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for lemon_user
-- ----------------------------
DROP TABLE IF EXISTS `lemon_user`;
CREATE TABLE `lemon_user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名，唯一',
  `nickname` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_del`(`username`, `delete_time`) USING BTREE,
  UNIQUE INDEX `email_del`(`email`, `delete_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lemon_user
-- ----------------------------
INSERT INTO `lemon_user` VALUES (1, 'root', 'root', NULL, NULL, '2021-10-28 22:16:42.453', '2021-10-28 22:16:42.453', NULL);
INSERT INTO `lemon_user` VALUES (2, 'guest', NULL, NULL, NULL, '2022-05-08 12:21:06.341', '2022-05-08 12:21:06.341', NULL);

-- ----------------------------
-- Table structure for lemon_user_group
-- ----------------------------
DROP TABLE IF EXISTS `lemon_user_group`;
CREATE TABLE `lemon_user_group`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '用户id',
  `group_id` int(10) UNSIGNED NOT NULL COMMENT '分组id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_group_id`(`user_id`, `group_id`) USING BTREE COMMENT '联合索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lemon_user_group
-- ----------------------------
INSERT INTO `lemon_user_group` VALUES (1, 1, 1);
INSERT INTO `lemon_user_group` VALUES (2, 2, 2);

-- ----------------------------
-- Table structure for lemon_user_identity
-- ----------------------------
DROP TABLE IF EXISTS `lemon_user_identity`;
CREATE TABLE `lemon_user_identity`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '用户id',
  `identity_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `identifier` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `credential` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lemon_user_identity
-- ----------------------------
INSERT INTO `lemon_user_identity` VALUES (1, 1, 'USERNAME_PASSWORD', 'root', 'pbkdf2sha256:64000:18:24:n:yUnDokcNRbwILZllmUOItIyo9MnI00QW:6ZcPf+sfzyoygOU8h/GSoirF', '2021-10-28 22:16:42.457', '2021-10-28 22:16:42.457', NULL);
INSERT INTO `lemon_user_identity` VALUES (2, 2, 'USERNAME_PASSWORD', 'guest', 'pbkdf2sha256:64000:18:24:n:M8MdU53QFv6iJLWPJNsSPdsdLPIT3zV5:0XsKd3PC7MrRwN7MnFlVZIeO', '2022-05-08 12:21:06.740', '2022-05-08 12:21:06.740', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nickname` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `unify_uid` int(11) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mobile` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `wx_profile` json NULL,
  `create_time` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3),
  `update_time` datetime(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `delete_time` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_openid`(`openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
