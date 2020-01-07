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

 Date: 16/12/2019 11:19:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fortune_activi_config
-- ----------------------------
DROP TABLE IF EXISTS `fortune_activi_config`;
CREATE TABLE `fortune_activi_config`  (
  `id` bigint(22) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动名称',
  `detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动详情：活动规则介绍',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开启时间：活动开启时间，精确到时分秒00：00：00。修改开启时间时，若开启时间没有大于等于当前时间，则按当前时间设置',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间：活动结束时间，精确到时分秒00：00：00。活动结束前1小时，发送全服邮件提醒玩家：“活动还有1小时结束，未使用的红包将被清空，请及时使用！”',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `delete_status` int(1) NULL DEFAULT 0 COMMENT '删除状态（假删除）',
  `maxnum_reward` bigint(22) NOT NULL DEFAULT 0 COMMENT '奖励上限：红包奖励的最高金额 ',
  `minnum_reward` bigint(22) NOT NULL DEFAULT 0 COMMENT '奖励下限：红包奖励的最低金额，值需大于等于0.01',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '天降财神活动配置' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
