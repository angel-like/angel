/*
 Navicat MySQL Data Transfer

 Source Server         : 145
 Source Server Type    : MySQL
 Source Server Version : 50646
 Source Host           : 10.0.0.145:3306
 Source Schema         : webhome

 Target Server Type    : MySQL
 Target Server Version : 50646
 File Encoding         : 65001

 Date: 16/12/2019 11:19:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for envelope_task_config
-- ----------------------------
DROP TABLE IF EXISTS `envelope_task_config`;
CREATE TABLE `envelope_task_config`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT,
  `event_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件触发红包奖励的事件方法',
  `event_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '事件名称',
  `activity_id` bigint(22) NULL DEFAULT NULL COMMENT '活动id',
  `event_param` bigint(22) NOT NULL DEFAULT 0 COMMENT '事件参数值 达到多少值才算完成这一次事件，默认0，值需大于等于0',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态（假删除）',
  `rewards` int(11) NOT NULL DEFAULT 1 COMMENT '奖励数量 完成事件时奖励的红包个数，整数，默认1，值需大于等于1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `活动事件已存在`(`event_code`, `activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '红包任务后台配置' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
