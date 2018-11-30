/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : 127.0.0.1:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 30/11/2018 11:18:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `user_pass` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `user_mail` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `user_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_company` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户公司名称',
  `user_createdate` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `user_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '用户状态（0：冻结，1：正常）',
  `user_dept` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门',
  `last_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '上一次登录错误锁定的时间戳',
  `user_realname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', 'admin@test.com', '', 'test', '2018-11-01 19:48:03', 1, 'department', '2018-11-14 17:26:30', 'admin');
INSERT INTO `user` VALUES (6, 'Boc121-default', 'E10ADC3949BA59ABBE56E057F20F883E', 'admin@test.com', NULL, 'test', '2018-11-02 11:32:37', 1, 'department', '2018-11-02 11:32:37', 'default');
INSERT INTO `user` VALUES (7, 'test01', 'E10ADC3949BA59ABBE56E057F20F883E', 'aaa@dfdd.com', '15921230000', 'test', '2018-11-02 11:47:45', 1, 'department', '2018-11-02 11:47:45', 'test01');
INSERT INTO `user` VALUES (10, 'user01', 'E10ADC3949BA59ABBE56E057F20F883E', '1112@qq.com', '13535364636', 'test', '2018-11-02 15:50:45', 1, 'department', '2018-11-02 15:50:45', 'user01');
INSERT INTO `user` VALUES (12, 'user213', 'E10ADC3949BA59ABBE56E057F20F883E', '12@qq.com', '13253546463', 'test', '2018-11-02 16:39:09', 1, 'department', '2018-11-02 16:39:09', 'user213');
INSERT INTO `user` VALUES (14, 'u007', 'E10ADC3949BA59ABBE56E057F20F883E', 'u@qq.com', '13854325435', 'test', '2018-11-05 17:57:45', 1, 'department', '2018-11-05 17:57:45', 'u007');
INSERT INTO `user` VALUES (15, 'u008', 'E10ADC3949BA59ABBE56E057F20F883E', '123@qq.com', '13634545664', 'test', '2018-11-06 16:04:03', 1, 'department', '2018-11-06 16:04:03', 'u008');
INSERT INTO `user` VALUES (20, 'Boc213-default', 'E10ADC3949BA59ABBE56E057F20F883E', 'admin@test.com', NULL, 'test', '2018-11-07 17:35:49', 1, 'department', '2018-11-07 17:35:49', 'Boc213-default');
INSERT INTO `user` VALUES (21, 'ggg123132', 'E10ADC3949BA59ABBE56E057F20F883E', '222@qq.com', '15151467107', 'test', '2018-11-07 17:36:35', 1, 'department', '2018-11-07 17:36:35', 'ggg123132');
INSERT INTO `user` VALUES (22, 'u1', 'E10ADC3949BA59ABBE56E057F20F883E', 'u1@bocloud.com', NULL, 'test', '2018-11-07 21:00:23', 1, 'department', '2018-11-07 21:00:19', 'u1');
INSERT INTO `user` VALUES (23, 'u3', 'E10ADC3949BA59ABBE56E057F20F883E', 'u3@bocloud.com', NULL, 'test', '2018-11-07 21:00:23', 1, 'department', '2018-11-07 21:00:19', 'u3');
INSERT INTO `user` VALUES (35, 'aaaaaaaaa', 'E10ADC3949BA59ABBE56E057F20F883E', '1@1.com', '13222222222', 'test', '2018-11-27 09:55:37', 1, 'department', '2018-11-27 09:55:37', 'aaaaaaaaa');
INSERT INTO `user` VALUES (42, 'qwq', 'E10ADC3949BA59ABBE56E057F20F883E', '1@1.com', '13211111111', 'test', '2018-11-28 16:10:54', 1, 'department', '2018-11-28 16:10:54', 'qwq');

SET FOREIGN_KEY_CHECKS = 1;
