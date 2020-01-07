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

 Date: 16/12/2019 11:19:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for envelope_record
-- ----------------------------
DROP TABLE IF EXISTS `envelope_record`;
CREATE TABLE `envelope_record`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态（假删除）',
  `version` int(11) NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `user_id` bigint(22) NOT NULL COMMENT '玩家id',
  `user_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '玩家名称',
  `open_time` datetime(0) NULL DEFAULT NULL COMMENT '开启时间',
  `open_num` int(11) NULL DEFAULT 0 COMMENT '开启数量',
  `receive_coin` bigint(22) NULL DEFAULT 0 COMMENT '获得的金币：玩家一次性消耗红包个数获得的金币数',
  `before_open_coin` bigint(22) NULL DEFAULT 0 COMMENT '开启前金币：玩家开启红包前自身携带的金币',
  `after_open_coin` bigint(22) NULL DEFAULT 0 COMMENT '开启后金币：玩家开启红包后自身携带的金币',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '是否开启 0-未开启  1-已开启',
  `event_id` bigint(22) NOT NULL COMMENT '事件id',
  `activity_id` bigint(22) NOT NULL COMMENT '活动id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '天降财神红包记录' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
