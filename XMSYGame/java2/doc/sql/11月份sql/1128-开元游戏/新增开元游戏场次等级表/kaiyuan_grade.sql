/*
 Navicat MySQL Data Transfer

 Source Server         : 145
 Source Server Type    : MySQL
 Source Server Version : 50646
 Source Host           : 10.0.0.145:3306
 Source Schema         : game_manager

 Target Server Type    : MySQL
 Target Server Version : 50646
 File Encoding         : 65001

 Date: 28/11/2019 17:56:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kaiyuan_grade
-- ----------------------------
DROP TABLE IF EXISTS `kaiyuan_grade`;
CREATE TABLE `kaiyuan_grade`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NULL DEFAULT NULL COMMENT '游戏id',
  `service_id` int(11) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '开元房间号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '场次名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态（假删除）',
  `grade_id` int(11) NULL DEFAULT NULL COMMENT '场次等级id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '开元游戏场次等级' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of kaiyuan_grade
-- ----------------------------
INSERT INTO `kaiyuan_grade` VALUES (1, NULL, NULL, '荣耀厅', '2018-10-19 11:36:21', '2019-04-07 20:26:47', 0, 0, 1);
INSERT INTO `kaiyuan_grade` VALUES (2, NULL, NULL, '王牌厅', '2018-10-19 11:36:21', '2019-04-07 20:27:02', 0, 0, 2);
INSERT INTO `kaiyuan_grade` VALUES (3, NULL, NULL, '战神厅', '2018-10-19 11:36:21', '2019-04-07 20:27:16', 0, 0, 3);
INSERT INTO `kaiyuan_grade` VALUES (4, NULL, NULL, '金币厅', '2019-04-07 20:27:35', '2019-04-23 10:35:55', 0, 0, 4);
INSERT INTO `kaiyuan_grade` VALUES (5, NULL, NULL, '积分厅', '2019-04-23 10:36:02', '2019-04-23 10:36:02', 0, 0, 5);
INSERT INTO `kaiyuan_grade` VALUES (6, NULL, NULL, '体验厅', '2019-06-15 15:29:09', '2019-06-15 15:29:09', 0, 0, 6);

SET FOREIGN_KEY_CHECKS = 1;
