/*
Navicat MySQL Data Transfer

Source Server         : DB3
Source Server Version : 80015
Source Host           : 169.254.219.203:3508
Source Database       : webhome

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-04-21 22:07:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity_category
-- ----------------------------
DROP TABLE IF EXISTS `activity_category`;
CREATE TABLE `activity_category` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `category_name` varchar(255) NOT NULL COMMENT '类别名称',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（0：禁用  1：启用）',
  `enclosure_id` bigint(22) DEFAULT '0' COMMENT '图片id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动分类';

-- ----------------------------
-- Records of activity_category
-- ----------------------------

-- ----------------------------
-- Table structure for activity_info
-- ----------------------------
DROP TABLE IF EXISTS `activity_info`;
CREATE TABLE `activity_info` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `content` varchar(500) DEFAULT '' COMMENT '内容',
  `status` int(2) DEFAULT '1' COMMENT '状态（0：禁用  1：启用）',
  `effect_time` datetime DEFAULT NULL COMMENT '生效时间',
  `enclosure_id` bigint(22) DEFAULT '0' COMMENT '附件id',
  `category_id` bigint(22) NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动信息';

-- ----------------------------
-- Records of activity_info
-- ----------------------------

-- ----------------------------
-- Table structure for activity_sign_in
-- ----------------------------
DROP TABLE IF EXISTS `activity_sign_in`;
CREATE TABLE `activity_sign_in` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL COMMENT '用户id',
  `user_account` varchar(50) NOT NULL DEFAULT '' COMMENT '账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签到管理';

-- ----------------------------
-- Records of activity_sign_in
-- ----------------------------

-- ----------------------------
-- Table structure for agent_commission
-- ----------------------------
DROP TABLE IF EXISTS `agent_commission`;
CREATE TABLE `agent_commission` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `enable` bit(1) DEFAULT b'1' COMMENT '1：启用 0: 禁用',
  `type` int(255) NOT NULL COMMENT '返利类型0：金额返点，1：比例返点',
  `val` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '优惠比例或优惠金额',
  `agent_grade_id` bigint(22) DEFAULT '0' COMMENT '等级id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_uk` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理佣金';

-- ----------------------------
-- Records of agent_commission
-- ----------------------------

-- ----------------------------
-- Table structure for agent_grade
-- ----------------------------
DROP TABLE IF EXISTS `agent_grade`;
CREATE TABLE `agent_grade` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `enable` bit(1) DEFAULT b'1' COMMENT '1：启用 0: 禁用',
  `grade_name` varchar(255) NOT NULL DEFAULT '' COMMENT '等级名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_uk` (`grade_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理等级';

-- ----------------------------
-- Records of agent_grade
-- ----------------------------

-- ----------------------------
-- Table structure for app_ad_cofig
-- ----------------------------
DROP TABLE IF EXISTS `app_ad_cofig`;
CREATE TABLE `app_ad_cofig` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `url` varchar(255) DEFAULT '' COMMENT '路径',
  `enclosure_id` bigint(22) NOT NULL COMMENT '图片id',
  `type` bigint(10) NOT NULL DEFAULT '1' COMMENT '状态（1.不可点击，2：可跳转，3，跳游戏）',
  `md5` varchar(50) NOT NULL DEFAULT '' COMMENT 'md5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='app广告配置';

-- ----------------------------
-- Records of app_ad_cofig
-- ----------------------------
INSERT INTO `app_ad_cofig` VALUES ('3', '0', '2019-04-11 21:08:48', '2019-04-21 16:28:02', '0', '图一', '4', '871', '1', '8a0bb46a07c5feffa83c5d36a1bebc19');
INSERT INTO `app_ad_cofig` VALUES ('10', '0', '2019-04-11 21:09:15', '2019-04-21 16:51:56', '0', '图二', '', '878', '1', '1341e3d90f8eafb72523e6acb26a5e8d');
INSERT INTO `app_ad_cofig` VALUES ('17', '1', '2019-04-11 21:10:05', '2019-04-21 13:05:51', '0', '图三', 'https://www.abqp0.cc/', '864', '2', 'bc24fc4b9df59b3a1d247546223dfd7f');

-- ----------------------------
-- Table structure for app_alert_cofig
-- ----------------------------
DROP TABLE IF EXISTS `app_alert_cofig`;
CREATE TABLE `app_alert_cofig` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `enclosure_id` bigint(22) NOT NULL COMMENT '附件ID',
  `url` varchar(50) DEFAULT '' COMMENT '路径',
  `md5` varchar(50) DEFAULT NULL COMMENT 'md5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='app弹框配置';

-- ----------------------------
-- Records of app_alert_cofig
-- ----------------------------
INSERT INTO `app_alert_cofig` VALUES ('1', '0', '2019-03-07 15:05:43', '2019-03-07 15:05:43', '0', '新游戏上线', '387', 'www.baidu.com', 'cf5c026de678b7679fd3f8f6c6aa87b1');
INSERT INTO `app_alert_cofig` VALUES ('2', '0', '2019-03-07 15:21:29', '2019-03-07 15:21:29', '0', '充值大酬宾', '388', '', '9000e2403cf361e4126769c178d517c4');
INSERT INTO `app_alert_cofig` VALUES ('3', '0', '2019-03-07 15:32:14', '2019-03-07 15:32:14', '0', '开业大酬宾', '389', '', '9000e2403cf361e4126769c178d517c4');

-- ----------------------------
-- Table structure for app_customer_service
-- ----------------------------
DROP TABLE IF EXISTS `app_customer_service`;
CREATE TABLE `app_customer_service` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `url` varchar(255) DEFAULT '' COMMENT '客服url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='app客服管理';

-- ----------------------------
-- Records of app_customer_service
-- ----------------------------
INSERT INTO `app_customer_service` VALUES ('1', '0', '2019-03-06 15:27:48', '2019-04-08 23:58:57', '0', 'https://e-141816.chatnow.meiqia.com/dist/standalone.html');

-- ----------------------------
-- Table structure for app_pay_cofig
-- ----------------------------
DROP TABLE IF EXISTS `app_pay_cofig`;
CREATE TABLE `app_pay_cofig` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) NOT NULL COMMENT '客服',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '1:微信，2：QQ，3在线存款',
  `type_num` varchar(50) NOT NULL DEFAULT '' COMMENT '客服号',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（1.启用，0：禁用）',
  `tips` varchar(255) NOT NULL DEFAULT '' COMMENT '提示语',
  `icon` bigint(22) NOT NULL COMMENT '图标（附件ID）',
  `icon_md5` varchar(50) NOT NULL DEFAULT '' COMMENT '图标Md5值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='app支付配置';

-- ----------------------------
-- Records of app_pay_cofig
-- ----------------------------
INSERT INTO `app_pay_cofig` VALUES ('1', '0', '2019-03-04 11:28:20', '2019-04-20 19:39:51', '0', '小小', '2', '95235011', '', '复制QQ成功，请到QQ添加好友', '366', 'a1ffee7d8fb3e0e99c2fd18abdea3029');
INSERT INTO `app_pay_cofig` VALUES ('2', '0', '2019-03-04 11:29:42', '2019-03-05 16:38:03', '0', '小一', '1', '36363666696969', '', '复制微信成功，请到微信添加好友', '367', 'a1ffee7d8fb3e0e99c2fd18abdea3029');

-- ----------------------------
-- Table structure for app_promotions
-- ----------------------------
DROP TABLE IF EXISTS `app_promotions`;
CREATE TABLE `app_promotions` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `type_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '属性ID',
  `enclosure_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '附件ID',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `remake` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '备用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='APP优惠活动';

-- ----------------------------
-- Records of app_promotions
-- ----------------------------
INSERT INTO `app_promotions` VALUES ('12', '1', '2019-01-28 17:13:25', '2019-03-06 09:33:24', '0', '13', '218', '<p><strong>亲爱的召唤师：</strong></p><p>菲奥娜的新手训练营活动已完成更新上线，所有游戏等级小于30级的角色可以继续参与活动。本次更新内容如下：</p><p>1、 在1—30级期间升级奖励的物品更新，原活动物品已经下线。新手召唤师将有机会体验到更多类型的英雄，皮肤，战利品。</p><p>2、 可优惠低折扣购买的英雄组合。</p><p>3、 官网和游戏客户端内均有入口可同步参与领取和购买。</p><h3><strong style=\"color: rgb(0, 0, 0);\">官网入口：</strong><a href=\"https://lol.qq.com/act/a20181228bootcamp/index.html\" target=\"_blank\" style=\"color: rgb(0, 0, 255);\"><strong>https://lol.qq.com/act/a20181228bootcamp/index.html</strong></a></h3>', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('13', '0', '2019-01-28 17:13:25', '2019-03-06 16:00:35', '0', '13', '379', '<h1><br></h1><h1>英雄联盟2019视频大赏2月1日首发</h1><p><br></p><p><span style=\"color: rgb(68, 68, 68);\">英雄联盟官方</span>&nbsp;2019-02-01</p><p>12:02:18&nbsp;字体：&nbsp;<span style=\"color: rgb(170, 170, 170);\">大</span>&nbsp;<span style=\"color: rgb(170, 170, 170);\">中</span>&nbsp;<span style=\"color: rgb(170, 170, 170);\">小</span></p><p><br></p><p>4300次阅读&nbsp;9</p><p><br></p><p>时值春节将近，《英雄联盟》即将奉上一场精彩炫目的视听盛宴，以作为给广大玩家们的新春开门礼物——英雄联盟2019视频大赏将于2月1日晚间20：00首发上线，最酷炫的操作、最欢乐的瞬间、最感人的时刻、最荣耀的冠军、最走心的演讲，关于过去的2018年，关于刚刚开启的2019赛季，年度视频大赏将与广大玩家共同领略那些经典镜头，共度新春佳节。</p><p><br></p><p><strong>英雄联盟2019视频大赏专题</strong><a href=\"https://lol.qq.com/act/a20190129spring/page7.html\" target=\"_blank\" style=\"color: rgb(187, 154, 108);\"><strong>&gt;&gt;点击查看</strong></a></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\"><img src=\"https://shp.qpic.cn/cfwebcap/0/22c350b65989e0a1967f33d99ff387ea/0?width=600&amp;height=301\" height=\"301\" width=\"600\"></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\">图1：英雄联盟2019视频大赏</p><p><br></p><p>除英雄联盟官方网站、掌上英雄联盟、客户端电视台三大官方渠道，届时广大玩家还可锁定斗鱼、虎牙、企鹅电竞、熊猫、</p><p>wegame、腾讯视频、腾讯体育、企鹅体育、企鹅号、NOW直播、新浪微博、哔哩哔哩、触手直播、快手直播观看视频首发。</p><p><br></p><p><br></p><p><strong style=\"color: rgb(53, 126, 131);\">《走位说》首秀将至 明星主播在线发红包</strong></p><p><br></p><p>《走位说》是英雄联盟首档主播演讲节目，首期节目将于2月1日正式上线。首期节目当中，余小C、节奏、王稳健、虎神将一一登场亮相，与玩家们分享他们自己的心路历程与“传奇故事”。</p><iframe class=\"ql-video ql-align-center\" frameborder=\"0\" allowfullscreen=\"true\" src=\"https://v.qq.com/txp/iframe/player.html?vid=h0833u7xnqx\" height=\"430\" width=\"600\"></iframe><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\">&nbsp;</p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\">走位说预告——余小C</p><p><br></p><p>节目当中，以一手德莱厄斯为人所知的余小C，将在节目当中在线向玩家们传授自己的游戏秘诀——竟是…多吃饭?!</p><p>唯一拥有LPL选手经历的节奏，将带领玩家们一同回顾S6时期令人影响深刻的BO10；“老王”王稳健的现场演讲，将分享自己从“阳台少年</p><p>”变身“腰间盘突出”的心路历程；而玩家心中“贾克斯”的化身虎神，将在节目中与大家分享他与武器大师的渊源。</p><p><br></p><p>与此同时，在节目首播的过程中，明星主播还将在线为观众们派送新年红包。而以首期节目为开端，《走位说》还将在未来邀请更多主播登上演讲舞台，与玩家们分享更多他们的亲身故事与电竞人生。</p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\"><img src=\"https://shp.qpic.cn/cfwebcap/0/baac136c821e8aefbd2b697f754dbb95/0?width=600&amp;height=183\" height=\"183\" width=\"600\"></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\">图2：走位说明星主播在线派发红包</p><p><br></p><p><br></p><p><strong style=\"color: rgb(53, 126, 131);\">精彩视频全放送 共同领略年度经典时刻</strong></p><p><br></p><p>除了全新亮相的《走位说》，十余部精心创作的英雄联盟视频也将在2月1日晚间与广大玩家见面。</p><p><br></p><p>由知名游戏视频制作人徐老师带来的《LOL搞笑精彩集锦》是一款英雄联盟视频集锦视频，由搞笑的失误操作和大神精彩操作共同组成，让观众在欣赏大神操作秀的同时，也能看到很多搞笑操作会心一笑。</p><p><br></p><p>此前已推出多个系列精彩视频的拳师七号将携</p><p>《冠军中年人》作为给大家的春节贺礼，《冠军中年人》是解说拳师7号制作的英雄联盟系列游戏节目。是以诙谐搞笑的解说风格，为大家带来的韩服双排视频。</p><p><br></p><p>作为一位深耕于游戏视频制作，专注于电竞赛事的视频制作者，吃瓜游戏人将从赛场视角出发，带来</p><p>《吃瓜盘点》系列视频之十大经典解说、夺冠瞬间、抢龙瞬间多部作品。而另一位 UP主鸡鸡夫斯基则将带来他关于无限乱斗的独家花式解读。</p><p><br></p><p>除了以上精选节目，LPL官方视频《英雄麦克疯》也将推出年度TOP10特集，与广大玩家一同回顾职业选手们在赛场上的“骚话</p><p>”与金句，而2018主播年度回顾则将带领大家一同用娱乐的方式对主播们2018的高光/搞笑时刻进行盘点，这里不仅有王者的操作，更有风骚的尴尬“青铜”时刻。</p><p><br></p><p>2月1日玩家 20:00，英雄联盟2019视频大赏即将首发上线，《英雄联盟》将与广大玩家共同期待，并共享其中的欢乐与精彩。英雄，一起去超越!</p>', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('14', '0', '2019-01-28 17:13:25', '2019-02-03 14:37:28', '0', '14', '220', '<p>黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗</p>', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('15', '0', '2019-01-28 17:13:25', '2019-02-03 14:38:45', '0', '15', '222', '<p>黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗</p>', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('16', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('18', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('19', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('20', '0', '2019-01-28 17:13:25', '2019-04-19 19:38:07', '0', '12', '836', '<div data-v-78ec414c=\"\" class=\"tabContainer tabContainer-active\"><div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动\n			详情</span></div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <font color=\"#FFFF00\">★</font>即日起下载AB棋牌【手机APP】成功注册的用户，即可获得18元彩金，尚未注册/存款的亲们强烈建议您注册/存款，越多优惠等您您哟！</div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tbody><tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>活动对像</th>\n              <th>手机APP下载</th>\n              <th>彩金/限制</th>\n              <th>出款上限</th> \n               <th>申请方式</th>\n            </tr>\n            <tr>\n              <td>全体新会员</td>\n              <td>苹果ios/安卓Android</td>\n              <td>下载APP送18元</td>\n              <td>满108元即可提款<p><b><font color=\"#FE0103\">提现秒到</font></b></p></td>\n              <td>优惠大厅<p>在线客服</p></td>\n            </tr>\n          </tbody></table>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <font color=\"#FFFF00\">★</font>下载APP,成功注册AB棋牌会员。<p>\n          <font color=\"#FFFF00\">★</font>完善个人信息，绑定提款银行账号，获取18元彩金，首次出款无法更改银行账号。</p>\n			<p>\n          <font color=\"#FFFF00\">★</font>活动专员审核后自动派送，多IP账号或多次申请无法再次赠送彩金（每个会员只能申请一次）。</p>\n			<p>\n          <font color=\"#FFFF00\">★</font>会员领取优惠后，第一次申请提款前不可更改银行账号及任何资料；每个IP只限一次如出现同IP多现象，则视为同一人，此IP下所有账号公司有权拒绝派送彩金。</p></div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p><font color=\"#FFFF00\">★</font>本活动最终解释权和裁决权归AB棋牌所有，AB棋牌保留修改暂停中止该优惠活动等所有权力。</p>\n        </div>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div></div>', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('21', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '14', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('22', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '15', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('23', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('25', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('26', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('27', '0', '2019-01-28 17:13:25', '2019-04-19 19:24:28', '0', '12', '850', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('28', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '14', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('29', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '15', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('30', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('32', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('33', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('34', '0', '2019-01-28 17:13:25', '2019-04-19 17:57:47', '0', '12', '843', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('35', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '14', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('36', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '15', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('37', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('39', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `app_promotions` VALUES ('45', '0', '2019-04-21 20:39:10', '2019-04-21 20:39:10', '0', '12', '885', '892', '');

-- ----------------------------
-- Table structure for app_promotions_type
-- ----------------------------
DROP TABLE IF EXISTS `app_promotions_type`;
CREATE TABLE `app_promotions_type` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='APP优惠活动类型';

-- ----------------------------
-- Records of app_promotions_type
-- ----------------------------
INSERT INTO `app_promotions_type` VALUES ('12', '0', '2019-01-28 16:42:20', '2019-04-19 13:56:54', '0', '棋牌游戏');
INSERT INTO `app_promotions_type` VALUES ('13', '0', '2019-01-28 16:42:43', '2019-03-06 18:08:02', '0', '捕鱼达人');
INSERT INTO `app_promotions_type` VALUES ('14', '0', '2019-01-28 16:43:12', '2019-01-28 16:43:12', '0', '房卡场优惠');
INSERT INTO `app_promotions_type` VALUES ('15', '0', '2019-01-28 16:44:00', '2019-03-06 18:09:28', '0', '电子游艺');

-- ----------------------------
-- Table structure for cash_bank
-- ----------------------------
DROP TABLE IF EXISTS `cash_bank`;
CREATE TABLE `cash_bank` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) NOT NULL COMMENT '银行名称',
  `hierarchy_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '所属层级ID',
  `bank_account` varchar(50) NOT NULL DEFAULT '' COMMENT '银行卡号',
  `bank_user` varchar(50) NOT NULL DEFAULT '' COMMENT '收款人姓名',
  `bank_address` varchar(255) NOT NULL COMMENT '开户行',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否展示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='系统银行表（提供给用户充值的平台银行卡号）';

-- ----------------------------
-- Records of cash_bank
-- ----------------------------
INSERT INTO `cash_bank` VALUES ('1', '0', '2019-01-23 14:22:25', '2019-04-20 17:26:50', '0', '建设银行', '1', '44050140020400000882', '广州豪戴贸易有限公司', '广州东风东路支行', '');
INSERT INTO `cash_bank` VALUES ('2', '0', '2019-01-23 14:23:16', '2019-04-20 17:24:51', '0', '农业银行', '2', '38145801040010230', '青岛津海恒越商贸有限公司', '慈溪农商行横河支行营业部', '');
INSERT INTO `cash_bank` VALUES ('3', '1', '2019-02-04 11:29:36', '2019-02-04 11:29:36', '0', '浦发', '1', '212131113', '在这种', '吱吱吱吱吱吱吱吱', '');
INSERT INTO `cash_bank` VALUES ('10', '0', '2019-04-20 17:07:16', '2019-04-20 17:27:14', '0', '建设银行', '2', '44050140020400000882', '广州豪戴贸易有限公司', '广州东风东路支行', '');
INSERT INTO `cash_bank` VALUES ('17', '0', '2019-04-20 17:07:33', '2019-04-20 17:25:13', '0', '农业银行', '1', '38145801040010230', '青岛津海恒越商贸有限公司', '慈溪农商行横河支行营业部', '');

-- ----------------------------
-- Table structure for cash_hierarchy_bet
-- ----------------------------
DROP TABLE IF EXISTS `cash_hierarchy_bet`;
CREATE TABLE `cash_hierarchy_bet` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `hierarchy_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '所属层级ID',
  `bet` decimal(22,2) unsigned NOT NULL COMMENT '下注金币',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否展示',
  `service_fee` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '服务费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='用户打码管理';

-- ----------------------------
-- Records of cash_hierarchy_bet
-- ----------------------------

-- ----------------------------
-- Table structure for cash_price_config
-- ----------------------------
DROP TABLE IF EXISTS `cash_price_config`;
CREATE TABLE `cash_price_config` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `price` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '售价',
  `unit_price` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `type` int(1) DEFAULT '0' COMMENT '类型   0:充值 1：提现',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否展示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='现金价格预设配置表（提供给用户充值、提现的预设金额）';

-- ----------------------------
-- Records of cash_price_config
-- ----------------------------
INSERT INTO `cash_price_config` VALUES ('1', '0', '2019-02-18 10:39:42', '2019-02-18 10:39:42', '0', '100.00', '100.00', '0', '');
INSERT INTO `cash_price_config` VALUES ('2', '0', '2019-02-18 10:40:53', '2019-02-18 10:40:53', '0', '200.00', '200.00', '0', '');
INSERT INTO `cash_price_config` VALUES ('3', '0', '2019-02-18 10:41:01', '2019-02-18 10:41:01', '0', '300.00', '300.00', '0', '');
INSERT INTO `cash_price_config` VALUES ('4', '0', '2019-02-18 10:41:14', '2019-02-18 10:41:14', '0', '1000.00', '1000.00', '1', '');
INSERT INTO `cash_price_config` VALUES ('5', '0', '2019-02-18 10:41:26', '2019-02-18 10:41:26', '0', '200.00', '100.00', '1', '');

-- ----------------------------
-- Table structure for cash_recharge_discount_rule
-- ----------------------------
DROP TABLE IF EXISTS `cash_recharge_discount_rule`;
CREATE TABLE `cash_recharge_discount_rule` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `hierarchy_id` bigint(22) DEFAULT NULL COMMENT '所属层级ID',
  `recharge_amount` decimal(22,2) unsigned NOT NULL COMMENT '充值金额',
  `enable` bit(22) NOT NULL DEFAULT b'1' COMMENT '是否展示',
  `discount_amount` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
  `sys_dictionary_id` bigint(22) DEFAULT NULL COMMENT '充值方式id',
  `type` int(1) DEFAULT '0' COMMENT '0：现金赠送  1：比例赠送',
  PRIMARY KEY (`id`),
  KEY `index_hierarchy_id` (`hierarchy_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值优惠规则表';

-- ----------------------------
-- Records of cash_recharge_discount_rule
-- ----------------------------

-- ----------------------------
-- Table structure for cash_third_platform
-- ----------------------------
DROP TABLE IF EXISTS `cash_third_platform`;
CREATE TABLE `cash_third_platform` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `hierarchy_id` bigint(22) NOT NULL COMMENT '所属层级ID',
  `third_payment_name` varchar(50) NOT NULL COMMENT '第三方平台名称',
  `enable` bit(22) NOT NULL DEFAULT b'1' COMMENT '是否展示',
  `appid` varchar(100) NOT NULL COMMENT 'appid',
  `secret_key` varchar(255) NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方平台';

-- ----------------------------
-- Records of cash_third_platform
-- ----------------------------

-- ----------------------------
-- Table structure for game_config_url
-- ----------------------------
DROP TABLE IF EXISTS `game_config_url`;
CREATE TABLE `game_config_url` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT '' COMMENT '游戏路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='游戏路径表';

-- ----------------------------
-- Records of game_config_url
-- ----------------------------
INSERT INTO `game_config_url` VALUES ('1', '0', '0', '2019-03-12 14:23:47', '2019-03-12 16:55:39', 'http://103.64.13.240:12000/doudizhu/');

-- ----------------------------
-- Table structure for game_rebate_record
-- ----------------------------
DROP TABLE IF EXISTS `game_rebate_record`;
CREATE TABLE `game_rebate_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT '游戏类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '乐观锁版本号',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `game_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '游戏ID',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `rebate_coin` bigint(22) NOT NULL DEFAULT '0' COMMENT '返利金额',
  `rebate_time` date NOT NULL COMMENT '返利时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `record_uk` (`game_id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='游戏返利记录表';

-- ----------------------------
-- Records of game_rebate_record
-- ----------------------------

-- ----------------------------
-- Table structure for game_record
-- ----------------------------
DROP TABLE IF EXISTS `game_record`;
CREATE TABLE `game_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) DEFAULT NULL,
  `game_id` bigint(20) NOT NULL DEFAULT '0',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) DEFAULT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注总金币',
  `prize_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '中奖金币',
  `user_type` int(1) NOT NULL DEFAULT '0' COMMENT '用户类型',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `mini` bit(1) DEFAULT b'0' COMMENT '是否小游戏',
  `profit_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '代理商盈利金币',
  `water_profit` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '抽水金额',
  `exchange_rate` decimal(2,2) NOT NULL DEFAULT '0.00' COMMENT '房卡和金币的兑换比例',
  `water_rate` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '抽水比例',
  `round` int(9) NOT NULL COMMENT '局数',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `valid_bet` bigint(20) NOT NULL DEFAULT '0' COMMENT '有效下注',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  PRIMARY KEY (`id`),
  KEY `index_userid` (`user_id`),
  KEY `index_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48146 DEFAULT CHARSET=utf8 COMMENT='游戏记录';

-- ----------------------------
-- Records of game_record
-- ----------------------------
INSERT INTO `game_record` VALUES ('48135', '500526', 'qwe1234', '6', '百人炸金花', '3', '战神厅', '3', '百人房间', '10000000', '10000000', '0', '0', '0', '2019-04-21 22:01:22', '2019-04-21 22:01:22', '\0', '-10000000', '0.00', '0.00', '0.00', '0', '20190421220038-633-18905-3-2457', '10000000', '\0');
INSERT INTO `game_record` VALUES ('48142', '500526', 'qwe1234', '6', '百人炸金花', '3', '战神厅', '3', '百人房间', '20000000', '20000000', '0', '0', '0', '2019-04-21 22:02:16', '2019-04-21 22:02:16', '\0', '-20000000', '0.00', '0.00', '0.00', '0', '20190421220131-633-18905-999-2458', '20000000', '\0');

-- ----------------------------
-- Table structure for game_record_baijia
-- ----------------------------
DROP TABLE IF EXISTS `game_record_baijia`;
CREATE TABLE `game_record_baijia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `bet_area` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域',
  `bet_area_str` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域字符',
  `bet_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `prize_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '输赢金币',
  `banker_card` varchar(20) NOT NULL COMMENT '庄家牌型',
  `banker_card_str` varchar(20) NOT NULL COMMENT '庄家牌型字符',
  `idle_card` varchar(20) NOT NULL COMMENT '闲家牌型',
  `idle_card_str` varchar(20) NOT NULL COMMENT '闲家牌型字符',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1582 DEFAULT CHARSET=utf8 COMMENT='游戏记录-百家乐';

-- ----------------------------
-- Records of game_record_baijia
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_bairenniuniu
-- ----------------------------
DROP TABLE IF EXISTS `game_record_bairenniuniu`;
CREATE TABLE `game_record_bairenniuniu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `bet_area` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域',
  `bet_area_str` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域字符',
  `bet_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `prize_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '输赢金币',
  `banker_card` varchar(100) NOT NULL DEFAULT '' COMMENT '庄家牌型',
  `banker_card_str` varchar(100) NOT NULL DEFAULT '' COMMENT '庄家牌型字符',
  `idle_card` varchar(200) NOT NULL DEFAULT '' COMMENT '闲家牌型',
  `idle_card_str` varchar(200) NOT NULL DEFAULT '' COMMENT '闲家牌型字符',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3227 DEFAULT CHARSET=utf8 COMMENT='游戏记录-百人牛牛';

-- ----------------------------
-- Records of game_record_bairenniuniu
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_bairenpaijiu
-- ----------------------------
DROP TABLE IF EXISTS `game_record_bairenpaijiu`;
CREATE TABLE `game_record_bairenpaijiu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT '' COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT '' COMMENT '房间名称',
  `bet_area` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域',
  `bet_area_str` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域字符',
  `bet_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `prize_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '输赢金币',
  `banker_card` varchar(200) NOT NULL DEFAULT '' COMMENT '庄家牌型',
  `banker_card_str` varchar(200) NOT NULL DEFAULT '' COMMENT '庄家牌型字符',
  `idle_card` varchar(100) NOT NULL DEFAULT '' COMMENT '闲家牌型',
  `idle_card_str` varchar(100) NOT NULL DEFAULT '' COMMENT '闲家牌型字符',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=469 DEFAULT CHARSET=utf8 COMMENT='游戏记录-百人牌九';

-- ----------------------------
-- Records of game_record_bairenpaijiu
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_bairenzhajinhua
-- ----------------------------
DROP TABLE IF EXISTS `game_record_bairenzhajinhua`;
CREATE TABLE `game_record_bairenzhajinhua` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `bet_area` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域',
  `bet_area_str` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域字符',
  `bet_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `prize_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '输赢金币',
  `banker_card` varchar(100) NOT NULL DEFAULT '' COMMENT '庄家牌型',
  `banker_card_str` varchar(100) NOT NULL DEFAULT '' COMMENT '庄家牌型字符',
  `idle_card` varchar(200) NOT NULL DEFAULT '' COMMENT '闲家牌型',
  `idle_card_str` varchar(200) NOT NULL DEFAULT '' COMMENT '闲家牌型字符',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2436 DEFAULT CHARSET=utf8 COMMENT='游戏记录-诈金花';

-- ----------------------------
-- Records of game_record_bairenzhajinhua
-- ----------------------------
INSERT INTO `game_record_bairenzhajinhua` VALUES ('2432', '500526', 'qwe1234', '6', '百人炸金花', '3', '战神厅', '3', '百人房间', '0,20000000,0,0,0,0,0,0,0', '东:0,南:20000000,西:0,北:0,对子:0,顺子:0,金花:0,顺金:0,炸弹:0', '20000000', '20000000', '49,8,44,57,25', '黑桃A,方块8,红桃Q,黑桃9,梅花9', '东:22,12,42,10,41,;南:20,28,52,38,27,;西:13,53,43,37,34,;北:56,21,7,9,29,', '东:梅花6,方块Q,红桃10,方块10,红桃9,;南:梅花4,梅花Q,黑桃4,红桃6,梅花J,;西:方块K,黑桃5,红桃J,红桃5,红桃2,;北:黑桃8,梅花5,方块7,方块9,梅花K,', '15844000', '15844000', '\0', '20190421220131-633-18905-999-2458', '0', '0', '2019-04-21 22:02:16', '2019-04-21 22:02:16');

-- ----------------------------
-- Table structure for game_record_cattl_fangka
-- ----------------------------
DROP TABLE IF EXISTS `game_record_cattl_fangka`;
CREATE TABLE `game_record_cattl_fangka` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `game_type` int(9) NOT NULL COMMENT '玩法模式（1.抢庄模式、2.通比模式）',
  `pay_type` int(9) NOT NULL COMMENT '支付类型（1.房主支付、2.AA支付）',
  `base_score` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '底分',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币 ',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '赢输金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `cards_int` varchar(100) NOT NULL COMMENT '牌型',
  `cards_str` varchar(255) NOT NULL COMMENT '牌型',
  `bet_multiple` int(9) NOT NULL COMMENT '下注倍数',
  `brand_multiple` int(9) NOT NULL COMMENT '牌倍数',
  `multiple` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '倍数',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `round` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '局数',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`,`round`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=490 DEFAULT CHARSET=utf8 COMMENT='游戏记录-房卡牛牛';

-- ----------------------------
-- Records of game_record_cattl_fangka
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_cattl_qiangzhuang
-- ----------------------------
DROP TABLE IF EXISTS `game_record_cattl_qiangzhuang`;
CREATE TABLE `game_record_cattl_qiangzhuang` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `base_score` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '底分',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币 ',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '赢输金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `cards_int` varchar(100) NOT NULL COMMENT '牌型',
  `cards_str` varchar(255) NOT NULL COMMENT '牌型',
  `bet_multiple` int(9) NOT NULL COMMENT '下注倍数',
  `brand_multiple` int(9) NOT NULL COMMENT '牌倍数',
  `multiple` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '倍数',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `banker` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否庄家',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2702 DEFAULT CHARSET=utf8 COMMENT='游戏记录-抢庄牛牛';

-- ----------------------------
-- Records of game_record_cattl_qiangzhuang
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_cattl_tongbi
-- ----------------------------
DROP TABLE IF EXISTS `game_record_cattl_tongbi`;
CREATE TABLE `game_record_cattl_tongbi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `base_score` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '底分',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币 ',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '赢输金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `cards_int` varchar(100) NOT NULL COMMENT '牌型',
  `cards_str` varchar(255) NOT NULL COMMENT '牌型',
  `bet_multiple` int(9) NOT NULL COMMENT '下注倍数',
  `brand_multiple` int(9) NOT NULL COMMENT '牌倍数',
  `multiple` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '倍数',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2891 DEFAULT CHARSET=utf8 COMMENT='游戏记录-通比牛牛';

-- ----------------------------
-- Records of game_record_cattl_tongbi
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_daily
-- ----------------------------
DROP TABLE IF EXISTS `game_record_daily`;
CREATE TABLE `game_record_daily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `valid_bet` bigint(20) NOT NULL DEFAULT '0' COMMENT '有效下注',
  `prize_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '中奖金币',
  `water_profit` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '抽水金额',
  `profit_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '代理商盈利金币',
  `count_day` date DEFAULT NULL COMMENT '结算日期',
  `user_water_profit` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '会员返水',
  `user_water_rate` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '会员返水比例',
  `user_return` bit(1) DEFAULT b'0' COMMENT '是否计算返水',
  `agent_return` bit(1) DEFAULT b'0' COMMENT '代理商是否返佣',
  `abnormal` bit(1) DEFAULT b'0' COMMENT '用户返水是否异常',
  `abnormal_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '用户返水异常内容',
  `agent_abnormal` bit(1) DEFAULT b'0' COMMENT '代理商返佣是否异常',
  `agent_abnormal_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '代理商返佣异常内容',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_uk` (`user_id`,`count_day`),
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=777 DEFAULT CHARSET=utf8 COMMENT='每日会员游戏总下注记录';

-- ----------------------------
-- Records of game_record_daily
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_fightlandlords
-- ----------------------------
DROP TABLE IF EXISTS `game_record_fightlandlords`;
CREATE TABLE `game_record_fightlandlords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `base_score` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '底分',
  `coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '赢输金币',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `landlord` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否地主',
  `multiple` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '倍数',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1463 DEFAULT CHARSET=utf8 COMMENT='游戏记录-斗地主';

-- ----------------------------
-- Records of game_record_fightlandlords
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_longhu
-- ----------------------------
DROP TABLE IF EXISTS `game_record_longhu`;
CREATE TABLE `game_record_longhu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `bet_area` varchar(255) NOT NULL DEFAULT '',
  `bet_area_str` varchar(255) NOT NULL DEFAULT '' COMMENT '下注区域字符',
  `bet_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `prize_coins` bigint(11) DEFAULT '0' COMMENT '输赢金币',
  `open_card` int(20) NOT NULL DEFAULT '0' COMMENT '丢弃的牌',
  `open_card_str` varchar(20) NOT NULL COMMENT '丢弃的牌字符',
  `dragon_card` int(20) NOT NULL DEFAULT '0' COMMENT '龙牌型 ',
  `dragon_card_str` varchar(20) NOT NULL COMMENT '龙牌型字符',
  `tiger_card` int(20) NOT NULL DEFAULT '0' COMMENT '龙牌型',
  `tiger_card_str` varchar(20) NOT NULL COMMENT '龙牌型字符',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1316 DEFAULT CHARSET=utf8 COMMENT='游戏记录-龙虎斗';

-- ----------------------------
-- Records of game_record_longhu
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_pipeizhajinhua
-- ----------------------------
DROP TABLE IF EXISTS `game_record_pipeizhajinhua`;
CREATE TABLE `game_record_pipeizhajinhua` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `base_score` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '底分',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币 ',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '赢输金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `cards_int` varchar(100) NOT NULL COMMENT '牌型',
  `cards_str` varchar(255) NOT NULL COMMENT '牌型',
  `multiple` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '倍数',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2380 DEFAULT CHARSET=utf8 COMMENT='游戏记录-匹配炸金花';

-- ----------------------------
-- Records of game_record_pipeizhajinhua
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_shisanshui
-- ----------------------------
DROP TABLE IF EXISTS `game_record_shisanshui`;
CREATE TABLE `game_record_shisanshui` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `base_score` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '底分',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币 ',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '赢输金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `head_cards_int` varchar(100) NOT NULL COMMENT '牌型',
  `head_cards_str` varchar(255) NOT NULL COMMENT '牌型',
  `middle_cards_int` varchar(100) NOT NULL COMMENT '中墩牌型',
  `middle_cards_str` varchar(255) NOT NULL COMMENT '中墩牌型',
  `bottom_cards_int` varchar(100) NOT NULL COMMENT '尾墩牌型',
  `bottom_cards_str` varchar(255) NOT NULL COMMENT '尾墩牌型',
  `multiple` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '倍数',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10850 DEFAULT CHARSET=utf8 COMMENT='游戏记录-十三水';

-- ----------------------------
-- Records of game_record_shisanshui
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_shisanshui_fangka
-- ----------------------------
DROP TABLE IF EXISTS `game_record_shisanshui_fangka`;
CREATE TABLE `game_record_shisanshui_fangka` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `game_type` int(9) NOT NULL DEFAULT '0' COMMENT '玩法模式',
  `pay_type` int(9) NOT NULL DEFAULT '0' COMMENT '支付类型 1.房主支付 2.AA支付',
  `base_score` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '底分',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币 ',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '赢输金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `head_cards_int` varchar(100) NOT NULL COMMENT '牌型',
  `head_cards_str` varchar(255) NOT NULL COMMENT '牌型',
  `middle_cards_int` varchar(100) NOT NULL COMMENT '中墩牌型',
  `middle_cards_str` varchar(255) NOT NULL COMMENT '中墩牌型',
  `bottom_cards_int` varchar(100) NOT NULL COMMENT '尾墩牌型',
  `bottom_cards_str` varchar(255) NOT NULL COMMENT '尾墩牌型',
  `multiple` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '倍数',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `round` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '局数',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`,`round`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=525 DEFAULT CHARSET=utf8 COMMENT='游戏记录-房卡十三水';

-- ----------------------------
-- Records of game_record_shisanshui_fangka
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_twoeightbar
-- ----------------------------
DROP TABLE IF EXISTS `game_record_twoeightbar`;
CREATE TABLE `game_record_twoeightbar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) DEFAULT NULL COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) DEFAULT NULL COMMENT '房间名称',
  `base_score` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '底分',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币 ',
  `bet_coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注金币',
  `coins` bigint(20) NOT NULL DEFAULT '0' COMMENT '赢输金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `cards_int` varchar(100) NOT NULL COMMENT '牌型',
  `cards_str` varchar(255) NOT NULL COMMENT '牌型',
  `bet_multiple` int(9) NOT NULL COMMENT '下注倍数',
  `brand_multiple` int(9) NOT NULL COMMENT '牌倍数',
  `multiple` int(9) unsigned NOT NULL DEFAULT '0' COMMENT '倍数',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `banker` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否庄家',
  `game_round_no` varchar(255) NOT NULL DEFAULT '' COMMENT '游戏局号',
  `round` int(3) unsigned NOT NULL DEFAULT '0' COMMENT '局数',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`,`round`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3857 DEFAULT CHARSET=utf8 COMMENT='游戏记录-二八杠';

-- ----------------------------
-- Records of game_record_twoeightbar
-- ----------------------------

-- ----------------------------
-- Table structure for game_record_undersea_treasure
-- ----------------------------
DROP TABLE IF EXISTS `game_record_undersea_treasure`;
CREATE TABLE `game_record_undersea_treasure` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `game_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏id',
  `game_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '游戏名称',
  `grade_id` bigint(11) NOT NULL COMMENT '场次id',
  `grade_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '场次名称',
  `room_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '房间id',
  `room_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '房间名称',
  `pow` bigint(11) NOT NULL COMMENT '中奖倍数',
  `bet_lines` bigint(11) NOT NULL COMMENT '压线数',
  `lines` varchar(255) NOT NULL DEFAULT '' COMMENT '中奖线情况',
  `scenes` varchar(255) NOT NULL DEFAULT '' COMMENT '图标情况',
  `bcoins` bigint(11) NOT NULL DEFAULT '0' COMMENT '底分',
  `bet_coins` bigint(11) NOT NULL,
  `prize_coins` bigint(11) NOT NULL DEFAULT '0' COMMENT '输赢金币',
  `coins_before` bigint(20) NOT NULL DEFAULT '0' COMMENT '下注前金币',
  `coins_after` bigint(20) NOT NULL DEFAULT '0' COMMENT '结束后金币',
  `robot` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否机器人',
  `game_round_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '游戏局号',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态（假删除）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`game_id`,`grade_id`,`room_id`,`game_round_no`,`user_id`) USING BTREE,
  KEY `index_userid` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12880 DEFAULT CHARSET=utf8 COMMENT='游戏记录-海底宝藏';

-- ----------------------------
-- Records of game_record_undersea_treasure
-- ----------------------------

-- ----------------------------
-- Table structure for hierarchy_game_role
-- ----------------------------
DROP TABLE IF EXISTS `hierarchy_game_role`;
CREATE TABLE `hierarchy_game_role` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `hierarchy_id` bigint(22) NOT NULL COMMENT '层级ID',
  `game_id` bigint(22) NOT NULL COMMENT '游戏ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `hierarchy_id` (`hierarchy_id`,`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1211 DEFAULT CHARSET=utf8 COMMENT='层级游戏权限关联表';

-- ----------------------------
-- Records of hierarchy_game_role
-- ----------------------------
INSERT INTO `hierarchy_game_role` VALUES ('1', '0', '2019-03-15 15:35:41', '2019-03-15 15:35:41', '0', '25', '1');
INSERT INTO `hierarchy_game_role` VALUES ('2', '0', '2019-03-15 15:35:41', '2019-03-15 15:35:41', '0', '25', '2');
INSERT INTO `hierarchy_game_role` VALUES ('3', '0', '2019-03-15 15:35:41', '2019-03-15 15:35:41', '0', '25', '3');
INSERT INTO `hierarchy_game_role` VALUES ('4', '0', '2019-03-15 15:35:41', '2019-03-15 15:35:41', '0', '25', '7');
INSERT INTO `hierarchy_game_role` VALUES ('5', '0', '2019-03-15 15:35:41', '2019-03-15 15:35:41', '0', '25', '8');
INSERT INTO `hierarchy_game_role` VALUES ('6', '0', '2019-03-15 15:35:41', '2019-03-15 15:35:41', '0', '25', '9');
INSERT INTO `hierarchy_game_role` VALUES ('7', '0', '2019-03-15 15:35:41', '2019-03-15 15:35:41', '0', '25', '10');
INSERT INTO `hierarchy_game_role` VALUES ('8', '0', '2019-03-15 15:35:41', '2019-03-15 15:35:41', '0', '25', '4');
INSERT INTO `hierarchy_game_role` VALUES ('9', '0', '2019-03-15 15:35:41', '2019-03-15 15:35:41', '0', '25', '5');
INSERT INTO `hierarchy_game_role` VALUES ('10', '0', '2019-03-15 15:35:41', '2019-03-15 15:35:41', '0', '25', '6');
INSERT INTO `hierarchy_game_role` VALUES ('11', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '1');
INSERT INTO `hierarchy_game_role` VALUES ('12', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '2');
INSERT INTO `hierarchy_game_role` VALUES ('13', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '3');
INSERT INTO `hierarchy_game_role` VALUES ('14', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '7');
INSERT INTO `hierarchy_game_role` VALUES ('15', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '8');
INSERT INTO `hierarchy_game_role` VALUES ('16', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '11');
INSERT INTO `hierarchy_game_role` VALUES ('17', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '9');
INSERT INTO `hierarchy_game_role` VALUES ('18', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '10');
INSERT INTO `hierarchy_game_role` VALUES ('19', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '4');
INSERT INTO `hierarchy_game_role` VALUES ('20', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '5');
INSERT INTO `hierarchy_game_role` VALUES ('21', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '6');
INSERT INTO `hierarchy_game_role` VALUES ('22', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '12');
INSERT INTO `hierarchy_game_role` VALUES ('23', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '13');
INSERT INTO `hierarchy_game_role` VALUES ('24', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '14');
INSERT INTO `hierarchy_game_role` VALUES ('25', '0', '2019-04-04 15:11:25', '2019-04-04 15:11:25', '0', '1', '201');
INSERT INTO `hierarchy_game_role` VALUES ('59', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '1');
INSERT INTO `hierarchy_game_role` VALUES ('66', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '2');
INSERT INTO `hierarchy_game_role` VALUES ('73', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '3');
INSERT INTO `hierarchy_game_role` VALUES ('80', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '7');
INSERT INTO `hierarchy_game_role` VALUES ('87', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '8');
INSERT INTO `hierarchy_game_role` VALUES ('94', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '11');
INSERT INTO `hierarchy_game_role` VALUES ('101', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '9');
INSERT INTO `hierarchy_game_role` VALUES ('108', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '10');
INSERT INTO `hierarchy_game_role` VALUES ('115', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '4');
INSERT INTO `hierarchy_game_role` VALUES ('122', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '5');
INSERT INTO `hierarchy_game_role` VALUES ('129', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '6');
INSERT INTO `hierarchy_game_role` VALUES ('136', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '12');
INSERT INTO `hierarchy_game_role` VALUES ('143', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '13');
INSERT INTO `hierarchy_game_role` VALUES ('150', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '14');
INSERT INTO `hierarchy_game_role` VALUES ('157', '0', '2019-04-20 18:40:51', '2019-04-20 18:40:51', '0', '2', '201');
INSERT INTO `hierarchy_game_role` VALUES ('164', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '1');
INSERT INTO `hierarchy_game_role` VALUES ('171', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '2');
INSERT INTO `hierarchy_game_role` VALUES ('178', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '3');
INSERT INTO `hierarchy_game_role` VALUES ('185', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '7');
INSERT INTO `hierarchy_game_role` VALUES ('192', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '8');
INSERT INTO `hierarchy_game_role` VALUES ('199', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '11');
INSERT INTO `hierarchy_game_role` VALUES ('206', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '9');
INSERT INTO `hierarchy_game_role` VALUES ('213', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '10');
INSERT INTO `hierarchy_game_role` VALUES ('220', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '4');
INSERT INTO `hierarchy_game_role` VALUES ('227', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '5');
INSERT INTO `hierarchy_game_role` VALUES ('234', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '6');
INSERT INTO `hierarchy_game_role` VALUES ('241', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '12');
INSERT INTO `hierarchy_game_role` VALUES ('248', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '13');
INSERT INTO `hierarchy_game_role` VALUES ('255', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '14');
INSERT INTO `hierarchy_game_role` VALUES ('262', '0', '2019-04-20 18:41:12', '2019-04-20 18:41:12', '0', '3', '201');
INSERT INTO `hierarchy_game_role` VALUES ('374', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '1');
INSERT INTO `hierarchy_game_role` VALUES ('381', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '2');
INSERT INTO `hierarchy_game_role` VALUES ('388', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '3');
INSERT INTO `hierarchy_game_role` VALUES ('395', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '7');
INSERT INTO `hierarchy_game_role` VALUES ('402', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '8');
INSERT INTO `hierarchy_game_role` VALUES ('409', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '11');
INSERT INTO `hierarchy_game_role` VALUES ('416', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '9');
INSERT INTO `hierarchy_game_role` VALUES ('423', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '10');
INSERT INTO `hierarchy_game_role` VALUES ('430', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '4');
INSERT INTO `hierarchy_game_role` VALUES ('437', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '5');
INSERT INTO `hierarchy_game_role` VALUES ('444', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '6');
INSERT INTO `hierarchy_game_role` VALUES ('451', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '12');
INSERT INTO `hierarchy_game_role` VALUES ('458', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '13');
INSERT INTO `hierarchy_game_role` VALUES ('465', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '14');
INSERT INTO `hierarchy_game_role` VALUES ('472', '0', '2019-04-20 18:42:02', '2019-04-20 18:42:02', '0', '5', '201');
INSERT INTO `hierarchy_game_role` VALUES ('479', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '1');
INSERT INTO `hierarchy_game_role` VALUES ('486', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '2');
INSERT INTO `hierarchy_game_role` VALUES ('493', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '3');
INSERT INTO `hierarchy_game_role` VALUES ('500', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '7');
INSERT INTO `hierarchy_game_role` VALUES ('507', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '8');
INSERT INTO `hierarchy_game_role` VALUES ('514', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '11');
INSERT INTO `hierarchy_game_role` VALUES ('521', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '9');
INSERT INTO `hierarchy_game_role` VALUES ('528', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '10');
INSERT INTO `hierarchy_game_role` VALUES ('535', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '4');
INSERT INTO `hierarchy_game_role` VALUES ('542', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '5');
INSERT INTO `hierarchy_game_role` VALUES ('549', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '6');
INSERT INTO `hierarchy_game_role` VALUES ('556', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '12');
INSERT INTO `hierarchy_game_role` VALUES ('563', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '13');
INSERT INTO `hierarchy_game_role` VALUES ('570', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '14');
INSERT INTO `hierarchy_game_role` VALUES ('577', '0', '2019-04-20 18:42:21', '2019-04-20 18:42:21', '0', '6', '201');
INSERT INTO `hierarchy_game_role` VALUES ('584', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '1');
INSERT INTO `hierarchy_game_role` VALUES ('591', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '2');
INSERT INTO `hierarchy_game_role` VALUES ('598', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '3');
INSERT INTO `hierarchy_game_role` VALUES ('605', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '7');
INSERT INTO `hierarchy_game_role` VALUES ('612', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '8');
INSERT INTO `hierarchy_game_role` VALUES ('619', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '11');
INSERT INTO `hierarchy_game_role` VALUES ('626', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '9');
INSERT INTO `hierarchy_game_role` VALUES ('633', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '10');
INSERT INTO `hierarchy_game_role` VALUES ('640', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '4');
INSERT INTO `hierarchy_game_role` VALUES ('647', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '5');
INSERT INTO `hierarchy_game_role` VALUES ('654', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '6');
INSERT INTO `hierarchy_game_role` VALUES ('661', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '12');
INSERT INTO `hierarchy_game_role` VALUES ('668', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '13');
INSERT INTO `hierarchy_game_role` VALUES ('675', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '14');
INSERT INTO `hierarchy_game_role` VALUES ('682', '0', '2019-04-20 18:43:31', '2019-04-20 18:43:31', '0', '7', '201');
INSERT INTO `hierarchy_game_role` VALUES ('1109', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '1');
INSERT INTO `hierarchy_game_role` VALUES ('1116', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '2');
INSERT INTO `hierarchy_game_role` VALUES ('1123', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '3');
INSERT INTO `hierarchy_game_role` VALUES ('1130', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '7');
INSERT INTO `hierarchy_game_role` VALUES ('1137', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '8');
INSERT INTO `hierarchy_game_role` VALUES ('1144', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '11');
INSERT INTO `hierarchy_game_role` VALUES ('1151', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '9');
INSERT INTO `hierarchy_game_role` VALUES ('1158', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '10');
INSERT INTO `hierarchy_game_role` VALUES ('1165', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '4');
INSERT INTO `hierarchy_game_role` VALUES ('1172', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '5');
INSERT INTO `hierarchy_game_role` VALUES ('1179', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '6');
INSERT INTO `hierarchy_game_role` VALUES ('1186', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '12');
INSERT INTO `hierarchy_game_role` VALUES ('1193', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '13');
INSERT INTO `hierarchy_game_role` VALUES ('1200', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '14');
INSERT INTO `hierarchy_game_role` VALUES ('1207', '0', '2019-04-20 18:44:29', '2019-04-20 18:44:29', '0', '8', '201');

-- ----------------------------
-- Table structure for ip_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `ip_blacklist`;
CREATE TABLE `ip_blacklist` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `ip` varchar(50) NOT NULL DEFAULT '' COMMENT 'ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ip黑名单';

-- ----------------------------
-- Records of ip_blacklist
-- ----------------------------

-- ----------------------------
-- Table structure for order_administrator_recharge
-- ----------------------------
DROP TABLE IF EXISTS `order_administrator_recharge`;
CREATE TABLE `order_administrator_recharge` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `order_no` varchar(22) DEFAULT '' COMMENT '订单号',
  `sys_user_account` varchar(255) DEFAULT '' COMMENT '操作人用户名',
  `sys_user_id` bigint(20) DEFAULT NULL COMMENT '操作人id',
  `amount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '金额',
  `remake` varchar(500) DEFAULT '',
  `account` varchar(500) DEFAULT '' COMMENT '支付人账号',
  `hierarchy_id` varchar(255) DEFAULT '' COMMENT '层级id集合',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_uk` (`order_no`) USING BTREE,
  KEY `index_account` (`account`) USING BTREE,
  KEY `index_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8 COMMENT='人工充值';

-- ----------------------------
-- Records of order_administrator_recharge
-- ----------------------------

-- ----------------------------
-- Table structure for order_bank_recharge
-- ----------------------------
DROP TABLE IF EXISTS `order_bank_recharge`;
CREATE TABLE `order_bank_recharge` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) NOT NULL COMMENT '存款人',
  `deposit_date` datetime NOT NULL COMMENT '存款时间',
  `deposit_amount` decimal(22,2) unsigned NOT NULL COMMENT '存款金额',
  `deposit_blank` varchar(255) DEFAULT NULL COMMENT '存款银行',
  `income_bank` varchar(255) NOT NULL COMMENT '收款银行',
  `bank_account` varchar(255) NOT NULL COMMENT '收款账号',
  `payee` varchar(255) NOT NULL COMMENT '收款人',
  `open_bank` varchar(500) DEFAULT '' COMMENT '开户行网点',
  `status` varchar(255) NOT NULL COMMENT '订单状态',
  `deposit_type` varchar(255) DEFAULT NULL COMMENT '存款方式',
  `user_id` bigint(20) NOT NULL,
  `discount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '优惠金额',
  `examine` bit(1) DEFAULT b'0' COMMENT '是否审核',
  `recharge_date` datetime DEFAULT NULL COMMENT '充值时间',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行卡充值';

-- ----------------------------
-- Records of order_bank_recharge
-- ----------------------------

-- ----------------------------
-- Table structure for order_cash_examine
-- ----------------------------
DROP TABLE IF EXISTS `order_cash_examine`;
CREATE TABLE `order_cash_examine` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `recharge_amount` bigint(22) unsigned NOT NULL DEFAULT '0' COMMENT '存款金额',
  `hierarchy_discount_amount` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '等级优惠金额(存款金额*等级优惠比例)',
  `hierarchy_discount_multiple` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '等级优惠打码倍数',
  `hierarchy_discount_bet` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '等级优惠打码(等级优惠金额*等级优惠打码倍数)',
  `hierarchy_relaxation_rate` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '等级放宽比例',
  `hierarchy_relaxation_bet` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '等级放宽打码(等级正常打码数+等级优惠打码数)*等级放宽比例)',
  `hierarchy_normal_multiple` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '等级常态打码倍数',
  `hierarchy_normal_bet` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '等级常态打码(存款金额*等级常态打码倍数)',
  `user_need_bet` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '总需要打码数(等级正常打码数+等级优惠打码数+上次剩余打码-等级放宽打码)',
  `user_valid_bet` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '当前用户有效打码',
  `deduction_need_bet` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '用户取款操作减少的需要打码',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否结算（0：未审核，1：已审核）',
  `hierarchy_administrative_rate` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '等级行政费比例',
  `hierarchy_administrative_amount` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '等级行政费(用户总余额*用户等级行政费比例)',
  `deduction_administrative` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '已经被扣的行政费用(行政费被扣后总需要打码就要清0了，意味着扣过钱了剩余的余额就不用扣钱了)',
  `user_id` bigint(22) NOT NULL DEFAULT '0',
  `user_account` varchar(50) NOT NULL DEFAULT '' COMMENT '用户账号',
  `order_no` varchar(50) NOT NULL DEFAULT '' COMMENT '充值订单号',
  `order_time` datetime NOT NULL,
  `deduction_valid_bet` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '用户取款操作减少的有效打码',
  `user_money` decimal(22,2) DEFAULT '0.00' COMMENT '用户余额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`) USING BTREE,
  KEY `index_user_id` (`user_id`) USING BTREE,
  KEY `index_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1771 DEFAULT CHARSET=utf8 COMMENT='取款稽查表';

-- ----------------------------
-- Records of order_cash_examine
-- ----------------------------

-- ----------------------------
-- Table structure for order_preferential
-- ----------------------------
DROP TABLE IF EXISTS `order_preferential`;
CREATE TABLE `order_preferential` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `first_recharge` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否首充 0:满送1:首冲',
  `recharge_amount` bigint(22) NOT NULL DEFAULT '0' COMMENT '充值金额',
  `gift_proportion` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '返利比例',
  `hierarchy_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '层级id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_firstrecharge_hierarchy` (`first_recharge`,`hierarchy_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='充值优惠';

-- ----------------------------
-- Records of order_preferential
-- ----------------------------
INSERT INTO `order_preferential` VALUES ('1', '0', '2019-01-29 09:54:29', '2019-02-28 23:07:19', '0', '', '100', '0.01', '1');
INSERT INTO `order_preferential` VALUES ('2', '0', '2019-01-29 09:54:29', '2019-01-29 09:54:29', '0', '\0', '0', '0.01', '1');
INSERT INTO `order_preferential` VALUES ('3', '0', '2019-01-29 09:55:22', '2019-01-29 09:55:22', '0', '', '0', '0.01', '2');
INSERT INTO `order_preferential` VALUES ('4', '0', '2019-01-29 09:55:22', '2019-01-29 09:55:22', '0', '\0', '0', '0.01', '2');
INSERT INTO `order_preferential` VALUES ('5', '0', '2019-01-29 09:55:22', '2019-02-28 23:07:32', '0', '', '100', '0.01', '3');
INSERT INTO `order_preferential` VALUES ('6', '0', '2019-01-29 09:55:22', '2019-01-29 09:55:22', '0', '\0', '0', '0.01', '3');
INSERT INTO `order_preferential` VALUES ('7', '0', '2019-01-29 09:55:22', '2019-01-29 09:55:22', '0', '', '0', '0.01', '4');
INSERT INTO `order_preferential` VALUES ('8', '0', '2019-01-29 09:55:22', '2019-01-29 09:55:22', '0', '\0', '0', '0.01', '4');
INSERT INTO `order_preferential` VALUES ('9', '0', '2019-01-29 09:55:22', '2019-01-29 09:55:22', '0', '', '0', '0.01', '5');
INSERT INTO `order_preferential` VALUES ('10', '0', '2019-01-29 09:55:22', '2019-01-29 09:55:22', '0', '\0', '0', '0.01', '5');
INSERT INTO `order_preferential` VALUES ('11', '0', '2019-01-29 09:55:22', '2019-01-29 09:55:22', '0', '', '0', '0.01', '6');
INSERT INTO `order_preferential` VALUES ('12', '0', '2019-01-29 09:55:22', '2019-01-29 09:55:22', '0', '\0', '0', '0.01', '6');
INSERT INTO `order_preferential` VALUES ('13', '0', '2019-02-20 14:51:29', '2019-02-20 14:51:29', '0', '', '0', '0.00', '7');
INSERT INTO `order_preferential` VALUES ('14', '0', '2019-02-20 14:51:29', '2019-02-20 14:51:29', '0', '\0', '0', '0.00', '7');
INSERT INTO `order_preferential` VALUES ('15', '0', '2019-02-20 14:51:29', '2019-02-20 14:51:29', '0', '', '0', '0.00', '8');
INSERT INTO `order_preferential` VALUES ('16', '0', '2019-02-20 14:51:29', '2019-02-20 14:51:29', '0', '\0', '0', '0.00', '8');

-- ----------------------------
-- Table structure for order_recharge
-- ----------------------------
DROP TABLE IF EXISTS `order_recharge`;
CREATE TABLE `order_recharge` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `order_no` varchar(22) NOT NULL DEFAULT '' COMMENT '订单号',
  `admin_order_no` varchar(22) DEFAULT '' COMMENT '人工充值主订单号',
  `sys_user_account` varchar(20) DEFAULT '' COMMENT '操作人用户名',
  `sys_user_id` bigint(20) DEFAULT NULL COMMENT '操作人id',
  `amount` bigint(22) unsigned DEFAULT '0' COMMENT '金额',
  `user_need_bet` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '总需要打码数',
  `hierarchy_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '层级id集合',
  `deposit_name` varchar(255) DEFAULT NULL COMMENT '存款人(表单上用户填的存款人)',
  `deposit_date` datetime DEFAULT NULL COMMENT '存款时间',
  `deposit_type` int(2) DEFAULT '0' COMMENT '银行充值类型:0, "网银转账"，1, "ATM转账" 2, "银行柜台转账"3....',
  `deposit_bank` varchar(255) DEFAULT NULL COMMENT '存款银行',
  `income_bank` varchar(255) DEFAULT NULL COMMENT '收款银行',
  `deposit_bank_account` varchar(255) DEFAULT '' COMMENT '存款银行账号',
  `income_bank_account` varchar(255) DEFAULT NULL COMMENT '收款账号',
  `payee` varchar(255) DEFAULT NULL COMMENT '收款人',
  `open_bank` varchar(500) DEFAULT '' COMMENT '开户行网点',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '订单状态0：未确认，1:取消 2：完成',
  `user_id` bigint(20) DEFAULT '0' COMMENT '支付人id',
  `user_account` varchar(20) DEFAULT '' COMMENT '支付人账号',
  `recharge_time` datetime DEFAULT NULL COMMENT '充值时间(系统给用户账号加钱的时间)',
  `fristrecharge` bit(1) DEFAULT b'0' COMMENT '是否是首充（0：不是，1：是）',
  `merchant_order_no` varchar(255) DEFAULT '' COMMENT '第三方支付平台订单号',
  `recharge_platform` varchar(255) DEFAULT '' COMMENT '第三方支付平台',
  `recharge_type` int(1) NOT NULL DEFAULT '0' COMMENT '1:后台人工充值,2:第三方支付,3:线下银行卡打款',
  `recharge_terminal` varchar(22) DEFAULT '' COMMENT '充值终端WEB,PC',
  `pre_money` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '充值前主账户金额',
  `discount_id` bigint(22) DEFAULT '0' COMMENT '优惠id',
  `discount_amount` decimal(22,2) DEFAULT '0.00' COMMENT '优惠金额',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `ip` varchar(50) DEFAULT '' COMMENT '充值请求ip',
  `final_money` decimal(22,2) DEFAULT '0.00' COMMENT '充值后余额',
  `recharge_channel` bigint(2) DEFAULT '0' COMMENT '充值渠道',
  `unwithdraw` decimal(20,2) DEFAULT '0.00' COMMENT '未撤回的金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_order_no` (`order_no`) USING BTREE,
  KEY `index_user_account` (`user_account`) USING BTREE,
  KEY `index_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2366 DEFAULT CHARSET=utf8 COMMENT='充值订单表';

-- ----------------------------
-- Records of order_recharge
-- ----------------------------

-- ----------------------------
-- Table structure for order_take_money
-- ----------------------------
DROP TABLE IF EXISTS `order_take_money`;
CREATE TABLE `order_take_money` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '取款用户id',
  `user_account` varchar(255) DEFAULT '' COMMENT '用户账号',
  `account` varchar(255) NOT NULL DEFAULT '' COMMENT '取款账号',
  `take_amount` bigint(22) unsigned NOT NULL DEFAULT '0' COMMENT '取款金额',
  `poundage` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '手续费',
  `obtain_amount` decimal(22,2) NOT NULL DEFAULT '0.00' COMMENT '实际得到的金额',
  `income_no` varchar(255) NOT NULL DEFAULT '' COMMENT '收入账号',
  `bank_name` varchar(255) DEFAULT '' COMMENT '银行名称',
  `account_name` varchar(255) NOT NULL DEFAULT '' COMMENT '开户人',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态(0:未审核，1：失败，2.完成)',
  `order_examine_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '取款审核ID',
  `account_type` int(11) NOT NULL DEFAULT '0' COMMENT '取款类型(0：支付宝转账，1:银行转账，2：微信)',
  `sys_user_id` bigint(22) DEFAULT '0' COMMENT '操作管理员id',
  `sys_user_account` varchar(255) DEFAULT '' COMMENT '操作用户账号',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `type` int(11) DEFAULT '0' COMMENT '0:账号取现。1：佣金取现',
  `order_no` varchar(55) NOT NULL DEFAULT '' COMMENT '取款订单号',
  `bet_cancel` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否取消打码',
  PRIMARY KEY (`id`),
  KEY `index_user_account` (`user_account`) USING BTREE,
  KEY `index_create_time` (`create_time`) USING BTREE,
  KEY `uk_order_no` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=469 DEFAULT CHARSET=utf8 COMMENT='取款记录表';

-- ----------------------------
-- Records of order_take_money
-- ----------------------------

-- ----------------------------
-- Table structure for order_third_recharge
-- ----------------------------
DROP TABLE IF EXISTS `order_third_recharge`;
CREATE TABLE `order_third_recharge` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `account` varchar(255) NOT NULL DEFAULT '' COMMENT '支付人账号',
  `user_id` bigint(20) DEFAULT '0' COMMENT '支付人Id',
  `amount` decimal(22,2) unsigned DEFAULT NULL COMMENT '金额',
  `remake` varchar(500) DEFAULT '',
  `order_no` varchar(255) DEFAULT '' COMMENT '订单ID',
  `merchant_order_no` varchar(255) DEFAULT '' COMMENT '支付方订单号',
  `platform` varchar(255) DEFAULT '' COMMENT '支付平台',
  `type` varchar(22) DEFAULT NULL COMMENT '属性(app,web)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_no`) USING BTREE,
  UNIQUE KEY `merchant_order_id` (`merchant_order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付订单表';

-- ----------------------------
-- Records of order_third_recharge
-- ----------------------------

-- ----------------------------
-- Table structure for pay_channel_config
-- ----------------------------
DROP TABLE IF EXISTS `pay_channel_config`;
CREATE TABLE `pay_channel_config` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `pay_id` bigint(22) NOT NULL COMMENT 'WEB图标ID',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态(1.启用，0.禁用)',
  `channel_id` bigint(22) NOT NULL COMMENT 'WEB图标ID',
  `high_limit` bigint(22) NOT NULL DEFAULT '0' COMMENT '限制最高支付金额',
  `low_limit` bigint(22) NOT NULL DEFAULT '0' COMMENT '限制最低支付金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pay` (`delete_status`,`pay_id`,`channel_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='支付渠道配置';

-- ----------------------------
-- Records of pay_channel_config
-- ----------------------------
INSERT INTO `pay_channel_config` VALUES ('1', '0', '2019-04-10 16:43:43', '2019-04-20 23:19:25', '0', '1', '', '12', '3000', '50');
INSERT INTO `pay_channel_config` VALUES ('2', '0', '2019-04-10 17:13:29', '2019-04-20 23:18:38', '0', '2', '', '12', '3000', '10');
INSERT INTO `pay_channel_config` VALUES ('3', '0', '2019-04-20 16:04:29', '2019-04-20 23:19:45', '0', '1', '', '13', '3000', '50');
INSERT INTO `pay_channel_config` VALUES ('17', '0', '2019-04-20 16:05:18', '2019-04-20 23:18:58', '0', '2', '', '13', '3000', '10');

-- ----------------------------
-- Table structure for pay_config
-- ----------------------------
DROP TABLE IF EXISTS `pay_config`;
CREATE TABLE `pay_config` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `payment_method` varchar(255) NOT NULL DEFAULT '' COMMENT '支付方式',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态(1.启用，0.禁用)',
  `first_push` bit(1) DEFAULT b'0' COMMENT '是否首推',
  `payment_method_name` varchar(255) NOT NULL DEFAULT '' COMMENT '支付方式',
  `app_icon_id` bigint(22) DEFAULT NULL COMMENT 'aAPP图标ID',
  `app_icon_md5` varchar(255) DEFAULT NULL COMMENT 'APPMD5',
  `web_icon_id` bigint(22) DEFAULT NULL COMMENT 'WEB图标ID',
  `web_icon_md5` varchar(255) DEFAULT NULL COMMENT 'WEBMD5',
  `enclosure_id` bigint(22) DEFAULT NULL COMMENT 'WEB图标ID',
  `enclosure_md5` varchar(255) DEFAULT NULL COMMENT 'WEBMD5',
  `alias_name` varchar(255) DEFAULT '' COMMENT '别名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='支付渠道配置';

-- ----------------------------
-- Records of pay_config
-- ----------------------------
INSERT INTO `pay_config` VALUES ('1', '0', '2019-03-25 16:13:34', '2019-04-20 23:20:19', '2', '鼎盛支付', '12,13', '', '\0', '支付宝,微信', '390', '57658dc5a90a11a6db89fc999f20c524', '390', '57658dc5a90a11a6db89fc999f20c524', '391', '946056374f6fa826ddd07da4cc632703', '支付通');
INSERT INTO `pay_config` VALUES ('2', '0', '2019-03-25 17:05:12', '2019-04-20 23:20:39', '1', '柒柒支付', '12,13', '', '', '支付宝,微信', '390', null, '390', null, '390', null, '七七');
INSERT INTO `pay_config` VALUES ('3', '1', '2019-04-18 14:21:09', '2019-04-18 14:21:09', '0', '窝窝扫码', '13', '', '\0', '微信', '780', '242d687ae0c03a91e711c3ef3aeeee35', '787', 'f0b29ade8a6c663ee5dc5b62857cb1b3', '794', '6dbfd96546e127e1d474c0b87611a519', '扫码支付');

-- ----------------------------
-- Table structure for pool_dispatch_detail_list
-- ----------------------------
DROP TABLE IF EXISTS `pool_dispatch_detail_list`;
CREATE TABLE `pool_dispatch_detail_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(22) NOT NULL COMMENT '用户ID',
  `user_account` varchar(100) DEFAULT '' COMMENT '用户账号',
  `task_id` bigint(20) NOT NULL COMMENT '任务id',
  `task_title` varchar(255) DEFAULT '' COMMENT '派奖任务标题',
  `task_detail_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '派奖奖项id',
  `detail_title` varchar(255) DEFAULT NULL COMMENT '派奖明细标题',
  `amount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '金额',
  `dispatch_time` datetime DEFAULT NULL COMMENT '派发时间',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `order_no` varchar(22) DEFAULT '' COMMENT '订单号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userid_detailid_uk` (`user_id`,`task_id`,`task_detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='派奖明细记录表';

-- ----------------------------
-- Records of pool_dispatch_detail_list
-- ----------------------------

-- ----------------------------
-- Table structure for pool_dispatch_task
-- ----------------------------
DROP TABLE IF EXISTS `pool_dispatch_task`;
CREATE TABLE `pool_dispatch_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `amount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '总金额',
  `pool_game_id` bigint(22) DEFAULT '0' COMMENT '奖池id',
  `ranking_list_id` bigint(22) DEFAULT '0' COMMENT '排行榜单id',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0' COMMENT '是否删除',
  `status` int(1) DEFAULT '0' COMMENT '状态 0：发起 1：成功 2：失败',
  `type` int(1) DEFAULT '1' COMMENT '类型 1:日排行 2:周排行',
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_type_uk` (`title`,`ranking_list_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pool_dispatch_task
-- ----------------------------

-- ----------------------------
-- Table structure for pool_dispatch_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `pool_dispatch_task_detail`;
CREATE TABLE `pool_dispatch_task_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT '' COMMENT '奖项名称',
  `num` int(3) unsigned DEFAULT '0' COMMENT '人数',
  `rate` decimal(7,4) unsigned DEFAULT '0.0000' COMMENT '比例',
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务ID',
  `userids` varchar(1000) DEFAULT '' COMMENT '指定派奖人id',
  `hierarchy_ids` varchar(200) DEFAULT '' COMMENT '层级id集合',
  `version` int(11) DEFAULT '0',
  `delete_status` int(1) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_taskid_uk` (`title`,`task_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='派奖奖项明细表';

-- ----------------------------
-- Records of pool_dispatch_task_detail
-- ----------------------------

-- ----------------------------
-- Table structure for pool_game
-- ----------------------------
DROP TABLE IF EXISTS `pool_game`;
CREATE TABLE `pool_game` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `count_time` datetime DEFAULT NULL COMMENT '更新奖池的最后时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `pool` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '奖金',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（0：禁用  1：启用）',
  `game_id` bigint(22) DEFAULT '0' COMMENT '游戏id',
  `bet_rate` decimal(4,2) unsigned DEFAULT '0.00' COMMENT '下注比例',
  PRIMARY KEY (`id`),
  UNIQUE KEY `game_id_uk` (`game_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='游戏奖池表';

-- ----------------------------
-- Records of pool_game
-- ----------------------------
INSERT INTO `pool_game` VALUES ('3', '0', '2019-04-08 10:20:34', '2019-04-21 00:00:01', '2019-04-21 00:00:01', '0', '6358500.00', '', '1', '0.10');

-- ----------------------------
-- Table structure for ranking_list
-- ----------------------------
DROP TABLE IF EXISTS `ranking_list`;
CREATE TABLE `ranking_list` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `rank_list_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '排行榜名称',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（0：禁用  1：启用）',
  `top_num` int(4) unsigned DEFAULT '20' COMMENT '榜单前几名参与抽奖',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='排行榜';

-- ----------------------------
-- Records of ranking_list
-- ----------------------------
INSERT INTO `ranking_list` VALUES ('1', '0', '2019-04-08 13:39:30', '2019-04-08 13:39:30', '0', '分享榜', '', '20');
INSERT INTO `ranking_list` VALUES ('2', '0', '2019-04-08 13:39:56', '2019-04-08 13:39:56', '0', '充值榜', '', '20');
INSERT INTO `ranking_list` VALUES ('3', '0', '2019-04-08 13:40:25', '2019-04-08 13:40:25', '0', '派奖榜', '', '20');
INSERT INTO `ranking_list` VALUES ('4', '0', '2019-04-08 13:40:52', '2019-04-08 13:40:52', '0', '财富榜', '', '20');

-- ----------------------------
-- Table structure for ranking_list_day
-- ----------------------------
DROP TABLE IF EXISTS `ranking_list_day`;
CREATE TABLE `ranking_list_day` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `ranking_list_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '排行榜id',
  `amount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '排行值',
  `position` int(3) DEFAULT '0' COMMENT '名次',
  `that_day` date DEFAULT NULL COMMENT '统计日期',
  `type` int(1) DEFAULT '0' COMMENT '0:未返佣  1：管理员新增（不返佣） 2：已返佣 3：返佣异常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `rankinglist_day_uk` (`that_day`,`user_id`,`ranking_list_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9254 DEFAULT CHARSET=utf8 COMMENT='日排行榜';

-- ----------------------------
-- Records of ranking_list_day
-- ----------------------------

-- ----------------------------
-- Table structure for ranking_list_week
-- ----------------------------
DROP TABLE IF EXISTS `ranking_list_week`;
CREATE TABLE `ranking_list_week` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_account` varchar(50) NOT NULL DEFAULT '' COMMENT '用户账号',
  `ranking_list_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '排行榜id',
  `amount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '排行值',
  `position` int(3) DEFAULT '0' COMMENT '名次',
  `week_of_year` int(6) unsigned DEFAULT '0' COMMENT '第几周',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ranking_week_uk` (`user_id`,`ranking_list_id`,`week_of_year`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1211 DEFAULT CHARSET=utf8 COMMENT='周排行榜';

-- ----------------------------
-- Records of ranking_list_week
-- ----------------------------

-- ----------------------------
-- Table structure for recharge_amount
-- ----------------------------
DROP TABLE IF EXISTS `recharge_amount`;
CREATE TABLE `recharge_amount` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `amount` bigint(50) NOT NULL COMMENT '金额',
  `pay_id` bigint(22) NOT NULL COMMENT '渠道ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='充值内容（金额）';

-- ----------------------------
-- Records of recharge_amount
-- ----------------------------
INSERT INTO `recharge_amount` VALUES ('3', '0', '2019-04-09 00:14:12', '2019-04-09 00:14:12', '0', '100', '1');
INSERT INTO `recharge_amount` VALUES ('10', '0', '2019-04-09 00:14:19', '2019-04-09 00:14:19', '0', '200', '1');
INSERT INTO `recharge_amount` VALUES ('17', '0', '2019-04-09 00:14:29', '2019-04-09 00:14:29', '0', '300', '1');
INSERT INTO `recharge_amount` VALUES ('24', '0', '2019-04-09 00:14:38', '2019-04-21 22:05:03', '0', '100', '2');
INSERT INTO `recharge_amount` VALUES ('31', '0', '2019-04-09 00:14:45', '2019-04-21 22:04:54', '0', '50', '2');
INSERT INTO `recharge_amount` VALUES ('38', '0', '2019-04-09 00:14:51', '2019-04-21 22:04:45', '0', '30', '2');
INSERT INTO `recharge_amount` VALUES ('45', '1', '2019-04-20 18:51:43', '2019-04-20 18:51:43', '0', '10', '2');
INSERT INTO `recharge_amount` VALUES ('52', '0', '2019-04-21 00:35:01', '2019-04-21 00:35:01', '0', '10', '2');
INSERT INTO `recharge_amount` VALUES ('59', '0', '2019-04-21 22:05:13', '2019-04-21 22:05:13', '0', '200', '2');
INSERT INTO `recharge_amount` VALUES ('66', '0', '2019-04-21 22:06:54', '2019-04-21 22:06:54', '0', '300', '2');
INSERT INTO `recharge_amount` VALUES ('73', '0', '2019-04-21 22:07:02', '2019-04-21 22:07:02', '0', '500', '2');
INSERT INTO `recharge_amount` VALUES ('80', '0', '2019-04-21 22:07:11', '2019-04-21 22:07:11', '0', '1000', '2');
INSERT INTO `recharge_amount` VALUES ('87', '0', '2019-04-21 22:07:21', '2019-04-21 22:07:21', '0', '2000', '2');

-- ----------------------------
-- Table structure for recharge_channel
-- ----------------------------
DROP TABLE IF EXISTS `recharge_channel`;
CREATE TABLE `recharge_channel` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '渠道名称',
  `type` bigint(22) NOT NULL COMMENT '导航类型',
  `min_amount` bigint(10) DEFAULT '0' COMMENT '最小金额',
  `enable` bit(1) DEFAULT b'1' COMMENT '1.启用，2.禁用（状态）',
  `app_icon_id` bigint(22) DEFAULT NULL COMMENT 'app图标ID',
  `app_icon_md5` varchar(50) DEFAULT '' COMMENT 'appMD5',
  `web_icon_id` bigint(22) DEFAULT NULL COMMENT 'web端图标',
  `web_icon_md5` varchar(50) DEFAULT '' COMMENT 'webMD5',
  `enclosure_id` bigint(22) DEFAULT NULL COMMENT '附件',
  `md5` varchar(50) DEFAULT NULL COMMENT 'MD5',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='支付渠道';

-- ----------------------------
-- Records of recharge_channel
-- ----------------------------
INSERT INTO `recharge_channel` VALUES ('12', '0', '2019-03-06 17:26:16', '2019-04-09 00:03:09', '0', '支付宝', '12', '100', '', '689', 'c51079688627c1e62695fae170a91b48', '390', '', '392', '7dc4c1e813a0f12a37ea6590dcd320c9');
INSERT INTO `recharge_channel` VALUES ('13', '0', '2019-03-06 17:26:39', '2019-04-09 00:03:25', '0', '微信', '13', '10', '', '696', '403f80e247cc40256e24833545cff15e', '391', '', '393', '7dc4c1e813a0f12a37ea6590dcd320c9');
INSERT INTO `recharge_channel` VALUES ('29', '0', '2019-03-06 17:26:39', '2019-04-09 00:03:38', '0', 'qq支付', '29', '10', '', '703', 'fb14aa5f370bcbd15deaa3b7b45289a8', '391', '', '393', '7dc4c1e813a0f12a37ea6590dcd320c9');
INSERT INTO `recharge_channel` VALUES ('30', '0', '2019-03-06 17:26:39', '2019-03-07 20:34:13', '0', '京东支付', '30', '10', '', '391', '946056374f6fa826ddd07da4cc632703', '391', '', '393', '7dc4c1e813a0f12a37ea6590dcd320c9');
INSERT INTO `recharge_channel` VALUES ('31', '0', '2019-03-06 17:26:39', '2019-03-07 20:34:13', '0', '银联支付', '31', '10', '', '391', '946056374f6fa826ddd07da4cc632703', '391', '', '393', '7dc4c1e813a0f12a37ea6590dcd320c9');
INSERT INTO `recharge_channel` VALUES ('32', '0', '2019-03-06 17:26:39', '2019-03-07 20:34:13', '0', '快捷支付', '32', '10', '', '391', '946056374f6fa826ddd07da4cc632703', '391', '', '393', '7dc4c1e813a0f12a37ea6590dcd320c9');

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha` (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统验证码';

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `code` varchar(20) NOT NULL COMMENT 'code',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `parent_code` varchar(20) DEFAULT NULL COMMENT '上级',
  `enable` bit(1) DEFAULT b'1' COMMENT '是否可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_uk` (`code`,`parent_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8 COMMENT='用户动态码';

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES ('48', '0', '2019-01-08 10:43:18', '2019-01-18 15:11:35', '0', '001', '银行名称', '0', '');
INSERT INTO `sys_dictionary` VALUES ('49', '0', '2019-01-08 10:43:18', '2019-01-18 15:11:35', '0', 'b001', '招商银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('50', '0', '2019-01-08 10:43:18', '2019-01-18 15:11:35', '0', 'b002', '工商银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('51', '0', '2019-01-08 10:43:18', '2019-01-18 15:11:35', '0', 'b003', '建设银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('52', '0', '2019-01-08 10:43:18', '2019-01-18 15:11:35', '0', 'b004', '交通银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('53', '0', '2019-01-10 11:34:47', '2019-01-18 15:11:35', '0', '002', '用户类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('54', '0', '2019-01-10 11:36:11', '2019-01-18 15:11:35', '0', 'TRIAL', '试玩账号', '002', '');
INSERT INTO `sys_dictionary` VALUES ('55', '0', '2019-01-10 11:36:14', '2019-01-18 15:11:35', '0', 'USER', '普通会员', '002', '');
INSERT INTO `sys_dictionary` VALUES ('56', '0', '2019-01-10 11:36:18', '2019-01-18 15:11:35', '0', 'VIP', 'VIP', '002', '');
INSERT INTO `sys_dictionary` VALUES ('57', '0', '2019-01-16 15:41:58', '2019-01-18 15:11:35', '0', '003', '交易类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('58', '0', '2019-01-18 15:11:35', '2019-01-18 15:11:35', '0', '0', '存款', '003', '');
INSERT INTO `sys_dictionary` VALUES ('59', '0', '2019-01-18 15:11:46', '2019-01-18 15:11:46', '0', '1', '取款', '003', '');
INSERT INTO `sys_dictionary` VALUES ('60', '0', '2019-01-18 15:11:57', '2019-01-18 15:11:57', '0', '2', '冲销', '003', '');
INSERT INTO `sys_dictionary` VALUES ('61', '0', '2019-01-18 15:12:19', '2019-01-18 15:12:19', '0', '3', '返利', '003', '');
INSERT INTO `sys_dictionary` VALUES ('62', '0', '2019-01-18 15:12:49', '2019-01-18 15:12:49', '0', '004', '交易具体类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('63', '0', '2019-01-18 15:13:12', '2019-01-18 15:13:12', '0', '005', '订单状态', '0', '');
INSERT INTO `sys_dictionary` VALUES ('64', '0', '2019-01-18 15:13:33', '2019-01-18 15:13:33', '0', '006', '充值类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('65', '0', '2019-01-18 15:14:35', '2019-01-18 15:14:35', '0', 'BANK', '银行卡充值', '006', '');
INSERT INTO `sys_dictionary` VALUES ('66', '0', '2019-01-18 15:14:55', '2019-01-18 15:15:30', '0', 'THIRD', '第三方充值', '006', '');
INSERT INTO `sys_dictionary` VALUES ('67', '0', '2019-01-18 15:15:17', '2019-01-18 15:15:17', '0', 'ADMIN', '人工充值', '006', '');
INSERT INTO `sys_dictionary` VALUES ('68', '0', '2019-01-18 16:43:50', '2019-01-18 16:43:50', '0', '0', '未确认', '005', '');
INSERT INTO `sys_dictionary` VALUES ('69', '0', '2019-01-18 16:45:15', '2019-01-18 16:45:15', '0', '1', '审核失败', '005', '');
INSERT INTO `sys_dictionary` VALUES ('70', '0', '2019-01-18 16:45:25', '2019-01-18 16:45:25', '0', '2', '订单完成', '005', '');
INSERT INTO `sys_dictionary` VALUES ('71', '0', '2019-01-18 16:46:35', '2019-01-18 16:46:35', '0', '11', '银行充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('72', '0', '2019-01-18 16:46:49', '2019-01-18 16:46:49', '0', '12', '支付宝充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('73', '0', '2019-01-18 16:46:59', '2019-01-18 16:46:59', '0', '13', '微信充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('74', '0', '2019-01-18 16:47:10', '2019-01-18 16:47:10', '0', '14', '人工充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('75', '0', '2019-01-18 16:47:21', '2019-01-18 16:47:21', '0', '15', '佣金取款', '004', '');
INSERT INTO `sys_dictionary` VALUES ('76', '0', '2019-01-18 16:47:30', '2019-01-18 16:47:30', '0', '16', '账户取款', '004', '');
INSERT INTO `sys_dictionary` VALUES ('77', '0', '2019-01-18 16:47:39', '2019-01-18 16:47:39', '0', '17', '额度转换', '004', '');
INSERT INTO `sys_dictionary` VALUES ('78', '0', '2019-01-18 16:47:49', '2019-01-18 16:47:49', '0', '18', '签到返利', '004', '');
INSERT INTO `sys_dictionary` VALUES ('79', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '19', '推荐返利', '004', '');
INSERT INTO `sys_dictionary` VALUES ('80', '1', '2019-01-25 17:27:36', '2019-01-25 17:27:36', '0', '007', '头部菜单类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('81', '0', '2019-02-16 19:05:51', '2019-02-16 19:50:43', '0', '21', '资金归集', '004', '');
INSERT INTO `sys_dictionary` VALUES ('82', '0', '2019-02-19 10:22:02', '2019-02-19 10:22:02', '0', 'imageType', '图片类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('83', '0', '2019-02-19 10:22:46', '2019-02-19 10:22:46', '0', 'logo', 'LOGO', 'imageType', '');
INSERT INTO `sys_dictionary` VALUES ('84', '0', '2019-02-19 10:23:06', '2019-02-19 10:23:06', '0', 'exhibition', '轮播图', 'imageType', '');
INSERT INTO `sys_dictionary` VALUES ('85', '0', '2019-02-19 10:23:25', '2019-02-19 10:23:25', '0', 'couplets', '对联', 'imageType', '');
INSERT INTO `sys_dictionary` VALUES ('86', '0', '2019-02-19 10:23:41', '2019-02-19 10:23:41', '0', 'gameRecord', '游戏记录轮播图', 'imageType', '');
INSERT INTO `sys_dictionary` VALUES ('87', '0', '2019-02-19 10:24:04', '2019-02-19 10:24:04', '0', 'transactionRecord', '交易记录轮播图', 'imageType', '');
INSERT INTO `sys_dictionary` VALUES ('88', '0', '2019-02-19 10:24:17', '2019-02-19 10:24:17', '0', 'recharge', '充值轮播图', 'imageType', '');
INSERT INTO `sys_dictionary` VALUES ('89', '0', '2019-02-19 10:26:08', '2019-02-19 10:26:08', '0', 'promotions', '优惠活动图', 'imageType', '');
INSERT INTO `sys_dictionary` VALUES ('90', '0', '2019-02-19 10:26:22', '2019-02-19 10:26:22', '0', 'pay', '首页支付图', 'imageType', '');
INSERT INTO `sys_dictionary` VALUES ('91', '0', '2019-03-01 17:21:25', '2019-03-01 17:21:25', '0', 'b005', '浦发银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('92', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '20', '奖池派奖', '004', '');
INSERT INTO `sys_dictionary` VALUES ('94', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '22', '取消取款', '004', '');
INSERT INTO `sys_dictionary` VALUES ('95', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '23', '佣金转金币', '004', '');
INSERT INTO `sys_dictionary` VALUES ('96', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '24', '实名返利', '004', '');
INSERT INTO `sys_dictionary` VALUES ('97', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '25', '佣金取现', '004', '');
INSERT INTO `sys_dictionary` VALUES ('98', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '26', '游戏返利', '004', '');
INSERT INTO `sys_dictionary` VALUES ('99', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '27', '撤销充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('100', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '28', '手续费返还', '004', '');
INSERT INTO `sys_dictionary` VALUES ('101', '0', '2019-03-15 11:33:35', '2019-03-15 11:33:35', '0', '5', '额度转换', '003', '');
INSERT INTO `sys_dictionary` VALUES ('102', '0', '2019-03-15 11:33:54', '2019-03-15 11:33:54', '0', '6', '归集', '003', '');
INSERT INTO `sys_dictionary` VALUES ('103', '0', '2019-03-15 11:34:16', '2019-03-15 11:34:16', '0', '8', '取款回退', '003', '');
INSERT INTO `sys_dictionary` VALUES ('104', '0', '2019-03-15 11:34:33', '2019-03-15 11:34:33', '0', '9', '撤销充值', '003', '');
INSERT INTO `sys_dictionary` VALUES ('105', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '29', 'qq充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('106', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '30', '京东充值', '004', '');
INSERT INTO `sys_dictionary` VALUES ('107', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '31', '快捷支付', '004', '');
INSERT INTO `sys_dictionary` VALUES ('108', '0', '2019-01-18 16:47:58', '2019-01-18 16:47:58', '0', '32', '银联支付', '004', '');
INSERT INTO `sys_dictionary` VALUES ('109', '0', '2019-03-23 15:03:41', '2019-03-23 15:03:41', '0', 'RewardType', '奖励类型', '0', '');
INSERT INTO `sys_dictionary` VALUES ('110', '0', '2019-03-23 15:04:00', '2019-03-23 15:04:00', '0', '1', '实名返利', 'RewardType', '');
INSERT INTO `sys_dictionary` VALUES ('111', '0', '2019-04-01 13:51:47', '2019-04-01 13:51:47', '0', 'URL', '路径管理', '0', '');
INSERT INTO `sys_dictionary` VALUES ('112', '0', '2019-04-01 13:52:29', '2019-04-07 21:26:02', '0', 'H5Pay', 'https://wap.abqp0.cc/m/PayCounts', 'URL', '');
INSERT INTO `sys_dictionary` VALUES ('113', '0', '2019-04-02 16:58:45', '2019-04-02 16:58:45', '0', '2', '用户返水', 'RewardType', '');
INSERT INTO `sys_dictionary` VALUES ('115', '0', '2019-04-08 23:49:27', '2019-04-08 23:49:45', '0', 'recommendationUrl', 'https://wap.abqp0.cc/m/AppRegistered', 'URL', '');
INSERT INTO `sys_dictionary` VALUES ('122', '0', '2019-04-15 14:05:22', '2019-04-15 14:05:22', '0', 'AppActivityUrl', 'https://wap.abqp0.cc/m/mobile/IndexActivity/zh', 'URL', '');
INSERT INTO `sys_dictionary` VALUES ('129', '0', '2019-04-20 09:24:41', '2019-04-20 09:24:41', '0', 'b006', '农业银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('136', '0', '2019-04-20 09:24:52', '2019-04-20 09:24:52', '0', 'b007', '中国银行', '001', '');
INSERT INTO `sys_dictionary` VALUES ('143', '0', '2019-04-20 09:26:45', '2019-04-20 09:26:45', '0', '33', '会员下注返水', '004', '');
INSERT INTO `sys_dictionary` VALUES ('150', '0', '2019-04-20 09:28:03', '2019-04-20 09:28:03', '0', '34', '代理商下线下注返佣', '004', '');
INSERT INTO `sys_dictionary` VALUES ('157', '0', '2019-04-20 09:28:24', '2019-04-20 09:28:24', '0', '35', '代理商下线充值返佣', '004', '');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` text COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9051 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=636 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '子账号', null, null, '0', 'system', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '子账号管理', 'sys/user', null, '1', 'admin', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色权限管理', 'sys/role', null, '1', 'role', '0');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'sys/menu', null, '1', 'menu', '3');
INSERT INTO `sys_menu` VALUES ('5', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('6', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('7', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('8', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('9', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('10', '3', '新增', null, 'sys:role:save,sys:menu:rooleList', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('11', '3', '修改', null, 'sys:role:update,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('12', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('13', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('14', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('15', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '0', '用户管理', null, null, '0', 'admin', '1');
INSERT INTO `sys_menu` VALUES ('18', '17', '会员管理', 'account/users', null, '1', 'geren', '1');
INSERT INTO `sys_menu` VALUES ('19', '18', '账号列表标签页', null, 'account:tabs:account', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '18', '真实姓名审核标签页', null, 'account:tabs:username', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '18', '邀请码管理标签页', null, 'account:tabs:invitation', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '18', '用户注册管理标签页', null, 'account:tabs:userRegistration', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('23', '18', '操作记录标签页', null, 'account:tabs:operationRecord', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('24', '17', '注册管理', 'sysregisternecessary/sysregisternecessary', null, '1', 'geren', '0');
INSERT INTO `sys_menu` VALUES ('25', '24', '查看', null, 'sysregisternecessary:sysregisternecessary:list,sysregisternecessary:sysregisternecessary:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('26', '24', '修改', null, 'sysregisternecessary:sysregisternecessary:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('27', '0', '网站管理', null, null, '0', 'web', '9');
INSERT INTO `sys_menu` VALUES ('28', '27', '图片管理', 'webhomeimage/webhomeimage', null, '1', 'editor', '1');
INSERT INTO `sys_menu` VALUES ('29', '28', '新增', null, 'webhomeimage:webhomeimage:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('30', '28', '修改', null, 'webhomeimage:webhomeimage:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('31', '28', '查看', null, 'webhomeimage:webhomeimage:list,webhomeimage:webhomeimage:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('32', '28', '上传', null, 'webhomeenclosure:webhomeenclosure:uploadFile', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('33', '27', '联系及网站信息', 'webhomecontact/webhomecontact', null, '1', 'geren', '2');
INSERT INTO `sys_menu` VALUES ('34', '33', '查看', null, 'webhomecontact:webhomecontact:list,webhomecontact:webhomecontact:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('35', '33', '新增', null, 'webhomecontact:webhomecontact:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('36', '33', '修改', null, 'webhomecontact:webhomecontact:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('37', '33', '删除', null, 'webhomecontact:webhomecontact:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('38', '18', '查询', null, 'user:user:list,user:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('39', '18', '删除', null, 'user:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('40', '18', '新增', null, 'user:user:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('41', '18', '修改', null, 'user:user:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('42', '18', '修改密码', null, 'user:user:editpasswd', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('294', '27', '菜单管理', 'webhomemenu/webhomemenu', 'webhomemenu:webhomemenu:childrenList', '1', 'menu', '3');
INSERT INTO `sys_menu` VALUES ('295', '294', '查看', null, 'webhomemenu:webhomemenu:list,webhomemenu:webhomemenu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('296', '294', '新增', null, 'webhomemenu:webhomemenu:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('297', '0', '优惠活动', null, null, '0', 'youhui', '4');
INSERT INTO `sys_menu` VALUES ('298', '297', '站内信(邮件)', 'messagemanagement/messagemanagement', null, '1', 'duanxin', '3');
INSERT INTO `sys_menu` VALUES ('299', '298', '新增', null, 'messagemanagement:messagemanagement:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('300', '298', '修改', null, 'messagemanagement:messagemanagement:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('301', '298', '删除', null, 'messagemanagement:messagemanagement:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('302', '298', '查看', null, 'messagemanagement:messagemanagement:list,messagemanagement:messagemanagement:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('303', '27', '游戏配置管理', 'webhomemenutemplate/webhomemenutemplate', 'webhomemenutemplate:webhomemenutemplate:delete', '1', 'game', '4');
INSERT INTO `sys_menu` VALUES ('304', '303', '查看', null, 'webhomemenutemplate:webhomemenutemplate:list,webhomemenutemplate:webhomemenutemplate:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('305', '303', '新增', null, 'webhomemenutemplate:webhomemenutemplate:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('306', '303', '修改', null, 'webhomemenutemplate:webhomemenutemplate:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('307', '303', '游戏导航列表', null, 'webhomemenu:webhomemenu:gameMenuList', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('308', '297', '公告管理', 'noticemanagement/noticemanagement', null, '1', 'pinglun', '2');
INSERT INTO `sys_menu` VALUES ('309', '308', '新增', null, 'noticemanagement:noticemanagement:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('310', '308', '修改', null, 'noticemanagement:noticemanagement:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('311', '308', '删除', null, 'noticemanagement:noticemanagement:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('312', '308', '查询', null, 'noticemanagement:noticemanagement:list,noticemanagement:noticemanagement:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('315', '18', '修改层级', null, 'user:user:editHierarchy', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('316', '18', '更多', null, 'user:user:more', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('317', '18', '查看操作记录', null, 'userlog:userlog:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('318', '0', '系统设置', null, null, '0', 'shezhi', '10');
INSERT INTO `sys_menu` VALUES ('319', '318', 'OTP管理', 'userotp/userotp', null, '1', 'config', '0');
INSERT INTO `sys_menu` VALUES ('320', '319', '新增', null, 'userotp:userotp:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('321', '319', '修改', null, 'userotp:userotp:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('322', '319', '删除', null, 'userotp:userotp:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('323', '319', '查询', null, 'userotp:userotp:list,userotp:userotp:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('324', '318', '网址绑定', 'domainmanagement/domainmanagement', null, '1', 'config', '0');
INSERT INTO `sys_menu` VALUES ('325', '324', '新增', null, 'domainmanagement:domainmanagement:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('326', '324', '修改', null, 'domainmanagement:domainmanagement:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('327', '324', '删除', null, 'domainmanagement:domainmanagement:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('328', '324', '查询', null, 'domainmanagement:domainmanagement:list,domainmanagement:domainmanagement:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('329', '0', '代理管理', null, '', '0', 'daili', '3');
INSERT INTO `sys_menu` VALUES ('330', '27', '友情链接管理', 'webhomefriendship/webhomefriendship', null, '1', 'shezhi', '7');
INSERT INTO `sys_menu` VALUES ('331', '330', '查看', null, 'webhomefriendship:webhomefriendship:list,webhomefriendship:webhomefriendship:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('332', '330', '新增', null, 'webhomefriendship:webhomefriendship:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('333', '330', '修改', null, 'webhomefriendship:webhomefriendship:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('334', '0', '现金管理', null, null, '0', 'money', '5');
INSERT INTO `sys_menu` VALUES ('337', '334', '银行卡设定', 'cashbank/cashbank', null, '1', 'editor', '0');
INSERT INTO `sys_menu` VALUES ('338', '337', '查看', null, 'cashbank:cashbank:list,cashbank:cashbank:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('339', '337', '新增', null, 'cashbank:cashbank:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('340', '337', '修改', null, 'cashbank:cashbank:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('341', '337', '删除', null, 'cashbank:cashbank:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('345', '17', '会员层级管理', 'userhierarchy/userhierarchy', 'userhierarchy:userhierarchy:closeVipEnable,userhierarchy:userhierarchy:openVipEnable', '1', 'zhedie', '2');
INSERT INTO `sys_menu` VALUES ('346', '345', '查看', null, 'userhierarchy:userhierarchy:list,userhierarchy:userhierarchy:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('347', '345', '新增', null, 'userhierarchy:userhierarchy:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('348', '345', '修改', null, 'userhierarchy:userhierarchy:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('349', '345', '删除', null, 'userhierarchy:userhierarchy:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('350', '345', '下拉框', null, 'userhierarchy:userhierarchy:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('351', '18', '编辑用户银行卡信息', null, 'user:user:editbankinfo', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('352', '18', '删除用户银行卡信息', null, 'user:user:deletebankinfo', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('353', '345', '启用', null, 'sysbank:sysbank:enable', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('354', '345', '禁用', null, 'sysbank:sysbank:prohibit', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('358', '18', '新增邀请码', null, 'recommendationcode:recommendationcode:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('359', '18', '修改邀请码', null, 'recommendationcode:recommendationcode:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('360', '18', '删除邀请码', null, 'recommendationcode:recommendationcode:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('361', '18', '查询邀请码', null, 'recommendationcode:recommendationcode:list,recommendationcode:recommendationcode:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('362', '1', '子账号消息推送', 'messagemanagement/adminmessage', null, '1', 'duanxin', '2');
INSERT INTO `sys_menu` VALUES ('363', '362', '新增', null, 'messagemanagement:adminmessage:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('364', '362', '修改', null, 'messagemanagement:adminmessage:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('365', '362', '删除', null, 'messagemanagement:adminmessage:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('366', '362', '查询', 'messagemanagement:adminmessage:info', 'messagemanagement:adminmessage:info,messagemanagement:adminmessage:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('367', '17', '黑名单', 'userblacklist/userblacklist', null, '1', 'admin', '4');
INSERT INTO `sys_menu` VALUES ('368', '367', '新增', null, 'userblacklist:userblacklist:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('369', '367', '查看', null, 'userblacklist:userblacklist:list,userblacklist:userblacklist:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('370', '367', '删除', null, 'userblacklist:userblacklist:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('375', '0', '游戏记录', null, null, '0', 'game', '6');
INSERT INTO `sys_menu` VALUES ('386', '1', '数字字典', 'sysdictionary/sysdictionary', 'sysdictionary:sysdictionary:select', '1', 'editor', '4');
INSERT INTO `sys_menu` VALUES ('387', '386', '新增', null, 'sysdictionary:sysdictionary:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('388', '386', '更新', null, 'sysdictionary:sysdictionary:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('389', '386', '删除', null, 'sysdictionary:sysdictionary:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('390', '386', '查询', null, 'sysdictionary:sysdictionary:list,sysdictionary:sysdictionary:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('391', '345', '设置默认', null, 'userhierarchy:userhierarchy:setdefault', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('392', '17', '人工充值', 'orderadministratorrecharge/orderadministratorrecharge', null, '1', 'tubiao', '6');
INSERT INTO `sys_menu` VALUES ('393', '392', '创建订单', null, 'orderadministratorrecharge:orderadministratorrecharge:create', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('394', '392', '查询', null, 'orderadministratorrecharge:orderadministratorrecharge:list,orderadministratorrecharge:orderadministratorrecharge:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('395', '392', '删除', null, 'messagemanagement:messagemanagement:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('396', '0', '奖池管理', null, null, '0', 'shoucangfill', '7');
INSERT INTO `sys_menu` VALUES ('397', '396', '添加游戏奖池', 'poolgame/poolgame', null, '1', 'editor', '1');
INSERT INTO `sys_menu` VALUES ('398', '397', '新增', null, 'poolgame:poolgame:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('399', '397', '修改', null, 'poolgame:poolgame:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('400', '397', '删除', null, 'poolgame:poolgame:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('401', '397', '查询', null, 'poolgame:poolgame:list,poolgame:poolgame:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('402', '396', '排行榜单', 'rankinglist/rankinglist', null, '1', 'menu', '5');
INSERT INTO `sys_menu` VALUES ('403', '402', '新增', null, 'rankinglist:rankinglist:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('404', '402', '修改', null, 'rankinglist:rankinglist:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('405', '402', '删除', null, 'rankinglist:rankinglist:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('406', '402', '查询', null, 'rankinglist:rankinglist:list,rankinglist:rankinglist:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('408', '407', '查询', null, 'gamerecord:gamerecord:list,gamerecord:gamerecord:info,gameanalysis:gameanalysis:list,gameanalysis:gameanalysis:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('415', '329', '代理账号管理', 'agent/agent', 'agent:agent:list,agent:agent:info', '1', 'role', '0');
INSERT INTO `sys_menu` VALUES ('416', '415', '取消代理', null, 'agent:agent:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('417', '294', '修改', null, 'webhomemenu:webhomemenu:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('419', '329', '等级设定', 'useragenthierarchy/useragenthierarchy', 'useragenthierarchy:useragenthierarchy:list,useragenthierarchy:useragenthierarchy:info', '1', 'shezhi', '2');
INSERT INTO `sys_menu` VALUES ('420', '419', '修改', null, 'useragenthierarchy:useragenthierarchy:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('421', '419', '删除', null, 'useragenthierarchy:useragenthierarchy:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('422', '419', '新增', null, 'useragenthierarchy:useragenthierarchy:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('423', '27', '网站头部管理', 'webhomehead/webhomehead', null, '1', 'system', '5');
INSERT INTO `sys_menu` VALUES ('424', '423', '新增', null, 'webhomehead:webhomehead:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('425', '423', '查看', null, 'webhomehead:webhomehead:list,webhomehead:webhomehead:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('426', '423', '修改', null, 'webhomehead:webhomehead:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('427', '423', '删除', null, 'webhomehead:webhomehead:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('428', '297', '充值优惠', 'orderpreferential/orderpreferential', null, '1', 'tixing', '6');
INSERT INTO `sys_menu` VALUES ('429', '428', '新增', null, 'orderpreferential:orderpreferential:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('430', '428', '修改', null, 'orderpreferential:orderpreferential:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('431', '428', '查看', null, 'orderpreferential:orderpreferential:list,orderpreferential:orderpreferential:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('437', '28', '获取图片路径', null, 'webhomeenclosure:webhomeenclosure:enclosure', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('440', '396', '派发奖金任务', 'pooldispatchtask/pooldispatchtask', 'pooldispatchtask:pooldispatchtask:dispatch', '1', 'config', '2');
INSERT INTO `sys_menu` VALUES ('441', '440', '查看', null, 'pooldispatchtask:pooldispatchtask:list,pooldispatchtask:pooldispatchtask:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('442', '440', '新增', null, 'pooldispatchtask:pooldispatchtask:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('443', '440', '修改', null, 'pooldispatchtask:pooldispatchtask:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('444', '440', '删除', null, 'pooldispatchtask:pooldispatchtask:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('445', '396', '派奖明细记录表', 'pooldispatchdetaillist/pooldispatchdetaillist', null, '1', 'config', '4');
INSERT INTO `sys_menu` VALUES ('446', '445', '查看', null, 'pooldispatchdetaillist:pooldispatchdetaillist:list,pooldispatchdetaillist:pooldispatchdetaillist:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('447', '445', '新增', null, 'pooldispatchdetaillist:pooldispatchdetaillist:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('448', '445', '修改', null, 'pooldispatchdetaillist:pooldispatchdetaillist:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('449', '445', '删除', null, 'pooldispatchdetaillist:pooldispatchdetaillist:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('450', '396', '日排行榜', 'rankinglistday/rankinglistday', null, '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('451', '450', '查看', null, 'rankinglistday:rankinglistday:list,rankinglistday:rankinglistday:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('452', '450', '新增', null, 'rankinglistday:rankinglistday:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('453', '450', '修改', null, 'rankinglistday:rankinglistday:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('454', '450', '删除', null, 'rankinglistday:rankinglistday:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('455', '396', '派奖奖项明细表', 'pooldispatchtaskdetail/pooldispatchtaskdetail', null, '1', 'config', '3');
INSERT INTO `sys_menu` VALUES ('456', '455', '查看', null, 'pooldispatchtaskdetail:pooldispatchtaskdetail:list,pooldispatchtaskdetail:pooldispatchtaskdetail:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('457', '455', '新增', null, 'pooldispatchtaskdetail:pooldispatchtaskdetail:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('458', '455', '修改', null, 'pooldispatchtaskdetail:pooldispatchtaskdetail:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('459', '455', '删除', null, 'pooldispatchtaskdetail:pooldispatchtaskdetail:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('460', '396', '周排行榜', 'rankinglistweek/rankinglistweek', null, '1', 'config', '7');
INSERT INTO `sys_menu` VALUES ('461', '460', '查看', null, 'rankinglistweek:rankinglistweek:list,rankinglistweek:rankinglistweek:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('462', '460', '新增', null, 'rankinglistweek:rankinglistweek:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('463', '460', '修改', null, 'rankinglistweek:rankinglistweek:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('464', '460', '删除', null, 'rankinglistweek:rankinglistweek:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('465', '17', '试玩账号', 'account/trial_user', null, '1', 'zonghe', '7');
INSERT INTO `sys_menu` VALUES ('466', '465', '查询试玩配置', null, 'usertrialconfig:usertrialconfig:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('467', '465', '保存试玩配置', null, 'usertrialconfig:usertrialconfig:update,usertrialconfig:usertrialconfig:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('468', '465', '更多', null, 'user:user:more', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('469', '465', '查询', null, 'user:user:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('470', '18', '更新基本信息', null, 'userinfo:userinfo:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('471', '18', '查询用户的基本信息', null, 'userinfo:userinfo:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('472', '297', '活动类型管理', 'webhomepromotionstype/webhomepromotionstype', null, '1', 'youhui', '0');
INSERT INTO `sys_menu` VALUES ('473', '472', '查看', null, 'webhomepromotionstype:webhomepromotionstype:list,webhomepromotionstype:webhomepromotionstype:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('474', '472', '新增', null, 'webhomepromotionstype:webhomepromotionstype:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('475', '472', '修改', null, 'webhomepromotionstype:webhomepromotionstype:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('476', '297', '活动管理', 'webhomepromotions/webhomepromotions', 'webhomepromotions:webhomepromotions:list,webhomepromotions:webhomepromotions:delete', '1', 'youhui', '1');
INSERT INTO `sys_menu` VALUES ('477', '476', '查看', null, 'webhomepromotions:webhomepromotions:list,webhomepromotions:webhomepromotions:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('478', '476', '新增', null, 'webhomepromotions:webhomepromotions:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('479', '476', '修改', null, 'webhomepromotions:webhomepromotions:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('480', '17', '会员注单', 'account/usergamerecord', null, '1', 'shoucang', '3');
INSERT INTO `sys_menu` VALUES ('481', '480', '查询', null, 'user:gemerecord:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('482', '27', '热门游戏管理', 'webhomepopulargames/webhomepopulargames', null, '1', 'remen', '6');
INSERT INTO `sys_menu` VALUES ('483', '482', '新增', null, 'webhomepopulargames:webhomepopulargames:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('484', '482', '修改', null, 'webhomepopulargames:webhomepopulargames:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('485', '482', '查看', null, 'webhomepopulargames:webhomepopulargames:list,webhomepopulargames:webhomepopulargames:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('486', '294', '菜单下拉', null, 'webhomemenu:webhomemenu:getMenuListForSelect', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('487', '377', '游戏下拉', null, 'gameinfo:gameinfo:gameInfoListForSelect', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('488', '294', '删除', null, 'webhomemenu:webhomemenu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('489', '0', '订单管理', null, null, '0', 'web', '2');
INSERT INTO `sys_menu` VALUES ('490', '489', '银行卡存款订单', 'orderrecharge/orderbankrecharge', 'orderrecharge:orderrecharge:list,orderrecharge:orderrecharge:info,orderbankrecharge:orderbankrecharge:confirmed,orderbankrecharge:orderbankrecharge:canceled', '1', 'menu', '0');
INSERT INTO `sys_menu` VALUES ('491', '489', '第三方支付订单', 'orderrecharge/orderthirdrecharge', 'orderrecharge:orderrecharge:list,orderrecharge:orderrecharge:info', '1', 'menu', '0');
INSERT INTO `sys_menu` VALUES ('492', '489', '取款订单', 'orderrecharge/ordertakemoney', 'ordertakemoney:ordertakemoney:list,ordertakemoney:ordertakemoney:info,ordertakemoney:ordertakemoney:confirmed', '1', 'menu', '0');
INSERT INTO `sys_menu` VALUES ('493', '489', '佣金提现', 'orderrecharge/ordertakecommission', 'ordertakemoney:ordertakemoney:list,ordertakemoney:ordertakemoney:info', '1', 'menu', '0');
INSERT INTO `sys_menu` VALUES ('494', '472', '下拉', null, 'webhomepromotionstype:webhomepromotionstype:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('495', '0', '财务统计', '1', null, '0', 'tubiao', '5');
INSERT INTO `sys_menu` VALUES ('496', '419', '下拉', 'useragenthierarchy/useragenthierarchy', 'useragenthierarchy:useragenthierarchy:select', '2', 'shezhi', '2');
INSERT INTO `sys_menu` VALUES ('497', '17', '会员分析', 'statistics/userstatistics', 'userstatistics:userstatistics:list,userstatistics:userstatistics:getInsertUserNumber', '1', 'tubiao', '8');
INSERT INTO `sys_menu` VALUES ('498', '495', '存款统计', 'statistics/rechargestatistics', 'rechargestatistics:rechargestatistics:list', '1', 'money', '2');
INSERT INTO `sys_menu` VALUES ('499', '428', '启用', null, 'orderpreferential:orderpreferential:enable', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('500', '428', '禁用', null, 'orderpreferential:orderpreferential:disable', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('501', '495', '取款统计', 'statistics/takemoneystatistics', 'takemoneystatistics:takemoneystatistics:list', '1', 'money', '4');
INSERT INTO `sys_menu` VALUES ('502', '297', '奖励设定', 'userrebate/userrebate', 'userrebate:userrebate:list,userrebate:userrebate:info,userrebate:userrebate:save,userrebate:userrebate:update', '1', 'shoucangfill', '0');
INSERT INTO `sys_menu` VALUES ('503', '17', 'IP黑名单管理', 'ipblacklist/ipblacklist', 'ipblacklist:ipblacklist:list,ipblacklist:ipblacklist:info,ipblacklist:ipblacklist:save,ipblacklist:ipblacklist:update,ipblacklist:ipblacklist:delete', '1', 'role', '9');
INSERT INTO `sys_menu` VALUES ('504', '334', '现金价格预设配置表', 'cashpriceconfig/cashpriceconfig', null, '1', 'config', '1');
INSERT INTO `sys_menu` VALUES ('505', '504', '查看', null, 'cashpriceconfig:cashpriceconfig:list,cashpriceconfig:cashpriceconfig:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('506', '504', '新增', null, 'cashpriceconfig:cashpriceconfig:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('507', '504', '修改', null, 'cashpriceconfig:cashpriceconfig:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('508', '504', '删除', null, 'cashpriceconfig:cashpriceconfig:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('526', '633', '斗地主', 'gamerecordfightlandlords/gamerecordfightlandlords', null, '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('527', '526', '查看', null, 'gamerecordfightlandlords:gamerecordfightlandlords:list,gamerecordfightlandlords:gamerecordfightlandlords:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('531', '1', '操作日志', 'sys/log', 'sys:log:list,sys:log:info', '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('532', '334', '取款方式设置', 'ordertakemoney/ordertakechannel', 'ordertakemoney:ordertakemoney:channel', '1', 'config', '2');
INSERT INTO `sys_menu` VALUES ('533', '28', '删除', null, 'webhomeimage:webhomeimage:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('534', '392', '撤销人工充值订单', null, 'orderadministratorrecharge:orderadministratorrecharge:revoke', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('535', '633', '通比牛牛', 'gamerecordcattltongbi/gamerecordcattltongbi', null, '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('536', '535', '查看', null, 'gamerecordcattltongbi:gamerecordcattltongbi:list,gamerecordcattltongbi:gamerecordcattltongbi:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('540', '18', '修改余额', null, 'user:user:editUserMoney', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('541', '0', 'APP管理', null, null, '0', 'app', '11');
INSERT INTO `sys_menu` VALUES ('542', '541', '充值通道管理', 'apppaycofig/apppaycofig', 'apppaycofig:apppaycofig:list,apppaycofig:apppaycofig:info,apppaycofig:apppaycofig:save,apppaycofig:apppaycofig:update,apppaycofig:apppaycofig:delete,apppaycofig:apppaycofig:openEnable,apppaycofig:apppaycofig:closeEnable', '1', 'appPay', '0');
INSERT INTO `sys_menu` VALUES ('543', '541', '广告配置', 'appadcofig/appadcofig', 'appadcofig:appadcofig:list,appadcofig:appadcofig:info,appadcofig:appadcofig:save,appadcofig:appadcofig:update,appadcofig:appadcofig:delete', '1', 'ad', '1');
INSERT INTO `sys_menu` VALUES ('544', '541', '客服管理', 'appcustomerservice/appcustomerservice', 'appcustomerservice:appcustomerservice:list,appcustomerservice:appcustomerservice:info,appcustomerservice:appcustomerservice:save,appcustomerservice:appcustomerservice:update,appcustomerservice:appcustomerservice:delete', '1', 'admin', '2');
INSERT INTO `sys_menu` VALUES ('547', '541', '弹窗配置', 'appalertcofig/appalertcofig', 'appalertcofig:appalertcofig:list,appalertcofig:appalertcofig:info,appalertcofig:appalertcofig:save,appalertcofig:appalertcofig:update,appalertcofig:appalertcofig:delete', '1', 'editor', '5');
INSERT INTO `sys_menu` VALUES ('548', '543', '游戏下拉', null, 'gameinfo:gameinfo:gameSelect', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('549', '345', '游戏菜单', null, 'gameinfo:gameinfo:gameMenu', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('550', '27', '网站游戏跳转路径管理', 'gameconfigurl/gameconfigurl', 'gameconfigurl:gameconfigurl:list,gameconfigurl:gameconfigurl:info,gameconfigurl:gameconfigurl:save,gameconfigurl:gameconfigurl:update,gameconfigurl:gameconfigurl:delete', '1', 'daohang', '8');
INSERT INTO `sys_menu` VALUES ('551', '330', '内容', null, 'webhomefriendshiptemplate:webhomefriendshiptemplate:list,webhomefriendshiptemplate:webhomefriendshiptemplate:info,webhomefriendshiptemplate:webhomefriendshiptemplate:save,webhomefriendshiptemplate:webhomefriendshiptemplate:update,webhomefriendshiptemplate:webhomefriendshiptemplate:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('552', '334', '支付渠道管理', 'payconfig/payconfig', 'payconfig:payconfig:list,payconfig:payconfig:info,payconfig:payconfig:save,payconfig:payconfig:update,payconfig:payconfig:delete', '1', 'daili', '3');
INSERT INTO `sys_menu` VALUES ('553', '552', '启用/禁用', null, 'payconfig:payconfig:enable,payconfig:payconfig:disable', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('554', '552', '设为首推', null, 'payconfig:payconfig:firstPush', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('555', '334', '支付方式管理', 'rechargechannel/rechargechannel', 'rechargechannel:rechargechannel:list,rechargechannel:rechargechannel:info,rechargechannel:rechargechannel:save,rechargechannel:rechargechannel:update,rechargechannel:rechargechannel:delete', '1', 'money', '4');
INSERT INTO `sys_menu` VALUES ('556', '334', '充值金额设定', 'rechargeamount/rechargeamount', 'rechargeamount:rechargeamount:list,rechargeamount:rechargeamount:info,rechargeamount:rechargeamount:save,rechargeamount:rechargeamount:update,rechargeamount:rechargeamount:delete', '1', 'shezhi', '5');
INSERT INTO `sys_menu` VALUES ('557', '556', '支付下拉', null, 'payconfig:payconfig:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('558', '552', '下拉', null, 'rechargechannel:rechargechannel:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('559', '490', '数量', null, 'orderrecharge:orderrecharge:totalNumber', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('560', '492', '数量', null, 'ordertakemoney:ordertakemoney:totalNumber', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('561', '17', '交易记录', 'usertransactionrecord/usertransactionrecord', 'usertransactionrecord:usertransactionrecord:list', '1', 'money', '10');
INSERT INTO `sys_menu` VALUES ('562', '415', '修改代理', null, 'agent:agent:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('563', '17', '登陆记录', 'userlogin/userlogin', 'userlogin:userlogin:list,userlogin:userlogin:info', '1', 'log', '10');
INSERT INTO `sys_menu` VALUES ('564', '392', '层级下拉', null, 'userhierarchy:userhierarchy:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('565', '491', '平台下拉', null, 'payconfig:payconfig:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('566', '491', '渠道下拉', null, 'rechargechannel:rechargechannel:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('567', '493', '佣金取款数量', null, 'ordertakemoney:ordertakemoney:commissionTotalNumber', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('568', '633', '二八杠', 'gamerecordtwoeightbar/gamerecordtwoeightbar', null, '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('569', '568', '查看', null, 'gamerecordtwoeightbar:gamerecordtwoeightbar:list,gamerecordtwoeightbar:gamerecordtwoeightbar:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('573', '634', '房卡十三水', 'gamerecordshisanshuifangka/gamerecordshisanshuifangka', null, '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('574', '573', '查看', null, 'gamerecordshisanshuifangka:gamerecordshisanshuifangka:list,gamerecordshisanshuifangka:gamerecordshisanshuifangka:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('578', '633', '十三水', 'gamerecordshisanshui/gamerecordshisanshui', null, '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('579', '578', '查看', null, 'gamerecordshisanshui:gamerecordshisanshui:list,gamerecordshisanshui:gamerecordshisanshui:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('583', '633', '匹配炸金花', 'gamerecordpipeizhajinhua/gamerecordpipeizhajinhua', null, '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('584', '583', '查看', null, 'gamerecordpipeizhajinhua:gamerecordpipeizhajinhua:list,gamerecordpipeizhajinhua:gamerecordpipeizhajinhua:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('588', '633', '抢庄牛牛', 'gamerecordcattlqiangzhuang/gamerecordcattlqiangzhuang', null, '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('589', '588', '查看', null, 'gamerecordcattlqiangzhuang:gamerecordcattlqiangzhuang:list,gamerecordcattlqiangzhuang:gamerecordcattlqiangzhuang:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('593', '634', '房卡牛牛', 'gamerecordcattlfangka/gamerecordcattlfangka', null, '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('594', '593', '查看', null, 'gamerecordcattlfangka:gamerecordcattlfangka:list,gamerecordcattlfangka:gamerecordcattlfangka:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('598', '616', '会员每日下注统计', 'gamerecorddaily/gamerecorddaily', null, '1', 'config', '0');
INSERT INTO `sys_menu` VALUES ('599', '598', '查看', null, 'gamerecorddaily:gamerecorddaily:list,gamerecorddaily:gamerecorddaily:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('603', '541', '活动类型管理', 'apppromotionstype/apppromotionstype', null, '1', 'youhui', '0');
INSERT INTO `sys_menu` VALUES ('604', '603', '查看', null, 'apppromotionstype:apppromotionstype:list,apppromotionstype:apppromotionstype:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('605', '603', '新增', null, 'apppromotionstype:apppromotionstype:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('606', '603', '修改', null, 'apppromotionstype:apppromotionstype:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('607', '541', '活动管理', 'apppromotions/apppromotions', 'apppromotions:apppromotions:list,apppromotions:apppromotions:delete', '1', 'youhui', '1');
INSERT INTO `sys_menu` VALUES ('608', '607', '查看', null, 'apppromotions:apppromotions:list,apppromotions:apppromotions:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('609', '607', '新增', null, 'apppromotions:apppromotions:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('610', '607', '修改', null, 'apppromotions:apppromotions:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('611', '603', '下拉', null, 'apppromotionstype:apppromotionstype:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('612', '334', '支付限额管理', 'paychannelconfig/paychannelconfig', 'paychannelconfig:paychannelconfig:list,paychannelconfig:paychannelconfig:info', '1', 'config', '4');
INSERT INTO `sys_menu` VALUES ('613', '612', '添加', null, 'paychannelconfig:paychannelconfig:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('614', '612', '修改', null, 'paychannelconfig:paychannelconfig:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('615', '612', '删除', null, 'paychannelconfig:paychannelconfig:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('616', '0', '游戏盈利', null, null, '0', 'tubiao', '6');
INSERT INTO `sys_menu` VALUES ('617', '616', '日游戏盈利', 'gameanalysisdaily/gameanalysisdaily', 'gameanalysisdaily:gameanalysisdaily:list,gameanalysisdaily:gameanalysisdaily:info', '1', 'menu', '0');
INSERT INTO `sys_menu` VALUES ('618', '635', '海底宝藏', 'gamerecordunderseatreasure/gamerecordunderseatreasure', 'gamerecordunderseatreasure:gamerecordunderseatreasure:list', '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('619', '618', '查看', null, 'gamerecordunderseatreasure:gamerecordunderseatreasure:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('620', '630', '龙虎斗', 'gamerecordlonghu/gamerecordlonghu', 'gamerecordlonghu:gamerecordlonghu:list', '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('621', '620', '查看', null, 'gamerecordlonghu:gamerecordlonghu:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('622', '630', '百家乐', 'gamerecordbaijia/gamerecordbaijia', 'gamerecordbaijia:gamerecordbaijia:list', '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('623', '622', '查看', null, 'gamerecordbaijia:gamerecordbaijia:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('624', '630', '百人牌九', 'gamerecordbairenpaijiu/gamerecordbairenpaijiu', 'gamerecordpaijiu:gamerecordpaijiu:list', '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('625', '624', '查看', null, 'gamerecordpaijiu:gamerecordpaijiu:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('626', '630', '百人炸金花', 'gamerecordbairenzhajinhua/gamerecordbairenzhajinhua', 'gamerecordzhajinhua:gamerecordzhajinhua:list', '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('627', '626', '查看', null, 'gamerecordzhajinhua:gamerecordzhajinhua:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('630', '375', '百人游戏', null, null, '0', 'menu', '1');
INSERT INTO `sys_menu` VALUES ('631', '630', '百人牛牛', 'gamerecordbairenniuniu/gamerecordbairenniuniu', 'gamerecordbairenniuniu:gamerecordbairenniuniu:list', '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('632', '631', '查看', null, 'gamerecordbairenniuniu:gamerecordbairenniuniu:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('633', '375', '匹配游戏', null, null, '0', 'menu', '1');
INSERT INTO `sys_menu` VALUES ('634', '375', '房卡游戏', null, null, '0', 'menu', '2');
INSERT INTO `sys_menu` VALUES ('635', '375', '电子游戏', null, null, '0', 'menu', '3');

-- ----------------------------
-- Table structure for sys_message_management
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_management`;
CREATE TABLE `sys_message_management` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `content_type` int(2) DEFAULT '1' COMMENT '类型(1：会员站内信 2:管理员站内信）',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（0：禁用  1：启用）',
  `target_object` int(1) DEFAULT '3' COMMENT '目标对象（1：指定用户id 2:指定用户层次(角色) 3：所有用户）',
  `failure_time` datetime DEFAULT NULL COMMENT '失效时间',
  `effect_time` datetime DEFAULT NULL COMMENT '生效时间',
  `hierarchy_ids` varchar(500) DEFAULT '' COMMENT '层级ids',
  `user_account` varchar(1000) DEFAULT '' COMMENT '用户账号集合',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2086 DEFAULT CHARSET=utf8 COMMENT='站内信';

-- ----------------------------
-- Records of sys_message_management
-- ----------------------------

-- ----------------------------
-- Table structure for sys_message_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_user`;
CREATE TABLE `sys_message_user` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `message_id` bigint(22) DEFAULT NULL COMMENT '消息id',
  `user_id` bigint(22) NOT NULL COMMENT '用户id',
  `user_account` varchar(100) NOT NULL COMMENT '用户账号',
  `status` int(2) NOT NULL COMMENT '状态（0：未读 1：已读）',
  `delete_message` bit(1) DEFAULT b'0' COMMENT '站内信删除状态（0：未删 1：已删）',
  PRIMARY KEY (`id`),
  KEY `index_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2373 DEFAULT CHARSET=utf8 COMMENT='站内信用户关系表';

-- ----------------------------
-- Records of sys_message_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_notice_management
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_management`;
CREATE TABLE `sys_notice_management` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `content` varchar(500) NOT NULL COMMENT '内容',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（0：禁用  1：启用）',
  `effect_time` datetime DEFAULT NULL COMMENT '生效时间',
  `failure_time` datetime DEFAULT NULL COMMENT '失效时间',
  `target_object` int(2) DEFAULT '3' COMMENT '目标对象（1：指定用户id 2:指定用户层次(角色) 3：所有用户）',
  `hierarchy_ids` varchar(500) DEFAULT '' COMMENT '层级id集合',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='公告管理';

-- ----------------------------
-- Records of sys_notice_management
-- ----------------------------
INSERT INTO `sys_notice_management` VALUES ('3', '0', '2019-04-11 18:54:48', '2019-04-11 18:54:48', '0', '开业大酬宾', '注册就送金币注册就送金币注册就送金币注册就送金币注册就送金币注册就送金币注册就送金币', '', '2019-04-11 00:00:00', '2019-04-30 18:54:29', '3', '');

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_otp
-- ----------------------------
DROP TABLE IF EXISTS `sys_otp`;
CREATE TABLE `sys_otp` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `otp_secret` varchar(50) DEFAULT '' COMMENT 'otp秘钥',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `bind_ip` varchar(600) DEFAULT '' COMMENT 'otp绑定的ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 COMMENT='用户动态码';

-- ----------------------------
-- Records of sys_otp
-- ----------------------------
INSERT INTO `sys_otp` VALUES ('3', '2019-04-09 18:43:03', '2019-04-09 18:43:03', '0', 'BERQDOOY7AZFEFDI', 'aleng', '202.190.145.4');
INSERT INTO `sys_otp` VALUES ('24', '2019-04-19 14:47:28', '2019-04-19 14:47:28', '0', 'TTMFASD3YZT2QWIN', 'ok123789', '45.125.193.166');
INSERT INTO `sys_otp` VALUES ('31', '2019-04-19 15:12:43', '2019-04-19 15:12:43', '0', '7MLKILKNW5KVQ7W7', 'xiaocai', '45.125.193.166');
INSERT INTO `sys_otp` VALUES ('38', '2019-04-19 15:17:08', '2019-04-19 15:26:21', '0', 'OO3HDQ6NTJUOBB6Z', 'abxamfb79', '45.125.193.166');
INSERT INTO `sys_otp` VALUES ('45', '2019-04-19 16:24:17', '2019-04-19 16:24:17', '0', 'VMR5QQJIHZ7UASVW', 'xiaojiang', '45.125.193.166');
INSERT INTO `sys_otp` VALUES ('52', '2019-04-19 23:58:14', '2019-04-20 01:20:22', '0', 'TIAEMXQ7T2KM3CNQ', 'zuzhang1', '123.108.248.230');
INSERT INTO `sys_otp` VALUES ('59', '2019-04-20 00:16:48', '2019-04-21 21:05:28', '0', 'UK3GZVRSMPWRDFAF', 'chukuan1', '103.17.214.74,123.108.248.230');
INSERT INTO `sys_otp` VALUES ('66', '2019-04-20 00:52:53', '2019-04-20 01:12:00', '0', 'OFLQRDNHJQHIXQUW', 'zuzhang2', '123.108.248.230');
INSERT INTO `sys_otp` VALUES ('73', '2019-04-20 01:00:01', '2019-04-20 01:20:08', '0', 'PSTHHXZ7W2RLWCZF', 'rukuan1', '123.108.248.230');
INSERT INTO `sys_otp` VALUES ('80', '2019-04-20 01:01:31', '2019-04-20 01:12:56', '0', '3ME5O5VZC3UHJWEA', 'rukuan2', '123.108.248.230');
INSERT INTO `sys_otp` VALUES ('87', '2019-04-20 01:02:43', '2019-04-20 01:19:58', '0', 'IUJDPSSH224SZBB7', 'kefu1', '123.108.248.230');
INSERT INTO `sys_otp` VALUES ('94', '2019-04-20 01:03:34', '2019-04-20 01:20:02', '0', 'K73OOIPGTDDIOJKN', 'kefu2', '123.108.248.230');
INSERT INTO `sys_otp` VALUES ('101', '2019-04-20 01:04:19', '2019-04-20 01:12:40', '0', 'S7DEEQGAESUE7LAH', 'kefu3', '123.108.248.230');
INSERT INTO `sys_otp` VALUES ('108', '2019-04-20 01:05:08', '2019-04-20 01:12:47', '0', 'BBBVMIOQKA6ZSIHL', 'kefu4', '123.108.248.230');
INSERT INTO `sys_otp` VALUES ('122', '2019-04-21 20:48:33', '2019-04-21 20:48:33', '0', 'EI4U3P3DHONXO3DJ', 'manager', '45.125.193.166');

-- ----------------------------
-- Table structure for sys_register_necessary
-- ----------------------------
DROP TABLE IF EXISTS `sys_register_necessary`;
CREATE TABLE `sys_register_necessary` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(22) NOT NULL COMMENT '显示名称',
  `show` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否展示',
  `necessary` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否必填',
  `hints` varchar(50) NOT NULL COMMENT '提示语',
  `order_number` varchar(11) NOT NULL COMMENT '序号',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `validation_rule` varchar(500) DEFAULT '' COMMENT '验证规则',
  `validation_describe` varchar(255) DEFAULT '' COMMENT '验证描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='注册必填控制表';

-- ----------------------------
-- Records of sys_register_necessary
-- ----------------------------
INSERT INTO `sys_register_necessary` VALUES ('1', '2019-01-24 14:57:23', '2019-02-28 20:36:46', '0', '0', '身份证', '\0', '\0', '取款需要身份证验证', '1', 'userIdentity', '^[0-9]*$', '身份证错误！');
INSERT INTO `sys_register_necessary` VALUES ('2', '2019-01-24 14:57:23', '2019-02-03 15:35:06', '0', '0', '电话', '\0', '\0', '取款需要电话验证', '2', 'userPhone', '^[0-9]*$', '电话错误！');
INSERT INTO `sys_register_necessary` VALUES ('3', '2019-01-24 14:57:23', '2019-02-28 20:37:32', '0', '0', '邮箱', '\0', '\0', '取款需要邮箱验证', '3', 'userEmail', '^[0-9]*$', '游戏错误');
INSERT INTO `sys_register_necessary` VALUES ('4', '2019-01-24 14:57:23', '2019-02-28 20:37:41', '0', '0', '邮编', '\0', '\0', '取款需要邮编验证', '4', 'postCode', '/^0$|^[1-9]\\d*$/', '邮编错误！');
INSERT INTO `sys_register_necessary` VALUES ('5', '2019-01-24 14:57:23', '2019-02-28 20:37:48', '0', '0', '地址', '\0', '\0', '取款需要地址验证', '5', 'userAddress', '/^0$|^[1-9]\\d*$/', '地址错误！');
INSERT INTO `sys_register_necessary` VALUES ('6', '2019-01-24 14:57:23', '2019-01-24 17:34:23', '0', '0', 'QQ', '\0', '\0', '取款需要QQ验证', '6', 'userQq', '/^0$|^[1-9]\\d*$/', 'QQ显示错误');
INSERT INTO `sys_register_necessary` VALUES ('7', '2019-01-24 14:57:23', '2019-01-24 17:33:30', '0', '0', '生日', '\0', '\0', '取款需要生日验证', '7', 'userBirthday', '/^0$|^[1-9]\\d*$/', '生日错误');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', '系统管理员', '1', '2018-09-16 11:02:46');
INSERT INTO `sys_role` VALUES ('2', '财务管理员', '财务人员使用', '1', '2018-09-16 11:24:06');
INSERT INTO `sys_role` VALUES ('3', '厅主管理员', '厅主管理员', '1', '2018-09-17 10:30:08');
INSERT INTO `sys_role` VALUES ('4', '普通管理员', '普通管理员', '1', '2018-09-21 12:41:55');
INSERT INTO `sys_role` VALUES ('10', 'yang', 'yang', '1', '2019-04-08 15:05:35');
INSERT INTO `sys_role` VALUES ('17', 'xiaocai', '小财', '1', '2019-04-09 19:55:25');
INSERT INTO `sys_role` VALUES ('24', 'lll', 'lll', '1', '2019-04-11 20:14:04');
INSERT INTO `sys_role` VALUES ('38', '阿豹', null, '2', '2019-04-19 14:42:39');
INSERT INTO `sys_role` VALUES ('45', '小财', null, '2', '2019-04-19 15:10:22');
INSERT INTO `sys_role` VALUES ('52', '大富', null, '2', '2019-04-19 15:15:46');
INSERT INTO `sys_role` VALUES ('59', '组长', '组长', '2', '2019-04-19 18:50:06');
INSERT INTO `sys_role` VALUES ('66', '出款人员', '出款人员', '2', '2019-04-19 21:41:02');
INSERT INTO `sys_role` VALUES ('73', '入款人员', '入款人员', '2', '2019-04-19 21:49:14');
INSERT INTO `sys_role` VALUES ('80', '客服人员', '客服人员', '2', '2019-04-19 21:54:50');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53067 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('931', '2', '-666666');
INSERT INTO `sys_role_menu` VALUES ('14066', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('14073', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('14080', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('14087', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('14094', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('14101', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('14108', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('14115', '1', '9');
INSERT INTO `sys_role_menu` VALUES ('14122', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('14129', '1', '11');
INSERT INTO `sys_role_menu` VALUES ('14136', '1', '12');
INSERT INTO `sys_role_menu` VALUES ('14143', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('14150', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('14157', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('14164', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('14171', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('14178', '1', '362');
INSERT INTO `sys_role_menu` VALUES ('14185', '1', '363');
INSERT INTO `sys_role_menu` VALUES ('14192', '1', '364');
INSERT INTO `sys_role_menu` VALUES ('14199', '1', '365');
INSERT INTO `sys_role_menu` VALUES ('14206', '1', '366');
INSERT INTO `sys_role_menu` VALUES ('14213', '1', '386');
INSERT INTO `sys_role_menu` VALUES ('14220', '1', '387');
INSERT INTO `sys_role_menu` VALUES ('14227', '1', '388');
INSERT INTO `sys_role_menu` VALUES ('14234', '1', '389');
INSERT INTO `sys_role_menu` VALUES ('14241', '1', '390');
INSERT INTO `sys_role_menu` VALUES ('14248', '1', '531');
INSERT INTO `sys_role_menu` VALUES ('14255', '1', '17');
INSERT INTO `sys_role_menu` VALUES ('14262', '1', '18');
INSERT INTO `sys_role_menu` VALUES ('14269', '1', '19');
INSERT INTO `sys_role_menu` VALUES ('14276', '1', '20');
INSERT INTO `sys_role_menu` VALUES ('14283', '1', '21');
INSERT INTO `sys_role_menu` VALUES ('14290', '1', '22');
INSERT INTO `sys_role_menu` VALUES ('14297', '1', '23');
INSERT INTO `sys_role_menu` VALUES ('14304', '1', '38');
INSERT INTO `sys_role_menu` VALUES ('14311', '1', '39');
INSERT INTO `sys_role_menu` VALUES ('14318', '1', '40');
INSERT INTO `sys_role_menu` VALUES ('14325', '1', '41');
INSERT INTO `sys_role_menu` VALUES ('14332', '1', '42');
INSERT INTO `sys_role_menu` VALUES ('14339', '1', '315');
INSERT INTO `sys_role_menu` VALUES ('14346', '1', '316');
INSERT INTO `sys_role_menu` VALUES ('14353', '1', '317');
INSERT INTO `sys_role_menu` VALUES ('14360', '1', '351');
INSERT INTO `sys_role_menu` VALUES ('14367', '1', '352');
INSERT INTO `sys_role_menu` VALUES ('14374', '1', '358');
INSERT INTO `sys_role_menu` VALUES ('14381', '1', '359');
INSERT INTO `sys_role_menu` VALUES ('14388', '1', '360');
INSERT INTO `sys_role_menu` VALUES ('14395', '1', '361');
INSERT INTO `sys_role_menu` VALUES ('14402', '1', '470');
INSERT INTO `sys_role_menu` VALUES ('14409', '1', '471');
INSERT INTO `sys_role_menu` VALUES ('14416', '1', '540');
INSERT INTO `sys_role_menu` VALUES ('14423', '1', '24');
INSERT INTO `sys_role_menu` VALUES ('14430', '1', '25');
INSERT INTO `sys_role_menu` VALUES ('14437', '1', '26');
INSERT INTO `sys_role_menu` VALUES ('14444', '1', '345');
INSERT INTO `sys_role_menu` VALUES ('14451', '1', '346');
INSERT INTO `sys_role_menu` VALUES ('14458', '1', '347');
INSERT INTO `sys_role_menu` VALUES ('14465', '1', '348');
INSERT INTO `sys_role_menu` VALUES ('14472', '1', '349');
INSERT INTO `sys_role_menu` VALUES ('14479', '1', '350');
INSERT INTO `sys_role_menu` VALUES ('14486', '1', '353');
INSERT INTO `sys_role_menu` VALUES ('14493', '1', '354');
INSERT INTO `sys_role_menu` VALUES ('14500', '1', '391');
INSERT INTO `sys_role_menu` VALUES ('14507', '1', '549');
INSERT INTO `sys_role_menu` VALUES ('14514', '1', '367');
INSERT INTO `sys_role_menu` VALUES ('14521', '1', '368');
INSERT INTO `sys_role_menu` VALUES ('14528', '1', '369');
INSERT INTO `sys_role_menu` VALUES ('14535', '1', '370');
INSERT INTO `sys_role_menu` VALUES ('14542', '1', '392');
INSERT INTO `sys_role_menu` VALUES ('14549', '1', '393');
INSERT INTO `sys_role_menu` VALUES ('14556', '1', '394');
INSERT INTO `sys_role_menu` VALUES ('14563', '1', '395');
INSERT INTO `sys_role_menu` VALUES ('14570', '1', '534');
INSERT INTO `sys_role_menu` VALUES ('14577', '1', '564');
INSERT INTO `sys_role_menu` VALUES ('14584', '1', '465');
INSERT INTO `sys_role_menu` VALUES ('14591', '1', '466');
INSERT INTO `sys_role_menu` VALUES ('14598', '1', '467');
INSERT INTO `sys_role_menu` VALUES ('14605', '1', '468');
INSERT INTO `sys_role_menu` VALUES ('14612', '1', '469');
INSERT INTO `sys_role_menu` VALUES ('14619', '1', '480');
INSERT INTO `sys_role_menu` VALUES ('14626', '1', '481');
INSERT INTO `sys_role_menu` VALUES ('14633', '1', '497');
INSERT INTO `sys_role_menu` VALUES ('14640', '1', '503');
INSERT INTO `sys_role_menu` VALUES ('14647', '1', '561');
INSERT INTO `sys_role_menu` VALUES ('14654', '1', '563');
INSERT INTO `sys_role_menu` VALUES ('14661', '1', '27');
INSERT INTO `sys_role_menu` VALUES ('14668', '1', '28');
INSERT INTO `sys_role_menu` VALUES ('14675', '1', '29');
INSERT INTO `sys_role_menu` VALUES ('14682', '1', '30');
INSERT INTO `sys_role_menu` VALUES ('14689', '1', '31');
INSERT INTO `sys_role_menu` VALUES ('14696', '1', '32');
INSERT INTO `sys_role_menu` VALUES ('14703', '1', '437');
INSERT INTO `sys_role_menu` VALUES ('14710', '1', '533');
INSERT INTO `sys_role_menu` VALUES ('14717', '1', '33');
INSERT INTO `sys_role_menu` VALUES ('14724', '1', '34');
INSERT INTO `sys_role_menu` VALUES ('14731', '1', '35');
INSERT INTO `sys_role_menu` VALUES ('14738', '1', '36');
INSERT INTO `sys_role_menu` VALUES ('14745', '1', '37');
INSERT INTO `sys_role_menu` VALUES ('14752', '1', '294');
INSERT INTO `sys_role_menu` VALUES ('14759', '1', '295');
INSERT INTO `sys_role_menu` VALUES ('14766', '1', '296');
INSERT INTO `sys_role_menu` VALUES ('14773', '1', '417');
INSERT INTO `sys_role_menu` VALUES ('14780', '1', '486');
INSERT INTO `sys_role_menu` VALUES ('14787', '1', '488');
INSERT INTO `sys_role_menu` VALUES ('14794', '1', '303');
INSERT INTO `sys_role_menu` VALUES ('14801', '1', '304');
INSERT INTO `sys_role_menu` VALUES ('14808', '1', '305');
INSERT INTO `sys_role_menu` VALUES ('14815', '1', '306');
INSERT INTO `sys_role_menu` VALUES ('14822', '1', '307');
INSERT INTO `sys_role_menu` VALUES ('14829', '1', '330');
INSERT INTO `sys_role_menu` VALUES ('14836', '1', '331');
INSERT INTO `sys_role_menu` VALUES ('14843', '1', '332');
INSERT INTO `sys_role_menu` VALUES ('14850', '1', '333');
INSERT INTO `sys_role_menu` VALUES ('14857', '1', '551');
INSERT INTO `sys_role_menu` VALUES ('14864', '1', '423');
INSERT INTO `sys_role_menu` VALUES ('14871', '1', '424');
INSERT INTO `sys_role_menu` VALUES ('14878', '1', '425');
INSERT INTO `sys_role_menu` VALUES ('14885', '1', '426');
INSERT INTO `sys_role_menu` VALUES ('14892', '1', '427');
INSERT INTO `sys_role_menu` VALUES ('14899', '1', '482');
INSERT INTO `sys_role_menu` VALUES ('14906', '1', '483');
INSERT INTO `sys_role_menu` VALUES ('14913', '1', '484');
INSERT INTO `sys_role_menu` VALUES ('14920', '1', '485');
INSERT INTO `sys_role_menu` VALUES ('14927', '1', '550');
INSERT INTO `sys_role_menu` VALUES ('14934', '1', '297');
INSERT INTO `sys_role_menu` VALUES ('14941', '1', '298');
INSERT INTO `sys_role_menu` VALUES ('14948', '1', '299');
INSERT INTO `sys_role_menu` VALUES ('14955', '1', '300');
INSERT INTO `sys_role_menu` VALUES ('14962', '1', '301');
INSERT INTO `sys_role_menu` VALUES ('14969', '1', '302');
INSERT INTO `sys_role_menu` VALUES ('14976', '1', '308');
INSERT INTO `sys_role_menu` VALUES ('14983', '1', '309');
INSERT INTO `sys_role_menu` VALUES ('14990', '1', '310');
INSERT INTO `sys_role_menu` VALUES ('14997', '1', '311');
INSERT INTO `sys_role_menu` VALUES ('15004', '1', '312');
INSERT INTO `sys_role_menu` VALUES ('15011', '1', '428');
INSERT INTO `sys_role_menu` VALUES ('15018', '1', '429');
INSERT INTO `sys_role_menu` VALUES ('15025', '1', '430');
INSERT INTO `sys_role_menu` VALUES ('15032', '1', '431');
INSERT INTO `sys_role_menu` VALUES ('15039', '1', '499');
INSERT INTO `sys_role_menu` VALUES ('15046', '1', '500');
INSERT INTO `sys_role_menu` VALUES ('15053', '1', '472');
INSERT INTO `sys_role_menu` VALUES ('15060', '1', '473');
INSERT INTO `sys_role_menu` VALUES ('15067', '1', '474');
INSERT INTO `sys_role_menu` VALUES ('15074', '1', '475');
INSERT INTO `sys_role_menu` VALUES ('15081', '1', '494');
INSERT INTO `sys_role_menu` VALUES ('15088', '1', '476');
INSERT INTO `sys_role_menu` VALUES ('15095', '1', '477');
INSERT INTO `sys_role_menu` VALUES ('15102', '1', '478');
INSERT INTO `sys_role_menu` VALUES ('15109', '1', '479');
INSERT INTO `sys_role_menu` VALUES ('15116', '1', '502');
INSERT INTO `sys_role_menu` VALUES ('15123', '1', '318');
INSERT INTO `sys_role_menu` VALUES ('15130', '1', '319');
INSERT INTO `sys_role_menu` VALUES ('15137', '1', '320');
INSERT INTO `sys_role_menu` VALUES ('15144', '1', '321');
INSERT INTO `sys_role_menu` VALUES ('15151', '1', '322');
INSERT INTO `sys_role_menu` VALUES ('15158', '1', '323');
INSERT INTO `sys_role_menu` VALUES ('15165', '1', '324');
INSERT INTO `sys_role_menu` VALUES ('15172', '1', '325');
INSERT INTO `sys_role_menu` VALUES ('15179', '1', '326');
INSERT INTO `sys_role_menu` VALUES ('15186', '1', '327');
INSERT INTO `sys_role_menu` VALUES ('15193', '1', '328');
INSERT INTO `sys_role_menu` VALUES ('15200', '1', '329');
INSERT INTO `sys_role_menu` VALUES ('15207', '1', '415');
INSERT INTO `sys_role_menu` VALUES ('15214', '1', '416');
INSERT INTO `sys_role_menu` VALUES ('15221', '1', '562');
INSERT INTO `sys_role_menu` VALUES ('15228', '1', '419');
INSERT INTO `sys_role_menu` VALUES ('15235', '1', '420');
INSERT INTO `sys_role_menu` VALUES ('15242', '1', '421');
INSERT INTO `sys_role_menu` VALUES ('15249', '1', '422');
INSERT INTO `sys_role_menu` VALUES ('15256', '1', '496');
INSERT INTO `sys_role_menu` VALUES ('15263', '1', '334');
INSERT INTO `sys_role_menu` VALUES ('15270', '1', '337');
INSERT INTO `sys_role_menu` VALUES ('15277', '1', '338');
INSERT INTO `sys_role_menu` VALUES ('15284', '1', '339');
INSERT INTO `sys_role_menu` VALUES ('15291', '1', '340');
INSERT INTO `sys_role_menu` VALUES ('15298', '1', '341');
INSERT INTO `sys_role_menu` VALUES ('15305', '1', '504');
INSERT INTO `sys_role_menu` VALUES ('15312', '1', '505');
INSERT INTO `sys_role_menu` VALUES ('15319', '1', '506');
INSERT INTO `sys_role_menu` VALUES ('15326', '1', '507');
INSERT INTO `sys_role_menu` VALUES ('15333', '1', '508');
INSERT INTO `sys_role_menu` VALUES ('15340', '1', '532');
INSERT INTO `sys_role_menu` VALUES ('15347', '1', '552');
INSERT INTO `sys_role_menu` VALUES ('15354', '1', '553');
INSERT INTO `sys_role_menu` VALUES ('15361', '1', '554');
INSERT INTO `sys_role_menu` VALUES ('15368', '1', '558');
INSERT INTO `sys_role_menu` VALUES ('15375', '1', '555');
INSERT INTO `sys_role_menu` VALUES ('15382', '1', '556');
INSERT INTO `sys_role_menu` VALUES ('15389', '1', '557');
INSERT INTO `sys_role_menu` VALUES ('15396', '1', '612');
INSERT INTO `sys_role_menu` VALUES ('15403', '1', '613');
INSERT INTO `sys_role_menu` VALUES ('15410', '1', '614');
INSERT INTO `sys_role_menu` VALUES ('15417', '1', '615');
INSERT INTO `sys_role_menu` VALUES ('15424', '1', '375');
INSERT INTO `sys_role_menu` VALUES ('15508', '1', '407');
INSERT INTO `sys_role_menu` VALUES ('15515', '1', '408');
INSERT INTO `sys_role_menu` VALUES ('15592', '1', '526');
INSERT INTO `sys_role_menu` VALUES ('15599', '1', '527');
INSERT INTO `sys_role_menu` VALUES ('15606', '1', '528');
INSERT INTO `sys_role_menu` VALUES ('15613', '1', '529');
INSERT INTO `sys_role_menu` VALUES ('15620', '1', '530');
INSERT INTO `sys_role_menu` VALUES ('15627', '1', '535');
INSERT INTO `sys_role_menu` VALUES ('15634', '1', '536');
INSERT INTO `sys_role_menu` VALUES ('15641', '1', '537');
INSERT INTO `sys_role_menu` VALUES ('15648', '1', '538');
INSERT INTO `sys_role_menu` VALUES ('15655', '1', '539');
INSERT INTO `sys_role_menu` VALUES ('15662', '1', '568');
INSERT INTO `sys_role_menu` VALUES ('15669', '1', '569');
INSERT INTO `sys_role_menu` VALUES ('15676', '1', '570');
INSERT INTO `sys_role_menu` VALUES ('15683', '1', '571');
INSERT INTO `sys_role_menu` VALUES ('15690', '1', '572');
INSERT INTO `sys_role_menu` VALUES ('15697', '1', '573');
INSERT INTO `sys_role_menu` VALUES ('15704', '1', '574');
INSERT INTO `sys_role_menu` VALUES ('15711', '1', '575');
INSERT INTO `sys_role_menu` VALUES ('15718', '1', '576');
INSERT INTO `sys_role_menu` VALUES ('15725', '1', '577');
INSERT INTO `sys_role_menu` VALUES ('15732', '1', '578');
INSERT INTO `sys_role_menu` VALUES ('15739', '1', '579');
INSERT INTO `sys_role_menu` VALUES ('15746', '1', '580');
INSERT INTO `sys_role_menu` VALUES ('15753', '1', '581');
INSERT INTO `sys_role_menu` VALUES ('15760', '1', '582');
INSERT INTO `sys_role_menu` VALUES ('15767', '1', '583');
INSERT INTO `sys_role_menu` VALUES ('15774', '1', '584');
INSERT INTO `sys_role_menu` VALUES ('15781', '1', '585');
INSERT INTO `sys_role_menu` VALUES ('15788', '1', '586');
INSERT INTO `sys_role_menu` VALUES ('15795', '1', '587');
INSERT INTO `sys_role_menu` VALUES ('15802', '1', '588');
INSERT INTO `sys_role_menu` VALUES ('15809', '1', '589');
INSERT INTO `sys_role_menu` VALUES ('15816', '1', '590');
INSERT INTO `sys_role_menu` VALUES ('15823', '1', '591');
INSERT INTO `sys_role_menu` VALUES ('15830', '1', '592');
INSERT INTO `sys_role_menu` VALUES ('15837', '1', '593');
INSERT INTO `sys_role_menu` VALUES ('15844', '1', '594');
INSERT INTO `sys_role_menu` VALUES ('15851', '1', '595');
INSERT INTO `sys_role_menu` VALUES ('15858', '1', '596');
INSERT INTO `sys_role_menu` VALUES ('15865', '1', '597');
INSERT INTO `sys_role_menu` VALUES ('15872', '1', '598');
INSERT INTO `sys_role_menu` VALUES ('15879', '1', '599');
INSERT INTO `sys_role_menu` VALUES ('15886', '1', '600');
INSERT INTO `sys_role_menu` VALUES ('15893', '1', '601');
INSERT INTO `sys_role_menu` VALUES ('15900', '1', '602');
INSERT INTO `sys_role_menu` VALUES ('15907', '1', '396');
INSERT INTO `sys_role_menu` VALUES ('15914', '1', '397');
INSERT INTO `sys_role_menu` VALUES ('15921', '1', '398');
INSERT INTO `sys_role_menu` VALUES ('15928', '1', '399');
INSERT INTO `sys_role_menu` VALUES ('15935', '1', '400');
INSERT INTO `sys_role_menu` VALUES ('15942', '1', '401');
INSERT INTO `sys_role_menu` VALUES ('15949', '1', '402');
INSERT INTO `sys_role_menu` VALUES ('15956', '1', '403');
INSERT INTO `sys_role_menu` VALUES ('15963', '1', '404');
INSERT INTO `sys_role_menu` VALUES ('15970', '1', '405');
INSERT INTO `sys_role_menu` VALUES ('15977', '1', '406');
INSERT INTO `sys_role_menu` VALUES ('15984', '1', '440');
INSERT INTO `sys_role_menu` VALUES ('15991', '1', '441');
INSERT INTO `sys_role_menu` VALUES ('15998', '1', '442');
INSERT INTO `sys_role_menu` VALUES ('16005', '1', '443');
INSERT INTO `sys_role_menu` VALUES ('16012', '1', '444');
INSERT INTO `sys_role_menu` VALUES ('16019', '1', '445');
INSERT INTO `sys_role_menu` VALUES ('16026', '1', '446');
INSERT INTO `sys_role_menu` VALUES ('16033', '1', '447');
INSERT INTO `sys_role_menu` VALUES ('16040', '1', '448');
INSERT INTO `sys_role_menu` VALUES ('16047', '1', '449');
INSERT INTO `sys_role_menu` VALUES ('16054', '1', '450');
INSERT INTO `sys_role_menu` VALUES ('16061', '1', '451');
INSERT INTO `sys_role_menu` VALUES ('16068', '1', '452');
INSERT INTO `sys_role_menu` VALUES ('16075', '1', '453');
INSERT INTO `sys_role_menu` VALUES ('16082', '1', '454');
INSERT INTO `sys_role_menu` VALUES ('16089', '1', '455');
INSERT INTO `sys_role_menu` VALUES ('16096', '1', '456');
INSERT INTO `sys_role_menu` VALUES ('16103', '1', '457');
INSERT INTO `sys_role_menu` VALUES ('16110', '1', '458');
INSERT INTO `sys_role_menu` VALUES ('16117', '1', '459');
INSERT INTO `sys_role_menu` VALUES ('16124', '1', '460');
INSERT INTO `sys_role_menu` VALUES ('16131', '1', '461');
INSERT INTO `sys_role_menu` VALUES ('16138', '1', '462');
INSERT INTO `sys_role_menu` VALUES ('16145', '1', '463');
INSERT INTO `sys_role_menu` VALUES ('16152', '1', '464');
INSERT INTO `sys_role_menu` VALUES ('16159', '1', '489');
INSERT INTO `sys_role_menu` VALUES ('16166', '1', '490');
INSERT INTO `sys_role_menu` VALUES ('16173', '1', '559');
INSERT INTO `sys_role_menu` VALUES ('16180', '1', '491');
INSERT INTO `sys_role_menu` VALUES ('16187', '1', '565');
INSERT INTO `sys_role_menu` VALUES ('16194', '1', '566');
INSERT INTO `sys_role_menu` VALUES ('16201', '1', '492');
INSERT INTO `sys_role_menu` VALUES ('16208', '1', '560');
INSERT INTO `sys_role_menu` VALUES ('16215', '1', '493');
INSERT INTO `sys_role_menu` VALUES ('16222', '1', '567');
INSERT INTO `sys_role_menu` VALUES ('16229', '1', '495');
INSERT INTO `sys_role_menu` VALUES ('16236', '1', '498');
INSERT INTO `sys_role_menu` VALUES ('16243', '1', '501');
INSERT INTO `sys_role_menu` VALUES ('16250', '1', '541');
INSERT INTO `sys_role_menu` VALUES ('16257', '1', '542');
INSERT INTO `sys_role_menu` VALUES ('16264', '1', '543');
INSERT INTO `sys_role_menu` VALUES ('16271', '1', '548');
INSERT INTO `sys_role_menu` VALUES ('16278', '1', '544');
INSERT INTO `sys_role_menu` VALUES ('16285', '1', '547');
INSERT INTO `sys_role_menu` VALUES ('16292', '1', '603');
INSERT INTO `sys_role_menu` VALUES ('16299', '1', '604');
INSERT INTO `sys_role_menu` VALUES ('16306', '1', '605');
INSERT INTO `sys_role_menu` VALUES ('16313', '1', '606');
INSERT INTO `sys_role_menu` VALUES ('16320', '1', '611');
INSERT INTO `sys_role_menu` VALUES ('16327', '1', '607');
INSERT INTO `sys_role_menu` VALUES ('16334', '1', '608');
INSERT INTO `sys_role_menu` VALUES ('16341', '1', '609');
INSERT INTO `sys_role_menu` VALUES ('16348', '1', '610');
INSERT INTO `sys_role_menu` VALUES ('16355', '1', '-666666');
INSERT INTO `sys_role_menu` VALUES ('18539', '4', '2');
INSERT INTO `sys_role_menu` VALUES ('18546', '4', '5');
INSERT INTO `sys_role_menu` VALUES ('18553', '4', '6');
INSERT INTO `sys_role_menu` VALUES ('18560', '4', '7');
INSERT INTO `sys_role_menu` VALUES ('18567', '4', '8');
INSERT INTO `sys_role_menu` VALUES ('18574', '4', '362');
INSERT INTO `sys_role_menu` VALUES ('18581', '4', '363');
INSERT INTO `sys_role_menu` VALUES ('18588', '4', '364');
INSERT INTO `sys_role_menu` VALUES ('18595', '4', '365');
INSERT INTO `sys_role_menu` VALUES ('18602', '4', '366');
INSERT INTO `sys_role_menu` VALUES ('18609', '4', '17');
INSERT INTO `sys_role_menu` VALUES ('18616', '4', '18');
INSERT INTO `sys_role_menu` VALUES ('18623', '4', '19');
INSERT INTO `sys_role_menu` VALUES ('18630', '4', '20');
INSERT INTO `sys_role_menu` VALUES ('18637', '4', '21');
INSERT INTO `sys_role_menu` VALUES ('18644', '4', '22');
INSERT INTO `sys_role_menu` VALUES ('18651', '4', '23');
INSERT INTO `sys_role_menu` VALUES ('18658', '4', '38');
INSERT INTO `sys_role_menu` VALUES ('18665', '4', '39');
INSERT INTO `sys_role_menu` VALUES ('18672', '4', '40');
INSERT INTO `sys_role_menu` VALUES ('18679', '4', '41');
INSERT INTO `sys_role_menu` VALUES ('18686', '4', '42');
INSERT INTO `sys_role_menu` VALUES ('18693', '4', '315');
INSERT INTO `sys_role_menu` VALUES ('18700', '4', '316');
INSERT INTO `sys_role_menu` VALUES ('18707', '4', '317');
INSERT INTO `sys_role_menu` VALUES ('18714', '4', '351');
INSERT INTO `sys_role_menu` VALUES ('18721', '4', '352');
INSERT INTO `sys_role_menu` VALUES ('18728', '4', '358');
INSERT INTO `sys_role_menu` VALUES ('18735', '4', '359');
INSERT INTO `sys_role_menu` VALUES ('18742', '4', '360');
INSERT INTO `sys_role_menu` VALUES ('18749', '4', '361');
INSERT INTO `sys_role_menu` VALUES ('18756', '4', '470');
INSERT INTO `sys_role_menu` VALUES ('18763', '4', '471');
INSERT INTO `sys_role_menu` VALUES ('18770', '4', '540');
INSERT INTO `sys_role_menu` VALUES ('18777', '4', '24');
INSERT INTO `sys_role_menu` VALUES ('18784', '4', '25');
INSERT INTO `sys_role_menu` VALUES ('18791', '4', '26');
INSERT INTO `sys_role_menu` VALUES ('18798', '4', '345');
INSERT INTO `sys_role_menu` VALUES ('18805', '4', '346');
INSERT INTO `sys_role_menu` VALUES ('18812', '4', '347');
INSERT INTO `sys_role_menu` VALUES ('18819', '4', '348');
INSERT INTO `sys_role_menu` VALUES ('18826', '4', '349');
INSERT INTO `sys_role_menu` VALUES ('18833', '4', '350');
INSERT INTO `sys_role_menu` VALUES ('18840', '4', '353');
INSERT INTO `sys_role_menu` VALUES ('18847', '4', '354');
INSERT INTO `sys_role_menu` VALUES ('18854', '4', '391');
INSERT INTO `sys_role_menu` VALUES ('18861', '4', '549');
INSERT INTO `sys_role_menu` VALUES ('18868', '4', '367');
INSERT INTO `sys_role_menu` VALUES ('18875', '4', '368');
INSERT INTO `sys_role_menu` VALUES ('18882', '4', '369');
INSERT INTO `sys_role_menu` VALUES ('18889', '4', '370');
INSERT INTO `sys_role_menu` VALUES ('18896', '4', '392');
INSERT INTO `sys_role_menu` VALUES ('18903', '4', '393');
INSERT INTO `sys_role_menu` VALUES ('18910', '4', '394');
INSERT INTO `sys_role_menu` VALUES ('18917', '4', '395');
INSERT INTO `sys_role_menu` VALUES ('18924', '4', '534');
INSERT INTO `sys_role_menu` VALUES ('18931', '4', '564');
INSERT INTO `sys_role_menu` VALUES ('18938', '4', '465');
INSERT INTO `sys_role_menu` VALUES ('18945', '4', '466');
INSERT INTO `sys_role_menu` VALUES ('18952', '4', '467');
INSERT INTO `sys_role_menu` VALUES ('18959', '4', '468');
INSERT INTO `sys_role_menu` VALUES ('18966', '4', '469');
INSERT INTO `sys_role_menu` VALUES ('18973', '4', '480');
INSERT INTO `sys_role_menu` VALUES ('18980', '4', '481');
INSERT INTO `sys_role_menu` VALUES ('18987', '4', '497');
INSERT INTO `sys_role_menu` VALUES ('18994', '4', '503');
INSERT INTO `sys_role_menu` VALUES ('19001', '4', '561');
INSERT INTO `sys_role_menu` VALUES ('19008', '4', '563');
INSERT INTO `sys_role_menu` VALUES ('19015', '4', '297');
INSERT INTO `sys_role_menu` VALUES ('19022', '4', '298');
INSERT INTO `sys_role_menu` VALUES ('19029', '4', '299');
INSERT INTO `sys_role_menu` VALUES ('19036', '4', '300');
INSERT INTO `sys_role_menu` VALUES ('19043', '4', '301');
INSERT INTO `sys_role_menu` VALUES ('19050', '4', '302');
INSERT INTO `sys_role_menu` VALUES ('19057', '4', '308');
INSERT INTO `sys_role_menu` VALUES ('19064', '4', '309');
INSERT INTO `sys_role_menu` VALUES ('19071', '4', '310');
INSERT INTO `sys_role_menu` VALUES ('19078', '4', '311');
INSERT INTO `sys_role_menu` VALUES ('19085', '4', '312');
INSERT INTO `sys_role_menu` VALUES ('19092', '4', '428');
INSERT INTO `sys_role_menu` VALUES ('19099', '4', '429');
INSERT INTO `sys_role_menu` VALUES ('19106', '4', '430');
INSERT INTO `sys_role_menu` VALUES ('19113', '4', '431');
INSERT INTO `sys_role_menu` VALUES ('19120', '4', '499');
INSERT INTO `sys_role_menu` VALUES ('19127', '4', '500');
INSERT INTO `sys_role_menu` VALUES ('19134', '4', '472');
INSERT INTO `sys_role_menu` VALUES ('19141', '4', '473');
INSERT INTO `sys_role_menu` VALUES ('19148', '4', '474');
INSERT INTO `sys_role_menu` VALUES ('19155', '4', '475');
INSERT INTO `sys_role_menu` VALUES ('19162', '4', '494');
INSERT INTO `sys_role_menu` VALUES ('19169', '4', '476');
INSERT INTO `sys_role_menu` VALUES ('19176', '4', '477');
INSERT INTO `sys_role_menu` VALUES ('19183', '4', '478');
INSERT INTO `sys_role_menu` VALUES ('19190', '4', '479');
INSERT INTO `sys_role_menu` VALUES ('19197', '4', '502');
INSERT INTO `sys_role_menu` VALUES ('19204', '4', '318');
INSERT INTO `sys_role_menu` VALUES ('19211', '4', '319');
INSERT INTO `sys_role_menu` VALUES ('19218', '4', '320');
INSERT INTO `sys_role_menu` VALUES ('19225', '4', '321');
INSERT INTO `sys_role_menu` VALUES ('19232', '4', '322');
INSERT INTO `sys_role_menu` VALUES ('19239', '4', '323');
INSERT INTO `sys_role_menu` VALUES ('19246', '4', '324');
INSERT INTO `sys_role_menu` VALUES ('19253', '4', '325');
INSERT INTO `sys_role_menu` VALUES ('19260', '4', '326');
INSERT INTO `sys_role_menu` VALUES ('19267', '4', '327');
INSERT INTO `sys_role_menu` VALUES ('19274', '4', '328');
INSERT INTO `sys_role_menu` VALUES ('19281', '4', '329');
INSERT INTO `sys_role_menu` VALUES ('19288', '4', '415');
INSERT INTO `sys_role_menu` VALUES ('19295', '4', '416');
INSERT INTO `sys_role_menu` VALUES ('19302', '4', '562');
INSERT INTO `sys_role_menu` VALUES ('19309', '4', '419');
INSERT INTO `sys_role_menu` VALUES ('19316', '4', '420');
INSERT INTO `sys_role_menu` VALUES ('19323', '4', '421');
INSERT INTO `sys_role_menu` VALUES ('19330', '4', '422');
INSERT INTO `sys_role_menu` VALUES ('19337', '4', '496');
INSERT INTO `sys_role_menu` VALUES ('19344', '4', '334');
INSERT INTO `sys_role_menu` VALUES ('19351', '4', '337');
INSERT INTO `sys_role_menu` VALUES ('19358', '4', '338');
INSERT INTO `sys_role_menu` VALUES ('19365', '4', '339');
INSERT INTO `sys_role_menu` VALUES ('19372', '4', '340');
INSERT INTO `sys_role_menu` VALUES ('19379', '4', '341');
INSERT INTO `sys_role_menu` VALUES ('19386', '4', '504');
INSERT INTO `sys_role_menu` VALUES ('19393', '4', '505');
INSERT INTO `sys_role_menu` VALUES ('19400', '4', '506');
INSERT INTO `sys_role_menu` VALUES ('19407', '4', '507');
INSERT INTO `sys_role_menu` VALUES ('19414', '4', '508');
INSERT INTO `sys_role_menu` VALUES ('19421', '4', '532');
INSERT INTO `sys_role_menu` VALUES ('19428', '4', '552');
INSERT INTO `sys_role_menu` VALUES ('19435', '4', '553');
INSERT INTO `sys_role_menu` VALUES ('19442', '4', '554');
INSERT INTO `sys_role_menu` VALUES ('19449', '4', '558');
INSERT INTO `sys_role_menu` VALUES ('19456', '4', '555');
INSERT INTO `sys_role_menu` VALUES ('19463', '4', '556');
INSERT INTO `sys_role_menu` VALUES ('19470', '4', '557');
INSERT INTO `sys_role_menu` VALUES ('19477', '4', '612');
INSERT INTO `sys_role_menu` VALUES ('19484', '4', '613');
INSERT INTO `sys_role_menu` VALUES ('19491', '4', '614');
INSERT INTO `sys_role_menu` VALUES ('19498', '4', '615');
INSERT INTO `sys_role_menu` VALUES ('19505', '4', '396');
INSERT INTO `sys_role_menu` VALUES ('19512', '4', '397');
INSERT INTO `sys_role_menu` VALUES ('19519', '4', '398');
INSERT INTO `sys_role_menu` VALUES ('19526', '4', '399');
INSERT INTO `sys_role_menu` VALUES ('19533', '4', '400');
INSERT INTO `sys_role_menu` VALUES ('19540', '4', '401');
INSERT INTO `sys_role_menu` VALUES ('19547', '4', '402');
INSERT INTO `sys_role_menu` VALUES ('19554', '4', '403');
INSERT INTO `sys_role_menu` VALUES ('19561', '4', '404');
INSERT INTO `sys_role_menu` VALUES ('19568', '4', '405');
INSERT INTO `sys_role_menu` VALUES ('19575', '4', '406');
INSERT INTO `sys_role_menu` VALUES ('19582', '4', '440');
INSERT INTO `sys_role_menu` VALUES ('19589', '4', '441');
INSERT INTO `sys_role_menu` VALUES ('19596', '4', '442');
INSERT INTO `sys_role_menu` VALUES ('19603', '4', '443');
INSERT INTO `sys_role_menu` VALUES ('19610', '4', '444');
INSERT INTO `sys_role_menu` VALUES ('19617', '4', '445');
INSERT INTO `sys_role_menu` VALUES ('19624', '4', '446');
INSERT INTO `sys_role_menu` VALUES ('19631', '4', '447');
INSERT INTO `sys_role_menu` VALUES ('19638', '4', '448');
INSERT INTO `sys_role_menu` VALUES ('19645', '4', '449');
INSERT INTO `sys_role_menu` VALUES ('19652', '4', '450');
INSERT INTO `sys_role_menu` VALUES ('19659', '4', '451');
INSERT INTO `sys_role_menu` VALUES ('19666', '4', '452');
INSERT INTO `sys_role_menu` VALUES ('19673', '4', '453');
INSERT INTO `sys_role_menu` VALUES ('19680', '4', '454');
INSERT INTO `sys_role_menu` VALUES ('19687', '4', '455');
INSERT INTO `sys_role_menu` VALUES ('19694', '4', '456');
INSERT INTO `sys_role_menu` VALUES ('19701', '4', '457');
INSERT INTO `sys_role_menu` VALUES ('19708', '4', '458');
INSERT INTO `sys_role_menu` VALUES ('19715', '4', '459');
INSERT INTO `sys_role_menu` VALUES ('19722', '4', '460');
INSERT INTO `sys_role_menu` VALUES ('19729', '4', '461');
INSERT INTO `sys_role_menu` VALUES ('19736', '4', '462');
INSERT INTO `sys_role_menu` VALUES ('19743', '4', '463');
INSERT INTO `sys_role_menu` VALUES ('19750', '4', '464');
INSERT INTO `sys_role_menu` VALUES ('19757', '4', '489');
INSERT INTO `sys_role_menu` VALUES ('19764', '4', '490');
INSERT INTO `sys_role_menu` VALUES ('19771', '4', '559');
INSERT INTO `sys_role_menu` VALUES ('19778', '4', '491');
INSERT INTO `sys_role_menu` VALUES ('19785', '4', '565');
INSERT INTO `sys_role_menu` VALUES ('19792', '4', '566');
INSERT INTO `sys_role_menu` VALUES ('19799', '4', '492');
INSERT INTO `sys_role_menu` VALUES ('19806', '4', '560');
INSERT INTO `sys_role_menu` VALUES ('19813', '4', '493');
INSERT INTO `sys_role_menu` VALUES ('19820', '4', '567');
INSERT INTO `sys_role_menu` VALUES ('19827', '4', '495');
INSERT INTO `sys_role_menu` VALUES ('19834', '4', '498');
INSERT INTO `sys_role_menu` VALUES ('19841', '4', '501');
INSERT INTO `sys_role_menu` VALUES ('19848', '4', '-666666');
INSERT INTO `sys_role_menu` VALUES ('19855', '4', '1');
INSERT INTO `sys_role_menu` VALUES ('19862', '10', '1');
INSERT INTO `sys_role_menu` VALUES ('19869', '10', '2');
INSERT INTO `sys_role_menu` VALUES ('19876', '10', '5');
INSERT INTO `sys_role_menu` VALUES ('19883', '10', '6');
INSERT INTO `sys_role_menu` VALUES ('19890', '10', '7');
INSERT INTO `sys_role_menu` VALUES ('19897', '10', '8');
INSERT INTO `sys_role_menu` VALUES ('19904', '10', '3');
INSERT INTO `sys_role_menu` VALUES ('19911', '10', '9');
INSERT INTO `sys_role_menu` VALUES ('19918', '10', '10');
INSERT INTO `sys_role_menu` VALUES ('19925', '10', '11');
INSERT INTO `sys_role_menu` VALUES ('19932', '10', '12');
INSERT INTO `sys_role_menu` VALUES ('19939', '10', '4');
INSERT INTO `sys_role_menu` VALUES ('19946', '10', '13');
INSERT INTO `sys_role_menu` VALUES ('19953', '10', '14');
INSERT INTO `sys_role_menu` VALUES ('19960', '10', '15');
INSERT INTO `sys_role_menu` VALUES ('19967', '10', '16');
INSERT INTO `sys_role_menu` VALUES ('19974', '10', '362');
INSERT INTO `sys_role_menu` VALUES ('19981', '10', '363');
INSERT INTO `sys_role_menu` VALUES ('19988', '10', '364');
INSERT INTO `sys_role_menu` VALUES ('19995', '10', '365');
INSERT INTO `sys_role_menu` VALUES ('20002', '10', '366');
INSERT INTO `sys_role_menu` VALUES ('20009', '10', '386');
INSERT INTO `sys_role_menu` VALUES ('20016', '10', '387');
INSERT INTO `sys_role_menu` VALUES ('20023', '10', '388');
INSERT INTO `sys_role_menu` VALUES ('20030', '10', '389');
INSERT INTO `sys_role_menu` VALUES ('20037', '10', '390');
INSERT INTO `sys_role_menu` VALUES ('20044', '10', '531');
INSERT INTO `sys_role_menu` VALUES ('20051', '10', '17');
INSERT INTO `sys_role_menu` VALUES ('20058', '10', '18');
INSERT INTO `sys_role_menu` VALUES ('20065', '10', '19');
INSERT INTO `sys_role_menu` VALUES ('20072', '10', '20');
INSERT INTO `sys_role_menu` VALUES ('20079', '10', '21');
INSERT INTO `sys_role_menu` VALUES ('20086', '10', '22');
INSERT INTO `sys_role_menu` VALUES ('20093', '10', '23');
INSERT INTO `sys_role_menu` VALUES ('20100', '10', '38');
INSERT INTO `sys_role_menu` VALUES ('20107', '10', '39');
INSERT INTO `sys_role_menu` VALUES ('20114', '10', '40');
INSERT INTO `sys_role_menu` VALUES ('20121', '10', '41');
INSERT INTO `sys_role_menu` VALUES ('20128', '10', '42');
INSERT INTO `sys_role_menu` VALUES ('20135', '10', '315');
INSERT INTO `sys_role_menu` VALUES ('20142', '10', '316');
INSERT INTO `sys_role_menu` VALUES ('20149', '10', '317');
INSERT INTO `sys_role_menu` VALUES ('20156', '10', '351');
INSERT INTO `sys_role_menu` VALUES ('20163', '10', '352');
INSERT INTO `sys_role_menu` VALUES ('20170', '10', '358');
INSERT INTO `sys_role_menu` VALUES ('20177', '10', '359');
INSERT INTO `sys_role_menu` VALUES ('20184', '10', '360');
INSERT INTO `sys_role_menu` VALUES ('20191', '10', '361');
INSERT INTO `sys_role_menu` VALUES ('20198', '10', '470');
INSERT INTO `sys_role_menu` VALUES ('20205', '10', '471');
INSERT INTO `sys_role_menu` VALUES ('20212', '10', '540');
INSERT INTO `sys_role_menu` VALUES ('20219', '10', '24');
INSERT INTO `sys_role_menu` VALUES ('20226', '10', '25');
INSERT INTO `sys_role_menu` VALUES ('20233', '10', '26');
INSERT INTO `sys_role_menu` VALUES ('20240', '10', '345');
INSERT INTO `sys_role_menu` VALUES ('20247', '10', '346');
INSERT INTO `sys_role_menu` VALUES ('20254', '10', '347');
INSERT INTO `sys_role_menu` VALUES ('20261', '10', '348');
INSERT INTO `sys_role_menu` VALUES ('20268', '10', '349');
INSERT INTO `sys_role_menu` VALUES ('20275', '10', '350');
INSERT INTO `sys_role_menu` VALUES ('20282', '10', '353');
INSERT INTO `sys_role_menu` VALUES ('20289', '10', '354');
INSERT INTO `sys_role_menu` VALUES ('20296', '10', '391');
INSERT INTO `sys_role_menu` VALUES ('20303', '10', '549');
INSERT INTO `sys_role_menu` VALUES ('20310', '10', '367');
INSERT INTO `sys_role_menu` VALUES ('20317', '10', '368');
INSERT INTO `sys_role_menu` VALUES ('20324', '10', '369');
INSERT INTO `sys_role_menu` VALUES ('20331', '10', '370');
INSERT INTO `sys_role_menu` VALUES ('20338', '10', '392');
INSERT INTO `sys_role_menu` VALUES ('20345', '10', '393');
INSERT INTO `sys_role_menu` VALUES ('20352', '10', '394');
INSERT INTO `sys_role_menu` VALUES ('20359', '10', '395');
INSERT INTO `sys_role_menu` VALUES ('20366', '10', '534');
INSERT INTO `sys_role_menu` VALUES ('20373', '10', '564');
INSERT INTO `sys_role_menu` VALUES ('20380', '10', '465');
INSERT INTO `sys_role_menu` VALUES ('20387', '10', '466');
INSERT INTO `sys_role_menu` VALUES ('20394', '10', '467');
INSERT INTO `sys_role_menu` VALUES ('20401', '10', '468');
INSERT INTO `sys_role_menu` VALUES ('20408', '10', '469');
INSERT INTO `sys_role_menu` VALUES ('20415', '10', '480');
INSERT INTO `sys_role_menu` VALUES ('20422', '10', '481');
INSERT INTO `sys_role_menu` VALUES ('20429', '10', '497');
INSERT INTO `sys_role_menu` VALUES ('20436', '10', '503');
INSERT INTO `sys_role_menu` VALUES ('20443', '10', '561');
INSERT INTO `sys_role_menu` VALUES ('20450', '10', '563');
INSERT INTO `sys_role_menu` VALUES ('20457', '10', '27');
INSERT INTO `sys_role_menu` VALUES ('20464', '10', '28');
INSERT INTO `sys_role_menu` VALUES ('20471', '10', '29');
INSERT INTO `sys_role_menu` VALUES ('20478', '10', '30');
INSERT INTO `sys_role_menu` VALUES ('20485', '10', '31');
INSERT INTO `sys_role_menu` VALUES ('20492', '10', '32');
INSERT INTO `sys_role_menu` VALUES ('20499', '10', '437');
INSERT INTO `sys_role_menu` VALUES ('20506', '10', '533');
INSERT INTO `sys_role_menu` VALUES ('20513', '10', '33');
INSERT INTO `sys_role_menu` VALUES ('20520', '10', '34');
INSERT INTO `sys_role_menu` VALUES ('20527', '10', '35');
INSERT INTO `sys_role_menu` VALUES ('20534', '10', '36');
INSERT INTO `sys_role_menu` VALUES ('20541', '10', '37');
INSERT INTO `sys_role_menu` VALUES ('20548', '10', '294');
INSERT INTO `sys_role_menu` VALUES ('20555', '10', '295');
INSERT INTO `sys_role_menu` VALUES ('20562', '10', '296');
INSERT INTO `sys_role_menu` VALUES ('20569', '10', '417');
INSERT INTO `sys_role_menu` VALUES ('20576', '10', '486');
INSERT INTO `sys_role_menu` VALUES ('20583', '10', '488');
INSERT INTO `sys_role_menu` VALUES ('20590', '10', '303');
INSERT INTO `sys_role_menu` VALUES ('20597', '10', '304');
INSERT INTO `sys_role_menu` VALUES ('20604', '10', '305');
INSERT INTO `sys_role_menu` VALUES ('20611', '10', '306');
INSERT INTO `sys_role_menu` VALUES ('20618', '10', '307');
INSERT INTO `sys_role_menu` VALUES ('20625', '10', '330');
INSERT INTO `sys_role_menu` VALUES ('20632', '10', '331');
INSERT INTO `sys_role_menu` VALUES ('20639', '10', '332');
INSERT INTO `sys_role_menu` VALUES ('20646', '10', '333');
INSERT INTO `sys_role_menu` VALUES ('20653', '10', '551');
INSERT INTO `sys_role_menu` VALUES ('20660', '10', '423');
INSERT INTO `sys_role_menu` VALUES ('20667', '10', '424');
INSERT INTO `sys_role_menu` VALUES ('20674', '10', '425');
INSERT INTO `sys_role_menu` VALUES ('20681', '10', '426');
INSERT INTO `sys_role_menu` VALUES ('20688', '10', '427');
INSERT INTO `sys_role_menu` VALUES ('20695', '10', '482');
INSERT INTO `sys_role_menu` VALUES ('20702', '10', '483');
INSERT INTO `sys_role_menu` VALUES ('20709', '10', '484');
INSERT INTO `sys_role_menu` VALUES ('20716', '10', '485');
INSERT INTO `sys_role_menu` VALUES ('20723', '10', '550');
INSERT INTO `sys_role_menu` VALUES ('20730', '10', '297');
INSERT INTO `sys_role_menu` VALUES ('20737', '10', '298');
INSERT INTO `sys_role_menu` VALUES ('20744', '10', '299');
INSERT INTO `sys_role_menu` VALUES ('20751', '10', '300');
INSERT INTO `sys_role_menu` VALUES ('20758', '10', '301');
INSERT INTO `sys_role_menu` VALUES ('20765', '10', '302');
INSERT INTO `sys_role_menu` VALUES ('20772', '10', '308');
INSERT INTO `sys_role_menu` VALUES ('20779', '10', '309');
INSERT INTO `sys_role_menu` VALUES ('20786', '10', '310');
INSERT INTO `sys_role_menu` VALUES ('20793', '10', '311');
INSERT INTO `sys_role_menu` VALUES ('20800', '10', '312');
INSERT INTO `sys_role_menu` VALUES ('20807', '10', '428');
INSERT INTO `sys_role_menu` VALUES ('20814', '10', '429');
INSERT INTO `sys_role_menu` VALUES ('20821', '10', '430');
INSERT INTO `sys_role_menu` VALUES ('20828', '10', '431');
INSERT INTO `sys_role_menu` VALUES ('20835', '10', '499');
INSERT INTO `sys_role_menu` VALUES ('20842', '10', '500');
INSERT INTO `sys_role_menu` VALUES ('20849', '10', '472');
INSERT INTO `sys_role_menu` VALUES ('20856', '10', '473');
INSERT INTO `sys_role_menu` VALUES ('20863', '10', '474');
INSERT INTO `sys_role_menu` VALUES ('20870', '10', '475');
INSERT INTO `sys_role_menu` VALUES ('20877', '10', '494');
INSERT INTO `sys_role_menu` VALUES ('20884', '10', '476');
INSERT INTO `sys_role_menu` VALUES ('20891', '10', '477');
INSERT INTO `sys_role_menu` VALUES ('20898', '10', '478');
INSERT INTO `sys_role_menu` VALUES ('20905', '10', '479');
INSERT INTO `sys_role_menu` VALUES ('20912', '10', '502');
INSERT INTO `sys_role_menu` VALUES ('20919', '10', '318');
INSERT INTO `sys_role_menu` VALUES ('20926', '10', '319');
INSERT INTO `sys_role_menu` VALUES ('20933', '10', '320');
INSERT INTO `sys_role_menu` VALUES ('20940', '10', '321');
INSERT INTO `sys_role_menu` VALUES ('20947', '10', '322');
INSERT INTO `sys_role_menu` VALUES ('20954', '10', '323');
INSERT INTO `sys_role_menu` VALUES ('20961', '10', '324');
INSERT INTO `sys_role_menu` VALUES ('20968', '10', '325');
INSERT INTO `sys_role_menu` VALUES ('20975', '10', '326');
INSERT INTO `sys_role_menu` VALUES ('20982', '10', '327');
INSERT INTO `sys_role_menu` VALUES ('20989', '10', '328');
INSERT INTO `sys_role_menu` VALUES ('20996', '10', '329');
INSERT INTO `sys_role_menu` VALUES ('21003', '10', '415');
INSERT INTO `sys_role_menu` VALUES ('21010', '10', '416');
INSERT INTO `sys_role_menu` VALUES ('21017', '10', '562');
INSERT INTO `sys_role_menu` VALUES ('21024', '10', '419');
INSERT INTO `sys_role_menu` VALUES ('21031', '10', '420');
INSERT INTO `sys_role_menu` VALUES ('21038', '10', '421');
INSERT INTO `sys_role_menu` VALUES ('21045', '10', '422');
INSERT INTO `sys_role_menu` VALUES ('21052', '10', '496');
INSERT INTO `sys_role_menu` VALUES ('21059', '10', '334');
INSERT INTO `sys_role_menu` VALUES ('21066', '10', '337');
INSERT INTO `sys_role_menu` VALUES ('21073', '10', '338');
INSERT INTO `sys_role_menu` VALUES ('21080', '10', '339');
INSERT INTO `sys_role_menu` VALUES ('21087', '10', '340');
INSERT INTO `sys_role_menu` VALUES ('21094', '10', '341');
INSERT INTO `sys_role_menu` VALUES ('21101', '10', '504');
INSERT INTO `sys_role_menu` VALUES ('21108', '10', '505');
INSERT INTO `sys_role_menu` VALUES ('21115', '10', '506');
INSERT INTO `sys_role_menu` VALUES ('21122', '10', '507');
INSERT INTO `sys_role_menu` VALUES ('21129', '10', '508');
INSERT INTO `sys_role_menu` VALUES ('21136', '10', '532');
INSERT INTO `sys_role_menu` VALUES ('21143', '10', '552');
INSERT INTO `sys_role_menu` VALUES ('21150', '10', '553');
INSERT INTO `sys_role_menu` VALUES ('21157', '10', '554');
INSERT INTO `sys_role_menu` VALUES ('21164', '10', '558');
INSERT INTO `sys_role_menu` VALUES ('21171', '10', '555');
INSERT INTO `sys_role_menu` VALUES ('21178', '10', '556');
INSERT INTO `sys_role_menu` VALUES ('21185', '10', '557');
INSERT INTO `sys_role_menu` VALUES ('21192', '10', '612');
INSERT INTO `sys_role_menu` VALUES ('21199', '10', '613');
INSERT INTO `sys_role_menu` VALUES ('21206', '10', '614');
INSERT INTO `sys_role_menu` VALUES ('21213', '10', '615');
INSERT INTO `sys_role_menu` VALUES ('21220', '10', '375');
INSERT INTO `sys_role_menu` VALUES ('21304', '10', '407');
INSERT INTO `sys_role_menu` VALUES ('21311', '10', '408');
INSERT INTO `sys_role_menu` VALUES ('21388', '10', '526');
INSERT INTO `sys_role_menu` VALUES ('21395', '10', '527');
INSERT INTO `sys_role_menu` VALUES ('21402', '10', '528');
INSERT INTO `sys_role_menu` VALUES ('21409', '10', '529');
INSERT INTO `sys_role_menu` VALUES ('21416', '10', '530');
INSERT INTO `sys_role_menu` VALUES ('21423', '10', '535');
INSERT INTO `sys_role_menu` VALUES ('21430', '10', '536');
INSERT INTO `sys_role_menu` VALUES ('21437', '10', '537');
INSERT INTO `sys_role_menu` VALUES ('21444', '10', '538');
INSERT INTO `sys_role_menu` VALUES ('21451', '10', '539');
INSERT INTO `sys_role_menu` VALUES ('21458', '10', '568');
INSERT INTO `sys_role_menu` VALUES ('21465', '10', '569');
INSERT INTO `sys_role_menu` VALUES ('21472', '10', '570');
INSERT INTO `sys_role_menu` VALUES ('21479', '10', '571');
INSERT INTO `sys_role_menu` VALUES ('21486', '10', '572');
INSERT INTO `sys_role_menu` VALUES ('21493', '10', '573');
INSERT INTO `sys_role_menu` VALUES ('21500', '10', '574');
INSERT INTO `sys_role_menu` VALUES ('21507', '10', '575');
INSERT INTO `sys_role_menu` VALUES ('21514', '10', '576');
INSERT INTO `sys_role_menu` VALUES ('21521', '10', '577');
INSERT INTO `sys_role_menu` VALUES ('21528', '10', '578');
INSERT INTO `sys_role_menu` VALUES ('21535', '10', '579');
INSERT INTO `sys_role_menu` VALUES ('21542', '10', '580');
INSERT INTO `sys_role_menu` VALUES ('21549', '10', '581');
INSERT INTO `sys_role_menu` VALUES ('21556', '10', '582');
INSERT INTO `sys_role_menu` VALUES ('21563', '10', '583');
INSERT INTO `sys_role_menu` VALUES ('21570', '10', '584');
INSERT INTO `sys_role_menu` VALUES ('21577', '10', '585');
INSERT INTO `sys_role_menu` VALUES ('21584', '10', '586');
INSERT INTO `sys_role_menu` VALUES ('21591', '10', '587');
INSERT INTO `sys_role_menu` VALUES ('21598', '10', '588');
INSERT INTO `sys_role_menu` VALUES ('21605', '10', '589');
INSERT INTO `sys_role_menu` VALUES ('21612', '10', '590');
INSERT INTO `sys_role_menu` VALUES ('21619', '10', '591');
INSERT INTO `sys_role_menu` VALUES ('21626', '10', '592');
INSERT INTO `sys_role_menu` VALUES ('21633', '10', '593');
INSERT INTO `sys_role_menu` VALUES ('21640', '10', '594');
INSERT INTO `sys_role_menu` VALUES ('21647', '10', '595');
INSERT INTO `sys_role_menu` VALUES ('21654', '10', '596');
INSERT INTO `sys_role_menu` VALUES ('21661', '10', '597');
INSERT INTO `sys_role_menu` VALUES ('21668', '10', '598');
INSERT INTO `sys_role_menu` VALUES ('21675', '10', '599');
INSERT INTO `sys_role_menu` VALUES ('21682', '10', '600');
INSERT INTO `sys_role_menu` VALUES ('21689', '10', '601');
INSERT INTO `sys_role_menu` VALUES ('21696', '10', '602');
INSERT INTO `sys_role_menu` VALUES ('21703', '10', '396');
INSERT INTO `sys_role_menu` VALUES ('21710', '10', '397');
INSERT INTO `sys_role_menu` VALUES ('21717', '10', '398');
INSERT INTO `sys_role_menu` VALUES ('21724', '10', '399');
INSERT INTO `sys_role_menu` VALUES ('21731', '10', '400');
INSERT INTO `sys_role_menu` VALUES ('21738', '10', '401');
INSERT INTO `sys_role_menu` VALUES ('21745', '10', '402');
INSERT INTO `sys_role_menu` VALUES ('21752', '10', '403');
INSERT INTO `sys_role_menu` VALUES ('21759', '10', '404');
INSERT INTO `sys_role_menu` VALUES ('21766', '10', '405');
INSERT INTO `sys_role_menu` VALUES ('21773', '10', '406');
INSERT INTO `sys_role_menu` VALUES ('21780', '10', '440');
INSERT INTO `sys_role_menu` VALUES ('21787', '10', '441');
INSERT INTO `sys_role_menu` VALUES ('21794', '10', '442');
INSERT INTO `sys_role_menu` VALUES ('21801', '10', '443');
INSERT INTO `sys_role_menu` VALUES ('21808', '10', '444');
INSERT INTO `sys_role_menu` VALUES ('21815', '10', '445');
INSERT INTO `sys_role_menu` VALUES ('21822', '10', '446');
INSERT INTO `sys_role_menu` VALUES ('21829', '10', '447');
INSERT INTO `sys_role_menu` VALUES ('21836', '10', '448');
INSERT INTO `sys_role_menu` VALUES ('21843', '10', '449');
INSERT INTO `sys_role_menu` VALUES ('21850', '10', '450');
INSERT INTO `sys_role_menu` VALUES ('21857', '10', '451');
INSERT INTO `sys_role_menu` VALUES ('21864', '10', '452');
INSERT INTO `sys_role_menu` VALUES ('21871', '10', '453');
INSERT INTO `sys_role_menu` VALUES ('21878', '10', '454');
INSERT INTO `sys_role_menu` VALUES ('21885', '10', '455');
INSERT INTO `sys_role_menu` VALUES ('21892', '10', '456');
INSERT INTO `sys_role_menu` VALUES ('21899', '10', '457');
INSERT INTO `sys_role_menu` VALUES ('21906', '10', '458');
INSERT INTO `sys_role_menu` VALUES ('21913', '10', '459');
INSERT INTO `sys_role_menu` VALUES ('21920', '10', '460');
INSERT INTO `sys_role_menu` VALUES ('21927', '10', '461');
INSERT INTO `sys_role_menu` VALUES ('21934', '10', '462');
INSERT INTO `sys_role_menu` VALUES ('21941', '10', '463');
INSERT INTO `sys_role_menu` VALUES ('21948', '10', '464');
INSERT INTO `sys_role_menu` VALUES ('21955', '10', '489');
INSERT INTO `sys_role_menu` VALUES ('21962', '10', '490');
INSERT INTO `sys_role_menu` VALUES ('21969', '10', '559');
INSERT INTO `sys_role_menu` VALUES ('21976', '10', '491');
INSERT INTO `sys_role_menu` VALUES ('21983', '10', '565');
INSERT INTO `sys_role_menu` VALUES ('21990', '10', '566');
INSERT INTO `sys_role_menu` VALUES ('21997', '10', '492');
INSERT INTO `sys_role_menu` VALUES ('22004', '10', '560');
INSERT INTO `sys_role_menu` VALUES ('22011', '10', '493');
INSERT INTO `sys_role_menu` VALUES ('22018', '10', '567');
INSERT INTO `sys_role_menu` VALUES ('22025', '10', '495');
INSERT INTO `sys_role_menu` VALUES ('22032', '10', '498');
INSERT INTO `sys_role_menu` VALUES ('22039', '10', '501');
INSERT INTO `sys_role_menu` VALUES ('22046', '10', '541');
INSERT INTO `sys_role_menu` VALUES ('22053', '10', '542');
INSERT INTO `sys_role_menu` VALUES ('22060', '10', '543');
INSERT INTO `sys_role_menu` VALUES ('22067', '10', '548');
INSERT INTO `sys_role_menu` VALUES ('22074', '10', '544');
INSERT INTO `sys_role_menu` VALUES ('22081', '10', '547');
INSERT INTO `sys_role_menu` VALUES ('22088', '10', '603');
INSERT INTO `sys_role_menu` VALUES ('22095', '10', '604');
INSERT INTO `sys_role_menu` VALUES ('22102', '10', '605');
INSERT INTO `sys_role_menu` VALUES ('22109', '10', '606');
INSERT INTO `sys_role_menu` VALUES ('22116', '10', '611');
INSERT INTO `sys_role_menu` VALUES ('22123', '10', '607');
INSERT INTO `sys_role_menu` VALUES ('22130', '10', '608');
INSERT INTO `sys_role_menu` VALUES ('22137', '10', '609');
INSERT INTO `sys_role_menu` VALUES ('22144', '10', '610');
INSERT INTO `sys_role_menu` VALUES ('22151', '10', '-666666');
INSERT INTO `sys_role_menu` VALUES ('22158', '17', '1');
INSERT INTO `sys_role_menu` VALUES ('22165', '17', '2');
INSERT INTO `sys_role_menu` VALUES ('22172', '17', '5');
INSERT INTO `sys_role_menu` VALUES ('22179', '17', '6');
INSERT INTO `sys_role_menu` VALUES ('22186', '17', '7');
INSERT INTO `sys_role_menu` VALUES ('22193', '17', '8');
INSERT INTO `sys_role_menu` VALUES ('22200', '17', '3');
INSERT INTO `sys_role_menu` VALUES ('22207', '17', '9');
INSERT INTO `sys_role_menu` VALUES ('22214', '17', '10');
INSERT INTO `sys_role_menu` VALUES ('22221', '17', '11');
INSERT INTO `sys_role_menu` VALUES ('22228', '17', '12');
INSERT INTO `sys_role_menu` VALUES ('22235', '17', '4');
INSERT INTO `sys_role_menu` VALUES ('22242', '17', '13');
INSERT INTO `sys_role_menu` VALUES ('22249', '17', '14');
INSERT INTO `sys_role_menu` VALUES ('22256', '17', '15');
INSERT INTO `sys_role_menu` VALUES ('22263', '17', '16');
INSERT INTO `sys_role_menu` VALUES ('22270', '17', '362');
INSERT INTO `sys_role_menu` VALUES ('22277', '17', '363');
INSERT INTO `sys_role_menu` VALUES ('22284', '17', '364');
INSERT INTO `sys_role_menu` VALUES ('22291', '17', '365');
INSERT INTO `sys_role_menu` VALUES ('22298', '17', '366');
INSERT INTO `sys_role_menu` VALUES ('22305', '17', '386');
INSERT INTO `sys_role_menu` VALUES ('22312', '17', '387');
INSERT INTO `sys_role_menu` VALUES ('22319', '17', '388');
INSERT INTO `sys_role_menu` VALUES ('22326', '17', '389');
INSERT INTO `sys_role_menu` VALUES ('22333', '17', '390');
INSERT INTO `sys_role_menu` VALUES ('22340', '17', '531');
INSERT INTO `sys_role_menu` VALUES ('22347', '17', '17');
INSERT INTO `sys_role_menu` VALUES ('22354', '17', '18');
INSERT INTO `sys_role_menu` VALUES ('22361', '17', '19');
INSERT INTO `sys_role_menu` VALUES ('22368', '17', '20');
INSERT INTO `sys_role_menu` VALUES ('22375', '17', '21');
INSERT INTO `sys_role_menu` VALUES ('22382', '17', '22');
INSERT INTO `sys_role_menu` VALUES ('22389', '17', '23');
INSERT INTO `sys_role_menu` VALUES ('22396', '17', '38');
INSERT INTO `sys_role_menu` VALUES ('22403', '17', '39');
INSERT INTO `sys_role_menu` VALUES ('22410', '17', '40');
INSERT INTO `sys_role_menu` VALUES ('22417', '17', '41');
INSERT INTO `sys_role_menu` VALUES ('22424', '17', '42');
INSERT INTO `sys_role_menu` VALUES ('22431', '17', '315');
INSERT INTO `sys_role_menu` VALUES ('22438', '17', '316');
INSERT INTO `sys_role_menu` VALUES ('22445', '17', '317');
INSERT INTO `sys_role_menu` VALUES ('22452', '17', '351');
INSERT INTO `sys_role_menu` VALUES ('22459', '17', '352');
INSERT INTO `sys_role_menu` VALUES ('22466', '17', '358');
INSERT INTO `sys_role_menu` VALUES ('22473', '17', '359');
INSERT INTO `sys_role_menu` VALUES ('22480', '17', '360');
INSERT INTO `sys_role_menu` VALUES ('22487', '17', '361');
INSERT INTO `sys_role_menu` VALUES ('22494', '17', '470');
INSERT INTO `sys_role_menu` VALUES ('22501', '17', '471');
INSERT INTO `sys_role_menu` VALUES ('22508', '17', '540');
INSERT INTO `sys_role_menu` VALUES ('22515', '17', '24');
INSERT INTO `sys_role_menu` VALUES ('22522', '17', '25');
INSERT INTO `sys_role_menu` VALUES ('22529', '17', '26');
INSERT INTO `sys_role_menu` VALUES ('22536', '17', '345');
INSERT INTO `sys_role_menu` VALUES ('22543', '17', '346');
INSERT INTO `sys_role_menu` VALUES ('22550', '17', '347');
INSERT INTO `sys_role_menu` VALUES ('22557', '17', '348');
INSERT INTO `sys_role_menu` VALUES ('22564', '17', '349');
INSERT INTO `sys_role_menu` VALUES ('22571', '17', '350');
INSERT INTO `sys_role_menu` VALUES ('22578', '17', '353');
INSERT INTO `sys_role_menu` VALUES ('22585', '17', '354');
INSERT INTO `sys_role_menu` VALUES ('22592', '17', '391');
INSERT INTO `sys_role_menu` VALUES ('22599', '17', '549');
INSERT INTO `sys_role_menu` VALUES ('22606', '17', '367');
INSERT INTO `sys_role_menu` VALUES ('22613', '17', '368');
INSERT INTO `sys_role_menu` VALUES ('22620', '17', '369');
INSERT INTO `sys_role_menu` VALUES ('22627', '17', '370');
INSERT INTO `sys_role_menu` VALUES ('22634', '17', '392');
INSERT INTO `sys_role_menu` VALUES ('22641', '17', '393');
INSERT INTO `sys_role_menu` VALUES ('22648', '17', '394');
INSERT INTO `sys_role_menu` VALUES ('22655', '17', '395');
INSERT INTO `sys_role_menu` VALUES ('22662', '17', '534');
INSERT INTO `sys_role_menu` VALUES ('22669', '17', '564');
INSERT INTO `sys_role_menu` VALUES ('22676', '17', '465');
INSERT INTO `sys_role_menu` VALUES ('22683', '17', '466');
INSERT INTO `sys_role_menu` VALUES ('22690', '17', '467');
INSERT INTO `sys_role_menu` VALUES ('22697', '17', '468');
INSERT INTO `sys_role_menu` VALUES ('22704', '17', '469');
INSERT INTO `sys_role_menu` VALUES ('22711', '17', '480');
INSERT INTO `sys_role_menu` VALUES ('22718', '17', '481');
INSERT INTO `sys_role_menu` VALUES ('22725', '17', '497');
INSERT INTO `sys_role_menu` VALUES ('22732', '17', '503');
INSERT INTO `sys_role_menu` VALUES ('22739', '17', '561');
INSERT INTO `sys_role_menu` VALUES ('22746', '17', '563');
INSERT INTO `sys_role_menu` VALUES ('22753', '17', '27');
INSERT INTO `sys_role_menu` VALUES ('22760', '17', '28');
INSERT INTO `sys_role_menu` VALUES ('22767', '17', '29');
INSERT INTO `sys_role_menu` VALUES ('22774', '17', '30');
INSERT INTO `sys_role_menu` VALUES ('22781', '17', '31');
INSERT INTO `sys_role_menu` VALUES ('22788', '17', '32');
INSERT INTO `sys_role_menu` VALUES ('22795', '17', '437');
INSERT INTO `sys_role_menu` VALUES ('22802', '17', '533');
INSERT INTO `sys_role_menu` VALUES ('22809', '17', '33');
INSERT INTO `sys_role_menu` VALUES ('22816', '17', '34');
INSERT INTO `sys_role_menu` VALUES ('22823', '17', '35');
INSERT INTO `sys_role_menu` VALUES ('22830', '17', '36');
INSERT INTO `sys_role_menu` VALUES ('22837', '17', '37');
INSERT INTO `sys_role_menu` VALUES ('22844', '17', '294');
INSERT INTO `sys_role_menu` VALUES ('22851', '17', '295');
INSERT INTO `sys_role_menu` VALUES ('22858', '17', '296');
INSERT INTO `sys_role_menu` VALUES ('22865', '17', '417');
INSERT INTO `sys_role_menu` VALUES ('22872', '17', '486');
INSERT INTO `sys_role_menu` VALUES ('22879', '17', '488');
INSERT INTO `sys_role_menu` VALUES ('22886', '17', '303');
INSERT INTO `sys_role_menu` VALUES ('22893', '17', '304');
INSERT INTO `sys_role_menu` VALUES ('22900', '17', '305');
INSERT INTO `sys_role_menu` VALUES ('22907', '17', '306');
INSERT INTO `sys_role_menu` VALUES ('22914', '17', '307');
INSERT INTO `sys_role_menu` VALUES ('22921', '17', '330');
INSERT INTO `sys_role_menu` VALUES ('22928', '17', '331');
INSERT INTO `sys_role_menu` VALUES ('22935', '17', '332');
INSERT INTO `sys_role_menu` VALUES ('22942', '17', '333');
INSERT INTO `sys_role_menu` VALUES ('22949', '17', '551');
INSERT INTO `sys_role_menu` VALUES ('22956', '17', '423');
INSERT INTO `sys_role_menu` VALUES ('22963', '17', '424');
INSERT INTO `sys_role_menu` VALUES ('22970', '17', '425');
INSERT INTO `sys_role_menu` VALUES ('22977', '17', '426');
INSERT INTO `sys_role_menu` VALUES ('22984', '17', '427');
INSERT INTO `sys_role_menu` VALUES ('22991', '17', '482');
INSERT INTO `sys_role_menu` VALUES ('22998', '17', '483');
INSERT INTO `sys_role_menu` VALUES ('23005', '17', '484');
INSERT INTO `sys_role_menu` VALUES ('23012', '17', '485');
INSERT INTO `sys_role_menu` VALUES ('23019', '17', '550');
INSERT INTO `sys_role_menu` VALUES ('23026', '17', '297');
INSERT INTO `sys_role_menu` VALUES ('23033', '17', '298');
INSERT INTO `sys_role_menu` VALUES ('23040', '17', '299');
INSERT INTO `sys_role_menu` VALUES ('23047', '17', '300');
INSERT INTO `sys_role_menu` VALUES ('23054', '17', '301');
INSERT INTO `sys_role_menu` VALUES ('23061', '17', '302');
INSERT INTO `sys_role_menu` VALUES ('23068', '17', '308');
INSERT INTO `sys_role_menu` VALUES ('23075', '17', '309');
INSERT INTO `sys_role_menu` VALUES ('23082', '17', '310');
INSERT INTO `sys_role_menu` VALUES ('23089', '17', '311');
INSERT INTO `sys_role_menu` VALUES ('23096', '17', '312');
INSERT INTO `sys_role_menu` VALUES ('23103', '17', '428');
INSERT INTO `sys_role_menu` VALUES ('23110', '17', '429');
INSERT INTO `sys_role_menu` VALUES ('23117', '17', '430');
INSERT INTO `sys_role_menu` VALUES ('23124', '17', '431');
INSERT INTO `sys_role_menu` VALUES ('23131', '17', '499');
INSERT INTO `sys_role_menu` VALUES ('23138', '17', '500');
INSERT INTO `sys_role_menu` VALUES ('23145', '17', '472');
INSERT INTO `sys_role_menu` VALUES ('23152', '17', '473');
INSERT INTO `sys_role_menu` VALUES ('23159', '17', '474');
INSERT INTO `sys_role_menu` VALUES ('23166', '17', '475');
INSERT INTO `sys_role_menu` VALUES ('23173', '17', '494');
INSERT INTO `sys_role_menu` VALUES ('23180', '17', '476');
INSERT INTO `sys_role_menu` VALUES ('23187', '17', '477');
INSERT INTO `sys_role_menu` VALUES ('23194', '17', '478');
INSERT INTO `sys_role_menu` VALUES ('23201', '17', '479');
INSERT INTO `sys_role_menu` VALUES ('23208', '17', '502');
INSERT INTO `sys_role_menu` VALUES ('23215', '17', '318');
INSERT INTO `sys_role_menu` VALUES ('23222', '17', '319');
INSERT INTO `sys_role_menu` VALUES ('23229', '17', '320');
INSERT INTO `sys_role_menu` VALUES ('23236', '17', '321');
INSERT INTO `sys_role_menu` VALUES ('23243', '17', '322');
INSERT INTO `sys_role_menu` VALUES ('23250', '17', '323');
INSERT INTO `sys_role_menu` VALUES ('23257', '17', '324');
INSERT INTO `sys_role_menu` VALUES ('23264', '17', '325');
INSERT INTO `sys_role_menu` VALUES ('23271', '17', '326');
INSERT INTO `sys_role_menu` VALUES ('23278', '17', '327');
INSERT INTO `sys_role_menu` VALUES ('23285', '17', '328');
INSERT INTO `sys_role_menu` VALUES ('23292', '17', '329');
INSERT INTO `sys_role_menu` VALUES ('23299', '17', '415');
INSERT INTO `sys_role_menu` VALUES ('23306', '17', '416');
INSERT INTO `sys_role_menu` VALUES ('23313', '17', '562');
INSERT INTO `sys_role_menu` VALUES ('23320', '17', '419');
INSERT INTO `sys_role_menu` VALUES ('23327', '17', '420');
INSERT INTO `sys_role_menu` VALUES ('23334', '17', '421');
INSERT INTO `sys_role_menu` VALUES ('23341', '17', '422');
INSERT INTO `sys_role_menu` VALUES ('23348', '17', '496');
INSERT INTO `sys_role_menu` VALUES ('23355', '17', '334');
INSERT INTO `sys_role_menu` VALUES ('23362', '17', '337');
INSERT INTO `sys_role_menu` VALUES ('23369', '17', '338');
INSERT INTO `sys_role_menu` VALUES ('23376', '17', '339');
INSERT INTO `sys_role_menu` VALUES ('23383', '17', '340');
INSERT INTO `sys_role_menu` VALUES ('23390', '17', '341');
INSERT INTO `sys_role_menu` VALUES ('23397', '17', '504');
INSERT INTO `sys_role_menu` VALUES ('23404', '17', '505');
INSERT INTO `sys_role_menu` VALUES ('23411', '17', '506');
INSERT INTO `sys_role_menu` VALUES ('23418', '17', '507');
INSERT INTO `sys_role_menu` VALUES ('23425', '17', '508');
INSERT INTO `sys_role_menu` VALUES ('23432', '17', '532');
INSERT INTO `sys_role_menu` VALUES ('23439', '17', '552');
INSERT INTO `sys_role_menu` VALUES ('23446', '17', '553');
INSERT INTO `sys_role_menu` VALUES ('23453', '17', '554');
INSERT INTO `sys_role_menu` VALUES ('23460', '17', '558');
INSERT INTO `sys_role_menu` VALUES ('23467', '17', '555');
INSERT INTO `sys_role_menu` VALUES ('23474', '17', '556');
INSERT INTO `sys_role_menu` VALUES ('23481', '17', '557');
INSERT INTO `sys_role_menu` VALUES ('23488', '17', '612');
INSERT INTO `sys_role_menu` VALUES ('23495', '17', '613');
INSERT INTO `sys_role_menu` VALUES ('23502', '17', '614');
INSERT INTO `sys_role_menu` VALUES ('23509', '17', '615');
INSERT INTO `sys_role_menu` VALUES ('23516', '17', '375');
INSERT INTO `sys_role_menu` VALUES ('23600', '17', '407');
INSERT INTO `sys_role_menu` VALUES ('23607', '17', '408');
INSERT INTO `sys_role_menu` VALUES ('23684', '17', '526');
INSERT INTO `sys_role_menu` VALUES ('23691', '17', '527');
INSERT INTO `sys_role_menu` VALUES ('23698', '17', '528');
INSERT INTO `sys_role_menu` VALUES ('23705', '17', '529');
INSERT INTO `sys_role_menu` VALUES ('23712', '17', '530');
INSERT INTO `sys_role_menu` VALUES ('23719', '17', '535');
INSERT INTO `sys_role_menu` VALUES ('23726', '17', '536');
INSERT INTO `sys_role_menu` VALUES ('23733', '17', '537');
INSERT INTO `sys_role_menu` VALUES ('23740', '17', '538');
INSERT INTO `sys_role_menu` VALUES ('23747', '17', '539');
INSERT INTO `sys_role_menu` VALUES ('23754', '17', '568');
INSERT INTO `sys_role_menu` VALUES ('23761', '17', '569');
INSERT INTO `sys_role_menu` VALUES ('23768', '17', '570');
INSERT INTO `sys_role_menu` VALUES ('23775', '17', '571');
INSERT INTO `sys_role_menu` VALUES ('23782', '17', '572');
INSERT INTO `sys_role_menu` VALUES ('23789', '17', '573');
INSERT INTO `sys_role_menu` VALUES ('23796', '17', '574');
INSERT INTO `sys_role_menu` VALUES ('23803', '17', '575');
INSERT INTO `sys_role_menu` VALUES ('23810', '17', '576');
INSERT INTO `sys_role_menu` VALUES ('23817', '17', '577');
INSERT INTO `sys_role_menu` VALUES ('23824', '17', '578');
INSERT INTO `sys_role_menu` VALUES ('23831', '17', '579');
INSERT INTO `sys_role_menu` VALUES ('23838', '17', '580');
INSERT INTO `sys_role_menu` VALUES ('23845', '17', '581');
INSERT INTO `sys_role_menu` VALUES ('23852', '17', '582');
INSERT INTO `sys_role_menu` VALUES ('23859', '17', '583');
INSERT INTO `sys_role_menu` VALUES ('23866', '17', '584');
INSERT INTO `sys_role_menu` VALUES ('23873', '17', '585');
INSERT INTO `sys_role_menu` VALUES ('23880', '17', '586');
INSERT INTO `sys_role_menu` VALUES ('23887', '17', '587');
INSERT INTO `sys_role_menu` VALUES ('23894', '17', '588');
INSERT INTO `sys_role_menu` VALUES ('23901', '17', '589');
INSERT INTO `sys_role_menu` VALUES ('23908', '17', '590');
INSERT INTO `sys_role_menu` VALUES ('23915', '17', '591');
INSERT INTO `sys_role_menu` VALUES ('23922', '17', '592');
INSERT INTO `sys_role_menu` VALUES ('23929', '17', '593');
INSERT INTO `sys_role_menu` VALUES ('23936', '17', '594');
INSERT INTO `sys_role_menu` VALUES ('23943', '17', '595');
INSERT INTO `sys_role_menu` VALUES ('23950', '17', '596');
INSERT INTO `sys_role_menu` VALUES ('23957', '17', '597');
INSERT INTO `sys_role_menu` VALUES ('23964', '17', '598');
INSERT INTO `sys_role_menu` VALUES ('23971', '17', '599');
INSERT INTO `sys_role_menu` VALUES ('23978', '17', '600');
INSERT INTO `sys_role_menu` VALUES ('23985', '17', '601');
INSERT INTO `sys_role_menu` VALUES ('23992', '17', '602');
INSERT INTO `sys_role_menu` VALUES ('23999', '17', '396');
INSERT INTO `sys_role_menu` VALUES ('24006', '17', '397');
INSERT INTO `sys_role_menu` VALUES ('24013', '17', '398');
INSERT INTO `sys_role_menu` VALUES ('24020', '17', '399');
INSERT INTO `sys_role_menu` VALUES ('24027', '17', '400');
INSERT INTO `sys_role_menu` VALUES ('24034', '17', '401');
INSERT INTO `sys_role_menu` VALUES ('24041', '17', '402');
INSERT INTO `sys_role_menu` VALUES ('24048', '17', '403');
INSERT INTO `sys_role_menu` VALUES ('24055', '17', '404');
INSERT INTO `sys_role_menu` VALUES ('24062', '17', '405');
INSERT INTO `sys_role_menu` VALUES ('24069', '17', '406');
INSERT INTO `sys_role_menu` VALUES ('24076', '17', '440');
INSERT INTO `sys_role_menu` VALUES ('24083', '17', '441');
INSERT INTO `sys_role_menu` VALUES ('24090', '17', '442');
INSERT INTO `sys_role_menu` VALUES ('24097', '17', '443');
INSERT INTO `sys_role_menu` VALUES ('24104', '17', '444');
INSERT INTO `sys_role_menu` VALUES ('24111', '17', '445');
INSERT INTO `sys_role_menu` VALUES ('24118', '17', '446');
INSERT INTO `sys_role_menu` VALUES ('24125', '17', '447');
INSERT INTO `sys_role_menu` VALUES ('24132', '17', '448');
INSERT INTO `sys_role_menu` VALUES ('24139', '17', '449');
INSERT INTO `sys_role_menu` VALUES ('24146', '17', '450');
INSERT INTO `sys_role_menu` VALUES ('24153', '17', '451');
INSERT INTO `sys_role_menu` VALUES ('24160', '17', '452');
INSERT INTO `sys_role_menu` VALUES ('24167', '17', '453');
INSERT INTO `sys_role_menu` VALUES ('24174', '17', '454');
INSERT INTO `sys_role_menu` VALUES ('24181', '17', '455');
INSERT INTO `sys_role_menu` VALUES ('24188', '17', '456');
INSERT INTO `sys_role_menu` VALUES ('24195', '17', '457');
INSERT INTO `sys_role_menu` VALUES ('24202', '17', '458');
INSERT INTO `sys_role_menu` VALUES ('24209', '17', '459');
INSERT INTO `sys_role_menu` VALUES ('24216', '17', '460');
INSERT INTO `sys_role_menu` VALUES ('24223', '17', '461');
INSERT INTO `sys_role_menu` VALUES ('24230', '17', '462');
INSERT INTO `sys_role_menu` VALUES ('24237', '17', '463');
INSERT INTO `sys_role_menu` VALUES ('24244', '17', '464');
INSERT INTO `sys_role_menu` VALUES ('24251', '17', '489');
INSERT INTO `sys_role_menu` VALUES ('24258', '17', '490');
INSERT INTO `sys_role_menu` VALUES ('24265', '17', '559');
INSERT INTO `sys_role_menu` VALUES ('24272', '17', '491');
INSERT INTO `sys_role_menu` VALUES ('24279', '17', '565');
INSERT INTO `sys_role_menu` VALUES ('24286', '17', '566');
INSERT INTO `sys_role_menu` VALUES ('24293', '17', '492');
INSERT INTO `sys_role_menu` VALUES ('24300', '17', '560');
INSERT INTO `sys_role_menu` VALUES ('24307', '17', '493');
INSERT INTO `sys_role_menu` VALUES ('24314', '17', '567');
INSERT INTO `sys_role_menu` VALUES ('24321', '17', '495');
INSERT INTO `sys_role_menu` VALUES ('24328', '17', '498');
INSERT INTO `sys_role_menu` VALUES ('24335', '17', '501');
INSERT INTO `sys_role_menu` VALUES ('24342', '17', '541');
INSERT INTO `sys_role_menu` VALUES ('24349', '17', '542');
INSERT INTO `sys_role_menu` VALUES ('24356', '17', '543');
INSERT INTO `sys_role_menu` VALUES ('24363', '17', '548');
INSERT INTO `sys_role_menu` VALUES ('24370', '17', '544');
INSERT INTO `sys_role_menu` VALUES ('24377', '17', '547');
INSERT INTO `sys_role_menu` VALUES ('24384', '17', '603');
INSERT INTO `sys_role_menu` VALUES ('24391', '17', '604');
INSERT INTO `sys_role_menu` VALUES ('24398', '17', '605');
INSERT INTO `sys_role_menu` VALUES ('24405', '17', '606');
INSERT INTO `sys_role_menu` VALUES ('24412', '17', '611');
INSERT INTO `sys_role_menu` VALUES ('24419', '17', '607');
INSERT INTO `sys_role_menu` VALUES ('24426', '17', '608');
INSERT INTO `sys_role_menu` VALUES ('24433', '17', '609');
INSERT INTO `sys_role_menu` VALUES ('24440', '17', '610');
INSERT INTO `sys_role_menu` VALUES ('24447', '17', '-666666');
INSERT INTO `sys_role_menu` VALUES ('24454', '24', '1');
INSERT INTO `sys_role_menu` VALUES ('24461', '24', '2');
INSERT INTO `sys_role_menu` VALUES ('24468', '24', '5');
INSERT INTO `sys_role_menu` VALUES ('24475', '24', '6');
INSERT INTO `sys_role_menu` VALUES ('24482', '24', '7');
INSERT INTO `sys_role_menu` VALUES ('24489', '24', '8');
INSERT INTO `sys_role_menu` VALUES ('24496', '24', '3');
INSERT INTO `sys_role_menu` VALUES ('24503', '24', '9');
INSERT INTO `sys_role_menu` VALUES ('24510', '24', '10');
INSERT INTO `sys_role_menu` VALUES ('24517', '24', '11');
INSERT INTO `sys_role_menu` VALUES ('24524', '24', '12');
INSERT INTO `sys_role_menu` VALUES ('24531', '24', '4');
INSERT INTO `sys_role_menu` VALUES ('24538', '24', '13');
INSERT INTO `sys_role_menu` VALUES ('24545', '24', '14');
INSERT INTO `sys_role_menu` VALUES ('24552', '24', '15');
INSERT INTO `sys_role_menu` VALUES ('24559', '24', '16');
INSERT INTO `sys_role_menu` VALUES ('24566', '24', '362');
INSERT INTO `sys_role_menu` VALUES ('24573', '24', '363');
INSERT INTO `sys_role_menu` VALUES ('24580', '24', '364');
INSERT INTO `sys_role_menu` VALUES ('24587', '24', '365');
INSERT INTO `sys_role_menu` VALUES ('24594', '24', '366');
INSERT INTO `sys_role_menu` VALUES ('24601', '24', '386');
INSERT INTO `sys_role_menu` VALUES ('24608', '24', '387');
INSERT INTO `sys_role_menu` VALUES ('24615', '24', '388');
INSERT INTO `sys_role_menu` VALUES ('24622', '24', '389');
INSERT INTO `sys_role_menu` VALUES ('24629', '24', '390');
INSERT INTO `sys_role_menu` VALUES ('24636', '24', '531');
INSERT INTO `sys_role_menu` VALUES ('24643', '24', '17');
INSERT INTO `sys_role_menu` VALUES ('24650', '24', '18');
INSERT INTO `sys_role_menu` VALUES ('24657', '24', '19');
INSERT INTO `sys_role_menu` VALUES ('24664', '24', '20');
INSERT INTO `sys_role_menu` VALUES ('24671', '24', '21');
INSERT INTO `sys_role_menu` VALUES ('24678', '24', '22');
INSERT INTO `sys_role_menu` VALUES ('24685', '24', '23');
INSERT INTO `sys_role_menu` VALUES ('24692', '24', '38');
INSERT INTO `sys_role_menu` VALUES ('24699', '24', '39');
INSERT INTO `sys_role_menu` VALUES ('24706', '24', '40');
INSERT INTO `sys_role_menu` VALUES ('24713', '24', '41');
INSERT INTO `sys_role_menu` VALUES ('24720', '24', '42');
INSERT INTO `sys_role_menu` VALUES ('24727', '24', '315');
INSERT INTO `sys_role_menu` VALUES ('24734', '24', '316');
INSERT INTO `sys_role_menu` VALUES ('24741', '24', '317');
INSERT INTO `sys_role_menu` VALUES ('24748', '24', '351');
INSERT INTO `sys_role_menu` VALUES ('24755', '24', '352');
INSERT INTO `sys_role_menu` VALUES ('24762', '24', '358');
INSERT INTO `sys_role_menu` VALUES ('24769', '24', '359');
INSERT INTO `sys_role_menu` VALUES ('24776', '24', '360');
INSERT INTO `sys_role_menu` VALUES ('24783', '24', '361');
INSERT INTO `sys_role_menu` VALUES ('24790', '24', '470');
INSERT INTO `sys_role_menu` VALUES ('24797', '24', '471');
INSERT INTO `sys_role_menu` VALUES ('24804', '24', '540');
INSERT INTO `sys_role_menu` VALUES ('24811', '24', '24');
INSERT INTO `sys_role_menu` VALUES ('24818', '24', '25');
INSERT INTO `sys_role_menu` VALUES ('24825', '24', '26');
INSERT INTO `sys_role_menu` VALUES ('24832', '24', '345');
INSERT INTO `sys_role_menu` VALUES ('24839', '24', '346');
INSERT INTO `sys_role_menu` VALUES ('24846', '24', '347');
INSERT INTO `sys_role_menu` VALUES ('24853', '24', '348');
INSERT INTO `sys_role_menu` VALUES ('24860', '24', '349');
INSERT INTO `sys_role_menu` VALUES ('24867', '24', '350');
INSERT INTO `sys_role_menu` VALUES ('24874', '24', '353');
INSERT INTO `sys_role_menu` VALUES ('24881', '24', '354');
INSERT INTO `sys_role_menu` VALUES ('24888', '24', '391');
INSERT INTO `sys_role_menu` VALUES ('24895', '24', '549');
INSERT INTO `sys_role_menu` VALUES ('24902', '24', '367');
INSERT INTO `sys_role_menu` VALUES ('24909', '24', '368');
INSERT INTO `sys_role_menu` VALUES ('24916', '24', '369');
INSERT INTO `sys_role_menu` VALUES ('24923', '24', '370');
INSERT INTO `sys_role_menu` VALUES ('24930', '24', '392');
INSERT INTO `sys_role_menu` VALUES ('24937', '24', '393');
INSERT INTO `sys_role_menu` VALUES ('24944', '24', '394');
INSERT INTO `sys_role_menu` VALUES ('24951', '24', '395');
INSERT INTO `sys_role_menu` VALUES ('24958', '24', '534');
INSERT INTO `sys_role_menu` VALUES ('24965', '24', '564');
INSERT INTO `sys_role_menu` VALUES ('24972', '24', '465');
INSERT INTO `sys_role_menu` VALUES ('24979', '24', '466');
INSERT INTO `sys_role_menu` VALUES ('24986', '24', '467');
INSERT INTO `sys_role_menu` VALUES ('24993', '24', '468');
INSERT INTO `sys_role_menu` VALUES ('25000', '24', '469');
INSERT INTO `sys_role_menu` VALUES ('25007', '24', '480');
INSERT INTO `sys_role_menu` VALUES ('25014', '24', '481');
INSERT INTO `sys_role_menu` VALUES ('25021', '24', '497');
INSERT INTO `sys_role_menu` VALUES ('25028', '24', '503');
INSERT INTO `sys_role_menu` VALUES ('25035', '24', '561');
INSERT INTO `sys_role_menu` VALUES ('25042', '24', '563');
INSERT INTO `sys_role_menu` VALUES ('25049', '24', '27');
INSERT INTO `sys_role_menu` VALUES ('25056', '24', '28');
INSERT INTO `sys_role_menu` VALUES ('25063', '24', '29');
INSERT INTO `sys_role_menu` VALUES ('25070', '24', '30');
INSERT INTO `sys_role_menu` VALUES ('25077', '24', '31');
INSERT INTO `sys_role_menu` VALUES ('25084', '24', '32');
INSERT INTO `sys_role_menu` VALUES ('25091', '24', '437');
INSERT INTO `sys_role_menu` VALUES ('25098', '24', '533');
INSERT INTO `sys_role_menu` VALUES ('25105', '24', '33');
INSERT INTO `sys_role_menu` VALUES ('25112', '24', '34');
INSERT INTO `sys_role_menu` VALUES ('25119', '24', '35');
INSERT INTO `sys_role_menu` VALUES ('25126', '24', '36');
INSERT INTO `sys_role_menu` VALUES ('25133', '24', '37');
INSERT INTO `sys_role_menu` VALUES ('25140', '24', '294');
INSERT INTO `sys_role_menu` VALUES ('25147', '24', '295');
INSERT INTO `sys_role_menu` VALUES ('25154', '24', '296');
INSERT INTO `sys_role_menu` VALUES ('25161', '24', '417');
INSERT INTO `sys_role_menu` VALUES ('25168', '24', '486');
INSERT INTO `sys_role_menu` VALUES ('25175', '24', '488');
INSERT INTO `sys_role_menu` VALUES ('25182', '24', '303');
INSERT INTO `sys_role_menu` VALUES ('25189', '24', '304');
INSERT INTO `sys_role_menu` VALUES ('25196', '24', '305');
INSERT INTO `sys_role_menu` VALUES ('25203', '24', '306');
INSERT INTO `sys_role_menu` VALUES ('25210', '24', '307');
INSERT INTO `sys_role_menu` VALUES ('25217', '24', '330');
INSERT INTO `sys_role_menu` VALUES ('25224', '24', '331');
INSERT INTO `sys_role_menu` VALUES ('25231', '24', '332');
INSERT INTO `sys_role_menu` VALUES ('25238', '24', '333');
INSERT INTO `sys_role_menu` VALUES ('25245', '24', '551');
INSERT INTO `sys_role_menu` VALUES ('25252', '24', '423');
INSERT INTO `sys_role_menu` VALUES ('25259', '24', '424');
INSERT INTO `sys_role_menu` VALUES ('25266', '24', '425');
INSERT INTO `sys_role_menu` VALUES ('25273', '24', '426');
INSERT INTO `sys_role_menu` VALUES ('25280', '24', '427');
INSERT INTO `sys_role_menu` VALUES ('25287', '24', '482');
INSERT INTO `sys_role_menu` VALUES ('25294', '24', '483');
INSERT INTO `sys_role_menu` VALUES ('25301', '24', '484');
INSERT INTO `sys_role_menu` VALUES ('25308', '24', '485');
INSERT INTO `sys_role_menu` VALUES ('25315', '24', '550');
INSERT INTO `sys_role_menu` VALUES ('25322', '24', '297');
INSERT INTO `sys_role_menu` VALUES ('25329', '24', '298');
INSERT INTO `sys_role_menu` VALUES ('25336', '24', '299');
INSERT INTO `sys_role_menu` VALUES ('25343', '24', '300');
INSERT INTO `sys_role_menu` VALUES ('25350', '24', '301');
INSERT INTO `sys_role_menu` VALUES ('25357', '24', '302');
INSERT INTO `sys_role_menu` VALUES ('25364', '24', '308');
INSERT INTO `sys_role_menu` VALUES ('25371', '24', '309');
INSERT INTO `sys_role_menu` VALUES ('25378', '24', '310');
INSERT INTO `sys_role_menu` VALUES ('25385', '24', '311');
INSERT INTO `sys_role_menu` VALUES ('25392', '24', '312');
INSERT INTO `sys_role_menu` VALUES ('25399', '24', '428');
INSERT INTO `sys_role_menu` VALUES ('25406', '24', '429');
INSERT INTO `sys_role_menu` VALUES ('25413', '24', '430');
INSERT INTO `sys_role_menu` VALUES ('25420', '24', '431');
INSERT INTO `sys_role_menu` VALUES ('25427', '24', '499');
INSERT INTO `sys_role_menu` VALUES ('25434', '24', '500');
INSERT INTO `sys_role_menu` VALUES ('25441', '24', '472');
INSERT INTO `sys_role_menu` VALUES ('25448', '24', '473');
INSERT INTO `sys_role_menu` VALUES ('25455', '24', '474');
INSERT INTO `sys_role_menu` VALUES ('25462', '24', '475');
INSERT INTO `sys_role_menu` VALUES ('25469', '24', '494');
INSERT INTO `sys_role_menu` VALUES ('25476', '24', '476');
INSERT INTO `sys_role_menu` VALUES ('25483', '24', '477');
INSERT INTO `sys_role_menu` VALUES ('25490', '24', '478');
INSERT INTO `sys_role_menu` VALUES ('25497', '24', '479');
INSERT INTO `sys_role_menu` VALUES ('25504', '24', '502');
INSERT INTO `sys_role_menu` VALUES ('25511', '24', '318');
INSERT INTO `sys_role_menu` VALUES ('25518', '24', '319');
INSERT INTO `sys_role_menu` VALUES ('25525', '24', '320');
INSERT INTO `sys_role_menu` VALUES ('25532', '24', '321');
INSERT INTO `sys_role_menu` VALUES ('25539', '24', '322');
INSERT INTO `sys_role_menu` VALUES ('25546', '24', '323');
INSERT INTO `sys_role_menu` VALUES ('25553', '24', '324');
INSERT INTO `sys_role_menu` VALUES ('25560', '24', '325');
INSERT INTO `sys_role_menu` VALUES ('25567', '24', '326');
INSERT INTO `sys_role_menu` VALUES ('25574', '24', '327');
INSERT INTO `sys_role_menu` VALUES ('25581', '24', '328');
INSERT INTO `sys_role_menu` VALUES ('25588', '24', '329');
INSERT INTO `sys_role_menu` VALUES ('25595', '24', '415');
INSERT INTO `sys_role_menu` VALUES ('25602', '24', '416');
INSERT INTO `sys_role_menu` VALUES ('25609', '24', '562');
INSERT INTO `sys_role_menu` VALUES ('25616', '24', '419');
INSERT INTO `sys_role_menu` VALUES ('25623', '24', '420');
INSERT INTO `sys_role_menu` VALUES ('25630', '24', '421');
INSERT INTO `sys_role_menu` VALUES ('25637', '24', '422');
INSERT INTO `sys_role_menu` VALUES ('25644', '24', '496');
INSERT INTO `sys_role_menu` VALUES ('25651', '24', '334');
INSERT INTO `sys_role_menu` VALUES ('25658', '24', '337');
INSERT INTO `sys_role_menu` VALUES ('25665', '24', '338');
INSERT INTO `sys_role_menu` VALUES ('25672', '24', '339');
INSERT INTO `sys_role_menu` VALUES ('25679', '24', '340');
INSERT INTO `sys_role_menu` VALUES ('25686', '24', '341');
INSERT INTO `sys_role_menu` VALUES ('25693', '24', '504');
INSERT INTO `sys_role_menu` VALUES ('25700', '24', '505');
INSERT INTO `sys_role_menu` VALUES ('25707', '24', '506');
INSERT INTO `sys_role_menu` VALUES ('25714', '24', '507');
INSERT INTO `sys_role_menu` VALUES ('25721', '24', '508');
INSERT INTO `sys_role_menu` VALUES ('25728', '24', '532');
INSERT INTO `sys_role_menu` VALUES ('25735', '24', '552');
INSERT INTO `sys_role_menu` VALUES ('25742', '24', '553');
INSERT INTO `sys_role_menu` VALUES ('25749', '24', '554');
INSERT INTO `sys_role_menu` VALUES ('25756', '24', '558');
INSERT INTO `sys_role_menu` VALUES ('25763', '24', '555');
INSERT INTO `sys_role_menu` VALUES ('25770', '24', '556');
INSERT INTO `sys_role_menu` VALUES ('25777', '24', '557');
INSERT INTO `sys_role_menu` VALUES ('25784', '24', '612');
INSERT INTO `sys_role_menu` VALUES ('25791', '24', '613');
INSERT INTO `sys_role_menu` VALUES ('25798', '24', '614');
INSERT INTO `sys_role_menu` VALUES ('25805', '24', '615');
INSERT INTO `sys_role_menu` VALUES ('25812', '24', '375');
INSERT INTO `sys_role_menu` VALUES ('25896', '24', '407');
INSERT INTO `sys_role_menu` VALUES ('25903', '24', '408');
INSERT INTO `sys_role_menu` VALUES ('25980', '24', '526');
INSERT INTO `sys_role_menu` VALUES ('25987', '24', '527');
INSERT INTO `sys_role_menu` VALUES ('25994', '24', '528');
INSERT INTO `sys_role_menu` VALUES ('26001', '24', '529');
INSERT INTO `sys_role_menu` VALUES ('26008', '24', '530');
INSERT INTO `sys_role_menu` VALUES ('26015', '24', '535');
INSERT INTO `sys_role_menu` VALUES ('26022', '24', '536');
INSERT INTO `sys_role_menu` VALUES ('26029', '24', '537');
INSERT INTO `sys_role_menu` VALUES ('26036', '24', '538');
INSERT INTO `sys_role_menu` VALUES ('26043', '24', '539');
INSERT INTO `sys_role_menu` VALUES ('26050', '24', '568');
INSERT INTO `sys_role_menu` VALUES ('26057', '24', '569');
INSERT INTO `sys_role_menu` VALUES ('26064', '24', '570');
INSERT INTO `sys_role_menu` VALUES ('26071', '24', '571');
INSERT INTO `sys_role_menu` VALUES ('26078', '24', '572');
INSERT INTO `sys_role_menu` VALUES ('26085', '24', '573');
INSERT INTO `sys_role_menu` VALUES ('26092', '24', '574');
INSERT INTO `sys_role_menu` VALUES ('26099', '24', '575');
INSERT INTO `sys_role_menu` VALUES ('26106', '24', '576');
INSERT INTO `sys_role_menu` VALUES ('26113', '24', '577');
INSERT INTO `sys_role_menu` VALUES ('26120', '24', '578');
INSERT INTO `sys_role_menu` VALUES ('26127', '24', '579');
INSERT INTO `sys_role_menu` VALUES ('26134', '24', '580');
INSERT INTO `sys_role_menu` VALUES ('26141', '24', '581');
INSERT INTO `sys_role_menu` VALUES ('26148', '24', '582');
INSERT INTO `sys_role_menu` VALUES ('26155', '24', '583');
INSERT INTO `sys_role_menu` VALUES ('26162', '24', '584');
INSERT INTO `sys_role_menu` VALUES ('26169', '24', '585');
INSERT INTO `sys_role_menu` VALUES ('26176', '24', '586');
INSERT INTO `sys_role_menu` VALUES ('26183', '24', '587');
INSERT INTO `sys_role_menu` VALUES ('26190', '24', '588');
INSERT INTO `sys_role_menu` VALUES ('26197', '24', '589');
INSERT INTO `sys_role_menu` VALUES ('26204', '24', '590');
INSERT INTO `sys_role_menu` VALUES ('26211', '24', '591');
INSERT INTO `sys_role_menu` VALUES ('26218', '24', '592');
INSERT INTO `sys_role_menu` VALUES ('26225', '24', '593');
INSERT INTO `sys_role_menu` VALUES ('26232', '24', '594');
INSERT INTO `sys_role_menu` VALUES ('26239', '24', '595');
INSERT INTO `sys_role_menu` VALUES ('26246', '24', '596');
INSERT INTO `sys_role_menu` VALUES ('26253', '24', '597');
INSERT INTO `sys_role_menu` VALUES ('26260', '24', '598');
INSERT INTO `sys_role_menu` VALUES ('26267', '24', '599');
INSERT INTO `sys_role_menu` VALUES ('26274', '24', '600');
INSERT INTO `sys_role_menu` VALUES ('26281', '24', '601');
INSERT INTO `sys_role_menu` VALUES ('26288', '24', '602');
INSERT INTO `sys_role_menu` VALUES ('26295', '24', '396');
INSERT INTO `sys_role_menu` VALUES ('26302', '24', '397');
INSERT INTO `sys_role_menu` VALUES ('26309', '24', '398');
INSERT INTO `sys_role_menu` VALUES ('26316', '24', '399');
INSERT INTO `sys_role_menu` VALUES ('26323', '24', '400');
INSERT INTO `sys_role_menu` VALUES ('26330', '24', '401');
INSERT INTO `sys_role_menu` VALUES ('26337', '24', '402');
INSERT INTO `sys_role_menu` VALUES ('26344', '24', '403');
INSERT INTO `sys_role_menu` VALUES ('26351', '24', '404');
INSERT INTO `sys_role_menu` VALUES ('26358', '24', '405');
INSERT INTO `sys_role_menu` VALUES ('26365', '24', '406');
INSERT INTO `sys_role_menu` VALUES ('26372', '24', '440');
INSERT INTO `sys_role_menu` VALUES ('26379', '24', '441');
INSERT INTO `sys_role_menu` VALUES ('26386', '24', '442');
INSERT INTO `sys_role_menu` VALUES ('26393', '24', '443');
INSERT INTO `sys_role_menu` VALUES ('26400', '24', '444');
INSERT INTO `sys_role_menu` VALUES ('26407', '24', '445');
INSERT INTO `sys_role_menu` VALUES ('26414', '24', '446');
INSERT INTO `sys_role_menu` VALUES ('26421', '24', '447');
INSERT INTO `sys_role_menu` VALUES ('26428', '24', '448');
INSERT INTO `sys_role_menu` VALUES ('26435', '24', '449');
INSERT INTO `sys_role_menu` VALUES ('26442', '24', '450');
INSERT INTO `sys_role_menu` VALUES ('26449', '24', '451');
INSERT INTO `sys_role_menu` VALUES ('26456', '24', '452');
INSERT INTO `sys_role_menu` VALUES ('26463', '24', '453');
INSERT INTO `sys_role_menu` VALUES ('26470', '24', '454');
INSERT INTO `sys_role_menu` VALUES ('26477', '24', '455');
INSERT INTO `sys_role_menu` VALUES ('26484', '24', '456');
INSERT INTO `sys_role_menu` VALUES ('26491', '24', '457');
INSERT INTO `sys_role_menu` VALUES ('26498', '24', '458');
INSERT INTO `sys_role_menu` VALUES ('26505', '24', '459');
INSERT INTO `sys_role_menu` VALUES ('26512', '24', '460');
INSERT INTO `sys_role_menu` VALUES ('26519', '24', '461');
INSERT INTO `sys_role_menu` VALUES ('26526', '24', '462');
INSERT INTO `sys_role_menu` VALUES ('26533', '24', '463');
INSERT INTO `sys_role_menu` VALUES ('26540', '24', '464');
INSERT INTO `sys_role_menu` VALUES ('26547', '24', '489');
INSERT INTO `sys_role_menu` VALUES ('26554', '24', '490');
INSERT INTO `sys_role_menu` VALUES ('26561', '24', '559');
INSERT INTO `sys_role_menu` VALUES ('26568', '24', '491');
INSERT INTO `sys_role_menu` VALUES ('26575', '24', '565');
INSERT INTO `sys_role_menu` VALUES ('26582', '24', '566');
INSERT INTO `sys_role_menu` VALUES ('26589', '24', '492');
INSERT INTO `sys_role_menu` VALUES ('26596', '24', '560');
INSERT INTO `sys_role_menu` VALUES ('26603', '24', '493');
INSERT INTO `sys_role_menu` VALUES ('26610', '24', '567');
INSERT INTO `sys_role_menu` VALUES ('26617', '24', '495');
INSERT INTO `sys_role_menu` VALUES ('26624', '24', '498');
INSERT INTO `sys_role_menu` VALUES ('26631', '24', '501');
INSERT INTO `sys_role_menu` VALUES ('26638', '24', '541');
INSERT INTO `sys_role_menu` VALUES ('26645', '24', '542');
INSERT INTO `sys_role_menu` VALUES ('26652', '24', '543');
INSERT INTO `sys_role_menu` VALUES ('26659', '24', '548');
INSERT INTO `sys_role_menu` VALUES ('26666', '24', '544');
INSERT INTO `sys_role_menu` VALUES ('26673', '24', '547');
INSERT INTO `sys_role_menu` VALUES ('26680', '24', '603');
INSERT INTO `sys_role_menu` VALUES ('26687', '24', '604');
INSERT INTO `sys_role_menu` VALUES ('26694', '24', '605');
INSERT INTO `sys_role_menu` VALUES ('26701', '24', '606');
INSERT INTO `sys_role_menu` VALUES ('26708', '24', '611');
INSERT INTO `sys_role_menu` VALUES ('26715', '24', '607');
INSERT INTO `sys_role_menu` VALUES ('26722', '24', '608');
INSERT INTO `sys_role_menu` VALUES ('26729', '24', '609');
INSERT INTO `sys_role_menu` VALUES ('26736', '24', '610');
INSERT INTO `sys_role_menu` VALUES ('26743', '24', '-666666');
INSERT INTO `sys_role_menu` VALUES ('40232', '80', '19');
INSERT INTO `sys_role_menu` VALUES ('40239', '80', '38');
INSERT INTO `sys_role_menu` VALUES ('40246', '80', '42');
INSERT INTO `sys_role_menu` VALUES ('40253', '80', '351');
INSERT INTO `sys_role_menu` VALUES ('40260', '80', '352');
INSERT INTO `sys_role_menu` VALUES ('40267', '80', '361');
INSERT INTO `sys_role_menu` VALUES ('40274', '80', '471');
INSERT INTO `sys_role_menu` VALUES ('40281', '80', '346');
INSERT INTO `sys_role_menu` VALUES ('40288', '80', '369');
INSERT INTO `sys_role_menu` VALUES ('40295', '80', '394');
INSERT INTO `sys_role_menu` VALUES ('40302', '80', '465');
INSERT INTO `sys_role_menu` VALUES ('40309', '80', '466');
INSERT INTO `sys_role_menu` VALUES ('40316', '80', '467');
INSERT INTO `sys_role_menu` VALUES ('40323', '80', '468');
INSERT INTO `sys_role_menu` VALUES ('40330', '80', '469');
INSERT INTO `sys_role_menu` VALUES ('40337', '80', '480');
INSERT INTO `sys_role_menu` VALUES ('40344', '80', '481');
INSERT INTO `sys_role_menu` VALUES ('40351', '80', '497');
INSERT INTO `sys_role_menu` VALUES ('40358', '80', '561');
INSERT INTO `sys_role_menu` VALUES ('40365', '80', '563');
INSERT INTO `sys_role_menu` VALUES ('40372', '80', '375');
INSERT INTO `sys_role_menu` VALUES ('40379', '80', '407');
INSERT INTO `sys_role_menu` VALUES ('40386', '80', '408');
INSERT INTO `sys_role_menu` VALUES ('40393', '80', '526');
INSERT INTO `sys_role_menu` VALUES ('40400', '80', '527');
INSERT INTO `sys_role_menu` VALUES ('40407', '80', '528');
INSERT INTO `sys_role_menu` VALUES ('40414', '80', '529');
INSERT INTO `sys_role_menu` VALUES ('40421', '80', '530');
INSERT INTO `sys_role_menu` VALUES ('40428', '80', '535');
INSERT INTO `sys_role_menu` VALUES ('40435', '80', '536');
INSERT INTO `sys_role_menu` VALUES ('40442', '80', '537');
INSERT INTO `sys_role_menu` VALUES ('40449', '80', '538');
INSERT INTO `sys_role_menu` VALUES ('40456', '80', '539');
INSERT INTO `sys_role_menu` VALUES ('40463', '80', '568');
INSERT INTO `sys_role_menu` VALUES ('40470', '80', '569');
INSERT INTO `sys_role_menu` VALUES ('40477', '80', '570');
INSERT INTO `sys_role_menu` VALUES ('40484', '80', '571');
INSERT INTO `sys_role_menu` VALUES ('40491', '80', '572');
INSERT INTO `sys_role_menu` VALUES ('40498', '80', '573');
INSERT INTO `sys_role_menu` VALUES ('40505', '80', '574');
INSERT INTO `sys_role_menu` VALUES ('40512', '80', '575');
INSERT INTO `sys_role_menu` VALUES ('40519', '80', '576');
INSERT INTO `sys_role_menu` VALUES ('40526', '80', '577');
INSERT INTO `sys_role_menu` VALUES ('40533', '80', '578');
INSERT INTO `sys_role_menu` VALUES ('40540', '80', '579');
INSERT INTO `sys_role_menu` VALUES ('40547', '80', '580');
INSERT INTO `sys_role_menu` VALUES ('40554', '80', '581');
INSERT INTO `sys_role_menu` VALUES ('40561', '80', '582');
INSERT INTO `sys_role_menu` VALUES ('40568', '80', '583');
INSERT INTO `sys_role_menu` VALUES ('40575', '80', '584');
INSERT INTO `sys_role_menu` VALUES ('40582', '80', '585');
INSERT INTO `sys_role_menu` VALUES ('40589', '80', '586');
INSERT INTO `sys_role_menu` VALUES ('40596', '80', '587');
INSERT INTO `sys_role_menu` VALUES ('40603', '80', '588');
INSERT INTO `sys_role_menu` VALUES ('40610', '80', '589');
INSERT INTO `sys_role_menu` VALUES ('40617', '80', '590');
INSERT INTO `sys_role_menu` VALUES ('40624', '80', '591');
INSERT INTO `sys_role_menu` VALUES ('40631', '80', '592');
INSERT INTO `sys_role_menu` VALUES ('40638', '80', '593');
INSERT INTO `sys_role_menu` VALUES ('40645', '80', '594');
INSERT INTO `sys_role_menu` VALUES ('40652', '80', '595');
INSERT INTO `sys_role_menu` VALUES ('40659', '80', '596');
INSERT INTO `sys_role_menu` VALUES ('40666', '80', '597');
INSERT INTO `sys_role_menu` VALUES ('40673', '80', '598');
INSERT INTO `sys_role_menu` VALUES ('40680', '80', '599');
INSERT INTO `sys_role_menu` VALUES ('40687', '80', '600');
INSERT INTO `sys_role_menu` VALUES ('40694', '80', '601');
INSERT INTO `sys_role_menu` VALUES ('40701', '80', '602');
INSERT INTO `sys_role_menu` VALUES ('40708', '80', '401');
INSERT INTO `sys_role_menu` VALUES ('40715', '80', '406');
INSERT INTO `sys_role_menu` VALUES ('40722', '80', '441');
INSERT INTO `sys_role_menu` VALUES ('40729', '80', '446');
INSERT INTO `sys_role_menu` VALUES ('40736', '80', '451');
INSERT INTO `sys_role_menu` VALUES ('40743', '80', '456');
INSERT INTO `sys_role_menu` VALUES ('40750', '80', '461');
INSERT INTO `sys_role_menu` VALUES ('40757', '80', '491');
INSERT INTO `sys_role_menu` VALUES ('40764', '80', '565');
INSERT INTO `sys_role_menu` VALUES ('40771', '80', '566');
INSERT INTO `sys_role_menu` VALUES ('40778', '80', '-666666');
INSERT INTO `sys_role_menu` VALUES ('40785', '80', '17');
INSERT INTO `sys_role_menu` VALUES ('40792', '80', '18');
INSERT INTO `sys_role_menu` VALUES ('40799', '80', '345');
INSERT INTO `sys_role_menu` VALUES ('40806', '80', '367');
INSERT INTO `sys_role_menu` VALUES ('40813', '80', '392');
INSERT INTO `sys_role_menu` VALUES ('40820', '80', '396');
INSERT INTO `sys_role_menu` VALUES ('40827', '80', '397');
INSERT INTO `sys_role_menu` VALUES ('40834', '80', '402');
INSERT INTO `sys_role_menu` VALUES ('40841', '80', '440');
INSERT INTO `sys_role_menu` VALUES ('40848', '80', '445');
INSERT INTO `sys_role_menu` VALUES ('40855', '80', '450');
INSERT INTO `sys_role_menu` VALUES ('40862', '80', '455');
INSERT INTO `sys_role_menu` VALUES ('40869', '80', '460');
INSERT INTO `sys_role_menu` VALUES ('40876', '80', '489');
INSERT INTO `sys_role_menu` VALUES ('40883', '73', '19');
INSERT INTO `sys_role_menu` VALUES ('40890', '73', '38');
INSERT INTO `sys_role_menu` VALUES ('40897', '73', '351');
INSERT INTO `sys_role_menu` VALUES ('40904', '73', '471');
INSERT INTO `sys_role_menu` VALUES ('40911', '73', '346');
INSERT INTO `sys_role_menu` VALUES ('40918', '73', '369');
INSERT INTO `sys_role_menu` VALUES ('40925', '73', '392');
INSERT INTO `sys_role_menu` VALUES ('40932', '73', '393');
INSERT INTO `sys_role_menu` VALUES ('40939', '73', '394');
INSERT INTO `sys_role_menu` VALUES ('40946', '73', '395');
INSERT INTO `sys_role_menu` VALUES ('40953', '73', '534');
INSERT INTO `sys_role_menu` VALUES ('40960', '73', '564');
INSERT INTO `sys_role_menu` VALUES ('40967', '73', '480');
INSERT INTO `sys_role_menu` VALUES ('40974', '73', '481');
INSERT INTO `sys_role_menu` VALUES ('40981', '73', '563');
INSERT INTO `sys_role_menu` VALUES ('40988', '73', '375');
INSERT INTO `sys_role_menu` VALUES ('40995', '73', '407');
INSERT INTO `sys_role_menu` VALUES ('41002', '73', '408');
INSERT INTO `sys_role_menu` VALUES ('41009', '73', '526');
INSERT INTO `sys_role_menu` VALUES ('41016', '73', '527');
INSERT INTO `sys_role_menu` VALUES ('41023', '73', '528');
INSERT INTO `sys_role_menu` VALUES ('41030', '73', '529');
INSERT INTO `sys_role_menu` VALUES ('41037', '73', '530');
INSERT INTO `sys_role_menu` VALUES ('41044', '73', '535');
INSERT INTO `sys_role_menu` VALUES ('41051', '73', '536');
INSERT INTO `sys_role_menu` VALUES ('41058', '73', '537');
INSERT INTO `sys_role_menu` VALUES ('41065', '73', '538');
INSERT INTO `sys_role_menu` VALUES ('41072', '73', '539');
INSERT INTO `sys_role_menu` VALUES ('41079', '73', '568');
INSERT INTO `sys_role_menu` VALUES ('41086', '73', '569');
INSERT INTO `sys_role_menu` VALUES ('41093', '73', '570');
INSERT INTO `sys_role_menu` VALUES ('41100', '73', '571');
INSERT INTO `sys_role_menu` VALUES ('41107', '73', '572');
INSERT INTO `sys_role_menu` VALUES ('41114', '73', '573');
INSERT INTO `sys_role_menu` VALUES ('41121', '73', '574');
INSERT INTO `sys_role_menu` VALUES ('41128', '73', '575');
INSERT INTO `sys_role_menu` VALUES ('41135', '73', '576');
INSERT INTO `sys_role_menu` VALUES ('41142', '73', '577');
INSERT INTO `sys_role_menu` VALUES ('41149', '73', '578');
INSERT INTO `sys_role_menu` VALUES ('41156', '73', '579');
INSERT INTO `sys_role_menu` VALUES ('41163', '73', '580');
INSERT INTO `sys_role_menu` VALUES ('41170', '73', '581');
INSERT INTO `sys_role_menu` VALUES ('41177', '73', '582');
INSERT INTO `sys_role_menu` VALUES ('41184', '73', '583');
INSERT INTO `sys_role_menu` VALUES ('41191', '73', '584');
INSERT INTO `sys_role_menu` VALUES ('41198', '73', '585');
INSERT INTO `sys_role_menu` VALUES ('41205', '73', '586');
INSERT INTO `sys_role_menu` VALUES ('41212', '73', '587');
INSERT INTO `sys_role_menu` VALUES ('41219', '73', '588');
INSERT INTO `sys_role_menu` VALUES ('41226', '73', '589');
INSERT INTO `sys_role_menu` VALUES ('41233', '73', '590');
INSERT INTO `sys_role_menu` VALUES ('41240', '73', '591');
INSERT INTO `sys_role_menu` VALUES ('41247', '73', '592');
INSERT INTO `sys_role_menu` VALUES ('41254', '73', '593');
INSERT INTO `sys_role_menu` VALUES ('41261', '73', '594');
INSERT INTO `sys_role_menu` VALUES ('41268', '73', '595');
INSERT INTO `sys_role_menu` VALUES ('41275', '73', '596');
INSERT INTO `sys_role_menu` VALUES ('41282', '73', '597');
INSERT INTO `sys_role_menu` VALUES ('41289', '73', '598');
INSERT INTO `sys_role_menu` VALUES ('41296', '73', '599');
INSERT INTO `sys_role_menu` VALUES ('41303', '73', '600');
INSERT INTO `sys_role_menu` VALUES ('41310', '73', '601');
INSERT INTO `sys_role_menu` VALUES ('41317', '73', '602');
INSERT INTO `sys_role_menu` VALUES ('41324', '73', '490');
INSERT INTO `sys_role_menu` VALUES ('41331', '73', '559');
INSERT INTO `sys_role_menu` VALUES ('41338', '73', '491');
INSERT INTO `sys_role_menu` VALUES ('41345', '73', '565');
INSERT INTO `sys_role_menu` VALUES ('41352', '73', '566');
INSERT INTO `sys_role_menu` VALUES ('41359', '73', '492');
INSERT INTO `sys_role_menu` VALUES ('41366', '73', '560');
INSERT INTO `sys_role_menu` VALUES ('41373', '73', '-666666');
INSERT INTO `sys_role_menu` VALUES ('41380', '73', '17');
INSERT INTO `sys_role_menu` VALUES ('41387', '73', '18');
INSERT INTO `sys_role_menu` VALUES ('41394', '73', '345');
INSERT INTO `sys_role_menu` VALUES ('41401', '73', '367');
INSERT INTO `sys_role_menu` VALUES ('41408', '73', '489');
INSERT INTO `sys_role_menu` VALUES ('43977', '3', '2');
INSERT INTO `sys_role_menu` VALUES ('43984', '3', '5');
INSERT INTO `sys_role_menu` VALUES ('43991', '3', '6');
INSERT INTO `sys_role_menu` VALUES ('43998', '3', '7');
INSERT INTO `sys_role_menu` VALUES ('44005', '3', '8');
INSERT INTO `sys_role_menu` VALUES ('44012', '3', '3');
INSERT INTO `sys_role_menu` VALUES ('44019', '3', '9');
INSERT INTO `sys_role_menu` VALUES ('44026', '3', '10');
INSERT INTO `sys_role_menu` VALUES ('44033', '3', '11');
INSERT INTO `sys_role_menu` VALUES ('44040', '3', '12');
INSERT INTO `sys_role_menu` VALUES ('44047', '3', '362');
INSERT INTO `sys_role_menu` VALUES ('44054', '3', '363');
INSERT INTO `sys_role_menu` VALUES ('44061', '3', '364');
INSERT INTO `sys_role_menu` VALUES ('44068', '3', '365');
INSERT INTO `sys_role_menu` VALUES ('44075', '3', '366');
INSERT INTO `sys_role_menu` VALUES ('44082', '3', '17');
INSERT INTO `sys_role_menu` VALUES ('44089', '3', '18');
INSERT INTO `sys_role_menu` VALUES ('44096', '3', '19');
INSERT INTO `sys_role_menu` VALUES ('44103', '3', '20');
INSERT INTO `sys_role_menu` VALUES ('44110', '3', '21');
INSERT INTO `sys_role_menu` VALUES ('44117', '3', '22');
INSERT INTO `sys_role_menu` VALUES ('44124', '3', '23');
INSERT INTO `sys_role_menu` VALUES ('44131', '3', '38');
INSERT INTO `sys_role_menu` VALUES ('44138', '3', '39');
INSERT INTO `sys_role_menu` VALUES ('44145', '3', '40');
INSERT INTO `sys_role_menu` VALUES ('44152', '3', '41');
INSERT INTO `sys_role_menu` VALUES ('44159', '3', '42');
INSERT INTO `sys_role_menu` VALUES ('44166', '3', '315');
INSERT INTO `sys_role_menu` VALUES ('44173', '3', '316');
INSERT INTO `sys_role_menu` VALUES ('44180', '3', '317');
INSERT INTO `sys_role_menu` VALUES ('44187', '3', '351');
INSERT INTO `sys_role_menu` VALUES ('44194', '3', '352');
INSERT INTO `sys_role_menu` VALUES ('44201', '3', '358');
INSERT INTO `sys_role_menu` VALUES ('44208', '3', '359');
INSERT INTO `sys_role_menu` VALUES ('44215', '3', '360');
INSERT INTO `sys_role_menu` VALUES ('44222', '3', '361');
INSERT INTO `sys_role_menu` VALUES ('44229', '3', '470');
INSERT INTO `sys_role_menu` VALUES ('44236', '3', '471');
INSERT INTO `sys_role_menu` VALUES ('44243', '3', '540');
INSERT INTO `sys_role_menu` VALUES ('44250', '3', '24');
INSERT INTO `sys_role_menu` VALUES ('44257', '3', '25');
INSERT INTO `sys_role_menu` VALUES ('44264', '3', '26');
INSERT INTO `sys_role_menu` VALUES ('44271', '3', '345');
INSERT INTO `sys_role_menu` VALUES ('44278', '3', '346');
INSERT INTO `sys_role_menu` VALUES ('44285', '3', '347');
INSERT INTO `sys_role_menu` VALUES ('44292', '3', '348');
INSERT INTO `sys_role_menu` VALUES ('44299', '3', '349');
INSERT INTO `sys_role_menu` VALUES ('44306', '3', '350');
INSERT INTO `sys_role_menu` VALUES ('44313', '3', '353');
INSERT INTO `sys_role_menu` VALUES ('44320', '3', '354');
INSERT INTO `sys_role_menu` VALUES ('44327', '3', '391');
INSERT INTO `sys_role_menu` VALUES ('44334', '3', '549');
INSERT INTO `sys_role_menu` VALUES ('44341', '3', '367');
INSERT INTO `sys_role_menu` VALUES ('44348', '3', '368');
INSERT INTO `sys_role_menu` VALUES ('44355', '3', '369');
INSERT INTO `sys_role_menu` VALUES ('44362', '3', '370');
INSERT INTO `sys_role_menu` VALUES ('44369', '3', '392');
INSERT INTO `sys_role_menu` VALUES ('44376', '3', '393');
INSERT INTO `sys_role_menu` VALUES ('44383', '3', '394');
INSERT INTO `sys_role_menu` VALUES ('44390', '3', '395');
INSERT INTO `sys_role_menu` VALUES ('44397', '3', '534');
INSERT INTO `sys_role_menu` VALUES ('44404', '3', '564');
INSERT INTO `sys_role_menu` VALUES ('44411', '3', '465');
INSERT INTO `sys_role_menu` VALUES ('44418', '3', '466');
INSERT INTO `sys_role_menu` VALUES ('44425', '3', '467');
INSERT INTO `sys_role_menu` VALUES ('44432', '3', '468');
INSERT INTO `sys_role_menu` VALUES ('44439', '3', '469');
INSERT INTO `sys_role_menu` VALUES ('44446', '3', '480');
INSERT INTO `sys_role_menu` VALUES ('44453', '3', '481');
INSERT INTO `sys_role_menu` VALUES ('44460', '3', '497');
INSERT INTO `sys_role_menu` VALUES ('44467', '3', '503');
INSERT INTO `sys_role_menu` VALUES ('44474', '3', '561');
INSERT INTO `sys_role_menu` VALUES ('44481', '3', '563');
INSERT INTO `sys_role_menu` VALUES ('44488', '3', '28');
INSERT INTO `sys_role_menu` VALUES ('44495', '3', '29');
INSERT INTO `sys_role_menu` VALUES ('44502', '3', '30');
INSERT INTO `sys_role_menu` VALUES ('44509', '3', '31');
INSERT INTO `sys_role_menu` VALUES ('44516', '3', '32');
INSERT INTO `sys_role_menu` VALUES ('44523', '3', '437');
INSERT INTO `sys_role_menu` VALUES ('44530', '3', '533');
INSERT INTO `sys_role_menu` VALUES ('44537', '3', '33');
INSERT INTO `sys_role_menu` VALUES ('44544', '3', '34');
INSERT INTO `sys_role_menu` VALUES ('44551', '3', '35');
INSERT INTO `sys_role_menu` VALUES ('44558', '3', '36');
INSERT INTO `sys_role_menu` VALUES ('44565', '3', '37');
INSERT INTO `sys_role_menu` VALUES ('44572', '3', '303');
INSERT INTO `sys_role_menu` VALUES ('44579', '3', '304');
INSERT INTO `sys_role_menu` VALUES ('44586', '3', '305');
INSERT INTO `sys_role_menu` VALUES ('44593', '3', '306');
INSERT INTO `sys_role_menu` VALUES ('44600', '3', '307');
INSERT INTO `sys_role_menu` VALUES ('44607', '3', '330');
INSERT INTO `sys_role_menu` VALUES ('44614', '3', '331');
INSERT INTO `sys_role_menu` VALUES ('44621', '3', '332');
INSERT INTO `sys_role_menu` VALUES ('44628', '3', '333');
INSERT INTO `sys_role_menu` VALUES ('44635', '3', '551');
INSERT INTO `sys_role_menu` VALUES ('44642', '3', '423');
INSERT INTO `sys_role_menu` VALUES ('44649', '3', '424');
INSERT INTO `sys_role_menu` VALUES ('44656', '3', '425');
INSERT INTO `sys_role_menu` VALUES ('44663', '3', '426');
INSERT INTO `sys_role_menu` VALUES ('44670', '3', '427');
INSERT INTO `sys_role_menu` VALUES ('44677', '3', '482');
INSERT INTO `sys_role_menu` VALUES ('44684', '3', '483');
INSERT INTO `sys_role_menu` VALUES ('44691', '3', '484');
INSERT INTO `sys_role_menu` VALUES ('44698', '3', '485');
INSERT INTO `sys_role_menu` VALUES ('44705', '3', '550');
INSERT INTO `sys_role_menu` VALUES ('44712', '3', '297');
INSERT INTO `sys_role_menu` VALUES ('44719', '3', '298');
INSERT INTO `sys_role_menu` VALUES ('44726', '3', '299');
INSERT INTO `sys_role_menu` VALUES ('44733', '3', '300');
INSERT INTO `sys_role_menu` VALUES ('44740', '3', '301');
INSERT INTO `sys_role_menu` VALUES ('44747', '3', '302');
INSERT INTO `sys_role_menu` VALUES ('44754', '3', '308');
INSERT INTO `sys_role_menu` VALUES ('44761', '3', '309');
INSERT INTO `sys_role_menu` VALUES ('44768', '3', '310');
INSERT INTO `sys_role_menu` VALUES ('44775', '3', '311');
INSERT INTO `sys_role_menu` VALUES ('44782', '3', '312');
INSERT INTO `sys_role_menu` VALUES ('44789', '3', '428');
INSERT INTO `sys_role_menu` VALUES ('44796', '3', '429');
INSERT INTO `sys_role_menu` VALUES ('44803', '3', '430');
INSERT INTO `sys_role_menu` VALUES ('44810', '3', '431');
INSERT INTO `sys_role_menu` VALUES ('44817', '3', '499');
INSERT INTO `sys_role_menu` VALUES ('44824', '3', '500');
INSERT INTO `sys_role_menu` VALUES ('44831', '3', '472');
INSERT INTO `sys_role_menu` VALUES ('44838', '3', '473');
INSERT INTO `sys_role_menu` VALUES ('44845', '3', '474');
INSERT INTO `sys_role_menu` VALUES ('44852', '3', '475');
INSERT INTO `sys_role_menu` VALUES ('44859', '3', '494');
INSERT INTO `sys_role_menu` VALUES ('44866', '3', '476');
INSERT INTO `sys_role_menu` VALUES ('44873', '3', '477');
INSERT INTO `sys_role_menu` VALUES ('44880', '3', '478');
INSERT INTO `sys_role_menu` VALUES ('44887', '3', '479');
INSERT INTO `sys_role_menu` VALUES ('44894', '3', '502');
INSERT INTO `sys_role_menu` VALUES ('44901', '3', '318');
INSERT INTO `sys_role_menu` VALUES ('44908', '3', '319');
INSERT INTO `sys_role_menu` VALUES ('44915', '3', '320');
INSERT INTO `sys_role_menu` VALUES ('44922', '3', '321');
INSERT INTO `sys_role_menu` VALUES ('44929', '3', '322');
INSERT INTO `sys_role_menu` VALUES ('44936', '3', '323');
INSERT INTO `sys_role_menu` VALUES ('44943', '3', '324');
INSERT INTO `sys_role_menu` VALUES ('44950', '3', '325');
INSERT INTO `sys_role_menu` VALUES ('44957', '3', '326');
INSERT INTO `sys_role_menu` VALUES ('44964', '3', '327');
INSERT INTO `sys_role_menu` VALUES ('44971', '3', '328');
INSERT INTO `sys_role_menu` VALUES ('44978', '3', '329');
INSERT INTO `sys_role_menu` VALUES ('44985', '3', '415');
INSERT INTO `sys_role_menu` VALUES ('44992', '3', '416');
INSERT INTO `sys_role_menu` VALUES ('44999', '3', '562');
INSERT INTO `sys_role_menu` VALUES ('45006', '3', '419');
INSERT INTO `sys_role_menu` VALUES ('45013', '3', '420');
INSERT INTO `sys_role_menu` VALUES ('45020', '3', '421');
INSERT INTO `sys_role_menu` VALUES ('45027', '3', '422');
INSERT INTO `sys_role_menu` VALUES ('45034', '3', '496');
INSERT INTO `sys_role_menu` VALUES ('45041', '3', '334');
INSERT INTO `sys_role_menu` VALUES ('45048', '3', '337');
INSERT INTO `sys_role_menu` VALUES ('45055', '3', '338');
INSERT INTO `sys_role_menu` VALUES ('45062', '3', '339');
INSERT INTO `sys_role_menu` VALUES ('45069', '3', '340');
INSERT INTO `sys_role_menu` VALUES ('45076', '3', '341');
INSERT INTO `sys_role_menu` VALUES ('45083', '3', '504');
INSERT INTO `sys_role_menu` VALUES ('45090', '3', '505');
INSERT INTO `sys_role_menu` VALUES ('45097', '3', '506');
INSERT INTO `sys_role_menu` VALUES ('45104', '3', '507');
INSERT INTO `sys_role_menu` VALUES ('45111', '3', '508');
INSERT INTO `sys_role_menu` VALUES ('45118', '3', '532');
INSERT INTO `sys_role_menu` VALUES ('45125', '3', '552');
INSERT INTO `sys_role_menu` VALUES ('45132', '3', '553');
INSERT INTO `sys_role_menu` VALUES ('45139', '3', '554');
INSERT INTO `sys_role_menu` VALUES ('45146', '3', '558');
INSERT INTO `sys_role_menu` VALUES ('45153', '3', '555');
INSERT INTO `sys_role_menu` VALUES ('45160', '3', '556');
INSERT INTO `sys_role_menu` VALUES ('45167', '3', '557');
INSERT INTO `sys_role_menu` VALUES ('45174', '3', '612');
INSERT INTO `sys_role_menu` VALUES ('45181', '3', '613');
INSERT INTO `sys_role_menu` VALUES ('45188', '3', '614');
INSERT INTO `sys_role_menu` VALUES ('45195', '3', '615');
INSERT INTO `sys_role_menu` VALUES ('45202', '3', '375');
INSERT INTO `sys_role_menu` VALUES ('45209', '3', '630');
INSERT INTO `sys_role_menu` VALUES ('45216', '3', '620');
INSERT INTO `sys_role_menu` VALUES ('45223', '3', '621');
INSERT INTO `sys_role_menu` VALUES ('45230', '3', '622');
INSERT INTO `sys_role_menu` VALUES ('45237', '3', '623');
INSERT INTO `sys_role_menu` VALUES ('45244', '3', '624');
INSERT INTO `sys_role_menu` VALUES ('45251', '3', '625');
INSERT INTO `sys_role_menu` VALUES ('45258', '3', '626');
INSERT INTO `sys_role_menu` VALUES ('45265', '3', '627');
INSERT INTO `sys_role_menu` VALUES ('45272', '3', '631');
INSERT INTO `sys_role_menu` VALUES ('45279', '3', '632');
INSERT INTO `sys_role_menu` VALUES ('45286', '3', '633');
INSERT INTO `sys_role_menu` VALUES ('45293', '3', '526');
INSERT INTO `sys_role_menu` VALUES ('45300', '3', '527');
INSERT INTO `sys_role_menu` VALUES ('45307', '3', '535');
INSERT INTO `sys_role_menu` VALUES ('45314', '3', '536');
INSERT INTO `sys_role_menu` VALUES ('45321', '3', '568');
INSERT INTO `sys_role_menu` VALUES ('45328', '3', '569');
INSERT INTO `sys_role_menu` VALUES ('45335', '3', '578');
INSERT INTO `sys_role_menu` VALUES ('45342', '3', '579');
INSERT INTO `sys_role_menu` VALUES ('45349', '3', '583');
INSERT INTO `sys_role_menu` VALUES ('45356', '3', '584');
INSERT INTO `sys_role_menu` VALUES ('45363', '3', '588');
INSERT INTO `sys_role_menu` VALUES ('45370', '3', '589');
INSERT INTO `sys_role_menu` VALUES ('45377', '3', '634');
INSERT INTO `sys_role_menu` VALUES ('45384', '3', '573');
INSERT INTO `sys_role_menu` VALUES ('45391', '3', '574');
INSERT INTO `sys_role_menu` VALUES ('45398', '3', '593');
INSERT INTO `sys_role_menu` VALUES ('45405', '3', '594');
INSERT INTO `sys_role_menu` VALUES ('45412', '3', '635');
INSERT INTO `sys_role_menu` VALUES ('45419', '3', '618');
INSERT INTO `sys_role_menu` VALUES ('45426', '3', '619');
INSERT INTO `sys_role_menu` VALUES ('45433', '3', '396');
INSERT INTO `sys_role_menu` VALUES ('45440', '3', '397');
INSERT INTO `sys_role_menu` VALUES ('45447', '3', '398');
INSERT INTO `sys_role_menu` VALUES ('45454', '3', '399');
INSERT INTO `sys_role_menu` VALUES ('45461', '3', '400');
INSERT INTO `sys_role_menu` VALUES ('45468', '3', '401');
INSERT INTO `sys_role_menu` VALUES ('45475', '3', '402');
INSERT INTO `sys_role_menu` VALUES ('45482', '3', '403');
INSERT INTO `sys_role_menu` VALUES ('45489', '3', '404');
INSERT INTO `sys_role_menu` VALUES ('45496', '3', '405');
INSERT INTO `sys_role_menu` VALUES ('45503', '3', '406');
INSERT INTO `sys_role_menu` VALUES ('45510', '3', '440');
INSERT INTO `sys_role_menu` VALUES ('45517', '3', '441');
INSERT INTO `sys_role_menu` VALUES ('45524', '3', '442');
INSERT INTO `sys_role_menu` VALUES ('45531', '3', '443');
INSERT INTO `sys_role_menu` VALUES ('45538', '3', '444');
INSERT INTO `sys_role_menu` VALUES ('45545', '3', '445');
INSERT INTO `sys_role_menu` VALUES ('45552', '3', '446');
INSERT INTO `sys_role_menu` VALUES ('45559', '3', '447');
INSERT INTO `sys_role_menu` VALUES ('45566', '3', '448');
INSERT INTO `sys_role_menu` VALUES ('45573', '3', '449');
INSERT INTO `sys_role_menu` VALUES ('45580', '3', '450');
INSERT INTO `sys_role_menu` VALUES ('45587', '3', '451');
INSERT INTO `sys_role_menu` VALUES ('45594', '3', '452');
INSERT INTO `sys_role_menu` VALUES ('45601', '3', '453');
INSERT INTO `sys_role_menu` VALUES ('45608', '3', '454');
INSERT INTO `sys_role_menu` VALUES ('45615', '3', '455');
INSERT INTO `sys_role_menu` VALUES ('45622', '3', '456');
INSERT INTO `sys_role_menu` VALUES ('45629', '3', '457');
INSERT INTO `sys_role_menu` VALUES ('45636', '3', '458');
INSERT INTO `sys_role_menu` VALUES ('45643', '3', '459');
INSERT INTO `sys_role_menu` VALUES ('45650', '3', '460');
INSERT INTO `sys_role_menu` VALUES ('45657', '3', '461');
INSERT INTO `sys_role_menu` VALUES ('45664', '3', '462');
INSERT INTO `sys_role_menu` VALUES ('45671', '3', '463');
INSERT INTO `sys_role_menu` VALUES ('45678', '3', '464');
INSERT INTO `sys_role_menu` VALUES ('45685', '3', '408');
INSERT INTO `sys_role_menu` VALUES ('45692', '3', '489');
INSERT INTO `sys_role_menu` VALUES ('45699', '3', '490');
INSERT INTO `sys_role_menu` VALUES ('45706', '3', '559');
INSERT INTO `sys_role_menu` VALUES ('45713', '3', '491');
INSERT INTO `sys_role_menu` VALUES ('45720', '3', '565');
INSERT INTO `sys_role_menu` VALUES ('45727', '3', '566');
INSERT INTO `sys_role_menu` VALUES ('45734', '3', '492');
INSERT INTO `sys_role_menu` VALUES ('45741', '3', '560');
INSERT INTO `sys_role_menu` VALUES ('45748', '3', '493');
INSERT INTO `sys_role_menu` VALUES ('45755', '3', '567');
INSERT INTO `sys_role_menu` VALUES ('45762', '3', '495');
INSERT INTO `sys_role_menu` VALUES ('45769', '3', '498');
INSERT INTO `sys_role_menu` VALUES ('45776', '3', '501');
INSERT INTO `sys_role_menu` VALUES ('45783', '3', '541');
INSERT INTO `sys_role_menu` VALUES ('45790', '3', '542');
INSERT INTO `sys_role_menu` VALUES ('45797', '3', '543');
INSERT INTO `sys_role_menu` VALUES ('45804', '3', '548');
INSERT INTO `sys_role_menu` VALUES ('45811', '3', '544');
INSERT INTO `sys_role_menu` VALUES ('45818', '3', '547');
INSERT INTO `sys_role_menu` VALUES ('45825', '3', '603');
INSERT INTO `sys_role_menu` VALUES ('45832', '3', '604');
INSERT INTO `sys_role_menu` VALUES ('45839', '3', '605');
INSERT INTO `sys_role_menu` VALUES ('45846', '3', '606');
INSERT INTO `sys_role_menu` VALUES ('45853', '3', '611');
INSERT INTO `sys_role_menu` VALUES ('45860', '3', '607');
INSERT INTO `sys_role_menu` VALUES ('45867', '3', '608');
INSERT INTO `sys_role_menu` VALUES ('45874', '3', '609');
INSERT INTO `sys_role_menu` VALUES ('45881', '3', '610');
INSERT INTO `sys_role_menu` VALUES ('45888', '3', '598');
INSERT INTO `sys_role_menu` VALUES ('45895', '3', '599');
INSERT INTO `sys_role_menu` VALUES ('45902', '3', '-666666');
INSERT INTO `sys_role_menu` VALUES ('45909', '3', '1');
INSERT INTO `sys_role_menu` VALUES ('45916', '3', '27');
INSERT INTO `sys_role_menu` VALUES ('45923', '3', '616');
INSERT INTO `sys_role_menu` VALUES ('45930', '38', '1');
INSERT INTO `sys_role_menu` VALUES ('45937', '38', '2');
INSERT INTO `sys_role_menu` VALUES ('45944', '38', '5');
INSERT INTO `sys_role_menu` VALUES ('45951', '38', '6');
INSERT INTO `sys_role_menu` VALUES ('45958', '38', '7');
INSERT INTO `sys_role_menu` VALUES ('45965', '38', '8');
INSERT INTO `sys_role_menu` VALUES ('45972', '38', '3');
INSERT INTO `sys_role_menu` VALUES ('45979', '38', '9');
INSERT INTO `sys_role_menu` VALUES ('45986', '38', '10');
INSERT INTO `sys_role_menu` VALUES ('45993', '38', '11');
INSERT INTO `sys_role_menu` VALUES ('46000', '38', '12');
INSERT INTO `sys_role_menu` VALUES ('46007', '38', '362');
INSERT INTO `sys_role_menu` VALUES ('46014', '38', '363');
INSERT INTO `sys_role_menu` VALUES ('46021', '38', '364');
INSERT INTO `sys_role_menu` VALUES ('46028', '38', '365');
INSERT INTO `sys_role_menu` VALUES ('46035', '38', '366');
INSERT INTO `sys_role_menu` VALUES ('46042', '38', '17');
INSERT INTO `sys_role_menu` VALUES ('46049', '38', '18');
INSERT INTO `sys_role_menu` VALUES ('46056', '38', '19');
INSERT INTO `sys_role_menu` VALUES ('46063', '38', '20');
INSERT INTO `sys_role_menu` VALUES ('46070', '38', '21');
INSERT INTO `sys_role_menu` VALUES ('46077', '38', '22');
INSERT INTO `sys_role_menu` VALUES ('46084', '38', '23');
INSERT INTO `sys_role_menu` VALUES ('46091', '38', '38');
INSERT INTO `sys_role_menu` VALUES ('46098', '38', '39');
INSERT INTO `sys_role_menu` VALUES ('46105', '38', '40');
INSERT INTO `sys_role_menu` VALUES ('46112', '38', '41');
INSERT INTO `sys_role_menu` VALUES ('46119', '38', '42');
INSERT INTO `sys_role_menu` VALUES ('46126', '38', '315');
INSERT INTO `sys_role_menu` VALUES ('46133', '38', '316');
INSERT INTO `sys_role_menu` VALUES ('46140', '38', '317');
INSERT INTO `sys_role_menu` VALUES ('46147', '38', '351');
INSERT INTO `sys_role_menu` VALUES ('46154', '38', '352');
INSERT INTO `sys_role_menu` VALUES ('46161', '38', '358');
INSERT INTO `sys_role_menu` VALUES ('46168', '38', '359');
INSERT INTO `sys_role_menu` VALUES ('46175', '38', '360');
INSERT INTO `sys_role_menu` VALUES ('46182', '38', '361');
INSERT INTO `sys_role_menu` VALUES ('46189', '38', '470');
INSERT INTO `sys_role_menu` VALUES ('46196', '38', '471');
INSERT INTO `sys_role_menu` VALUES ('46203', '38', '540');
INSERT INTO `sys_role_menu` VALUES ('46210', '38', '24');
INSERT INTO `sys_role_menu` VALUES ('46217', '38', '25');
INSERT INTO `sys_role_menu` VALUES ('46224', '38', '26');
INSERT INTO `sys_role_menu` VALUES ('46231', '38', '345');
INSERT INTO `sys_role_menu` VALUES ('46238', '38', '346');
INSERT INTO `sys_role_menu` VALUES ('46245', '38', '347');
INSERT INTO `sys_role_menu` VALUES ('46252', '38', '348');
INSERT INTO `sys_role_menu` VALUES ('46259', '38', '349');
INSERT INTO `sys_role_menu` VALUES ('46266', '38', '350');
INSERT INTO `sys_role_menu` VALUES ('46273', '38', '353');
INSERT INTO `sys_role_menu` VALUES ('46280', '38', '354');
INSERT INTO `sys_role_menu` VALUES ('46287', '38', '391');
INSERT INTO `sys_role_menu` VALUES ('46294', '38', '549');
INSERT INTO `sys_role_menu` VALUES ('46301', '38', '367');
INSERT INTO `sys_role_menu` VALUES ('46308', '38', '368');
INSERT INTO `sys_role_menu` VALUES ('46315', '38', '369');
INSERT INTO `sys_role_menu` VALUES ('46322', '38', '370');
INSERT INTO `sys_role_menu` VALUES ('46329', '38', '392');
INSERT INTO `sys_role_menu` VALUES ('46336', '38', '393');
INSERT INTO `sys_role_menu` VALUES ('46343', '38', '394');
INSERT INTO `sys_role_menu` VALUES ('46350', '38', '395');
INSERT INTO `sys_role_menu` VALUES ('46357', '38', '534');
INSERT INTO `sys_role_menu` VALUES ('46364', '38', '564');
INSERT INTO `sys_role_menu` VALUES ('46371', '38', '465');
INSERT INTO `sys_role_menu` VALUES ('46378', '38', '466');
INSERT INTO `sys_role_menu` VALUES ('46385', '38', '467');
INSERT INTO `sys_role_menu` VALUES ('46392', '38', '468');
INSERT INTO `sys_role_menu` VALUES ('46399', '38', '469');
INSERT INTO `sys_role_menu` VALUES ('46406', '38', '480');
INSERT INTO `sys_role_menu` VALUES ('46413', '38', '481');
INSERT INTO `sys_role_menu` VALUES ('46420', '38', '497');
INSERT INTO `sys_role_menu` VALUES ('46427', '38', '503');
INSERT INTO `sys_role_menu` VALUES ('46434', '38', '561');
INSERT INTO `sys_role_menu` VALUES ('46441', '38', '563');
INSERT INTO `sys_role_menu` VALUES ('46448', '38', '27');
INSERT INTO `sys_role_menu` VALUES ('46455', '38', '28');
INSERT INTO `sys_role_menu` VALUES ('46462', '38', '29');
INSERT INTO `sys_role_menu` VALUES ('46469', '38', '30');
INSERT INTO `sys_role_menu` VALUES ('46476', '38', '31');
INSERT INTO `sys_role_menu` VALUES ('46483', '38', '32');
INSERT INTO `sys_role_menu` VALUES ('46490', '38', '437');
INSERT INTO `sys_role_menu` VALUES ('46497', '38', '533');
INSERT INTO `sys_role_menu` VALUES ('46504', '38', '33');
INSERT INTO `sys_role_menu` VALUES ('46511', '38', '34');
INSERT INTO `sys_role_menu` VALUES ('46518', '38', '35');
INSERT INTO `sys_role_menu` VALUES ('46525', '38', '36');
INSERT INTO `sys_role_menu` VALUES ('46532', '38', '37');
INSERT INTO `sys_role_menu` VALUES ('46539', '38', '303');
INSERT INTO `sys_role_menu` VALUES ('46546', '38', '304');
INSERT INTO `sys_role_menu` VALUES ('46553', '38', '305');
INSERT INTO `sys_role_menu` VALUES ('46560', '38', '306');
INSERT INTO `sys_role_menu` VALUES ('46567', '38', '307');
INSERT INTO `sys_role_menu` VALUES ('46574', '38', '330');
INSERT INTO `sys_role_menu` VALUES ('46581', '38', '331');
INSERT INTO `sys_role_menu` VALUES ('46588', '38', '332');
INSERT INTO `sys_role_menu` VALUES ('46595', '38', '333');
INSERT INTO `sys_role_menu` VALUES ('46602', '38', '551');
INSERT INTO `sys_role_menu` VALUES ('46609', '38', '423');
INSERT INTO `sys_role_menu` VALUES ('46616', '38', '424');
INSERT INTO `sys_role_menu` VALUES ('46623', '38', '425');
INSERT INTO `sys_role_menu` VALUES ('46630', '38', '426');
INSERT INTO `sys_role_menu` VALUES ('46637', '38', '427');
INSERT INTO `sys_role_menu` VALUES ('46644', '38', '482');
INSERT INTO `sys_role_menu` VALUES ('46651', '38', '483');
INSERT INTO `sys_role_menu` VALUES ('46658', '38', '484');
INSERT INTO `sys_role_menu` VALUES ('46665', '38', '485');
INSERT INTO `sys_role_menu` VALUES ('46672', '38', '550');
INSERT INTO `sys_role_menu` VALUES ('46679', '38', '297');
INSERT INTO `sys_role_menu` VALUES ('46686', '38', '298');
INSERT INTO `sys_role_menu` VALUES ('46693', '38', '299');
INSERT INTO `sys_role_menu` VALUES ('46700', '38', '300');
INSERT INTO `sys_role_menu` VALUES ('46707', '38', '301');
INSERT INTO `sys_role_menu` VALUES ('46714', '38', '302');
INSERT INTO `sys_role_menu` VALUES ('46721', '38', '308');
INSERT INTO `sys_role_menu` VALUES ('46728', '38', '309');
INSERT INTO `sys_role_menu` VALUES ('46735', '38', '310');
INSERT INTO `sys_role_menu` VALUES ('46742', '38', '311');
INSERT INTO `sys_role_menu` VALUES ('46749', '38', '312');
INSERT INTO `sys_role_menu` VALUES ('46756', '38', '428');
INSERT INTO `sys_role_menu` VALUES ('46763', '38', '429');
INSERT INTO `sys_role_menu` VALUES ('46770', '38', '430');
INSERT INTO `sys_role_menu` VALUES ('46777', '38', '431');
INSERT INTO `sys_role_menu` VALUES ('46784', '38', '499');
INSERT INTO `sys_role_menu` VALUES ('46791', '38', '500');
INSERT INTO `sys_role_menu` VALUES ('46798', '38', '472');
INSERT INTO `sys_role_menu` VALUES ('46805', '38', '473');
INSERT INTO `sys_role_menu` VALUES ('46812', '38', '474');
INSERT INTO `sys_role_menu` VALUES ('46819', '38', '475');
INSERT INTO `sys_role_menu` VALUES ('46826', '38', '494');
INSERT INTO `sys_role_menu` VALUES ('46833', '38', '476');
INSERT INTO `sys_role_menu` VALUES ('46840', '38', '477');
INSERT INTO `sys_role_menu` VALUES ('46847', '38', '478');
INSERT INTO `sys_role_menu` VALUES ('46854', '38', '479');
INSERT INTO `sys_role_menu` VALUES ('46861', '38', '502');
INSERT INTO `sys_role_menu` VALUES ('46868', '38', '318');
INSERT INTO `sys_role_menu` VALUES ('46875', '38', '319');
INSERT INTO `sys_role_menu` VALUES ('46882', '38', '320');
INSERT INTO `sys_role_menu` VALUES ('46889', '38', '321');
INSERT INTO `sys_role_menu` VALUES ('46896', '38', '322');
INSERT INTO `sys_role_menu` VALUES ('46903', '38', '323');
INSERT INTO `sys_role_menu` VALUES ('46910', '38', '324');
INSERT INTO `sys_role_menu` VALUES ('46917', '38', '325');
INSERT INTO `sys_role_menu` VALUES ('46924', '38', '326');
INSERT INTO `sys_role_menu` VALUES ('46931', '38', '327');
INSERT INTO `sys_role_menu` VALUES ('46938', '38', '328');
INSERT INTO `sys_role_menu` VALUES ('46945', '38', '329');
INSERT INTO `sys_role_menu` VALUES ('46952', '38', '415');
INSERT INTO `sys_role_menu` VALUES ('46959', '38', '416');
INSERT INTO `sys_role_menu` VALUES ('46966', '38', '562');
INSERT INTO `sys_role_menu` VALUES ('46973', '38', '419');
INSERT INTO `sys_role_menu` VALUES ('46980', '38', '420');
INSERT INTO `sys_role_menu` VALUES ('46987', '38', '421');
INSERT INTO `sys_role_menu` VALUES ('46994', '38', '422');
INSERT INTO `sys_role_menu` VALUES ('47001', '38', '496');
INSERT INTO `sys_role_menu` VALUES ('47008', '38', '334');
INSERT INTO `sys_role_menu` VALUES ('47015', '38', '337');
INSERT INTO `sys_role_menu` VALUES ('47022', '38', '338');
INSERT INTO `sys_role_menu` VALUES ('47029', '38', '339');
INSERT INTO `sys_role_menu` VALUES ('47036', '38', '340');
INSERT INTO `sys_role_menu` VALUES ('47043', '38', '341');
INSERT INTO `sys_role_menu` VALUES ('47050', '38', '504');
INSERT INTO `sys_role_menu` VALUES ('47057', '38', '505');
INSERT INTO `sys_role_menu` VALUES ('47064', '38', '506');
INSERT INTO `sys_role_menu` VALUES ('47071', '38', '507');
INSERT INTO `sys_role_menu` VALUES ('47078', '38', '508');
INSERT INTO `sys_role_menu` VALUES ('47085', '38', '532');
INSERT INTO `sys_role_menu` VALUES ('47092', '38', '552');
INSERT INTO `sys_role_menu` VALUES ('47099', '38', '553');
INSERT INTO `sys_role_menu` VALUES ('47106', '38', '554');
INSERT INTO `sys_role_menu` VALUES ('47113', '38', '558');
INSERT INTO `sys_role_menu` VALUES ('47120', '38', '555');
INSERT INTO `sys_role_menu` VALUES ('47127', '38', '556');
INSERT INTO `sys_role_menu` VALUES ('47134', '38', '557');
INSERT INTO `sys_role_menu` VALUES ('47141', '38', '612');
INSERT INTO `sys_role_menu` VALUES ('47148', '38', '613');
INSERT INTO `sys_role_menu` VALUES ('47155', '38', '614');
INSERT INTO `sys_role_menu` VALUES ('47162', '38', '615');
INSERT INTO `sys_role_menu` VALUES ('47169', '38', '375');
INSERT INTO `sys_role_menu` VALUES ('47176', '38', '630');
INSERT INTO `sys_role_menu` VALUES ('47183', '38', '620');
INSERT INTO `sys_role_menu` VALUES ('47190', '38', '621');
INSERT INTO `sys_role_menu` VALUES ('47197', '38', '622');
INSERT INTO `sys_role_menu` VALUES ('47204', '38', '623');
INSERT INTO `sys_role_menu` VALUES ('47211', '38', '624');
INSERT INTO `sys_role_menu` VALUES ('47218', '38', '625');
INSERT INTO `sys_role_menu` VALUES ('47225', '38', '626');
INSERT INTO `sys_role_menu` VALUES ('47232', '38', '627');
INSERT INTO `sys_role_menu` VALUES ('47239', '38', '631');
INSERT INTO `sys_role_menu` VALUES ('47246', '38', '632');
INSERT INTO `sys_role_menu` VALUES ('47253', '38', '633');
INSERT INTO `sys_role_menu` VALUES ('47260', '38', '526');
INSERT INTO `sys_role_menu` VALUES ('47267', '38', '527');
INSERT INTO `sys_role_menu` VALUES ('47274', '38', '535');
INSERT INTO `sys_role_menu` VALUES ('47281', '38', '536');
INSERT INTO `sys_role_menu` VALUES ('47288', '38', '568');
INSERT INTO `sys_role_menu` VALUES ('47295', '38', '569');
INSERT INTO `sys_role_menu` VALUES ('47302', '38', '578');
INSERT INTO `sys_role_menu` VALUES ('47309', '38', '579');
INSERT INTO `sys_role_menu` VALUES ('47316', '38', '583');
INSERT INTO `sys_role_menu` VALUES ('47323', '38', '584');
INSERT INTO `sys_role_menu` VALUES ('47330', '38', '588');
INSERT INTO `sys_role_menu` VALUES ('47337', '38', '589');
INSERT INTO `sys_role_menu` VALUES ('47344', '38', '634');
INSERT INTO `sys_role_menu` VALUES ('47351', '38', '573');
INSERT INTO `sys_role_menu` VALUES ('47358', '38', '574');
INSERT INTO `sys_role_menu` VALUES ('47365', '38', '593');
INSERT INTO `sys_role_menu` VALUES ('47372', '38', '594');
INSERT INTO `sys_role_menu` VALUES ('47379', '38', '635');
INSERT INTO `sys_role_menu` VALUES ('47386', '38', '618');
INSERT INTO `sys_role_menu` VALUES ('47393', '38', '619');
INSERT INTO `sys_role_menu` VALUES ('47400', '38', '396');
INSERT INTO `sys_role_menu` VALUES ('47407', '38', '397');
INSERT INTO `sys_role_menu` VALUES ('47414', '38', '398');
INSERT INTO `sys_role_menu` VALUES ('47421', '38', '399');
INSERT INTO `sys_role_menu` VALUES ('47428', '38', '400');
INSERT INTO `sys_role_menu` VALUES ('47435', '38', '401');
INSERT INTO `sys_role_menu` VALUES ('47442', '38', '402');
INSERT INTO `sys_role_menu` VALUES ('47449', '38', '403');
INSERT INTO `sys_role_menu` VALUES ('47456', '38', '404');
INSERT INTO `sys_role_menu` VALUES ('47463', '38', '405');
INSERT INTO `sys_role_menu` VALUES ('47470', '38', '406');
INSERT INTO `sys_role_menu` VALUES ('47477', '38', '440');
INSERT INTO `sys_role_menu` VALUES ('47484', '38', '441');
INSERT INTO `sys_role_menu` VALUES ('47491', '38', '442');
INSERT INTO `sys_role_menu` VALUES ('47498', '38', '443');
INSERT INTO `sys_role_menu` VALUES ('47505', '38', '444');
INSERT INTO `sys_role_menu` VALUES ('47512', '38', '445');
INSERT INTO `sys_role_menu` VALUES ('47519', '38', '446');
INSERT INTO `sys_role_menu` VALUES ('47526', '38', '447');
INSERT INTO `sys_role_menu` VALUES ('47533', '38', '448');
INSERT INTO `sys_role_menu` VALUES ('47540', '38', '449');
INSERT INTO `sys_role_menu` VALUES ('47547', '38', '450');
INSERT INTO `sys_role_menu` VALUES ('47554', '38', '451');
INSERT INTO `sys_role_menu` VALUES ('47561', '38', '452');
INSERT INTO `sys_role_menu` VALUES ('47568', '38', '453');
INSERT INTO `sys_role_menu` VALUES ('47575', '38', '454');
INSERT INTO `sys_role_menu` VALUES ('47582', '38', '455');
INSERT INTO `sys_role_menu` VALUES ('47589', '38', '456');
INSERT INTO `sys_role_menu` VALUES ('47596', '38', '457');
INSERT INTO `sys_role_menu` VALUES ('47603', '38', '458');
INSERT INTO `sys_role_menu` VALUES ('47610', '38', '459');
INSERT INTO `sys_role_menu` VALUES ('47617', '38', '460');
INSERT INTO `sys_role_menu` VALUES ('47624', '38', '461');
INSERT INTO `sys_role_menu` VALUES ('47631', '38', '462');
INSERT INTO `sys_role_menu` VALUES ('47638', '38', '463');
INSERT INTO `sys_role_menu` VALUES ('47645', '38', '464');
INSERT INTO `sys_role_menu` VALUES ('47652', '38', '408');
INSERT INTO `sys_role_menu` VALUES ('47659', '38', '489');
INSERT INTO `sys_role_menu` VALUES ('47666', '38', '490');
INSERT INTO `sys_role_menu` VALUES ('47673', '38', '559');
INSERT INTO `sys_role_menu` VALUES ('47680', '38', '491');
INSERT INTO `sys_role_menu` VALUES ('47687', '38', '565');
INSERT INTO `sys_role_menu` VALUES ('47694', '38', '566');
INSERT INTO `sys_role_menu` VALUES ('47701', '38', '492');
INSERT INTO `sys_role_menu` VALUES ('47708', '38', '560');
INSERT INTO `sys_role_menu` VALUES ('47715', '38', '493');
INSERT INTO `sys_role_menu` VALUES ('47722', '38', '567');
INSERT INTO `sys_role_menu` VALUES ('47729', '38', '495');
INSERT INTO `sys_role_menu` VALUES ('47736', '38', '498');
INSERT INTO `sys_role_menu` VALUES ('47743', '38', '501');
INSERT INTO `sys_role_menu` VALUES ('47750', '38', '541');
INSERT INTO `sys_role_menu` VALUES ('47757', '38', '542');
INSERT INTO `sys_role_menu` VALUES ('47764', '38', '543');
INSERT INTO `sys_role_menu` VALUES ('47771', '38', '548');
INSERT INTO `sys_role_menu` VALUES ('47778', '38', '544');
INSERT INTO `sys_role_menu` VALUES ('47785', '38', '547');
INSERT INTO `sys_role_menu` VALUES ('47792', '38', '603');
INSERT INTO `sys_role_menu` VALUES ('47799', '38', '604');
INSERT INTO `sys_role_menu` VALUES ('47806', '38', '605');
INSERT INTO `sys_role_menu` VALUES ('47813', '38', '606');
INSERT INTO `sys_role_menu` VALUES ('47820', '38', '611');
INSERT INTO `sys_role_menu` VALUES ('47827', '38', '607');
INSERT INTO `sys_role_menu` VALUES ('47834', '38', '608');
INSERT INTO `sys_role_menu` VALUES ('47841', '38', '609');
INSERT INTO `sys_role_menu` VALUES ('47848', '38', '610');
INSERT INTO `sys_role_menu` VALUES ('47855', '38', '616');
INSERT INTO `sys_role_menu` VALUES ('47862', '38', '598');
INSERT INTO `sys_role_menu` VALUES ('47869', '38', '599');
INSERT INTO `sys_role_menu` VALUES ('47876', '38', '-666666');
INSERT INTO `sys_role_menu` VALUES ('47883', '45', '1');
INSERT INTO `sys_role_menu` VALUES ('47890', '45', '2');
INSERT INTO `sys_role_menu` VALUES ('47897', '45', '5');
INSERT INTO `sys_role_menu` VALUES ('47904', '45', '6');
INSERT INTO `sys_role_menu` VALUES ('47911', '45', '7');
INSERT INTO `sys_role_menu` VALUES ('47918', '45', '8');
INSERT INTO `sys_role_menu` VALUES ('47925', '45', '3');
INSERT INTO `sys_role_menu` VALUES ('47932', '45', '9');
INSERT INTO `sys_role_menu` VALUES ('47939', '45', '10');
INSERT INTO `sys_role_menu` VALUES ('47946', '45', '11');
INSERT INTO `sys_role_menu` VALUES ('47953', '45', '12');
INSERT INTO `sys_role_menu` VALUES ('47960', '45', '362');
INSERT INTO `sys_role_menu` VALUES ('47967', '45', '363');
INSERT INTO `sys_role_menu` VALUES ('47974', '45', '364');
INSERT INTO `sys_role_menu` VALUES ('47981', '45', '365');
INSERT INTO `sys_role_menu` VALUES ('47988', '45', '366');
INSERT INTO `sys_role_menu` VALUES ('47995', '45', '17');
INSERT INTO `sys_role_menu` VALUES ('48002', '45', '18');
INSERT INTO `sys_role_menu` VALUES ('48009', '45', '19');
INSERT INTO `sys_role_menu` VALUES ('48016', '45', '20');
INSERT INTO `sys_role_menu` VALUES ('48023', '45', '21');
INSERT INTO `sys_role_menu` VALUES ('48030', '45', '22');
INSERT INTO `sys_role_menu` VALUES ('48037', '45', '23');
INSERT INTO `sys_role_menu` VALUES ('48044', '45', '38');
INSERT INTO `sys_role_menu` VALUES ('48051', '45', '39');
INSERT INTO `sys_role_menu` VALUES ('48058', '45', '40');
INSERT INTO `sys_role_menu` VALUES ('48065', '45', '41');
INSERT INTO `sys_role_menu` VALUES ('48072', '45', '42');
INSERT INTO `sys_role_menu` VALUES ('48079', '45', '315');
INSERT INTO `sys_role_menu` VALUES ('48086', '45', '316');
INSERT INTO `sys_role_menu` VALUES ('48093', '45', '317');
INSERT INTO `sys_role_menu` VALUES ('48100', '45', '351');
INSERT INTO `sys_role_menu` VALUES ('48107', '45', '352');
INSERT INTO `sys_role_menu` VALUES ('48114', '45', '358');
INSERT INTO `sys_role_menu` VALUES ('48121', '45', '359');
INSERT INTO `sys_role_menu` VALUES ('48128', '45', '360');
INSERT INTO `sys_role_menu` VALUES ('48135', '45', '361');
INSERT INTO `sys_role_menu` VALUES ('48142', '45', '470');
INSERT INTO `sys_role_menu` VALUES ('48149', '45', '471');
INSERT INTO `sys_role_menu` VALUES ('48156', '45', '540');
INSERT INTO `sys_role_menu` VALUES ('48163', '45', '24');
INSERT INTO `sys_role_menu` VALUES ('48170', '45', '25');
INSERT INTO `sys_role_menu` VALUES ('48177', '45', '26');
INSERT INTO `sys_role_menu` VALUES ('48184', '45', '345');
INSERT INTO `sys_role_menu` VALUES ('48191', '45', '346');
INSERT INTO `sys_role_menu` VALUES ('48198', '45', '347');
INSERT INTO `sys_role_menu` VALUES ('48205', '45', '348');
INSERT INTO `sys_role_menu` VALUES ('48212', '45', '349');
INSERT INTO `sys_role_menu` VALUES ('48219', '45', '350');
INSERT INTO `sys_role_menu` VALUES ('48226', '45', '353');
INSERT INTO `sys_role_menu` VALUES ('48233', '45', '354');
INSERT INTO `sys_role_menu` VALUES ('48240', '45', '391');
INSERT INTO `sys_role_menu` VALUES ('48247', '45', '549');
INSERT INTO `sys_role_menu` VALUES ('48254', '45', '367');
INSERT INTO `sys_role_menu` VALUES ('48261', '45', '368');
INSERT INTO `sys_role_menu` VALUES ('48268', '45', '369');
INSERT INTO `sys_role_menu` VALUES ('48275', '45', '370');
INSERT INTO `sys_role_menu` VALUES ('48282', '45', '392');
INSERT INTO `sys_role_menu` VALUES ('48289', '45', '393');
INSERT INTO `sys_role_menu` VALUES ('48296', '45', '394');
INSERT INTO `sys_role_menu` VALUES ('48303', '45', '395');
INSERT INTO `sys_role_menu` VALUES ('48310', '45', '534');
INSERT INTO `sys_role_menu` VALUES ('48317', '45', '564');
INSERT INTO `sys_role_menu` VALUES ('48324', '45', '465');
INSERT INTO `sys_role_menu` VALUES ('48331', '45', '466');
INSERT INTO `sys_role_menu` VALUES ('48338', '45', '467');
INSERT INTO `sys_role_menu` VALUES ('48345', '45', '468');
INSERT INTO `sys_role_menu` VALUES ('48352', '45', '469');
INSERT INTO `sys_role_menu` VALUES ('48359', '45', '480');
INSERT INTO `sys_role_menu` VALUES ('48366', '45', '481');
INSERT INTO `sys_role_menu` VALUES ('48373', '45', '497');
INSERT INTO `sys_role_menu` VALUES ('48380', '45', '503');
INSERT INTO `sys_role_menu` VALUES ('48387', '45', '561');
INSERT INTO `sys_role_menu` VALUES ('48394', '45', '563');
INSERT INTO `sys_role_menu` VALUES ('48401', '45', '27');
INSERT INTO `sys_role_menu` VALUES ('48408', '45', '28');
INSERT INTO `sys_role_menu` VALUES ('48415', '45', '29');
INSERT INTO `sys_role_menu` VALUES ('48422', '45', '30');
INSERT INTO `sys_role_menu` VALUES ('48429', '45', '31');
INSERT INTO `sys_role_menu` VALUES ('48436', '45', '32');
INSERT INTO `sys_role_menu` VALUES ('48443', '45', '437');
INSERT INTO `sys_role_menu` VALUES ('48450', '45', '533');
INSERT INTO `sys_role_menu` VALUES ('48457', '45', '33');
INSERT INTO `sys_role_menu` VALUES ('48464', '45', '34');
INSERT INTO `sys_role_menu` VALUES ('48471', '45', '35');
INSERT INTO `sys_role_menu` VALUES ('48478', '45', '36');
INSERT INTO `sys_role_menu` VALUES ('48485', '45', '37');
INSERT INTO `sys_role_menu` VALUES ('48492', '45', '303');
INSERT INTO `sys_role_menu` VALUES ('48499', '45', '304');
INSERT INTO `sys_role_menu` VALUES ('48506', '45', '305');
INSERT INTO `sys_role_menu` VALUES ('48513', '45', '306');
INSERT INTO `sys_role_menu` VALUES ('48520', '45', '307');
INSERT INTO `sys_role_menu` VALUES ('48527', '45', '330');
INSERT INTO `sys_role_menu` VALUES ('48534', '45', '331');
INSERT INTO `sys_role_menu` VALUES ('48541', '45', '332');
INSERT INTO `sys_role_menu` VALUES ('48548', '45', '333');
INSERT INTO `sys_role_menu` VALUES ('48555', '45', '551');
INSERT INTO `sys_role_menu` VALUES ('48562', '45', '423');
INSERT INTO `sys_role_menu` VALUES ('48569', '45', '424');
INSERT INTO `sys_role_menu` VALUES ('48576', '45', '425');
INSERT INTO `sys_role_menu` VALUES ('48583', '45', '426');
INSERT INTO `sys_role_menu` VALUES ('48590', '45', '427');
INSERT INTO `sys_role_menu` VALUES ('48597', '45', '482');
INSERT INTO `sys_role_menu` VALUES ('48604', '45', '483');
INSERT INTO `sys_role_menu` VALUES ('48611', '45', '484');
INSERT INTO `sys_role_menu` VALUES ('48618', '45', '485');
INSERT INTO `sys_role_menu` VALUES ('48625', '45', '550');
INSERT INTO `sys_role_menu` VALUES ('48632', '45', '297');
INSERT INTO `sys_role_menu` VALUES ('48639', '45', '298');
INSERT INTO `sys_role_menu` VALUES ('48646', '45', '299');
INSERT INTO `sys_role_menu` VALUES ('48653', '45', '300');
INSERT INTO `sys_role_menu` VALUES ('48660', '45', '301');
INSERT INTO `sys_role_menu` VALUES ('48667', '45', '302');
INSERT INTO `sys_role_menu` VALUES ('48674', '45', '308');
INSERT INTO `sys_role_menu` VALUES ('48681', '45', '309');
INSERT INTO `sys_role_menu` VALUES ('48688', '45', '310');
INSERT INTO `sys_role_menu` VALUES ('48695', '45', '311');
INSERT INTO `sys_role_menu` VALUES ('48702', '45', '312');
INSERT INTO `sys_role_menu` VALUES ('48709', '45', '428');
INSERT INTO `sys_role_menu` VALUES ('48716', '45', '429');
INSERT INTO `sys_role_menu` VALUES ('48723', '45', '430');
INSERT INTO `sys_role_menu` VALUES ('48730', '45', '431');
INSERT INTO `sys_role_menu` VALUES ('48737', '45', '499');
INSERT INTO `sys_role_menu` VALUES ('48744', '45', '500');
INSERT INTO `sys_role_menu` VALUES ('48751', '45', '472');
INSERT INTO `sys_role_menu` VALUES ('48758', '45', '473');
INSERT INTO `sys_role_menu` VALUES ('48765', '45', '474');
INSERT INTO `sys_role_menu` VALUES ('48772', '45', '475');
INSERT INTO `sys_role_menu` VALUES ('48779', '45', '494');
INSERT INTO `sys_role_menu` VALUES ('48786', '45', '476');
INSERT INTO `sys_role_menu` VALUES ('48793', '45', '477');
INSERT INTO `sys_role_menu` VALUES ('48800', '45', '478');
INSERT INTO `sys_role_menu` VALUES ('48807', '45', '479');
INSERT INTO `sys_role_menu` VALUES ('48814', '45', '502');
INSERT INTO `sys_role_menu` VALUES ('48821', '45', '318');
INSERT INTO `sys_role_menu` VALUES ('48828', '45', '319');
INSERT INTO `sys_role_menu` VALUES ('48835', '45', '320');
INSERT INTO `sys_role_menu` VALUES ('48842', '45', '321');
INSERT INTO `sys_role_menu` VALUES ('48849', '45', '322');
INSERT INTO `sys_role_menu` VALUES ('48856', '45', '323');
INSERT INTO `sys_role_menu` VALUES ('48863', '45', '324');
INSERT INTO `sys_role_menu` VALUES ('48870', '45', '325');
INSERT INTO `sys_role_menu` VALUES ('48877', '45', '326');
INSERT INTO `sys_role_menu` VALUES ('48884', '45', '327');
INSERT INTO `sys_role_menu` VALUES ('48891', '45', '328');
INSERT INTO `sys_role_menu` VALUES ('48898', '45', '329');
INSERT INTO `sys_role_menu` VALUES ('48905', '45', '415');
INSERT INTO `sys_role_menu` VALUES ('48912', '45', '416');
INSERT INTO `sys_role_menu` VALUES ('48919', '45', '562');
INSERT INTO `sys_role_menu` VALUES ('48926', '45', '419');
INSERT INTO `sys_role_menu` VALUES ('48933', '45', '420');
INSERT INTO `sys_role_menu` VALUES ('48940', '45', '421');
INSERT INTO `sys_role_menu` VALUES ('48947', '45', '422');
INSERT INTO `sys_role_menu` VALUES ('48954', '45', '496');
INSERT INTO `sys_role_menu` VALUES ('48961', '45', '334');
INSERT INTO `sys_role_menu` VALUES ('48968', '45', '337');
INSERT INTO `sys_role_menu` VALUES ('48975', '45', '338');
INSERT INTO `sys_role_menu` VALUES ('48982', '45', '339');
INSERT INTO `sys_role_menu` VALUES ('48989', '45', '340');
INSERT INTO `sys_role_menu` VALUES ('48996', '45', '341');
INSERT INTO `sys_role_menu` VALUES ('49003', '45', '504');
INSERT INTO `sys_role_menu` VALUES ('49010', '45', '505');
INSERT INTO `sys_role_menu` VALUES ('49017', '45', '506');
INSERT INTO `sys_role_menu` VALUES ('49024', '45', '507');
INSERT INTO `sys_role_menu` VALUES ('49031', '45', '508');
INSERT INTO `sys_role_menu` VALUES ('49038', '45', '532');
INSERT INTO `sys_role_menu` VALUES ('49045', '45', '552');
INSERT INTO `sys_role_menu` VALUES ('49052', '45', '553');
INSERT INTO `sys_role_menu` VALUES ('49059', '45', '554');
INSERT INTO `sys_role_menu` VALUES ('49066', '45', '558');
INSERT INTO `sys_role_menu` VALUES ('49073', '45', '555');
INSERT INTO `sys_role_menu` VALUES ('49080', '45', '556');
INSERT INTO `sys_role_menu` VALUES ('49087', '45', '557');
INSERT INTO `sys_role_menu` VALUES ('49094', '45', '612');
INSERT INTO `sys_role_menu` VALUES ('49101', '45', '613');
INSERT INTO `sys_role_menu` VALUES ('49108', '45', '614');
INSERT INTO `sys_role_menu` VALUES ('49115', '45', '615');
INSERT INTO `sys_role_menu` VALUES ('49122', '45', '375');
INSERT INTO `sys_role_menu` VALUES ('49129', '45', '630');
INSERT INTO `sys_role_menu` VALUES ('49136', '45', '620');
INSERT INTO `sys_role_menu` VALUES ('49143', '45', '621');
INSERT INTO `sys_role_menu` VALUES ('49150', '45', '622');
INSERT INTO `sys_role_menu` VALUES ('49157', '45', '623');
INSERT INTO `sys_role_menu` VALUES ('49164', '45', '624');
INSERT INTO `sys_role_menu` VALUES ('49171', '45', '625');
INSERT INTO `sys_role_menu` VALUES ('49178', '45', '626');
INSERT INTO `sys_role_menu` VALUES ('49185', '45', '627');
INSERT INTO `sys_role_menu` VALUES ('49192', '45', '631');
INSERT INTO `sys_role_menu` VALUES ('49199', '45', '632');
INSERT INTO `sys_role_menu` VALUES ('49206', '45', '633');
INSERT INTO `sys_role_menu` VALUES ('49213', '45', '526');
INSERT INTO `sys_role_menu` VALUES ('49220', '45', '527');
INSERT INTO `sys_role_menu` VALUES ('49227', '45', '535');
INSERT INTO `sys_role_menu` VALUES ('49234', '45', '536');
INSERT INTO `sys_role_menu` VALUES ('49241', '45', '568');
INSERT INTO `sys_role_menu` VALUES ('49248', '45', '569');
INSERT INTO `sys_role_menu` VALUES ('49255', '45', '578');
INSERT INTO `sys_role_menu` VALUES ('49262', '45', '579');
INSERT INTO `sys_role_menu` VALUES ('49269', '45', '583');
INSERT INTO `sys_role_menu` VALUES ('49276', '45', '584');
INSERT INTO `sys_role_menu` VALUES ('49283', '45', '588');
INSERT INTO `sys_role_menu` VALUES ('49290', '45', '589');
INSERT INTO `sys_role_menu` VALUES ('49297', '45', '634');
INSERT INTO `sys_role_menu` VALUES ('49304', '45', '573');
INSERT INTO `sys_role_menu` VALUES ('49311', '45', '574');
INSERT INTO `sys_role_menu` VALUES ('49318', '45', '593');
INSERT INTO `sys_role_menu` VALUES ('49325', '45', '594');
INSERT INTO `sys_role_menu` VALUES ('49332', '45', '635');
INSERT INTO `sys_role_menu` VALUES ('49339', '45', '618');
INSERT INTO `sys_role_menu` VALUES ('49346', '45', '619');
INSERT INTO `sys_role_menu` VALUES ('49353', '45', '396');
INSERT INTO `sys_role_menu` VALUES ('49360', '45', '397');
INSERT INTO `sys_role_menu` VALUES ('49367', '45', '398');
INSERT INTO `sys_role_menu` VALUES ('49374', '45', '399');
INSERT INTO `sys_role_menu` VALUES ('49381', '45', '400');
INSERT INTO `sys_role_menu` VALUES ('49388', '45', '401');
INSERT INTO `sys_role_menu` VALUES ('49395', '45', '402');
INSERT INTO `sys_role_menu` VALUES ('49402', '45', '403');
INSERT INTO `sys_role_menu` VALUES ('49409', '45', '404');
INSERT INTO `sys_role_menu` VALUES ('49416', '45', '405');
INSERT INTO `sys_role_menu` VALUES ('49423', '45', '406');
INSERT INTO `sys_role_menu` VALUES ('49430', '45', '440');
INSERT INTO `sys_role_menu` VALUES ('49437', '45', '441');
INSERT INTO `sys_role_menu` VALUES ('49444', '45', '442');
INSERT INTO `sys_role_menu` VALUES ('49451', '45', '443');
INSERT INTO `sys_role_menu` VALUES ('49458', '45', '444');
INSERT INTO `sys_role_menu` VALUES ('49465', '45', '445');
INSERT INTO `sys_role_menu` VALUES ('49472', '45', '446');
INSERT INTO `sys_role_menu` VALUES ('49479', '45', '447');
INSERT INTO `sys_role_menu` VALUES ('49486', '45', '448');
INSERT INTO `sys_role_menu` VALUES ('49493', '45', '449');
INSERT INTO `sys_role_menu` VALUES ('49500', '45', '450');
INSERT INTO `sys_role_menu` VALUES ('49507', '45', '451');
INSERT INTO `sys_role_menu` VALUES ('49514', '45', '452');
INSERT INTO `sys_role_menu` VALUES ('49521', '45', '453');
INSERT INTO `sys_role_menu` VALUES ('49528', '45', '454');
INSERT INTO `sys_role_menu` VALUES ('49535', '45', '455');
INSERT INTO `sys_role_menu` VALUES ('49542', '45', '456');
INSERT INTO `sys_role_menu` VALUES ('49549', '45', '457');
INSERT INTO `sys_role_menu` VALUES ('49556', '45', '458');
INSERT INTO `sys_role_menu` VALUES ('49563', '45', '459');
INSERT INTO `sys_role_menu` VALUES ('49570', '45', '460');
INSERT INTO `sys_role_menu` VALUES ('49577', '45', '461');
INSERT INTO `sys_role_menu` VALUES ('49584', '45', '462');
INSERT INTO `sys_role_menu` VALUES ('49591', '45', '463');
INSERT INTO `sys_role_menu` VALUES ('49598', '45', '464');
INSERT INTO `sys_role_menu` VALUES ('49605', '45', '408');
INSERT INTO `sys_role_menu` VALUES ('49612', '45', '489');
INSERT INTO `sys_role_menu` VALUES ('49619', '45', '490');
INSERT INTO `sys_role_menu` VALUES ('49626', '45', '559');
INSERT INTO `sys_role_menu` VALUES ('49633', '45', '491');
INSERT INTO `sys_role_menu` VALUES ('49640', '45', '565');
INSERT INTO `sys_role_menu` VALUES ('49647', '45', '566');
INSERT INTO `sys_role_menu` VALUES ('49654', '45', '492');
INSERT INTO `sys_role_menu` VALUES ('49661', '45', '560');
INSERT INTO `sys_role_menu` VALUES ('49668', '45', '493');
INSERT INTO `sys_role_menu` VALUES ('49675', '45', '567');
INSERT INTO `sys_role_menu` VALUES ('49682', '45', '495');
INSERT INTO `sys_role_menu` VALUES ('49689', '45', '498');
INSERT INTO `sys_role_menu` VALUES ('49696', '45', '501');
INSERT INTO `sys_role_menu` VALUES ('49703', '45', '541');
INSERT INTO `sys_role_menu` VALUES ('49710', '45', '542');
INSERT INTO `sys_role_menu` VALUES ('49717', '45', '543');
INSERT INTO `sys_role_menu` VALUES ('49724', '45', '548');
INSERT INTO `sys_role_menu` VALUES ('49731', '45', '544');
INSERT INTO `sys_role_menu` VALUES ('49738', '45', '547');
INSERT INTO `sys_role_menu` VALUES ('49745', '45', '603');
INSERT INTO `sys_role_menu` VALUES ('49752', '45', '604');
INSERT INTO `sys_role_menu` VALUES ('49759', '45', '605');
INSERT INTO `sys_role_menu` VALUES ('49766', '45', '606');
INSERT INTO `sys_role_menu` VALUES ('49773', '45', '611');
INSERT INTO `sys_role_menu` VALUES ('49780', '45', '607');
INSERT INTO `sys_role_menu` VALUES ('49787', '45', '608');
INSERT INTO `sys_role_menu` VALUES ('49794', '45', '609');
INSERT INTO `sys_role_menu` VALUES ('49801', '45', '610');
INSERT INTO `sys_role_menu` VALUES ('49808', '45', '616');
INSERT INTO `sys_role_menu` VALUES ('49815', '45', '598');
INSERT INTO `sys_role_menu` VALUES ('49822', '45', '599');
INSERT INTO `sys_role_menu` VALUES ('49829', '45', '-666666');
INSERT INTO `sys_role_menu` VALUES ('49836', '52', '1');
INSERT INTO `sys_role_menu` VALUES ('49843', '52', '2');
INSERT INTO `sys_role_menu` VALUES ('49850', '52', '5');
INSERT INTO `sys_role_menu` VALUES ('49857', '52', '6');
INSERT INTO `sys_role_menu` VALUES ('49864', '52', '7');
INSERT INTO `sys_role_menu` VALUES ('49871', '52', '8');
INSERT INTO `sys_role_menu` VALUES ('49878', '52', '3');
INSERT INTO `sys_role_menu` VALUES ('49885', '52', '9');
INSERT INTO `sys_role_menu` VALUES ('49892', '52', '10');
INSERT INTO `sys_role_menu` VALUES ('49899', '52', '11');
INSERT INTO `sys_role_menu` VALUES ('49906', '52', '12');
INSERT INTO `sys_role_menu` VALUES ('49913', '52', '362');
INSERT INTO `sys_role_menu` VALUES ('49920', '52', '363');
INSERT INTO `sys_role_menu` VALUES ('49927', '52', '364');
INSERT INTO `sys_role_menu` VALUES ('49934', '52', '365');
INSERT INTO `sys_role_menu` VALUES ('49941', '52', '366');
INSERT INTO `sys_role_menu` VALUES ('49948', '52', '17');
INSERT INTO `sys_role_menu` VALUES ('49955', '52', '18');
INSERT INTO `sys_role_menu` VALUES ('49962', '52', '19');
INSERT INTO `sys_role_menu` VALUES ('49969', '52', '20');
INSERT INTO `sys_role_menu` VALUES ('49976', '52', '21');
INSERT INTO `sys_role_menu` VALUES ('49983', '52', '22');
INSERT INTO `sys_role_menu` VALUES ('49990', '52', '23');
INSERT INTO `sys_role_menu` VALUES ('49997', '52', '38');
INSERT INTO `sys_role_menu` VALUES ('50004', '52', '39');
INSERT INTO `sys_role_menu` VALUES ('50011', '52', '40');
INSERT INTO `sys_role_menu` VALUES ('50018', '52', '41');
INSERT INTO `sys_role_menu` VALUES ('50025', '52', '42');
INSERT INTO `sys_role_menu` VALUES ('50032', '52', '315');
INSERT INTO `sys_role_menu` VALUES ('50039', '52', '316');
INSERT INTO `sys_role_menu` VALUES ('50046', '52', '317');
INSERT INTO `sys_role_menu` VALUES ('50053', '52', '351');
INSERT INTO `sys_role_menu` VALUES ('50060', '52', '352');
INSERT INTO `sys_role_menu` VALUES ('50067', '52', '358');
INSERT INTO `sys_role_menu` VALUES ('50074', '52', '359');
INSERT INTO `sys_role_menu` VALUES ('50081', '52', '360');
INSERT INTO `sys_role_menu` VALUES ('50088', '52', '361');
INSERT INTO `sys_role_menu` VALUES ('50095', '52', '470');
INSERT INTO `sys_role_menu` VALUES ('50102', '52', '471');
INSERT INTO `sys_role_menu` VALUES ('50109', '52', '540');
INSERT INTO `sys_role_menu` VALUES ('50116', '52', '24');
INSERT INTO `sys_role_menu` VALUES ('50123', '52', '25');
INSERT INTO `sys_role_menu` VALUES ('50130', '52', '26');
INSERT INTO `sys_role_menu` VALUES ('50137', '52', '345');
INSERT INTO `sys_role_menu` VALUES ('50144', '52', '346');
INSERT INTO `sys_role_menu` VALUES ('50151', '52', '347');
INSERT INTO `sys_role_menu` VALUES ('50158', '52', '348');
INSERT INTO `sys_role_menu` VALUES ('50165', '52', '349');
INSERT INTO `sys_role_menu` VALUES ('50172', '52', '350');
INSERT INTO `sys_role_menu` VALUES ('50179', '52', '353');
INSERT INTO `sys_role_menu` VALUES ('50186', '52', '354');
INSERT INTO `sys_role_menu` VALUES ('50193', '52', '391');
INSERT INTO `sys_role_menu` VALUES ('50200', '52', '549');
INSERT INTO `sys_role_menu` VALUES ('50207', '52', '367');
INSERT INTO `sys_role_menu` VALUES ('50214', '52', '368');
INSERT INTO `sys_role_menu` VALUES ('50221', '52', '369');
INSERT INTO `sys_role_menu` VALUES ('50228', '52', '370');
INSERT INTO `sys_role_menu` VALUES ('50235', '52', '392');
INSERT INTO `sys_role_menu` VALUES ('50242', '52', '393');
INSERT INTO `sys_role_menu` VALUES ('50249', '52', '394');
INSERT INTO `sys_role_menu` VALUES ('50256', '52', '395');
INSERT INTO `sys_role_menu` VALUES ('50263', '52', '534');
INSERT INTO `sys_role_menu` VALUES ('50270', '52', '564');
INSERT INTO `sys_role_menu` VALUES ('50277', '52', '465');
INSERT INTO `sys_role_menu` VALUES ('50284', '52', '466');
INSERT INTO `sys_role_menu` VALUES ('50291', '52', '467');
INSERT INTO `sys_role_menu` VALUES ('50298', '52', '468');
INSERT INTO `sys_role_menu` VALUES ('50305', '52', '469');
INSERT INTO `sys_role_menu` VALUES ('50312', '52', '480');
INSERT INTO `sys_role_menu` VALUES ('50319', '52', '481');
INSERT INTO `sys_role_menu` VALUES ('50326', '52', '497');
INSERT INTO `sys_role_menu` VALUES ('50333', '52', '503');
INSERT INTO `sys_role_menu` VALUES ('50340', '52', '561');
INSERT INTO `sys_role_menu` VALUES ('50347', '52', '563');
INSERT INTO `sys_role_menu` VALUES ('50354', '52', '27');
INSERT INTO `sys_role_menu` VALUES ('50361', '52', '28');
INSERT INTO `sys_role_menu` VALUES ('50368', '52', '29');
INSERT INTO `sys_role_menu` VALUES ('50375', '52', '30');
INSERT INTO `sys_role_menu` VALUES ('50382', '52', '31');
INSERT INTO `sys_role_menu` VALUES ('50389', '52', '32');
INSERT INTO `sys_role_menu` VALUES ('50396', '52', '437');
INSERT INTO `sys_role_menu` VALUES ('50403', '52', '533');
INSERT INTO `sys_role_menu` VALUES ('50410', '52', '33');
INSERT INTO `sys_role_menu` VALUES ('50417', '52', '34');
INSERT INTO `sys_role_menu` VALUES ('50424', '52', '35');
INSERT INTO `sys_role_menu` VALUES ('50431', '52', '36');
INSERT INTO `sys_role_menu` VALUES ('50438', '52', '37');
INSERT INTO `sys_role_menu` VALUES ('50445', '52', '303');
INSERT INTO `sys_role_menu` VALUES ('50452', '52', '304');
INSERT INTO `sys_role_menu` VALUES ('50459', '52', '305');
INSERT INTO `sys_role_menu` VALUES ('50466', '52', '306');
INSERT INTO `sys_role_menu` VALUES ('50473', '52', '307');
INSERT INTO `sys_role_menu` VALUES ('50480', '52', '330');
INSERT INTO `sys_role_menu` VALUES ('50487', '52', '331');
INSERT INTO `sys_role_menu` VALUES ('50494', '52', '332');
INSERT INTO `sys_role_menu` VALUES ('50501', '52', '333');
INSERT INTO `sys_role_menu` VALUES ('50508', '52', '551');
INSERT INTO `sys_role_menu` VALUES ('50515', '52', '423');
INSERT INTO `sys_role_menu` VALUES ('50522', '52', '424');
INSERT INTO `sys_role_menu` VALUES ('50529', '52', '425');
INSERT INTO `sys_role_menu` VALUES ('50536', '52', '426');
INSERT INTO `sys_role_menu` VALUES ('50543', '52', '427');
INSERT INTO `sys_role_menu` VALUES ('50550', '52', '482');
INSERT INTO `sys_role_menu` VALUES ('50557', '52', '483');
INSERT INTO `sys_role_menu` VALUES ('50564', '52', '484');
INSERT INTO `sys_role_menu` VALUES ('50571', '52', '485');
INSERT INTO `sys_role_menu` VALUES ('50578', '52', '550');
INSERT INTO `sys_role_menu` VALUES ('50585', '52', '297');
INSERT INTO `sys_role_menu` VALUES ('50592', '52', '298');
INSERT INTO `sys_role_menu` VALUES ('50599', '52', '299');
INSERT INTO `sys_role_menu` VALUES ('50606', '52', '300');
INSERT INTO `sys_role_menu` VALUES ('50613', '52', '301');
INSERT INTO `sys_role_menu` VALUES ('50620', '52', '302');
INSERT INTO `sys_role_menu` VALUES ('50627', '52', '308');
INSERT INTO `sys_role_menu` VALUES ('50634', '52', '309');
INSERT INTO `sys_role_menu` VALUES ('50641', '52', '310');
INSERT INTO `sys_role_menu` VALUES ('50648', '52', '311');
INSERT INTO `sys_role_menu` VALUES ('50655', '52', '312');
INSERT INTO `sys_role_menu` VALUES ('50662', '52', '428');
INSERT INTO `sys_role_menu` VALUES ('50669', '52', '429');
INSERT INTO `sys_role_menu` VALUES ('50676', '52', '430');
INSERT INTO `sys_role_menu` VALUES ('50683', '52', '431');
INSERT INTO `sys_role_menu` VALUES ('50690', '52', '499');
INSERT INTO `sys_role_menu` VALUES ('50697', '52', '500');
INSERT INTO `sys_role_menu` VALUES ('50704', '52', '472');
INSERT INTO `sys_role_menu` VALUES ('50711', '52', '473');
INSERT INTO `sys_role_menu` VALUES ('50718', '52', '474');
INSERT INTO `sys_role_menu` VALUES ('50725', '52', '475');
INSERT INTO `sys_role_menu` VALUES ('50732', '52', '494');
INSERT INTO `sys_role_menu` VALUES ('50739', '52', '476');
INSERT INTO `sys_role_menu` VALUES ('50746', '52', '477');
INSERT INTO `sys_role_menu` VALUES ('50753', '52', '478');
INSERT INTO `sys_role_menu` VALUES ('50760', '52', '479');
INSERT INTO `sys_role_menu` VALUES ('50767', '52', '502');
INSERT INTO `sys_role_menu` VALUES ('50774', '52', '318');
INSERT INTO `sys_role_menu` VALUES ('50781', '52', '319');
INSERT INTO `sys_role_menu` VALUES ('50788', '52', '320');
INSERT INTO `sys_role_menu` VALUES ('50795', '52', '321');
INSERT INTO `sys_role_menu` VALUES ('50802', '52', '322');
INSERT INTO `sys_role_menu` VALUES ('50809', '52', '323');
INSERT INTO `sys_role_menu` VALUES ('50816', '52', '324');
INSERT INTO `sys_role_menu` VALUES ('50823', '52', '325');
INSERT INTO `sys_role_menu` VALUES ('50830', '52', '326');
INSERT INTO `sys_role_menu` VALUES ('50837', '52', '327');
INSERT INTO `sys_role_menu` VALUES ('50844', '52', '328');
INSERT INTO `sys_role_menu` VALUES ('50851', '52', '329');
INSERT INTO `sys_role_menu` VALUES ('50858', '52', '415');
INSERT INTO `sys_role_menu` VALUES ('50865', '52', '416');
INSERT INTO `sys_role_menu` VALUES ('50872', '52', '562');
INSERT INTO `sys_role_menu` VALUES ('50879', '52', '419');
INSERT INTO `sys_role_menu` VALUES ('50886', '52', '420');
INSERT INTO `sys_role_menu` VALUES ('50893', '52', '421');
INSERT INTO `sys_role_menu` VALUES ('50900', '52', '422');
INSERT INTO `sys_role_menu` VALUES ('50907', '52', '496');
INSERT INTO `sys_role_menu` VALUES ('50914', '52', '334');
INSERT INTO `sys_role_menu` VALUES ('50921', '52', '337');
INSERT INTO `sys_role_menu` VALUES ('50928', '52', '338');
INSERT INTO `sys_role_menu` VALUES ('50935', '52', '339');
INSERT INTO `sys_role_menu` VALUES ('50942', '52', '340');
INSERT INTO `sys_role_menu` VALUES ('50949', '52', '341');
INSERT INTO `sys_role_menu` VALUES ('50956', '52', '504');
INSERT INTO `sys_role_menu` VALUES ('50963', '52', '505');
INSERT INTO `sys_role_menu` VALUES ('50970', '52', '506');
INSERT INTO `sys_role_menu` VALUES ('50977', '52', '507');
INSERT INTO `sys_role_menu` VALUES ('50984', '52', '508');
INSERT INTO `sys_role_menu` VALUES ('50991', '52', '532');
INSERT INTO `sys_role_menu` VALUES ('50998', '52', '552');
INSERT INTO `sys_role_menu` VALUES ('51005', '52', '553');
INSERT INTO `sys_role_menu` VALUES ('51012', '52', '554');
INSERT INTO `sys_role_menu` VALUES ('51019', '52', '558');
INSERT INTO `sys_role_menu` VALUES ('51026', '52', '555');
INSERT INTO `sys_role_menu` VALUES ('51033', '52', '556');
INSERT INTO `sys_role_menu` VALUES ('51040', '52', '557');
INSERT INTO `sys_role_menu` VALUES ('51047', '52', '612');
INSERT INTO `sys_role_menu` VALUES ('51054', '52', '613');
INSERT INTO `sys_role_menu` VALUES ('51061', '52', '614');
INSERT INTO `sys_role_menu` VALUES ('51068', '52', '615');
INSERT INTO `sys_role_menu` VALUES ('51075', '52', '375');
INSERT INTO `sys_role_menu` VALUES ('51082', '52', '630');
INSERT INTO `sys_role_menu` VALUES ('51089', '52', '620');
INSERT INTO `sys_role_menu` VALUES ('51096', '52', '621');
INSERT INTO `sys_role_menu` VALUES ('51103', '52', '622');
INSERT INTO `sys_role_menu` VALUES ('51110', '52', '623');
INSERT INTO `sys_role_menu` VALUES ('51117', '52', '624');
INSERT INTO `sys_role_menu` VALUES ('51124', '52', '625');
INSERT INTO `sys_role_menu` VALUES ('51131', '52', '626');
INSERT INTO `sys_role_menu` VALUES ('51138', '52', '627');
INSERT INTO `sys_role_menu` VALUES ('51145', '52', '631');
INSERT INTO `sys_role_menu` VALUES ('51152', '52', '632');
INSERT INTO `sys_role_menu` VALUES ('51159', '52', '633');
INSERT INTO `sys_role_menu` VALUES ('51166', '52', '526');
INSERT INTO `sys_role_menu` VALUES ('51173', '52', '527');
INSERT INTO `sys_role_menu` VALUES ('51180', '52', '535');
INSERT INTO `sys_role_menu` VALUES ('51187', '52', '536');
INSERT INTO `sys_role_menu` VALUES ('51194', '52', '568');
INSERT INTO `sys_role_menu` VALUES ('51201', '52', '569');
INSERT INTO `sys_role_menu` VALUES ('51208', '52', '578');
INSERT INTO `sys_role_menu` VALUES ('51215', '52', '579');
INSERT INTO `sys_role_menu` VALUES ('51222', '52', '583');
INSERT INTO `sys_role_menu` VALUES ('51229', '52', '584');
INSERT INTO `sys_role_menu` VALUES ('51236', '52', '588');
INSERT INTO `sys_role_menu` VALUES ('51243', '52', '589');
INSERT INTO `sys_role_menu` VALUES ('51250', '52', '634');
INSERT INTO `sys_role_menu` VALUES ('51257', '52', '573');
INSERT INTO `sys_role_menu` VALUES ('51264', '52', '574');
INSERT INTO `sys_role_menu` VALUES ('51271', '52', '593');
INSERT INTO `sys_role_menu` VALUES ('51278', '52', '594');
INSERT INTO `sys_role_menu` VALUES ('51285', '52', '635');
INSERT INTO `sys_role_menu` VALUES ('51292', '52', '618');
INSERT INTO `sys_role_menu` VALUES ('51299', '52', '619');
INSERT INTO `sys_role_menu` VALUES ('51306', '52', '396');
INSERT INTO `sys_role_menu` VALUES ('51313', '52', '397');
INSERT INTO `sys_role_menu` VALUES ('51320', '52', '398');
INSERT INTO `sys_role_menu` VALUES ('51327', '52', '399');
INSERT INTO `sys_role_menu` VALUES ('51334', '52', '400');
INSERT INTO `sys_role_menu` VALUES ('51341', '52', '401');
INSERT INTO `sys_role_menu` VALUES ('51348', '52', '402');
INSERT INTO `sys_role_menu` VALUES ('51355', '52', '403');
INSERT INTO `sys_role_menu` VALUES ('51362', '52', '404');
INSERT INTO `sys_role_menu` VALUES ('51369', '52', '405');
INSERT INTO `sys_role_menu` VALUES ('51376', '52', '406');
INSERT INTO `sys_role_menu` VALUES ('51383', '52', '440');
INSERT INTO `sys_role_menu` VALUES ('51390', '52', '441');
INSERT INTO `sys_role_menu` VALUES ('51397', '52', '442');
INSERT INTO `sys_role_menu` VALUES ('51404', '52', '443');
INSERT INTO `sys_role_menu` VALUES ('51411', '52', '444');
INSERT INTO `sys_role_menu` VALUES ('51418', '52', '445');
INSERT INTO `sys_role_menu` VALUES ('51425', '52', '446');
INSERT INTO `sys_role_menu` VALUES ('51432', '52', '447');
INSERT INTO `sys_role_menu` VALUES ('51439', '52', '448');
INSERT INTO `sys_role_menu` VALUES ('51446', '52', '449');
INSERT INTO `sys_role_menu` VALUES ('51453', '52', '450');
INSERT INTO `sys_role_menu` VALUES ('51460', '52', '451');
INSERT INTO `sys_role_menu` VALUES ('51467', '52', '452');
INSERT INTO `sys_role_menu` VALUES ('51474', '52', '453');
INSERT INTO `sys_role_menu` VALUES ('51481', '52', '454');
INSERT INTO `sys_role_menu` VALUES ('51488', '52', '455');
INSERT INTO `sys_role_menu` VALUES ('51495', '52', '456');
INSERT INTO `sys_role_menu` VALUES ('51502', '52', '457');
INSERT INTO `sys_role_menu` VALUES ('51509', '52', '458');
INSERT INTO `sys_role_menu` VALUES ('51516', '52', '459');
INSERT INTO `sys_role_menu` VALUES ('51523', '52', '460');
INSERT INTO `sys_role_menu` VALUES ('51530', '52', '461');
INSERT INTO `sys_role_menu` VALUES ('51537', '52', '462');
INSERT INTO `sys_role_menu` VALUES ('51544', '52', '463');
INSERT INTO `sys_role_menu` VALUES ('51551', '52', '464');
INSERT INTO `sys_role_menu` VALUES ('51558', '52', '408');
INSERT INTO `sys_role_menu` VALUES ('51565', '52', '489');
INSERT INTO `sys_role_menu` VALUES ('51572', '52', '490');
INSERT INTO `sys_role_menu` VALUES ('51579', '52', '559');
INSERT INTO `sys_role_menu` VALUES ('51586', '52', '491');
INSERT INTO `sys_role_menu` VALUES ('51593', '52', '565');
INSERT INTO `sys_role_menu` VALUES ('51600', '52', '566');
INSERT INTO `sys_role_menu` VALUES ('51607', '52', '492');
INSERT INTO `sys_role_menu` VALUES ('51614', '52', '560');
INSERT INTO `sys_role_menu` VALUES ('51621', '52', '493');
INSERT INTO `sys_role_menu` VALUES ('51628', '52', '567');
INSERT INTO `sys_role_menu` VALUES ('51635', '52', '495');
INSERT INTO `sys_role_menu` VALUES ('51642', '52', '498');
INSERT INTO `sys_role_menu` VALUES ('51649', '52', '501');
INSERT INTO `sys_role_menu` VALUES ('51656', '52', '541');
INSERT INTO `sys_role_menu` VALUES ('51663', '52', '542');
INSERT INTO `sys_role_menu` VALUES ('51670', '52', '543');
INSERT INTO `sys_role_menu` VALUES ('51677', '52', '548');
INSERT INTO `sys_role_menu` VALUES ('51684', '52', '544');
INSERT INTO `sys_role_menu` VALUES ('51691', '52', '547');
INSERT INTO `sys_role_menu` VALUES ('51698', '52', '603');
INSERT INTO `sys_role_menu` VALUES ('51705', '52', '604');
INSERT INTO `sys_role_menu` VALUES ('51712', '52', '605');
INSERT INTO `sys_role_menu` VALUES ('51719', '52', '606');
INSERT INTO `sys_role_menu` VALUES ('51726', '52', '611');
INSERT INTO `sys_role_menu` VALUES ('51733', '52', '607');
INSERT INTO `sys_role_menu` VALUES ('51740', '52', '608');
INSERT INTO `sys_role_menu` VALUES ('51747', '52', '609');
INSERT INTO `sys_role_menu` VALUES ('51754', '52', '610');
INSERT INTO `sys_role_menu` VALUES ('51761', '52', '616');
INSERT INTO `sys_role_menu` VALUES ('51768', '52', '598');
INSERT INTO `sys_role_menu` VALUES ('51775', '52', '599');
INSERT INTO `sys_role_menu` VALUES ('51782', '52', '-666666');
INSERT INTO `sys_role_menu` VALUES ('51789', '59', '19');
INSERT INTO `sys_role_menu` VALUES ('51796', '59', '38');
INSERT INTO `sys_role_menu` VALUES ('51803', '59', '41');
INSERT INTO `sys_role_menu` VALUES ('51810', '59', '42');
INSERT INTO `sys_role_menu` VALUES ('51817', '59', '315');
INSERT INTO `sys_role_menu` VALUES ('51824', '59', '316');
INSERT INTO `sys_role_menu` VALUES ('51831', '59', '317');
INSERT INTO `sys_role_menu` VALUES ('51838', '59', '351');
INSERT INTO `sys_role_menu` VALUES ('51845', '59', '352');
INSERT INTO `sys_role_menu` VALUES ('51852', '59', '361');
INSERT INTO `sys_role_menu` VALUES ('51859', '59', '471');
INSERT INTO `sys_role_menu` VALUES ('51866', '59', '346');
INSERT INTO `sys_role_menu` VALUES ('51873', '59', '348');
INSERT INTO `sys_role_menu` VALUES ('51880', '59', '367');
INSERT INTO `sys_role_menu` VALUES ('51887', '59', '368');
INSERT INTO `sys_role_menu` VALUES ('51894', '59', '369');
INSERT INTO `sys_role_menu` VALUES ('51901', '59', '370');
INSERT INTO `sys_role_menu` VALUES ('51908', '59', '392');
INSERT INTO `sys_role_menu` VALUES ('51915', '59', '393');
INSERT INTO `sys_role_menu` VALUES ('51922', '59', '394');
INSERT INTO `sys_role_menu` VALUES ('51929', '59', '395');
INSERT INTO `sys_role_menu` VALUES ('51936', '59', '534');
INSERT INTO `sys_role_menu` VALUES ('51943', '59', '564');
INSERT INTO `sys_role_menu` VALUES ('51950', '59', '465');
INSERT INTO `sys_role_menu` VALUES ('51957', '59', '466');
INSERT INTO `sys_role_menu` VALUES ('51964', '59', '467');
INSERT INTO `sys_role_menu` VALUES ('51971', '59', '468');
INSERT INTO `sys_role_menu` VALUES ('51978', '59', '469');
INSERT INTO `sys_role_menu` VALUES ('51985', '59', '480');
INSERT INTO `sys_role_menu` VALUES ('51992', '59', '481');
INSERT INTO `sys_role_menu` VALUES ('51999', '59', '497');
INSERT INTO `sys_role_menu` VALUES ('52006', '59', '503');
INSERT INTO `sys_role_menu` VALUES ('52013', '59', '561');
INSERT INTO `sys_role_menu` VALUES ('52020', '59', '563');
INSERT INTO `sys_role_menu` VALUES ('52027', '59', '308');
INSERT INTO `sys_role_menu` VALUES ('52034', '59', '309');
INSERT INTO `sys_role_menu` VALUES ('52041', '59', '310');
INSERT INTO `sys_role_menu` VALUES ('52048', '59', '311');
INSERT INTO `sys_role_menu` VALUES ('52055', '59', '312');
INSERT INTO `sys_role_menu` VALUES ('52062', '59', '338');
INSERT INTO `sys_role_menu` VALUES ('52069', '59', '375');
INSERT INTO `sys_role_menu` VALUES ('52076', '59', '630');
INSERT INTO `sys_role_menu` VALUES ('52083', '59', '620');
INSERT INTO `sys_role_menu` VALUES ('52090', '59', '621');
INSERT INTO `sys_role_menu` VALUES ('52097', '59', '622');
INSERT INTO `sys_role_menu` VALUES ('52104', '59', '623');
INSERT INTO `sys_role_menu` VALUES ('52111', '59', '624');
INSERT INTO `sys_role_menu` VALUES ('52118', '59', '625');
INSERT INTO `sys_role_menu` VALUES ('52125', '59', '626');
INSERT INTO `sys_role_menu` VALUES ('52132', '59', '627');
INSERT INTO `sys_role_menu` VALUES ('52139', '59', '631');
INSERT INTO `sys_role_menu` VALUES ('52146', '59', '632');
INSERT INTO `sys_role_menu` VALUES ('52153', '59', '633');
INSERT INTO `sys_role_menu` VALUES ('52160', '59', '526');
INSERT INTO `sys_role_menu` VALUES ('52167', '59', '527');
INSERT INTO `sys_role_menu` VALUES ('52174', '59', '535');
INSERT INTO `sys_role_menu` VALUES ('52181', '59', '536');
INSERT INTO `sys_role_menu` VALUES ('52188', '59', '568');
INSERT INTO `sys_role_menu` VALUES ('52195', '59', '569');
INSERT INTO `sys_role_menu` VALUES ('52202', '59', '578');
INSERT INTO `sys_role_menu` VALUES ('52209', '59', '579');
INSERT INTO `sys_role_menu` VALUES ('52216', '59', '583');
INSERT INTO `sys_role_menu` VALUES ('52223', '59', '584');
INSERT INTO `sys_role_menu` VALUES ('52230', '59', '588');
INSERT INTO `sys_role_menu` VALUES ('52237', '59', '589');
INSERT INTO `sys_role_menu` VALUES ('52244', '59', '634');
INSERT INTO `sys_role_menu` VALUES ('52251', '59', '573');
INSERT INTO `sys_role_menu` VALUES ('52258', '59', '574');
INSERT INTO `sys_role_menu` VALUES ('52265', '59', '593');
INSERT INTO `sys_role_menu` VALUES ('52272', '59', '594');
INSERT INTO `sys_role_menu` VALUES ('52279', '59', '635');
INSERT INTO `sys_role_menu` VALUES ('52286', '59', '618');
INSERT INTO `sys_role_menu` VALUES ('52293', '59', '619');
INSERT INTO `sys_role_menu` VALUES ('52300', '59', '401');
INSERT INTO `sys_role_menu` VALUES ('52307', '59', '406');
INSERT INTO `sys_role_menu` VALUES ('52314', '59', '441');
INSERT INTO `sys_role_menu` VALUES ('52321', '59', '446');
INSERT INTO `sys_role_menu` VALUES ('52328', '59', '451');
INSERT INTO `sys_role_menu` VALUES ('52335', '59', '456');
INSERT INTO `sys_role_menu` VALUES ('52342', '59', '461');
INSERT INTO `sys_role_menu` VALUES ('52349', '59', '408');
INSERT INTO `sys_role_menu` VALUES ('52356', '59', '490');
INSERT INTO `sys_role_menu` VALUES ('52363', '59', '559');
INSERT INTO `sys_role_menu` VALUES ('52370', '59', '491');
INSERT INTO `sys_role_menu` VALUES ('52377', '59', '565');
INSERT INTO `sys_role_menu` VALUES ('52384', '59', '566');
INSERT INTO `sys_role_menu` VALUES ('52391', '59', '492');
INSERT INTO `sys_role_menu` VALUES ('52398', '59', '560');
INSERT INTO `sys_role_menu` VALUES ('52405', '59', '616');
INSERT INTO `sys_role_menu` VALUES ('52412', '59', '598');
INSERT INTO `sys_role_menu` VALUES ('52419', '59', '599');
INSERT INTO `sys_role_menu` VALUES ('52426', '59', '-666666');
INSERT INTO `sys_role_menu` VALUES ('52433', '59', '17');
INSERT INTO `sys_role_menu` VALUES ('52440', '59', '18');
INSERT INTO `sys_role_menu` VALUES ('52447', '59', '345');
INSERT INTO `sys_role_menu` VALUES ('52454', '59', '297');
INSERT INTO `sys_role_menu` VALUES ('52461', '59', '334');
INSERT INTO `sys_role_menu` VALUES ('52468', '59', '337');
INSERT INTO `sys_role_menu` VALUES ('52475', '59', '396');
INSERT INTO `sys_role_menu` VALUES ('52482', '59', '397');
INSERT INTO `sys_role_menu` VALUES ('52489', '59', '402');
INSERT INTO `sys_role_menu` VALUES ('52496', '59', '440');
INSERT INTO `sys_role_menu` VALUES ('52503', '59', '445');
INSERT INTO `sys_role_menu` VALUES ('52510', '59', '450');
INSERT INTO `sys_role_menu` VALUES ('52517', '59', '455');
INSERT INTO `sys_role_menu` VALUES ('52524', '59', '460');
INSERT INTO `sys_role_menu` VALUES ('52531', '59', '489');
INSERT INTO `sys_role_menu` VALUES ('52538', '66', '19');
INSERT INTO `sys_role_menu` VALUES ('52545', '66', '38');
INSERT INTO `sys_role_menu` VALUES ('52552', '66', '351');
INSERT INTO `sys_role_menu` VALUES ('52559', '66', '361');
INSERT INTO `sys_role_menu` VALUES ('52566', '66', '471');
INSERT INTO `sys_role_menu` VALUES ('52573', '66', '369');
INSERT INTO `sys_role_menu` VALUES ('52580', '66', '480');
INSERT INTO `sys_role_menu` VALUES ('52587', '66', '481');
INSERT INTO `sys_role_menu` VALUES ('52594', '66', '497');
INSERT INTO `sys_role_menu` VALUES ('52601', '66', '503');
INSERT INTO `sys_role_menu` VALUES ('52608', '66', '561');
INSERT INTO `sys_role_menu` VALUES ('52615', '66', '563');
INSERT INTO `sys_role_menu` VALUES ('52622', '66', '375');
INSERT INTO `sys_role_menu` VALUES ('52629', '66', '630');
INSERT INTO `sys_role_menu` VALUES ('52636', '66', '620');
INSERT INTO `sys_role_menu` VALUES ('52643', '66', '621');
INSERT INTO `sys_role_menu` VALUES ('52650', '66', '622');
INSERT INTO `sys_role_menu` VALUES ('52657', '66', '623');
INSERT INTO `sys_role_menu` VALUES ('52664', '66', '624');
INSERT INTO `sys_role_menu` VALUES ('52671', '66', '625');
INSERT INTO `sys_role_menu` VALUES ('52678', '66', '626');
INSERT INTO `sys_role_menu` VALUES ('52685', '66', '627');
INSERT INTO `sys_role_menu` VALUES ('52692', '66', '631');
INSERT INTO `sys_role_menu` VALUES ('52699', '66', '632');
INSERT INTO `sys_role_menu` VALUES ('52706', '66', '633');
INSERT INTO `sys_role_menu` VALUES ('52713', '66', '526');
INSERT INTO `sys_role_menu` VALUES ('52720', '66', '527');
INSERT INTO `sys_role_menu` VALUES ('52727', '66', '535');
INSERT INTO `sys_role_menu` VALUES ('52734', '66', '536');
INSERT INTO `sys_role_menu` VALUES ('52741', '66', '568');
INSERT INTO `sys_role_menu` VALUES ('52748', '66', '569');
INSERT INTO `sys_role_menu` VALUES ('52755', '66', '578');
INSERT INTO `sys_role_menu` VALUES ('52762', '66', '579');
INSERT INTO `sys_role_menu` VALUES ('52769', '66', '583');
INSERT INTO `sys_role_menu` VALUES ('52776', '66', '584');
INSERT INTO `sys_role_menu` VALUES ('52783', '66', '588');
INSERT INTO `sys_role_menu` VALUES ('52790', '66', '589');
INSERT INTO `sys_role_menu` VALUES ('52797', '66', '634');
INSERT INTO `sys_role_menu` VALUES ('52804', '66', '573');
INSERT INTO `sys_role_menu` VALUES ('52811', '66', '574');
INSERT INTO `sys_role_menu` VALUES ('52818', '66', '593');
INSERT INTO `sys_role_menu` VALUES ('52825', '66', '594');
INSERT INTO `sys_role_menu` VALUES ('52832', '66', '635');
INSERT INTO `sys_role_menu` VALUES ('52839', '66', '618');
INSERT INTO `sys_role_menu` VALUES ('52846', '66', '619');
INSERT INTO `sys_role_menu` VALUES ('52853', '66', '401');
INSERT INTO `sys_role_menu` VALUES ('52860', '66', '406');
INSERT INTO `sys_role_menu` VALUES ('52867', '66', '441');
INSERT INTO `sys_role_menu` VALUES ('52874', '66', '446');
INSERT INTO `sys_role_menu` VALUES ('52881', '66', '451');
INSERT INTO `sys_role_menu` VALUES ('52888', '66', '456');
INSERT INTO `sys_role_menu` VALUES ('52895', '66', '461');
INSERT INTO `sys_role_menu` VALUES ('52902', '66', '408');
INSERT INTO `sys_role_menu` VALUES ('52909', '66', '490');
INSERT INTO `sys_role_menu` VALUES ('52916', '66', '559');
INSERT INTO `sys_role_menu` VALUES ('52923', '66', '491');
INSERT INTO `sys_role_menu` VALUES ('52930', '66', '565');
INSERT INTO `sys_role_menu` VALUES ('52937', '66', '566');
INSERT INTO `sys_role_menu` VALUES ('52944', '66', '492');
INSERT INTO `sys_role_menu` VALUES ('52951', '66', '560');
INSERT INTO `sys_role_menu` VALUES ('52958', '66', '616');
INSERT INTO `sys_role_menu` VALUES ('52965', '66', '598');
INSERT INTO `sys_role_menu` VALUES ('52972', '66', '599');
INSERT INTO `sys_role_menu` VALUES ('52979', '66', '-666666');
INSERT INTO `sys_role_menu` VALUES ('52986', '66', '17');
INSERT INTO `sys_role_menu` VALUES ('52993', '66', '18');
INSERT INTO `sys_role_menu` VALUES ('53000', '66', '367');
INSERT INTO `sys_role_menu` VALUES ('53007', '66', '396');
INSERT INTO `sys_role_menu` VALUES ('53014', '66', '397');
INSERT INTO `sys_role_menu` VALUES ('53021', '66', '402');
INSERT INTO `sys_role_menu` VALUES ('53028', '66', '440');
INSERT INTO `sys_role_menu` VALUES ('53035', '66', '445');
INSERT INTO `sys_role_menu` VALUES ('53042', '66', '450');
INSERT INTO `sys_role_menu` VALUES ('53049', '66', '455');
INSERT INTO `sys_role_menu` VALUES ('53056', '66', '460');
INSERT INTO `sys_role_menu` VALUES ('53063', '66', '489');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `role_ids` varchar(20) DEFAULT NULL COMMENT '角色id（逗号分隔）',
  `token` varchar(32) DEFAULT NULL COMMENT '代理商秘钥',
  `agency_rate` varchar(4) DEFAULT NULL,
  `delete_status` int(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  PRIMARY KEY (`user_id`,`delete_status`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=280 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'd759fc6985417b6bfc0dd2a29a362f02a314a1d0131b111a98b9c71c6ad4a594', 'YzcmCZNvbXocrsz9dm8e', 'root@test.com', '13612345678', '1', '1', '2016-11-11 11:11:11', '1', null, null, '0');
INSERT INTO `sys_user` VALUES ('2', 'manager', '4eb3cba50d76ddea47c4618d954c1d4b00d5055ab530e5cd0c46233d1714ed96', 'JC2qJupau5aDDTgJ8hhQ', 'manager@qq.com', '15232654587', '1', '1', '2019-03-01 14:16:08', '3', null, null, '0');
INSERT INTO `sys_user` VALUES ('3', 'laoluo', '85390e6106c94e16f973e46e445db94ecf85fc2ba19370f0c1c9fc5bbc622439', 'l3zwdkQ5u9eNLNvlXhul', '280495605@qq.com', '18659214675', '1', '1', '2019-04-07 21:16:41', '1', null, null, '0');
INSERT INTO `sys_user` VALUES ('10', 'yang', '1d73e25ad594daf6025c479a814d86537ed65f66b5395ffbd99844fc9b7d215b', '4GnqLqxNr6w2mokbrmOA', '563139548@qq.com', '18050901156', '1', '1', '2019-04-08 15:06:10', '1', null, null, '0');
INSERT INTO `sys_user` VALUES ('17', 'adu', '59a4eb76d3232970639e184a9092c07e89a6302a78bd559c1efee91992eb1280', 'NZii1JHvq2Hjd6d4IPwp', '351245145@qq.com', '13400345678', '1', '1', '2019-04-09 14:22:51', '1', null, null, '0');
INSERT INTO `sys_user` VALUES ('24', 'aleng', 'b12c497e5984225eccaa51c9b47fd452ea13b36c502206db07584cc01525e198', 'PXW7rCru7VAXuScLriFO', 'aleng@qq.com', '15852963254', '1', '1', '2019-04-09 18:41:43', '4', null, null, '0');
INSERT INTO `sys_user` VALUES ('73', 'amu000000', '7ea6884a9b8d9a92a6546add45fea2b0e35f29a74cfb5194138137eababdefcf', 'iDjQfHg2WFo4EIUC8zdu', 'amu000000@qq.com', '14747114251', '1', '1', '2019-04-11 16:09:08', '1', null, null, '0');
INSERT INTO `sys_user` VALUES ('115', 'lll', 'b816df23681bd77ce80dd812c1c5908c49915ea6a63e1a597542174296143494', 'g3yqJc6RcntiHF4peEfj', '12345678@qq.com', '12345678901', '1', '10', '2019-04-11 20:14:43', '24', null, null, '0');
INSERT INTO `sys_user` VALUES ('164', 'ok678678', '389efb502fe102b8123c3d6431db75b6e1ec6326715ca4acbbf1dd375722bd0a', 't5g6uEybMSAwLvi0XNLY', '504978978@qq.com', '18658616602', '1', '2', '2019-04-19 14:36:08', '31', null, null, '0');
INSERT INTO `sys_user` VALUES ('192', 'ok123789', '3def520c60c5533444bda54c5b11cc2527f4fb8cfe57cc888ad9e0ed9406a338', '5VYH6GehGTUuejpVVD6G', '490500111@qq.com', '18658616602', '1', '2', '2019-04-19 14:45:37', '38', null, null, '0');
INSERT INTO `sys_user` VALUES ('199', 'xiaocai', '4b3a736a3bd064a28a14a7a0ca3a78809e2a372af25790f5d390ec7087bbe94a', 'p3HSqBiXSzp0FYC9gdIw', '78654256@qq.com', '13956786871', '1', '2', '2019-04-19 15:12:08', '45', null, null, '0');
INSERT INTO `sys_user` VALUES ('206', 'abxamfb79', 'f26bab6291b71b9a4c34da568b4e2aa5dd333d90cda2886031c3958d50fd1fdf', '8MoHsojdSzgsfIcDsAmQ', '23456723@qq.com', '13856786872', '1', '2', '2019-04-19 15:16:13', '52', null, null, '0');
INSERT INTO `sys_user` VALUES ('213', 'xiaojiang', '09f7db51df1a40aeb776df93503f14f914e1d2e47c475d5f24467ec621fc6584', 'ekSzygc14haraQpkvbWL', '245354678@qq.com', '13865786782', '1', '2', '2019-04-19 16:23:52', '38', null, null, '0');
INSERT INTO `sys_user` VALUES ('220', 'zuzhang1', '3d0157e01b8625c27772b56f9429334da2f189dd05c9a1f57c07778501257b50', 'fsJMwKp75mPZFrwa63aC', '143256789@qq.com', '13945678756', '1', '2', '2019-04-19 23:21:03', '59', null, null, '0');
INSERT INTO `sys_user` VALUES ('227', 'chukuan1', '6b9f291f8d9229f5ecf9fee50f4160d7873811c0442e8f2aace3df666115353a', 'dMhLXqG0kg67u06xTXGA', '234642163@qq.com', '13456225452', '1', '2', '2019-04-20 00:16:10', '66', null, null, '0');
INSERT INTO `sys_user` VALUES ('234', 'zuzhang2', '736bb86f95c34cb6ea24b982fd7a16dc42120abcd7b7f7d1321cb194081bf99a', 'HIJP0mBlF4gNudIYOV1P', '475473324@qq.com', '13965784657', '1', '2', '2019-04-20 00:51:00', '59', null, null, '0');
INSERT INTO `sys_user` VALUES ('241', 'rukuan1', 'f5975fef9af265a909447098b2192c32d266737c01b9db1fb45deb568d7bab1a', 'aLsX2AtrRksqhXdd9CLC', '78465675@qq.com', '13567868763', '1', '2', '2019-04-20 00:54:21', '73', null, null, '0');
INSERT INTO `sys_user` VALUES ('248', 'rukuan2', '5acfa9ada43816b1cbf14d0b61ed4080123434363172fcf4328e556d02be5d67', 'Lxh6RtFunhAzoKGm0v0W', '87587367@qq.com', '13986463976', '1', '2', '2019-04-20 00:54:56', '66', null, null, '0');
INSERT INTO `sys_user` VALUES ('255', 'kefu1', '46a241cb0a91338f88913f5279978785e9834d803bec3dbdc54dd747cd5ae3e2', 'iEzHnh2RYqpBZw4lN5Q3', '765545875@qq.com', '13768077869', '1', '2', '2019-04-20 00:57:07', '80', null, null, '0');
INSERT INTO `sys_user` VALUES ('262', 'kefu2', 'fce8f080ed5cd34409c8164624174217de623b1d66b4d13e39ab6172fa8989b1', 'IM0c5JDwdBmJ2U8K0AfP', '7865378@qq.com', '13868767932', '1', '2', '2019-04-20 00:57:50', '80', null, null, '0');
INSERT INTO `sys_user` VALUES ('269', 'kefu3', '7f515d73540c313784b206307e29f2b76d2a05142a1a35f694f626614e8be6a9', 'RyPcgJ0oYJEwtn10loyd', '57562887@qq.com', '13678465872', '1', '2', '2019-04-20 00:58:24', '80', null, null, '0');
INSERT INTO `sys_user` VALUES ('276', 'kefu4', '593a23eb22093b21cb60c5f5521e5cca654ad02443c0d799129bbd08622a0bc6', '2u8motz7t0PbSZsCiNsa', '634576287@qq.com', '13977845672', '1', '2', '2019-04-20 00:58:55', '80', null, null, '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2', '3');
INSERT INTO `sys_user_role` VALUES ('3', '3', '1');
INSERT INTO `sys_user_role` VALUES ('45', '10', '1');
INSERT INTO `sys_user_role` VALUES ('59', '24', '4');
INSERT INTO `sys_user_role` VALUES ('66', '17', '1');
INSERT INTO `sys_user_role` VALUES ('73', '73', '1');
INSERT INTO `sys_user_role` VALUES ('80', '115', '24');
INSERT INTO `sys_user_role` VALUES ('101', '192', '38');
INSERT INTO `sys_user_role` VALUES ('108', '199', '45');
INSERT INTO `sys_user_role` VALUES ('115', '206', '52');
INSERT INTO `sys_user_role` VALUES ('122', '213', '38');
INSERT INTO `sys_user_role` VALUES ('129', '220', '59');
INSERT INTO `sys_user_role` VALUES ('143', '227', '66');
INSERT INTO `sys_user_role` VALUES ('150', '234', '59');
INSERT INTO `sys_user_role` VALUES ('164', '248', '66');
INSERT INTO `sys_user_role` VALUES ('178', '241', '73');
INSERT INTO `sys_user_role` VALUES ('185', '255', '80');
INSERT INTO `sys_user_role` VALUES ('192', '262', '80');
INSERT INTO `sys_user_role` VALUES ('199', '269', '80');
INSERT INTO `sys_user_role` VALUES ('206', '276', '80');

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1', '0413b77ffb7a891f78f2d1de5083ba04', '2019-04-22 09:41:50', '2019-04-21 21:41:50');
INSERT INTO `sys_user_token` VALUES ('2', '414d6a502bc899f71b92c65ee426b4ad', '2019-04-22 08:51:09', '2019-04-21 20:51:09');
INSERT INTO `sys_user_token` VALUES ('3', 'a6cfadeb8bc42727b89d8ffb7642f25f', '2019-04-20 01:56:34', '2019-04-19 13:56:34');
INSERT INTO `sys_user_token` VALUES ('10', '04d67661d214c2ce40c0732d0b9fe1b1', '2019-04-22 01:39:32', '2019-04-21 13:39:32');
INSERT INTO `sys_user_token` VALUES ('17', '05d112fcbc4df2ff555f6f338d65256b', '2019-04-12 07:04:13', '2019-04-11 19:04:13');
INSERT INTO `sys_user_token` VALUES ('24', 'ec67efe75e87286f5fa2e1619a1a6d1b', '2019-04-16 06:57:11', '2019-04-15 18:57:11');
INSERT INTO `sys_user_token` VALUES ('73', '27eaa8092d863ab0e2400afe5a8381b6', '2019-04-22 03:13:42', '2019-04-21 15:13:42');
INSERT INTO `sys_user_token` VALUES ('192', '27618117f986ca4847c2eb655e61488e', '2019-04-22 10:00:36', '2019-04-21 22:00:36');
INSERT INTO `sys_user_token` VALUES ('199', 'c7f2cc0dffb30eb6c7bac82f32b1b089', '2019-04-22 08:52:39', '2019-04-21 20:52:39');
INSERT INTO `sys_user_token` VALUES ('213', '994bceb402e46b3b4e052810cdbbb3e9', '2019-04-22 09:00:35', '2019-04-21 21:00:35');
INSERT INTO `sys_user_token` VALUES ('220', 'bd76880275d95223582b618fe045363e', '2019-04-21 20:58:32', '2019-04-21 08:58:32');
INSERT INTO `sys_user_token` VALUES ('227', '01d79ae79eb753abd73ca58de7669408', '2019-04-22 09:07:24', '2019-04-21 21:07:24');
INSERT INTO `sys_user_token` VALUES ('234', '20ac722dd783386568f35d59dd1c070e', '2019-04-22 08:40:50', '2019-04-21 20:40:50');
INSERT INTO `sys_user_token` VALUES ('241', '66904a3c0ce72df51315f7720e7f8b52', '2019-04-22 01:21:53', '2019-04-21 13:21:53');
INSERT INTO `sys_user_token` VALUES ('248', 'a0d6b576154f9745311f0c9ae07ad918', '2019-04-22 09:13:43', '2019-04-21 21:13:43');
INSERT INTO `sys_user_token` VALUES ('255', '5ae074b3d2cade92c944362e30e2862c', '2019-04-21 20:51:31', '2019-04-21 08:51:31');
INSERT INTO `sys_user_token` VALUES ('262', '9014f24f1eda613549a0696edcb9ee6b', '2019-04-21 21:09:42', '2019-04-21 09:09:42');
INSERT INTO `sys_user_token` VALUES ('269', '8c92e2538afa0fd3cfe482b48a8af261', '2019-04-20 13:13:56', '2019-04-20 01:13:56');
INSERT INTO `sys_user_token` VALUES ('276', 'f0d345d4a3f9f79c7006aacfd491c8c0', '2019-04-20 13:14:14', '2019-04-20 01:14:14');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `account` varchar(100) NOT NULL DEFAULT '' COMMENT '账号名称',
  `portrait` varchar(22) DEFAULT '' COMMENT '头像',
  `sex` bit(1) NOT NULL DEFAULT b'1' COMMENT '性别(0:女，1：男)',
  `user_type` varchar(22) NOT NULL COMMENT '账号类型（试玩账号，普通会员，vip）',
  `token` varchar(255) DEFAULT NULL COMMENT 'token验证Id',
  `union_type` varchar(22) DEFAULT NULL COMMENT '第三方平台',
  `open_id` varchar(255) DEFAULT NULL COMMENT '第三方交互ID',
  `union_id` varchar(255) DEFAULT NULL COMMENT '第三方ID',
  `superiors_id` bigint(22) DEFAULT NULL COMMENT '上级ID',
  `forbidden_enable` bit(1) DEFAULT b'0' COMMENT '禁用（正常）',
  `nobet_enable` bit(1) DEFAULT b'0' COMMENT '停押（正常）',
  `frozen_enable` bit(1) DEFAULT b'0' COMMENT '冻结(正常)',
  `hierarchy_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '层级id',
  `coin` bigint(22) unsigned NOT NULL DEFAULT '0' COMMENT '用户金币',
  `commission` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '佣金',
  `money` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '金钱',
  `register_ip` varchar(100) DEFAULT '' COMMENT '注册ip',
  `register_device_code` varchar(60) DEFAULT NULL COMMENT '注册机器码',
  `user_name` varchar(30) DEFAULT '' COMMENT '真实姓名',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `agent_enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（1：是代理。0：禁用代理）',
  `first_recharge` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否首冲过',
  `game_info_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '在玩游戏信息id',
  `game_server_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '游戏服务id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=501298 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_agent_hierarchy
-- ----------------------------
DROP TABLE IF EXISTS `user_agent_hierarchy`;
CREATE TABLE `user_agent_hierarchy` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '层级名称',
  `type` bit(1) DEFAULT b'0' COMMENT '是否是默认层级1为默认层级',
  `coin` bigint(22) unsigned NOT NULL DEFAULT '0' COMMENT '赠送金币',
  `commission` decimal(10,4) NOT NULL DEFAULT '0.0000' COMMENT '充值返佣比例',
  `proportion` decimal(10,4) NOT NULL DEFAULT '0.0000' COMMENT '返佣比例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='用户代理层级设置';

-- ----------------------------
-- Records of user_agent_hierarchy
-- ----------------------------
INSERT INTO `user_agent_hierarchy` VALUES ('1', '0', '2019-02-06 16:26:25', '2019-03-14 16:28:04', '0', '一级代理', '', '10000', '0.0010', '0.0100');
INSERT INTO `user_agent_hierarchy` VALUES ('29', '0', '2019-02-06 16:27:56', '2019-03-14 16:28:13', '0', '二级代理', '\0', '20000', '0.0100', '0.2000');

-- ----------------------------
-- Table structure for user_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `user_blacklist`;
CREATE TABLE `user_blacklist` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_account` varchar(50) DEFAULT '' COMMENT '用户账号',
  `type` bit(1) DEFAULT b'0' COMMENT '黑白名单0:黑名单',
  `remark` varchar(100) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户黑白名单表';

-- ----------------------------
-- Records of user_blacklist
-- ----------------------------

-- ----------------------------
-- Table structure for user_hierarchy
-- ----------------------------
DROP TABLE IF EXISTS `user_hierarchy`;
CREATE TABLE `user_hierarchy` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) NOT NULL COMMENT '层级名称',
  `type` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是默认层级1为默认层级',
  `recharge_multiple` decimal(10,2) NOT NULL DEFAULT '1.00' COMMENT '充值打码倍数',
  `discount_multiple` decimal(10,2) NOT NULL DEFAULT '1.00' COMMENT '优惠打码倍数',
  `relaxation_rate` decimal(3,2) NOT NULL DEFAULT '0.00' COMMENT '打码放宽比例',
  `administrative_rate` decimal(3,2) NOT NULL DEFAULT '0.00' COMMENT '行政费比例',
  `bet_rate` decimal(3,2) NOT NULL DEFAULT '0.50' COMMENT '下注超过该比例则用户下回充值的时候按照用户余额计算为打码数，用户打码未超过该比例的按照用户打码规则计算为打码的数量',
  `vip_enable` bit(1) NOT NULL DEFAULT b'0' COMMENT 'vip通道是否开启1：开启',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户层级表';

-- ----------------------------
-- Records of user_hierarchy
-- ----------------------------
INSERT INTO `user_hierarchy` VALUES ('1', '0', '2019-01-17 11:00:28', '2019-04-04 15:11:25', '0', '默认层', '', '1.50', '1.50', '0.50', '0.50', '0.50', '');
INSERT INTO `user_hierarchy` VALUES ('2', '0', '2019-01-17 11:00:32', '2019-04-20 18:40:51', '0', '第一层', '\0', '100.00', '1.00', '0.00', '0.10', '0.00', '\0');
INSERT INTO `user_hierarchy` VALUES ('3', '0', '2019-01-17 11:00:35', '2019-04-20 18:41:12', '0', '第二层', '\0', '10000.00', '1.00', '0.00', '0.00', '1.00', '\0');
INSERT INTO `user_hierarchy` VALUES ('4', '0', '2019-01-17 14:49:40', '2019-04-20 18:41:27', '0', '第三层', '\0', '8888888.00', '1.00', '0.00', '0.00', '1.00', '\0');
INSERT INTO `user_hierarchy` VALUES ('5', '0', '2019-01-29 09:54:29', '2019-04-20 18:42:02', '0', '第四层', '\0', '9999999.00', '1.50', '0.00', '0.00', '1.00', '\0');
INSERT INTO `user_hierarchy` VALUES ('6', '0', '2019-01-29 09:55:22', '2019-04-20 18:42:21', '0', '第五层', '\0', '8889999.00', '1.80', '0.00', '0.00', '1.00', '');
INSERT INTO `user_hierarchy` VALUES ('7', '0', '2019-03-04 14:27:59', '2019-04-20 18:43:31', '0', '第六层', '\0', '7897897.00', '2.00', '0.00', '0.00', '0.00', '');
INSERT INTO `user_hierarchy` VALUES ('8', '0', '2019-03-04 14:29:14', '2019-04-20 18:44:29', '0', '第八层', '\0', '7999999.00', '1.00', '0.00', '0.00', '0.00', '\0');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_identity` varchar(100) DEFAULT '' COMMENT '身份证号码',
  `user_phone` varchar(100) DEFAULT '' COMMENT '电话',
  `user_email` varchar(100) DEFAULT '' COMMENT '电子邮箱',
  `post_code` varchar(100) DEFAULT '' COMMENT '邮编',
  `user_address` varchar(255) DEFAULT '' COMMENT '地址',
  `user_qq` varchar(50) DEFAULT '' COMMENT 'QQ',
  `user_birthday` date DEFAULT NULL COMMENT '生日',
  `bank_name` varchar(100) DEFAULT '' COMMENT '银行名称',
  `bank_address` varchar(255) DEFAULT '' COMMENT '银行开户地址',
  `bank_card` varchar(32) DEFAULT '' COMMENT '银行卡号',
  `bank_account_name` varchar(255) DEFAULT '' COMMENT '银行卡开户名',
  `user_id` bigint(22) DEFAULT '0' COMMENT '用户id（唯一）',
  `alipay_account` varchar(255) DEFAULT '' COMMENT '支付宝账号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userid_uk` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1267 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of user_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT '' COMMENT '用户名',
  `operation` varchar(50) DEFAULT '' COMMENT '用户操作',
  `method` varchar(200) DEFAULT '' COMMENT '请求方法',
  `params` varchar(5000) DEFAULT '' COMMENT '请求参数',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT '' COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `member_id` bigint(22) DEFAULT '0' COMMENT '会员id',
  `member_account` varchar(100) DEFAULT '' COMMENT '会员账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=476 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of user_log
-- ----------------------------

-- ----------------------------
-- Table structure for user_login
-- ----------------------------
DROP TABLE IF EXISTS `user_login`;
CREATE TABLE `user_login` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '用户id',
  `ip` varchar(50) NOT NULL DEFAULT '' COMMENT '登陆ip',
  `device_code` varchar(255) DEFAULT '' COMMENT '机器码',
  `device_type` varchar(50) DEFAULT '' COMMENT '设备类型',
  `region` varchar(50) DEFAULT '' COMMENT '地区',
  `nation` varchar(50) DEFAULT '' COMMENT '国家',
  `ip_address` varchar(255) DEFAULT '' COMMENT '地理位置',
  `domain` varchar(100) DEFAULT '' COMMENT '域名',
  `edition` varchar(255) DEFAULT '' COMMENT '版本号',
  `browser` varchar(255) DEFAULT '' COMMENT '浏览器版本',
  `login_status` varchar(22) NOT NULL DEFAULT '' COMMENT '登陆状态',
  `token` varchar(255) DEFAULT '' COMMENT '登陆时token',
  `hall_id` bigint(22) DEFAULT '0' COMMENT '大厅id',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23128 DEFAULT CHARSET=utf8 COMMENT='用户登陆记录表';

-- ----------------------------
-- Records of user_login
-- ----------------------------

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(255) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `login_password` varchar(50) DEFAULT '' COMMENT '登陆密码',
  `bank_password` varchar(50) DEFAULT '' COMMENT '取款密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1267 DEFAULT CHARSET=utf8 COMMENT='用户密码表';

-- ----------------------------
-- Records of user_password
-- ----------------------------

-- ----------------------------
-- Table structure for user_rebate
-- ----------------------------
DROP TABLE IF EXISTS `user_rebate`;
CREATE TABLE `user_rebate` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `type` int(10) NOT NULL DEFAULT '0' COMMENT '返利类型',
  `coin` bigint(11) NOT NULL DEFAULT '0' COMMENT '返利金币',
  `water_rate` decimal(10,4) NOT NULL DEFAULT '0.0000' COMMENT '返水比例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户完善信息返利规则表';

-- ----------------------------
-- Records of user_rebate
-- ----------------------------
INSERT INTO `user_rebate` VALUES ('1', '0', '2019-02-16 19:02:30', '2019-03-23 15:19:37', '0', '1', '50000', '0.0000');
INSERT INTO `user_rebate` VALUES ('2', '0', '2019-04-02 16:59:09', '2019-04-02 16:59:09', '0', '2', '0', '0.0200');

-- ----------------------------
-- Table structure for user_recommendation
-- ----------------------------
DROP TABLE IF EXISTS `user_recommendation`;
CREATE TABLE `user_recommendation` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `user_account` varchar(255) NOT NULL DEFAULT '' COMMENT '用户账号',
  `recommendation_code` varchar(22) NOT NULL DEFAULT '' COMMENT '推荐码',
  `num` int(11) DEFAULT '0' COMMENT '邀请人数',
  `amount` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '总奖金',
  `coin` bigint(22) NOT NULL DEFAULT '0' COMMENT '金币',
  `agent_hierarchy_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '代理等级',
  PRIMARY KEY (`id`),
  UNIQUE KEY `recommendation_code` (`recommendation_code`) USING BTREE,
  UNIQUE KEY `user_id_uk` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1264 DEFAULT CHARSET=utf8 COMMENT='用户推荐码表';

-- ----------------------------
-- Records of user_recommendation
-- ----------------------------

-- ----------------------------
-- Table structure for user_recommendation_record
-- ----------------------------
DROP TABLE IF EXISTS `user_recommendation_record`;
CREATE TABLE `user_recommendation_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '被推荐人id',
  `user_account` varchar(255) NOT NULL DEFAULT '' COMMENT '被推荐人账号',
  `recommendation_code` varchar(22) NOT NULL DEFAULT '' COMMENT '推荐码',
  `promoter_id` bigint(22) DEFAULT '0' COMMENT '推广人id',
  `promoter_account` varchar(255) DEFAULT '' COMMENT '推广人账号',
  `promoting_profit` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '推广盈利',
  `coin` bigint(20) DEFAULT '0' COMMENT '金币',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `index_promoter` (`promoter_id`) USING BTREE COMMENT '推荐人索引',
  KEY `index_user_account` (`user_account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='用户推荐记录表';

-- ----------------------------
-- Records of user_recommendation_record
-- ----------------------------

-- ----------------------------
-- Table structure for user_transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `user_transaction_record`;
CREATE TABLE `user_transaction_record` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `user_id` bigint(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `user_account` varchar(255) NOT NULL DEFAULT '' COMMENT '用户账号',
  `type` int(22) NOT NULL DEFAULT '0' COMMENT '交易类型(0：存款 1:取款 2:冲销 3:返利，4：派奖，5: 额度转换，6：资金归集)',
  `order_no` varchar(22) NOT NULL DEFAULT '0' COMMENT '业务ID 订单编号',
  `amount` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '交易额',
  `remake` varchar(255) DEFAULT '' COMMENT '备注',
  `fristrecharge` bit(1) DEFAULT b'0' COMMENT '是否是首充（0：不是，1：是）',
  `money` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '账户余额',
  `coin` bigint(22) NOT NULL DEFAULT '0' COMMENT '金币',
  `commission` decimal(22,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '佣金',
  `detail_type` int(3) DEFAULT '0' COMMENT '具体的类型: 11:银行充值 12:支付宝充值 13:微信充值 14:人工充值 15: 佣金取款 16 账户取款 17 额度转换 18 签到返利 19推荐返利)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`,`detail_type`) USING BTREE COMMENT '订单号唯一',
  KEY `index_user_account` (`user_account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7490 DEFAULT CHARSET=utf8 COMMENT='资金交易明细';

-- ----------------------------
-- Records of user_transaction_record
-- ----------------------------

-- ----------------------------
-- Table structure for user_trial_config
-- ----------------------------
DROP TABLE IF EXISTS `user_trial_config`;
CREATE TABLE `user_trial_config` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `trial_number` bigint(10) DEFAULT '0' COMMENT '试玩个数',
  `trial_amount` decimal(12,2) DEFAULT '0.00' COMMENT '试玩金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='试玩账号配置';

-- ----------------------------
-- Records of user_trial_config
-- ----------------------------
INSERT INTO `user_trial_config` VALUES ('1', '0', '2019-02-28 21:05:25', '2019-04-20 23:10:54', '0', '0', '0.00');

-- ----------------------------
-- Table structure for webhome_contact
-- ----------------------------
DROP TABLE IF EXISTS `webhome_contact`;
CREATE TABLE `webhome_contact` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `enclosure_id` bigint(22) DEFAULT NULL COMMENT '附件ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `type` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型(联系方式，网站信息）',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否展示',
  `remake` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `url` varchar(255) DEFAULT '' COMMENT '跳转路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='联系管理';

-- ----------------------------
-- Records of webhome_contact
-- ----------------------------
INSERT INTO `webhome_contact` VALUES ('21', '0', '2019-01-27 09:15:00', '2019-04-10 10:57:52', '0', '96', '官方邮箱:', 'abqpvip@gmail.com', 'contact', '', null, 'null');
INSERT INTO `webhome_contact` VALUES ('22', '0', '2019-01-27 09:15:40', '2019-04-10 10:59:59', '0', '97', 'QQ客服：', '3965578', 'contact', '', null, 'http://wpa.qq.com/msgrd?v=3&uin=3965578&site=qq&menu=yes');
INSERT INTO `webhome_contact` VALUES ('23', '0', '2019-01-27 09:15:59', '2019-03-03 16:22:31', '0', '98', '微信客服：', 'ABYLKF', 'contact', '', null, '');
INSERT INTO `webhome_contact` VALUES ('24', '0', '2019-01-27 09:16:31', '2019-04-01 18:48:13', '0', '102', '澳门客服热线：', '00853 6265 3399', 'contact', '', null, '');
INSERT INTO `webhome_contact` VALUES ('25', '0', '2019-01-27 09:16:51', '2019-04-10 11:50:17', '0', '103', 'QQ代理：', '235699999', 'contact', '\0', null, 'http://wpa.qq.com/msgrd?v=3&uin=235699999&site=qq&menu=yes');
INSERT INTO `webhome_contact` VALUES ('26', '0', '2019-01-27 09:17:42', '2019-04-10 11:52:15', '0', '104', '微信代理：', 'ABQPDL', 'contact', '\0', null, '');
INSERT INTO `webhome_contact` VALUES ('27', '0', '2019-01-27 09:18:05', '2019-04-10 11:51:59', '0', '105', '客服投诉：', '769557788', 'contact', '\0', null, 'http://wpa.qq.com/msgrd?v=3&uin=769557788&site=qq&menu=yes');
INSERT INTO `webhome_contact` VALUES ('29', '0', '2019-01-27 09:58:21', '2019-03-09 16:00:13', '0', null, '1', 'abqp0.cc 的许可证 .zxab2018NO.012由AB棋牌授予使用权以及提供，并在澳门博彩监督委员会监管下严格遵循《网络博彩监督管法案2001》运营。', 'website', '', null, '');
INSERT INTO `webhome_contact` VALUES ('30', '0', '2019-01-27 09:59:13', '2019-01-27 09:59:13', '0', null, '2', '2018年12月25日获颁许可证。', 'website', '', null, '');
INSERT INTO `webhome_contact` VALUES ('31', '0', '2019-01-27 09:59:42', '2019-01-27 09:59:42', '0', null, '3', 'AB棋牌的营业许可证由澳门博彩监督委员会颁发并受其监管及授权在澳门提供服务。', 'website', '', null, '');
INSERT INTO `webhome_contact` VALUES ('32', '0', '2019-01-27 09:59:59', '2019-03-09 16:01:56', '0', '108', '4', 'abqpvip@gmail.com', 'website', '', null, '');
INSERT INTO `webhome_contact` VALUES ('33', '0', '2019-01-27 10:00:52', '2019-03-09 16:02:02', '0', null, '5', 'abqpvip@gmail.com', 'website', '', null, '');
INSERT INTO `webhome_contact` VALUES ('34', '0', '2019-01-27 10:01:24', '2019-01-27 10:01:24', '0', null, '6', '©  abqp0.cc  版权所有', 'website', '', null, '');
INSERT INTO `webhome_contact` VALUES ('35', '0', '2019-01-27 10:01:38', '2019-01-27 10:27:06', '0', null, '8', 'AB博彩监督委员会可实行强制收取债款。', 'website', '', null, '');
INSERT INTO `webhome_contact` VALUES ('36', '0', '2019-01-27 10:02:36', '2019-01-27 10:02:36', '0', '109', '7', '警告', 'website', '', null, '');
INSERT INTO `webhome_contact` VALUES ('37', '0', '2019-01-27 10:27:21', '2019-01-27 10:27:21', '0', null, '9', '法定年龄18岁以下禁止登陆abqp0.cc。', 'website', '', null, '');
INSERT INTO `webhome_contact` VALUES ('38', '0', '2019-01-27 10:27:39', '2019-01-27 10:27:39', '0', null, '10', '未成年人赌博是违法的。', 'website', '', null, '');

-- ----------------------------
-- Table structure for webhome_domain
-- ----------------------------
DROP TABLE IF EXISTS `webhome_domain`;
CREATE TABLE `webhome_domain` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `domain` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '域名',
  `type` int(1) DEFAULT NULL COMMENT '1:官网,2:管理后台',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='域名管理';

-- ----------------------------
-- Records of webhome_domain
-- ----------------------------
INSERT INTO `webhome_domain` VALUES ('52', '0', '2019-03-03 19:34:25', '2019-04-08 23:59:49', '0', 'https://www.abqp0.cc', '1');

-- ----------------------------
-- Table structure for webhome_enclosure
-- ----------------------------
DROP TABLE IF EXISTS `webhome_enclosure`;
CREATE TABLE `webhome_enclosure` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '文件名',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '文件路径',
  `type` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '文件类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=896 DEFAULT CHARSET=utf8 COMMENT='文件附件表';

-- ----------------------------
-- Records of webhome_enclosure
-- ----------------------------
INSERT INTO `webhome_enclosure` VALUES ('45', '0', '2019-01-22 14:14:04', '2019-01-25 10:34:16', '0', 'okok.png', 'https://image.99dongshi.com/FrCZIeQTPj2vYxRYhgfcgYP30I5A', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('46', '0', '2019-01-22 14:14:46', '2019-01-25 10:34:19', '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('47', '0', '2019-01-22 14:15:33', '2019-01-25 10:34:22', '0', '图层18.png', 'https://image.99dongshi.com/Fk5yppg_nwnVyiyuRXv63WHqoR03', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('48', '0', '2019-01-22 14:16:39', '2019-01-25 10:34:24', '0', '摇钱树.png', 'https://image.99dongshi.com/Fq0McM_cwG8JyxAuX_wO65orGujn', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('49', '0', '2019-01-25 09:33:12', '2019-01-25 10:34:27', '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('50', '0', '2019-01-25 10:37:15', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('51', '0', '2019-01-25 10:57:27', null, '0', 'call1.png', 'https://image.99dongshi.com/FpjVhKFyxuQgkIXj4wbS1mUMDduv', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('52', '0', '2019-01-25 10:57:46', null, '0', 'call2.png', 'https://image.99dongshi.com/Fn_g2lwoLsi9vQ0R9r6yrWw2Q9s-', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('53', '0', '2019-01-25 11:08:44', null, '0', '图层18.png', 'https://image.99dongshi.com/Fk5yppg_nwnVyiyuRXv63WHqoR03', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('54', '0', '2019-01-25 11:09:02', null, '0', '摇钱树.png', 'https://image.99dongshi.com/Fq0McM_cwG8JyxAuX_wO65orGujn', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('55', '0', '2019-01-25 11:09:17', null, '0', '摇钱树.png', 'https://image.99dongshi.com/Fq0McM_cwG8JyxAuX_wO65orGujn', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('56', '0', '2019-01-25 11:10:02', null, '0', '图层18.png', 'https://image.99dongshi.com/Fk5yppg_nwnVyiyuRXv63WHqoR03', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('57', '0', '2019-01-25 11:10:16', null, '0', '图层18.png', 'https://image.99dongshi.com/Fk5yppg_nwnVyiyuRXv63WHqoR03', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('58', '0', '2019-01-25 11:10:34', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('59', '0', '2019-01-26 16:51:00', null, '0', 'pay3.jpg', 'https://image.99dongshi.com/Fsh479wXURYzpyTR9xsmDIeoTkYz', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('60', '0', '2019-01-26 16:51:00', null, '0', 'pay5.jpg', 'https://image.99dongshi.com/FvmC1PdZh5qlpFZi03ZNJTMH_Mhs', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('61', '0', '2019-01-26 16:51:00', null, '0', 'pay6.jpg', 'https://image.99dongshi.com/FmQx32xuewSnjkirdsrkDG-PocLS', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('62', '0', '2019-01-26 16:51:00', null, '0', 'pay2.jpg', 'https://image.99dongshi.com/FlNPWepkn2UPn_iOeY__qBobPMVY', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('63', '0', '2019-01-26 16:51:00', null, '0', 'pay4.jpg', 'https://image.99dongshi.com/FvD3t7pFs_clvcqSKao2Gl43yDWk', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('64', '0', '2019-01-26 16:51:00', null, '0', 'pay1.jpg', 'https://image.99dongshi.com/FpIm6Kv5AzsQLIzG3__tyZ_cfMkA', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('65', '0', '2019-01-26 16:51:29', null, '0', 'pay5.jpg', 'https://image.99dongshi.com/FvmC1PdZh5qlpFZi03ZNJTMH_Mhs', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('66', '0', '2019-01-26 16:51:29', null, '0', 'pay6.jpg', 'https://image.99dongshi.com/FmQx32xuewSnjkirdsrkDG-PocLS', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('67', '0', '2019-01-26 16:51:29', null, '0', 'pay3.jpg', 'https://image.99dongshi.com/Fsh479wXURYzpyTR9xsmDIeoTkYz', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('68', '0', '2019-01-26 16:51:29', null, '0', 'pay4.jpg', 'https://image.99dongshi.com/FvD3t7pFs_clvcqSKao2Gl43yDWk', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('69', '0', '2019-01-26 16:51:29', null, '0', 'pay1.jpg', 'https://image.99dongshi.com/FpIm6Kv5AzsQLIzG3__tyZ_cfMkA', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('70', '0', '2019-01-26 16:51:29', null, '0', 'pay2.jpg', 'https://image.99dongshi.com/FlNPWepkn2UPn_iOeY__qBobPMVY', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('71', '0', '2019-01-26 16:55:22', null, '0', 'pay1.jpg', 'https://image.99dongshi.com/FpIm6Kv5AzsQLIzG3__tyZ_cfMkA', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('72', '0', '2019-01-26 16:55:33', null, '0', 'pay2.jpg', 'https://image.99dongshi.com/FlNPWepkn2UPn_iOeY__qBobPMVY', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('73', '0', '2019-01-26 16:55:40', null, '0', 'pay3.jpg', 'https://image.99dongshi.com/Fsh479wXURYzpyTR9xsmDIeoTkYz', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('74', '0', '2019-01-26 16:55:50', null, '0', 'pay4.jpg', 'https://image.99dongshi.com/FvD3t7pFs_clvcqSKao2Gl43yDWk', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('75', '0', '2019-01-26 16:56:48', null, '0', 'pay2.jpg', 'https://image.99dongshi.com/FlNPWepkn2UPn_iOeY__qBobPMVY', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('76', '0', '2019-01-26 16:59:46', null, '0', 'pay1.jpg', 'https://image.99dongshi.com/FpIm6Kv5AzsQLIzG3__tyZ_cfMkA', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('77', '0', '2019-01-26 16:59:56', null, '0', 'pay2.jpg', 'https://image.99dongshi.com/FlNPWepkn2UPn_iOeY__qBobPMVY', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('78', '0', '2019-01-26 17:00:05', null, '0', 'pay3.jpg', 'https://image.99dongshi.com/Fsh479wXURYzpyTR9xsmDIeoTkYz', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('79', '0', '2019-01-26 17:00:16', null, '0', 'pay4.jpg', 'https://image.99dongshi.com/FvD3t7pFs_clvcqSKao2Gl43yDWk', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('80', '0', '2019-01-26 17:00:40', null, '0', 'pay5.jpg', 'https://image.99dongshi.com/FvmC1PdZh5qlpFZi03ZNJTMH_Mhs', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('81', '0', '2019-01-26 17:02:16', null, '0', 'pay6.jpg', 'https://image.99dongshi.com/FmQx32xuewSnjkirdsrkDG-PocLS', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('82', '0', '2019-01-26 17:22:57', null, '0', '未标题-1.jpg', 'https://image.99dongshi.com/Frnsjx15fb3cpf0_7BXF21w74PfB', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('83', '0', '2019-01-26 19:11:54', null, '0', 'app.jpg', 'https://image.99dongshi.com/FsecbV6Y0vuRRnLcppskrTeJd2eE', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('84', '0', '2019-01-26 19:15:56', null, '0', 'app.jpg', 'https://image.99dongshi.com/FsecbV6Y0vuRRnLcppskrTeJd2eE', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('85', '0', '2019-01-26 19:16:36', null, '0', 'app.jpg', 'https://image.99dongshi.com/FsecbV6Y0vuRRnLcppskrTeJd2eE', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('86', '0', '2019-01-26 19:18:45', null, '0', 'app.jpg', 'https://image.99dongshi.com/FsecbV6Y0vuRRnLcppskrTeJd2eE', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('87', '0', '2019-01-26 19:23:23', null, '0', '17.png', 'https://image.99dongshi.com/Fgce3O9IckhrR1Y5sBlhmn2ossMx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('88', '0', '2019-01-26 19:26:34', null, '0', 'app.jpg', 'https://image.99dongshi.com/FsecbV6Y0vuRRnLcppskrTeJd2eE', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('89', '0', '2019-01-26 19:40:37', null, '0', '17.png', 'https://image.99dongshi.com/Fgce3O9IckhrR1Y5sBlhmn2ossMx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('90', '0', '2019-01-26 19:42:46', null, '0', '17.png', 'https://image.99dongshi.com/Fgce3O9IckhrR1Y5sBlhmn2ossMx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('91', '0', '2019-01-26 19:43:27', null, '0', 're.png', 'https://image.99dongshi.com/Fh4aYMsQm72fWJAL1kKuQ2-Grjij', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('92', '0', '2019-01-26 19:51:33', null, '0', 'lan3.png', 'https://image.99dongshi.com/FkTtNtDFqR2J7CNiV73fhQuxZ3qN', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('93', '0', '2019-01-26 19:51:53', null, '0', 'lan3.png', 'https://image.99dongshi.com/FkTtNtDFqR2J7CNiV73fhQuxZ3qN', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('94', '0', '2019-01-26 19:52:23', null, '0', 'lan2.png', 'https://image.99dongshi.com/FuOLmRPiDysVKk_6UBeusCe7-VgR', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('95', '0', '2019-01-26 19:53:01', null, '0', 're.png', 'https://image.99dongshi.com/Fh4aYMsQm72fWJAL1kKuQ2-Grjij', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('96', '0', '2019-01-27 09:14:59', null, '0', 'ps1.png', 'https://image.99dongshi.com/Fom_pDCLDH9ZbRYpTYP-69AiOFDa', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('97', '0', '2019-01-27 09:15:36', null, '0', 'ps2.png', 'https://image.99dongshi.com/FknSWzDOBL8NbKiBH0VTMHRb_xOL', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('98', '0', '2019-01-27 09:15:58', null, '0', 'ps3.png', 'https://image.99dongshi.com/FjUOL_Hwhk8CyGW7BHzuSqZQCJMC', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('99', '0', '2019-01-27 09:16:29', null, '0', 'ps4.png', 'https://image.99dongshi.com/FtuVtrDfDkcaTVtq_-YR9D44Aung', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('100', '0', '2019-01-27 09:18:04', null, '0', 'ps7.png', 'https://image.99dongshi.com/Fr8eCqYjE6xsKZlCf2PA1UFstiLs', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('101', '0', '2019-01-27 09:19:02', null, '0', 'ps3.png', 'https://image.99dongshi.com/FjUOL_Hwhk8CyGW7BHzuSqZQCJMC', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('102', '0', '2019-01-27 09:19:26', null, '0', 'ps4.png', 'https://image.99dongshi.com/FtuVtrDfDkcaTVtq_-YR9D44Aung', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('103', '0', '2019-01-27 09:19:39', null, '0', 'ps5.png', 'https://image.99dongshi.com/FqwTrnbPDp1_Rt3a-g2ziwwak-Ri', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('104', '0', '2019-01-27 09:19:49', null, '0', 'ps6.png', 'https://image.99dongshi.com/FhvsC0gvzBR0BSQ8wJhKo98HhhQ2', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('105', '0', '2019-01-27 09:20:00', null, '0', 'ps7.png', 'https://image.99dongshi.com/Fr8eCqYjE6xsKZlCf2PA1UFstiLs', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('106', '0', '2019-01-27 09:20:09', null, '0', 'ps8.png', 'https://image.99dongshi.com/FpqOj1BqeXMVVZffB7t3fQEUI8RP', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('107', '0', '2019-01-27 09:49:42', null, '0', '13.png', 'https://image.99dongshi.com/Fp_JaOoEcO_3Vf6z4g2mnuvp94xI', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('108', '0', '2019-01-27 10:00:36', null, '0', 'email1.png', 'https://image.99dongshi.com/FqmfRWJHwsiob-4Po5YdJ1oTcj3b', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('109', '0', '2019-01-27 10:02:33', null, '0', 'ti2.png', 'https://image.99dongshi.com/Foqwu4smhF05EQZdrp5UM9FazRAO', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('110', '0', '2019-01-27 13:39:48', null, '0', '8.png', 'https://image.99dongshi.com/FoEbgD7iIKmw68oSPcs5opBM4cfF', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('111', '0', '2019-01-27 13:40:59', null, '0', '17.png', 'https://image.99dongshi.com/Fgce3O9IckhrR1Y5sBlhmn2ossMx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('112', '0', '2019-01-27 14:14:39', null, '0', '17.png', 'https://image.99dongshi.com/Fgce3O9IckhrR1Y5sBlhmn2ossMx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('113', '0', '2019-01-27 18:22:25', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('114', '0', '2019-01-27 18:41:29', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('115', '0', '2019-01-27 18:42:09', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('116', '0', '2019-01-27 18:43:13', null, '0', '9.png', 'https://image.99dongshi.com/FiMkkbvgqhWhBa5hlIJq2YmfazWQ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('117', '0', '2019-01-27 19:11:21', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('118', '0', '2019-01-27 19:17:44', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('119', '0', '2019-01-27 19:53:33', null, '0', '17.png', 'https://image.99dongshi.com/Fgce3O9IckhrR1Y5sBlhmn2ossMx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('120', '0', '2019-01-27 19:55:12', null, '0', '17.png', 'https://image.99dongshi.com/Fgce3O9IckhrR1Y5sBlhmn2ossMx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('121', '0', '2019-01-27 19:56:07', null, '0', '17.png', 'https://image.99dongshi.com/Fgce3O9IckhrR1Y5sBlhmn2ossMx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('122', '0', '2019-01-27 20:00:53', null, '0', '17.png', 'https://image.99dongshi.com/Fgce3O9IckhrR1Y5sBlhmn2ossMx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('123', '0', '2019-01-27 20:28:24', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('124', '0', '2019-01-27 20:57:50', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('125', '0', '2019-01-27 20:59:10', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('126', '0', '2019-01-28 09:25:53', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('127', '0', '2019-01-28 09:29:51', null, '0', 'devil may crry 4 - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FvkBWZDSHrG3MEd40dtob741bWtk', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('128', '0', '2019-01-28 09:33:22', null, '0', 'devil may crry 4 - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FvkBWZDSHrG3MEd40dtob741bWtk', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('129', '0', '2019-01-28 09:34:57', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('130', '0', '2019-01-28 09:41:23', null, '0', 'devil may crry 4 - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FvkBWZDSHrG3MEd40dtob741bWtk', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('131', '0', '2019-01-28 09:54:40', null, '0', 'Q版女.png', 'https://image.99dongshi.com/FuWoaDl-6K28xT7ot1CKhy0bw_Aa', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('132', '0', '2019-01-28 09:58:04', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('133', '0', '2019-01-28 10:05:15', null, '0', 'devil may crry 4 - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FvkBWZDSHrG3MEd40dtob741bWtk', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('134', '0', '2019-01-28 10:07:58', null, '0', 'Q版男.png', 'https://image.99dongshi.com/FqxwYYv_EOhuehmR1iHGOQ6VS2Lo', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('135', '0', '2019-01-28 10:08:19', null, '0', '图层18.png', 'https://image.99dongshi.com/Fk5yppg_nwnVyiyuRXv63WHqoR03', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('136', '0', '2019-01-28 10:39:26', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('137', '0', '2019-01-28 11:04:42', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('138', '0', '2019-01-28 11:07:49', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('139', '0', '2019-01-28 14:24:12', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('140', '0', '2019-01-28 14:25:26', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('141', '0', '2019-01-28 14:30:05', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('142', '0', '2019-01-28 14:31:24', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('143', '0', '2019-01-28 14:32:52', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('144', '0', '2019-01-28 14:33:16', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('145', '0', '2019-01-28 14:34:25', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('146', '0', '2019-01-28 14:37:01', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('147', '0', '2019-01-28 14:41:38', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('148', '0', '2019-01-28 14:42:40', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('149', '0', '2019-01-28 14:43:30', null, '0', 'burnout paradise - 1 - esrb e pegi 7+.jps', 'https://image.99dongshi.com/Fr8dIgksIHDWit4BLFiPLkEMqqsf', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('150', '0', '2019-01-28 14:46:48', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('151', '0', '2019-01-28 14:46:59', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('152', '0', '2019-01-28 14:47:49', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('153', '0', '2019-01-28 14:49:18', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('154', '0', '2019-01-28 15:28:33', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('155', '0', '2019-01-28 16:16:01', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('156', '0', '2019-01-28 16:16:56', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('157', '0', '2019-01-28 16:45:52', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('158', '0', '2019-01-28 16:46:23', null, '0', 'devil may crry 4 - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FvkBWZDSHrG3MEd40dtob741bWtk', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('159', '0', '2019-01-28 16:50:58', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('160', '0', '2019-01-28 17:00:18', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('161', '0', '2019-01-28 17:10:01', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('162', '0', '2019-01-28 18:32:10', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('163', '0', '2019-01-29 10:30:04', null, '0', 'devil may crry 4 - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FvkBWZDSHrG3MEd40dtob741bWtk', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('164', '0', '2019-01-29 10:30:17', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('165', '0', '2019-01-29 10:30:44', null, '0', 'devil may crry 4 - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FvkBWZDSHrG3MEd40dtob741bWtk', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('166', '0', '2019-01-29 10:34:17', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('167', '0', '2019-01-29 10:34:33', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('168', '0', '2019-01-29 11:29:36', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('169', '0', '2019-01-29 13:48:34', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('170', '0', '2019-01-29 13:52:57', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('171', '0', '2019-01-29 14:00:35', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('172', '0', '2019-01-29 14:04:02', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('173', '0', '2019-01-29 14:05:31', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('174', '0', '2019-01-29 14:06:04', null, '0', 'devil may crry 4 - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FvkBWZDSHrG3MEd40dtob741bWtk', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('175', '0', '2019-01-29 14:08:37', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('176', '0', '2019-01-29 14:13:24', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('177', '0', '2019-01-29 14:18:22', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('178', '0', '2019-01-29 14:19:34', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('179', '0', '2019-01-29 14:29:56', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('180', '0', '2019-01-29 14:38:05', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('181', '0', '2019-01-29 14:38:22', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('182', '0', '2019-01-29 14:39:48', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('183', '0', '2019-01-29 15:02:05', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('184', '0', '2019-01-29 15:02:32', null, '0', 'guitar hero 3 - 2 - esrb t pegi 12+.jpg', 'https://image.99dongshi.com/FmQwquT-RgeamQ2fllmPdWrPsdfe', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('185', '0', '2019-01-29 16:00:58', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('186', '0', '2019-01-29 20:32:01', null, '0', 'Q版女.png', 'https://image.99dongshi.com/FuWoaDl-6K28xT7ot1CKhy0bw_Aa', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('187', '0', '2019-01-30 09:29:12', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('188', '0', '2019-01-30 09:29:18', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('189', '0', '2019-01-30 09:29:45', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('190', '0', '2019-01-30 09:30:05', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('191', '0', '2019-01-30 09:30:13', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('192', '0', '2019-01-30 09:30:21', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('193', '0', '2019-01-30 09:30:42', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('194', '0', '2019-01-30 09:35:17', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('195', '0', '2019-01-30 09:35:50', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('196', '0', '2019-01-30 09:36:00', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('197', '0', '2019-01-30 09:36:13', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('198', '0', '2019-01-30 09:36:20', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('199', '0', '2019-01-30 09:36:30', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('200', '0', '2019-01-30 09:36:41', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('201', '0', '2019-01-30 09:36:51', null, '0', 'hot1.png', 'https://image.99dongshi.com/FosCZh0Hsj_ExQJ7qTg1pTrEP21g', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('202', '0', '2019-01-30 14:36:31', null, '0', 'Q版女.png', 'https://image.99dongshi.com/FuWoaDl-6K28xT7ot1CKhy0bw_Aa', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('203', '0', '2019-01-30 17:19:28', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('204', '0', '2019-01-30 17:23:47', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('205', '0', '2019-01-30 19:37:28', null, '0', '图层18.png', 'https://image.99dongshi.com/Fk5yppg_nwnVyiyuRXv63WHqoR03', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('206', '0', '2019-01-30 19:38:17', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('207', '0', '2019-01-30 19:38:43', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('208', '0', '2019-01-30 19:45:37', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('209', '0', '2019-01-30 19:46:00', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('210', '0', '2019-02-01 16:16:03', null, '0', 'Q版男.png', 'https://image.99dongshi.com/FqxwYYv_EOhuehmR1iHGOQ6VS2Lo', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('211', '0', '2019-02-01 16:45:59', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('212', '0', '2019-02-01 17:01:47', null, '0', 'Q版女.png', 'https://image.99dongshi.com/FuWoaDl-6K28xT7ot1CKhy0bw_Aa', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('213', '0', '2019-02-01 17:11:48', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('214', '0', '2019-02-01 17:13:03', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('215', '0', '2019-02-01 20:32:34', null, '0', 'Q版女.png', 'https://image.99dongshi.com/FuWoaDl-6K28xT7ot1CKhy0bw_Aa', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('216', '0', '2019-02-03 14:17:12', null, '0', 'okok.png', 'https://image.99dongshi.com/FrCZIeQTPj2vYxRYhgfcgYP30I5A', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('217', '0', '2019-02-03 14:18:43', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('218', '0', '2019-02-03 14:36:59', null, '0', '摇钱树.png', 'https://image.99dongshi.com/Fq0McM_cwG8JyxAuX_wO65orGujn', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('219', '0', '2019-02-03 14:37:17', null, '0', '摇钱树.png', 'https://image.99dongshi.com/Fq0McM_cwG8JyxAuX_wO65orGujn', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('220', '0', '2019-02-03 14:37:28', null, '0', '图层18.png', 'https://image.99dongshi.com/Fk5yppg_nwnVyiyuRXv63WHqoR03', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('221', '0', '2019-02-03 14:37:40', null, '0', '摇钱树.png', 'https://image.99dongshi.com/Fq0McM_cwG8JyxAuX_wO65orGujn', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('222', '0', '2019-02-03 14:38:45', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('223', '0', '2019-02-03 14:40:05', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('224', '0', '2019-02-03 14:40:30', null, '0', 'okok.png', 'https://image.99dongshi.com/FrCZIeQTPj2vYxRYhgfcgYP30I5A', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('225', '0', '2019-02-03 17:09:11', null, '0', '图层18.png', 'https://image.99dongshi.com/Fk5yppg_nwnVyiyuRXv63WHqoR03', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('226', '0', '2019-02-03 17:21:49', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('227', '0', '2019-02-03 17:22:01', null, '0', '图层18.png', 'https://image.99dongshi.com/Fk5yppg_nwnVyiyuRXv63WHqoR03', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('228', '0', '2019-02-03 17:22:16', null, '0', 'okok.png', 'https://image.99dongshi.com/FrCZIeQTPj2vYxRYhgfcgYP30I5A', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('229', '0', '2019-02-03 17:22:28', null, '0', 'okok.png', 'https://image.99dongshi.com/FrCZIeQTPj2vYxRYhgfcgYP30I5A', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('230', '0', '2019-02-03 17:22:38', null, '0', '摇钱树.png', 'https://image.99dongshi.com/Fq0McM_cwG8JyxAuX_wO65orGujn', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('231', '0', '2019-02-03 19:00:50', null, '0', '百人赌场_03.jpg', 'https://image.99dongshi.com/Fmm_p3JOe4C7kH_E3yT4ls6R8pYZ', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('232', '0', '2019-02-03 19:00:56', null, '0', '百人赌场_06.jpg', 'https://image.99dongshi.com/Fkx-sqLr9CuZci4O6k6PplCzAwmM', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('233', '0', '2019-02-03 19:01:07', null, '0', '百人赌场_08.png', 'https://image.99dongshi.com/FisGknBO4ZS-6wTmClVLl8vn4Mgh', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('234', '0', '2019-02-03 19:01:16', null, '0', '百人赌场_10.png', 'https://image.99dongshi.com/FrStESKOmxkqgag9LZ34MoisdlE3', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('235', '0', '2019-02-03 19:03:27', null, '0', '大赢家.png', 'https://image.99dongshi.com/FjwC5wb4_1SiXVFcK7Y1mwkJV8WZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('236', '0', '2019-02-07 16:02:00', null, '0', 'okok.png', 'https://image.99dongshi.com/FrCZIeQTPj2vYxRYhgfcgYP30I5A', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('237', '0', '2019-02-07 16:02:39', null, '0', 'icon_bairenchang.png', 'https://image.99dongshi.com/FsR9_zdE9SZ3QNnD7AmzVgmA7H__', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('238', '0', '2019-02-07 16:02:54', null, '0', 'icon_buyu.png', 'https://image.99dongshi.com/FuAEZOCkUFhSQWS1_0LrllS-sAV4', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('239', '0', '2019-02-07 16:03:05', null, '0', 'icon_fangka.png', 'https://image.99dongshi.com/Fhp5_vDYLaag2zD5mMnd6Zvd8103', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('240', '0', '2019-02-07 16:03:15', null, '0', 'icon_laohuji.png', 'https://image.99dongshi.com/Fi4re-rUgTRLF2NLWKLrdWASQE6w', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('241', '0', '2019-02-07 16:04:08', null, '0', 'icon_xiuxianjingji.png', 'https://image.99dongshi.com/FufWCtWZ5gQUgo-h1tr0weUxRWdD', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('242', '0', '2019-02-09 16:52:31', null, '0', 'tit1.png', 'https://image.99dongshi.com/FqN_AaI3xHe5gZPgA84oKnYi8pXR', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('243', '0', '2019-02-09 16:52:50', null, '0', 'tit2.png', 'https://image.99dongshi.com/FpaRl1KlG2Z6lla-HGnB5NrX7-To', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('244', '0', '2019-02-09 16:53:02', null, '0', 'tit3.png', 'https://image.99dongshi.com/Fs5l-JmJnsdQM1LqMck5Hmw9uxIB', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('245', '0', '2019-02-09 16:53:10', null, '0', 'tit4.png', 'https://image.99dongshi.com/FtdKR9P5UZGfdzDoCA0mGrOmNfHP', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('246', '0', '2019-02-09 16:53:16', null, '0', 'tit5.png', 'https://image.99dongshi.com/FulgpjMNxibewwsX5v9G_Iwq6TqW', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('247', '0', '2019-02-11 17:25:17', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('248', '0', '2019-02-11 17:25:31', null, '0', 'tomb raider underworld - 2 - esrb t pegi 12+.jps', 'https://image.99dongshi.com/FhupuK1lDsrGUUzqZqIw4LVG3Ryw', 'image/jps');
INSERT INTO `webhome_enclosure` VALUES ('249', '0', '2019-02-15 16:25:56', null, '0', 'icon_fangka.png', 'https://image.99dongshi.com/Fhp5_vDYLaag2zD5mMnd6Zvd8103', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('250', '0', '2019-02-16 21:01:53', null, '0', '2_10.jpg', 'https://image.99dongshi.com/Futh5AQO8nUNxZ2B3qiprmOIKx7p', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('251', '0', '2019-02-16 21:02:00', null, '0', '2_12.jpg', 'https://image.99dongshi.com/Fjl8sV_6J5vfCJW_iiO66gYJmjFk', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('252', '0', '2019-02-16 21:02:11', null, '0', '2_14.jpg', 'https://image.99dongshi.com/Fj7QA2oUNnPFTA3Cy0dVIOwNEuKv', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('253', '0', '2019-02-16 21:02:20', null, '0', '2_19.jpg', 'https://image.99dongshi.com/FvzTbVtcfrn-e5hNzWLAKnKGWyM-', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('254', '0', '2019-02-16 21:02:31', null, '0', '2_22.jpg', 'https://image.99dongshi.com/FiUxM1ODbamvwntwln7Z0dwWr95v', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('255', '0', '2019-02-16 21:02:41', null, '0', '2_23.jpg', 'https://image.99dongshi.com/Fk_7x_53tzKDG8YjqQM7tzA2rRty', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('256', '0', '2019-02-16 21:03:53', null, '0', '1.png', 'https://image.99dongshi.com/FhwSHg1CPiTZzGVAPXPh8cmBGeEz', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('257', '0', '2019-02-16 21:04:03', null, '0', '2.png', 'https://image.99dongshi.com/Fr3NXU109K0SdijURa68wZZdihW9', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('258', '0', '2019-02-16 21:04:13', null, '0', '3.png', 'https://image.99dongshi.com/FuFdyCy8OsVlrBmBwgP0Uux1Hm1t', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('259', '0', '2019-02-16 21:04:27', null, '0', '3.png', 'https://image.99dongshi.com/FuFdyCy8OsVlrBmBwgP0Uux1Hm1t', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('260', '0', '2019-02-16 21:04:39', null, '0', '4.png', 'https://image.99dongshi.com/Fh7TTIiQsW3CEtZQb_Rd39jLUM3j', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('261', '0', '2019-02-16 21:04:51', null, '0', '5.png', 'https://image.99dongshi.com/FgGMGrsjmzy7JMi71hqqnI_AXVIA', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('262', '0', '2019-02-16 21:05:04', null, '0', '9.png', 'https://image.99dongshi.com/FqBYxbAAPvi1FIaAl4eSclkRDIFT', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('263', '0', '2019-02-19 14:34:20', null, '0', 'icon_suijipipei.png', 'https://image.99dongshi.com/FlEJ8O5DKABo3w50JEQGGye8T2wG', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('264', '0', '2019-02-19 15:03:49', null, '0', 'icon_bairenchang.png', 'https://image.99dongshi.com/FsR9_zdE9SZ3QNnD7AmzVgmA7H__', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('265', '0', '2019-03-02 15:09:47', null, '0', '未标题-4.png', 'https://image.99dongshi.com/FvS-pJSrsdQ0W2Lu1g4cCEskb7YK', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('266', '0', '2019-03-02 15:11:15', null, '0', '未标题-4.png', 'https://image.99dongshi.com/FvS-pJSrsdQ0W2Lu1g4cCEskb7YK', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('267', '0', '2019-03-02 15:11:42', null, '0', '未标题-4.png', 'https://image.99dongshi.com/FvS-pJSrsdQ0W2Lu1g4cCEskb7YK', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('268', '0', '2019-03-02 18:51:22', null, '0', '未标题-1.jpg', 'https://image.99dongshi.com/FgWg9EYx11MlY9irlQbJyksKep9y', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('269', '0', '2019-03-02 20:52:06', null, '0', 'cl.png', 'https://image.99dongshi.com/FtMDTqyBmf2wLWr4dTisN1vudZNW', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('270', '0', '2019-03-04 10:16:01', null, '0', 'icon_buyu_haiwang.png', 'https://image.99dongshi.com/FkhGX1mVO8MfOc2N8oycmrvpKuw3', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('271', '0', '2019-03-04 10:23:26', null, '0', 'icon_buyu_buyu.png', 'https://image.99dongshi.com/FoCGBHV-7WT2xdHjWKkeqqeEor8P', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('272', '0', '2019-03-04 10:24:01', null, '0', 'icon_buyu_haiwang.png', 'https://image.99dongshi.com/FkhGX1mVO8MfOc2N8oycmrvpKuw3', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('273', '0', '2019-03-04 10:24:26', null, '0', 'icon_buyu_haiwang.png', 'https://image.99dongshi.com/FkhGX1mVO8MfOc2N8oycmrvpKuw3', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('274', '0', '2019-03-04 10:24:44', null, '0', 'icon_buyu_jinshayinsha.png', 'https://image.99dongshi.com/FhFtWAKkgfu1ijFsH6rjuVhgYsJ0', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('275', '0', '2019-03-04 10:27:47', null, '0', 'icon_bairen_baijia.png', 'https://image.99dongshi.com/FnYidlEtm_lysRW76EjHsEovBdTI', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('276', '0', '2019-03-04 10:28:03', null, '0', 'icon_bairen_shisanshui.png', 'https://image.99dongshi.com/FrHe-XeJxBBDf0Rxzfnrs1Abpw3k', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('277', '0', '2019-03-04 10:30:20', null, '0', 'icon_laohuji_ailisi.png', 'https://image.99dongshi.com/Fq6Qxx6hb-l3_wzfK0DlqBY8evD5', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('278', '0', '2019-03-04 10:31:33', null, '0', 'icon_laohuji_alaikesi.png', 'https://image.99dongshi.com/FsaKDSdBoPM9O1uLbIciurEI0FaM', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('279', '0', '2019-03-04 10:32:42', null, '0', 'icon_laohuji_guaiji.png', 'https://image.99dongshi.com/Fpn3MTPmfQbqHFFSjHr6LMW7bhCy', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('280', '0', '2019-03-04 10:33:15', null, '0', 'icon_laohuji_haidibaozang.png', 'https://image.99dongshi.com/FhzzKUl_1yXwmlP1iaNzoOKxfQoS', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('281', '0', '2019-03-04 10:33:26', null, '0', 'icon_laohuji_haidibaozang.png', 'https://image.99dongshi.com/FhzzKUl_1yXwmlP1iaNzoOKxfQoS', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('282', '0', '2019-03-04 10:33:39', null, '0', 'icon_laohuji_jinpingmei.png', 'https://image.99dongshi.com/FncgXFy0qmBGq66jqH7AyjzxlRz7', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('283', '0', '2019-03-04 10:33:45', null, '0', 'icon_laohuji_jinpingmei.png', 'https://image.99dongshi.com/FncgXFy0qmBGq66jqH7AyjzxlRz7', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('284', '0', '2019-03-04 10:34:22', null, '0', 'icon_laohuji_luobinghan.png', 'https://image.99dongshi.com/FpRal8Lfq-DscSdKSmzOjXv3ES2i', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('285', '0', '2019-03-04 10:35:17', null, '0', 'icon_laohuji_xiyouji.png', 'https://image.99dongshi.com/Fg8jUoor9LxW1_SiAT8QBM9KRPvI', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('286', '0', '2019-03-04 10:35:40', null, '0', 'icon_laohuji_xibu.png', 'https://image.99dongshi.com/FpxRtUjVsaXjGbganxaFuKkGG_nV', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('287', '0', '2019-03-04 10:36:42', null, '0', 'icon_laohuji_yatelan.png', 'https://image.99dongshi.com/FlmaR5X7ZvnEUyJcxyVwzp64rUu7', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('288', '0', '2019-03-04 10:36:57', null, '0', 'icon_laohuji_yatelan.png', 'https://image.99dongshi.com/FlmaR5X7ZvnEUyJcxyVwzp64rUu7', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('289', '0', '2019-03-04 10:37:08', null, '0', 'icon_laohuji_yilisidan.png', 'https://image.99dongshi.com/Fk5Df3uSjR7yRGhYgkn_s-Ou6M6Y', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('290', '0', '2019-03-04 10:37:35', null, '0', 'icon_laohuji_weinisi.png', 'https://image.99dongshi.com/FsSqPeGv3DJMtw2R-2Vl13PdcmI1', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('291', '0', '2019-03-04 10:38:15', null, '0', 'icon_laohuji_shuihuzhuan.png', 'https://image.99dongshi.com/FhhuDABEnVKuNNrUjqsAiHtvXCTR', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('292', '0', '2019-03-04 10:38:48', null, '0', 'icon_laohuji_senlinwuhui.png', 'https://image.99dongshi.com/FsNuKuND_UnLIoh2gKCd9Nh_Ob7m', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('293', '0', '2019-03-04 10:40:46', null, '0', 'icon_qipai_doudizhu.png', 'https://image.99dongshi.com/FoUfrrNRzxFSqJh3qpWvX_JrC6JQ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('294', '0', '2019-03-04 10:41:20', null, '0', 'icon_qipai_longhudou.png', 'https://image.99dongshi.com/Fi8loYDt3kmo-nJ2GVrp8T4CrxBX', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('295', '0', '2019-03-04 10:41:45', null, '0', 'icon_qipai_longhudou.png', 'https://image.99dongshi.com/Fi8loYDt3kmo-nJ2GVrp8T4CrxBX', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('296', '0', '2019-03-04 10:42:06', null, '0', 'icon_qipai_zhajinhua.png', 'https://image.99dongshi.com/FnbXbr44rRtKN1VLo2X_4_Rz46Dp', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('297', '0', '2019-03-04 10:44:34', null, '0', 'icon_xiuxian_baowei.png', 'https://image.99dongshi.com/FgKnNODd4QXGTAI8N6OfayZGncmz', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('298', '0', '2019-03-04 10:53:32', null, '0', 'icon_xiuxian_lianhuan.png', 'https://image.99dongshi.com/Flyko7o3Q5TxK11Oq_dJdzYKheOT', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('299', '0', '2019-03-04 10:54:10', null, '0', 'icon_xiuxian_paoche.png', 'https://image.99dongshi.com/FqgwUZ47PDRQ-SabsPpGYy8t6r6H', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('300', '0', '2019-03-04 10:54:47', null, '0', 'icon_xiuxian_sanguo.png', 'https://image.99dongshi.com/FjW4N5-T9DhoCemT0An3trUH9nF6', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('301', '0', '2019-03-04 10:55:00', null, '0', 'icon_xiuxian_tanke.png', 'https://image.99dongshi.com/FiSC8j1B6w8sYPW0IXyPk-ZRw6X1', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('302', '0', '2019-03-04 10:55:14', null, '0', 'icon_xiuxian_xiangqi.png', 'https://image.99dongshi.com/Fl4Md_BtyiojNZ7DKUMkMJ193j3o', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('303', '0', '2019-03-04 16:24:39', null, '0', '捕鱼.jpg', 'https://image.99dongshi.com/FpOzs-fcVjPRZDz4mo-AriyzIidZ', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('304', '0', '2019-03-04 16:34:51', null, '0', '捕鱼.jpg', 'https://image.99dongshi.com/FpOzs-fcVjPRZDz4mo-AriyzIidZ', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('305', '0', '2019-03-04 16:34:58', null, '0', '海王2.jpg', 'https://image.99dongshi.com/FnU6bBRNcRUnr_whVqh4AXOqDeUj', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('306', '0', '2019-03-04 16:35:04', null, '0', '金鲨银鲨.jpg', 'https://image.99dongshi.com/FhlGoXOLQfF6BumDbvciYuHZ8iyw', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('307', '0', '2019-03-04 16:37:08', null, '0', '百家乐.jpg', 'https://image.99dongshi.com/FvcKgye2U2_cbyx2bj2aS_ZfptzF', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('308', '0', '2019-03-04 16:37:51', null, '0', '十三水.jpg', 'https://image.99dongshi.com/Fo5qvx5sEEfu-uZl3LFwSuO56J9q', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('309', '0', '2019-03-04 16:38:33', null, '0', '阿莱克斯塔萨.jpg', 'https://image.99dongshi.com/FqBzWr10Dqsf4N_DlnxOOjbGObYV', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('310', '0', '2019-03-04 16:39:12', null, '0', '爱丽丝仙境.jpg', 'https://image.99dongshi.com/Fq_feVWPcNcMExl8RkMX1w2VGe21', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('311', '0', '2019-03-04 16:40:10', null, '0', '古埃及宝藏.jpg', 'https://image.99dongshi.com/Fgyki3kwsQkp9h-Ai6uj0oh5LZKF', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('312', '0', '2019-03-04 16:40:38', null, '0', '海底宝藏.jpg', 'https://image.99dongshi.com/FiQsbiN2kEKtxtj4a5-kgo7ruTbw', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('313', '0', '2019-03-04 16:40:56', null, '0', '金瓶梅.jpg', 'https://image.99dongshi.com/FnqoB1tISjCQ86wu9rStPD4cr58S', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('314', '0', '2019-03-04 16:41:18', null, '0', '罗宾汉.jpg', 'https://image.99dongshi.com/FjJ0p2si2jJlaYaYFhCt6oCOs0_O', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('315', '0', '2019-03-04 16:41:33', null, '0', '森林舞会.jpg', 'https://image.99dongshi.com/Fr_SS2s3PHndwyotMRtjtTVrrAJI', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('316', '0', '2019-03-04 16:41:56', null, '0', '水浒传.jpg', 'https://image.99dongshi.com/FlJ9LHOUm4dilTZvol23Hq7K50eI', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('317', '0', '2019-03-04 16:43:10', null, '0', '威尼斯.jpg', 'https://image.99dongshi.com/FrHRNmQTGrUdNZ6P5cDTjRPWSsfV', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('318', '0', '2019-03-04 16:43:22', null, '0', '西部牛仔.jpg', 'https://image.99dongshi.com/FkG0nvyKOAu_O6Z5uLN5Rxe1lgFj', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('319', '0', '2019-03-04 16:43:45', null, '0', '西游记.jpg', 'https://image.99dongshi.com/Fki__d3wBRTq7vaTN9-V_bVODGhC', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('320', '0', '2019-03-04 16:43:57', null, '0', '亚特兰蒂斯传奇.jpg', 'https://image.99dongshi.com/FhvYjFzreu8n7pDoeeq5-XcU5BFK', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('321', '0', '2019-03-04 16:44:28', null, '0', '伊势丹的追求.jpg', 'https://image.99dongshi.com/FnOu33VL3RgzcjdGmTYOVbkuFpMC', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('322', '0', '2019-03-04 16:47:24', null, '0', '斗地主.jpg', 'https://image.99dongshi.com/Fgr6IUhceQq4jmSMkfjIDFfwnGce', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('323', '0', '2019-03-04 16:48:38', null, '0', '斗地主.jpg', 'https://image.99dongshi.com/Fgr6IUhceQq4jmSMkfjIDFfwnGce', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('324', '0', '2019-03-04 16:50:16', null, '0', '龙虎斗.jpg', 'https://image.99dongshi.com/FuN-RCzenw3O3-NQOx1-B0nOqqw0', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('325', '0', '2019-03-04 16:51:38', null, '0', '炸金花.jpg', 'https://image.99dongshi.com/FkhQ3K0HHscs9babedlCbtOu3aCO', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('326', '0', '2019-03-04 16:53:26', null, '0', '斗地主1.jpg', 'https://image.99dongshi.com/FofoejMIbrDNoK7BKWY06R9VlMEq', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('327', '0', '2019-03-04 16:53:37', null, '0', '炸金花1.jpg', 'https://image.99dongshi.com/Fi3bVBVRvgnAEsbQ-Gp19cEe-iTW', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('328', '0', '2019-03-04 16:54:01', null, '0', '保卫萝卜.jpg', 'https://image.99dongshi.com/FrtszvB0ZCGttwev6Hy0DJIL9uhg', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('329', '0', '2019-03-04 16:54:36', null, '0', '保卫萝卜.jpg', 'https://image.99dongshi.com/FrtszvB0ZCGttwev6Hy0DJIL9uhg', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('330', '0', '2019-03-04 16:54:56', null, '0', '连环夺宝.jpg', 'https://image.99dongshi.com/FkLkIkSZ_mULLq1Ix0fhs6_RfX0_', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('331', '0', '2019-03-04 16:56:09', null, '0', '跑车联盟.jpg', 'https://image.99dongshi.com/FvklqTXujp3j6ZQjzv7esPXi1YBY', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('332', '0', '2019-03-04 16:56:36', null, '0', '三国群英传.jpg', 'https://image.99dongshi.com/FoGjoYCXfvLr8HiDa698KQIInyY_', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('333', '0', '2019-03-04 16:57:35', null, '0', '坦克大战.jpg', 'https://image.99dongshi.com/FuA1CueYQottMIdyM-FWKz7MkCmE', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('334', '0', '2019-03-04 16:57:50', null, '0', '象棋.jpg', 'https://image.99dongshi.com/FqUDpCHUuzb-12o0Indo2YPVypbm', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('335', '0', '2019-03-04 19:58:24', null, '0', '未标题-4.png', 'https://image.99dongshi.com/FvS-pJSrsdQ0W2Lu1g4cCEskb7YK', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('336', '0', '2019-03-05 13:56:40', null, '0', '捕鱼.png', 'https://image.99dongshi.com/Fr3TuSTg9AeP2BZh0JL9bmOldIMU', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('337', '0', '2019-03-05 13:56:47', null, '0', '海王2.png', 'https://image.99dongshi.com/FlokOAo_tU8V5ORTxSeygrV5UO-C', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('338', '0', '2019-03-05 13:57:00', null, '0', '金鲨银鲨.png', 'https://image.99dongshi.com/FmyyXnFvYQzv4e6K60eFAKGntOi9', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('339', '0', '2019-03-05 13:57:33', null, '0', '百家乐.png', 'https://image.99dongshi.com/Fnm-J2-mOwci9E0A6MYVUu2Z4XUW', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('340', '0', '2019-03-05 13:57:40', null, '0', '十三水.png', 'https://image.99dongshi.com/FjCNWH0qE5SSFl05T0rd4hB7VzAx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('341', '0', '2019-03-05 14:06:58', null, '0', '阿莱克斯塔萨.png', 'https://image.99dongshi.com/FlwMbrStYndjfWFenMw5ZFJQMKvb', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('342', '0', '2019-03-05 14:07:06', null, '0', '古埃及宝藏.png', 'https://image.99dongshi.com/Fp8krritbe1iu3iogN0xVydQKga7', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('343', '0', '2019-03-05 14:07:14', null, '0', '海底宝藏.png', 'https://image.99dongshi.com/FmCFgP2b00iddAAV1UXYYgMJ2Ltt', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('344', '0', '2019-03-05 14:07:20', null, '0', '金瓶梅.png', 'https://image.99dongshi.com/Fmn8kGY_4agnMHgMEXLsFAaFUET0', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('345', '0', '2019-03-05 14:07:28', null, '0', '罗宾汉.png', 'https://image.99dongshi.com/FuDt0etLkcYPGCygbEnm3bFl71Gh', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('346', '0', '2019-03-05 14:07:38', null, '0', '西游记.png', 'https://image.99dongshi.com/FqDt07G39mUlvOEwr7yOgkeMHJiF', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('347', '0', '2019-03-05 14:07:45', null, '0', '西部牛仔.png', 'https://image.99dongshi.com/Fl8_QTXKjnx5urBRQaFRV_h0kPYS', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('348', '0', '2019-03-05 14:07:52', null, '0', '亚特兰蒂斯传奇.png', 'https://image.99dongshi.com/FtUxlrzOBkVfdbN4OqNM1Giues3L', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('349', '0', '2019-03-05 14:08:00', null, '0', '伊利斯丹的追求.png', 'https://image.99dongshi.com/FgyoLi9oLm7onKJ8MGkEB1KeEBbf', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('350', '0', '2019-03-05 14:08:12', null, '0', '威尼斯.png', 'https://image.99dongshi.com/FtUvwYmEHGg7iC_gTaxNKO17Xzjg', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('351', '0', '2019-03-05 14:08:19', null, '0', '水浒传.png', 'https://image.99dongshi.com/FtR8yXfic3fG464JRRLJZQQNcbMM', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('352', '0', '2019-03-05 14:08:29', null, '0', '森林舞会.png', 'https://image.99dongshi.com/FpaCoxDTXMuj3Jb0QaTYLD4lkkKn', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('353', '0', '2019-03-05 14:08:45', null, '0', '斗地主.png', 'https://image.99dongshi.com/FisusC1zF157fo1n8ptrTw6DKpws', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('354', '0', '2019-03-05 14:08:50', null, '0', '龙虎.png', 'https://image.99dongshi.com/FqYbv9DeZsTro0Wh1V6mwamqUwba', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('355', '0', '2019-03-05 14:08:56', null, '0', '炸金花.png', 'https://image.99dongshi.com/Fof3M9YeJPOU0qtAA7BrTejDf9W6', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('356', '0', '2019-03-05 14:09:29', null, '0', '保卫萝卜.png', 'https://image.99dongshi.com/FmZY4f8Bhwhjzl74PGSJ-77kc910', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('357', '0', '2019-03-05 14:09:38', null, '0', '连环夺宝.png', 'https://image.99dongshi.com/Fs6kwD_JNhuLOGJBcjl8w-8EFrGU', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('358', '0', '2019-03-05 14:09:46', null, '0', '跑车联盟.png', 'https://image.99dongshi.com/FtkOkf2ae8ZEbhstxrwpGU5KfK5X', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('359', '0', '2019-03-05 14:09:53', null, '0', '三国群英传.png', 'https://image.99dongshi.com/FsVp38n6U5EYyvCAt2NDRn963kgz', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('360', '0', '2019-03-05 14:10:02', null, '0', '坦克大战.png', 'https://image.99dongshi.com/FtPgiSlVY7TKAZ7O9QkbNtIKkqK-', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('361', '0', '2019-03-05 14:10:08', null, '0', '象棋.png', 'https://image.99dongshi.com/FlW8ENGpRNNn_a8d8IXRSUUfPiVT', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('362', '0', '2019-03-05 14:10:38', null, '0', 'fangka_fangkadoudizhu.png', 'https://image.99dongshi.com/FhlJ-41JP30fhzEFoNlYHSow4BzR', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('363', '0', '2019-03-05 14:10:52', null, '0', 'fangka_zhajinhua.png', 'https://image.99dongshi.com/Fg5lvnPkD4oqCX9x8v4_yvnZgx9I', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('364', '0', '2019-03-05 15:35:49', null, '0', '轮播3.jpg', 'https://image.99dongshi.com/FiICxnyd0oL-KtbHFNtM2zklRitu', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('365', '0', '2019-03-05 15:37:05', null, '0', '轮播1.jpg', 'https://image.99dongshi.com/FvTt8zYFxGOm0ZvIQUTWNHMdr-hu', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('366', '0', '2019-03-05 15:39:56', null, '0', '轮播2.jpg', 'https://image.99dongshi.com/FkykoIxDust7uz3_qvoDfEVpXitv', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('367', '0', '2019-03-05 16:19:03', null, '0', '轮播4.jpg', 'https://image.99dongshi.com/FvnCxJzC8fVam3jiIaf7GYoaZsb5', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('368', '0', '2019-03-05 16:20:02', null, '0', '轮播5.jpg', 'https://image.99dongshi.com/Fns8K5kCxu7mUS4_4e_psXlxwr-G', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('369', '0', '2019-03-05 16:21:24', null, '0', '左.png', 'https://image.99dongshi.com/FrMwuHuvJzXKXG7m4tsfYGB5Ny2k', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('370', '0', '2019-03-05 16:22:39', null, '0', '右.png', 'https://image.99dongshi.com/Fmexbm-dvh41-p8lAKUoPH9NNqNU', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('371', '0', '2019-03-05 19:57:57', null, '0', '优惠图 (1).jpg', 'https://image.99dongshi.com/FiaGu0zrqlFKwhot23WvCOPo8m3q', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('372', '0', '2019-03-05 20:00:07', null, '0', '优惠图 (1).jpg', 'https://image.99dongshi.com/FiaGu0zrqlFKwhot23WvCOPo8m3q', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('373', '0', '2019-03-06 16:15:15', null, '0', '6.png', 'https://image.99dongshi.com/FiRQMvRrCj0YK6FLbHuai2quy8od', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('374', '0', '2019-03-06 16:15:21', null, '0', '5.png', 'https://image.99dongshi.com/Fm3Cezet1mXOlJ856ZTz1YqubItb', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('375', '0', '2019-03-06 16:15:33', null, '0', '5.png', 'https://image.99dongshi.com/Fm3Cezet1mXOlJ856ZTz1YqubItb', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('376', '0', '2019-03-06 16:15:45', null, '0', '6.png', 'https://image.99dongshi.com/FiRQMvRrCj0YK6FLbHuai2quy8od', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('377', '0', '2019-03-06 17:26:12', null, '0', '未标题-4.png', 'https://image.99dongshi.com/FvS-pJSrsdQ0W2Lu1g4cCEskb7YK', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('378', '0', '2019-03-06 17:26:38', null, '0', '未标题-4.png', 'https://image.99dongshi.com/FvS-pJSrsdQ0W2Lu1g4cCEskb7YK', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('379', '0', '2019-03-06 19:46:10', null, '0', 'left1.jpg', 'https://image.99dongshi.com/FkUNiCtHZiaDv2H4550cP6AqAajK', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('380', '0', '2019-03-06 19:46:28', null, '0', 'left2.jpg', 'https://image.99dongshi.com/FlC2pz3b0liajIKlbtnfL0-Ln0sq', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('381', '0', '2019-03-06 19:46:45', null, '0', 'left3.jpg', 'https://image.99dongshi.com/FoY_ZgZmXpZlpHrJhQkGilIQ5xIt', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('382', '0', '2019-03-06 19:46:55', null, '0', 'left4.jpg', 'https://image.99dongshi.com/FsnRdRrKrau0cquSMeZ-YOp11aJv', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('383', '0', '2019-03-06 19:56:56', null, '0', 'right1.jpg', 'https://image.99dongshi.com/FkoPzXq5wlgb8OlG96bV5MLNjDK3', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('384', '0', '2019-03-06 19:57:09', null, '0', 'right2.jpg', 'https://image.99dongshi.com/FiEQNSWR2WHJiwzZ3m70TcW3lYWu', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('385', '0', '2019-03-06 19:57:31', null, '0', 'right3.jpg', 'https://image.99dongshi.com/FqTjy_QFgb_eb2KrWcBatG9Gzgvg', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('386', '0', '2019-03-07 14:51:58', null, '0', '赌场.jpg', 'https://image.99dongshi.com/FqMTEXjeulaUZ4FZCR-BQ0lmbFne', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('387', '0', '2019-03-07 15:03:11', null, '0', '赌场.jpg', 'https://image.99dongshi.com/FqMTEXjeulaUZ4FZCR-BQ0lmbFne', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('388', '0', '2019-03-07 15:21:27', null, '0', 'app.jpg', 'https://image.99dongshi.com/FsecbV6Y0vuRRnLcppskrTeJd2eE', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('389', '0', '2019-03-07 15:32:12', null, '0', 'app.jpg', 'https://image.99dongshi.com/FsecbV6Y0vuRRnLcppskrTeJd2eE', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('390', '0', '2019-03-07 15:53:20', null, '0', 'button_chongzhi_3.png', 'https://image.99dongshi.com/FoC1Sb5Ho14y5KHHqbW77NIzV04P', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('391', '0', '2019-03-07 15:53:43', null, '0', 'button_chongzhi_4.png', 'https://image.99dongshi.com/Fv2Ibn6xAVBfG7wjOCo6C0i19Mvi', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('392', '0', '2019-03-07 20:31:48', null, '0', 'img5.png', 'https://image.99dongshi.com/FgIvShi9jIlALX4qu0qgkqAqFi9o', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('393', '0', '2019-03-07 20:34:11', null, '0', 'img5.png', 'https://image.99dongshi.com/FgIvShi9jIlALX4qu0qgkqAqFi9o', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('394', '0', '2019-03-09 15:56:01', null, '0', '1920.jpg', 'https://image.99dongshi.com/Fng0JEcn62iJqKbLpvvKD0D7pYwr', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('395', '0', '2019-03-09 15:57:00', null, '0', '1920.jpg', 'https://image.99dongshi.com/Fng0JEcn62iJqKbLpvvKD0D7pYwr', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('396', '0', '2019-03-09 15:57:39', null, '0', '1920.jpg', 'https://image.99dongshi.com/Fng0JEcn62iJqKbLpvvKD0D7pYwr', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('397', '0', '2019-03-12 15:22:04', null, '0', 'banklist2.jpg', 'https://image.99dongshi.com/Fq1UXWXWPtRT1uEZAUImKQfeMA8s', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('398', '0', '2019-03-12 15:23:50', null, '0', 'banklist2.jpg', 'https://image.99dongshi.com/Fq1UXWXWPtRT1uEZAUImKQfeMA8s', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('399', '0', '2019-03-12 15:25:50', null, '0', 'banklist2.jpg', 'https://image.99dongshi.com/Fq1UXWXWPtRT1uEZAUImKQfeMA8s', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('400', '0', '2019-03-12 16:29:57', null, '0', 'do-name.png', 'https://image.99dongshi.com/Fj421UupCGNQ3TAhxOgn0q64Yhy9', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('401', '0', '2019-03-14 21:11:16', null, '0', 'app-tea_01.jpg', 'https://image.99dongshi.com/Fo4MsruqvWJ01kTuQpOzPFHPWIcJ', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('402', '0', '2019-03-14 21:11:25', null, '0', 'app-tea_02.jpg', 'https://image.99dongshi.com/Fl8zHbVi7J_Qmg-V5TSP-JDPMo4h', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('403', '0', '2019-03-14 21:11:37', null, '0', 'app-tea_03.jpg', 'https://image.99dongshi.com/FjSDvUX6WH97LZv_MuCpl1otAQ39', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('404', '0', '2019-03-14 21:11:52', null, '0', 'app-tea_04.jpg', 'https://image.99dongshi.com/FmBPIvTVMseUtc6VGIGqOAeO9VV5', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('405', '0', '2019-03-15 13:37:49', null, '0', 'logo.gif', 'https://image.99dongshi.com/FiapHAErVxDrhHVZewWVQgCmSsHO', 'image/gif');
INSERT INTO `webhome_enclosure` VALUES ('406', '0', '2019-03-15 13:41:38', null, '0', '预合成 2_1.gif', 'https://image.99dongshi.com/FmiXnJoCEc_SXvNNHnmw1846NPS6', 'image/gif');
INSERT INTO `webhome_enclosure` VALUES ('407', '0', '2019-03-15 15:08:05', null, '0', '对联1_05.png', 'https://image.99dongshi.com/FvuD-ldDUr8uRvgsHWPHn_lzlaOE', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('408', '0', '2019-03-15 15:08:15', null, '0', '对联1_08.png', 'https://image.99dongshi.com/Fqwly3kVvRN8dYhZ90rm07d5KSUZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('409', '0', '2019-03-15 15:13:44', null, '0', '对联1_11.png', 'https://image.99dongshi.com/FqFEo9eb-h4-G4P4fzz5ip_mqbnZ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('410', '0', '2019-03-15 15:13:56', null, '0', '对联1_14.png', 'https://image.99dongshi.com/Fsk3PatklkHDZd0WVExFXOgcwxLG', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('411', '0', '2019-03-15 16:49:21', null, '0', '对联2_05.png', 'https://image.99dongshi.com/FimIhGIOe4GgbQLA4s2TtePpIeiK', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('412', '0', '2019-03-15 16:49:36', null, '0', '对联2_08.png', 'https://image.99dongshi.com/FjhF0wAfUIkUmp0S3qyx-JSOt0u8', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('413', '0', '2019-03-15 16:49:45', null, '0', '对联2_11.png', 'https://image.99dongshi.com/FtHPY9u_vVNw18ufl7gaZXAcp6RG', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('415', '0', '2019-03-23 09:28:12', null, '0', '复制.png', 'https://image.99dongshi.com/FlaRIXA7TKFYjwhlA2pVJY3SvoXS', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('422', '0', '2019-03-23 09:29:28', null, '0', '立即下载游戏.png', 'https://image.99dongshi.com/FsmnefA74XE0RJaNkECt4JzFg8uv', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('429', '0', '2019-03-28 15:07:49', null, '0', 'icon_bairen_bjl.png', 'https://image.99dongshi.com/FhnbK9H884tTasXokhfcZNwSyJNn', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('436', '0', '2019-03-28 15:19:06', null, '0', 'icon_bairen_bjl_small.png', 'https://image.99dongshi.com/FkaeDAguul_QQGuC4unvzLnQHi7V', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('443', '0', '2019-03-28 15:20:11', null, '0', 'icon_bairen_lhd_small.png', 'https://image.99dongshi.com/Fpvide3VMc3s2cV0pt9Wsvc68125', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('450', '0', '2019-03-28 15:22:38', null, '0', 'icon_fangka_qznn.png', 'https://image.99dongshi.com/FldCE5iM79DLUsBkBd7Cob9CwzLR', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('457', '0', '2019-03-28 15:23:30', null, '0', 'icon_bairen_pj_small.png', 'https://image.99dongshi.com/FrXsLcFEvB7feO3qL8Wq9uw22h5l', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('464', '0', '2019-03-28 15:24:18', null, '0', 'icon_bairen_zjh_small.png', 'https://image.99dongshi.com/FiJr4qTCBxCTrCbI_LMyH59fK50A', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('471', '0', '2019-03-28 15:29:00', null, '0', 'icon_laohuji_hdbz_small.png', 'https://image.99dongshi.com/FvU7f4tZetCSE21wk-nRNNw3ZLvD', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('478', '0', '2019-03-28 15:33:00', null, '0', 'icon_fangka_zjh_small.png', 'https://image.99dongshi.com/FmAOKnK3eK5KevpALSg_NxMAyxj9', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('485', '0', '2019-03-28 15:33:40', null, '0', 'icon_fangka_ebg_small.png', 'https://image.99dongshi.com/Fjl4hrrLyqcFjAQ71KJ-Xvm4I6e6', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('492', '0', '2019-03-28 15:34:31', null, '0', 'icon_fangka_qznn_small.png', 'https://image.99dongshi.com/Fujs3ccaVgjgX92b0H0-JuG9Bgqw', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('499', '0', '2019-03-28 15:35:21', null, '0', 'icon_pipei_tbnn_small.png', 'https://image.99dongshi.com/Fn7vdyBc78oZ01-3tcSHoIC8W9HI', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('506', '0', '2019-03-28 15:35:36', null, '0', 'icon_pipei_qznn_small.png', 'https://image.99dongshi.com/FoQlvdwq6zFjr-ilGsrvCVxUT4VE', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('513', '0', '2019-03-28 15:36:43', null, '0', 'icon_pipei_ddz_small.png', 'https://image.99dongshi.com/Fsyg1xInaxL3Gxdedrf7r51--OLy', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('520', '0', '2019-03-28 15:37:24', null, '0', 'icon_fangka_sss_small.png', 'https://image.99dongshi.com/Fs2yPU8b6pDMnO3uSM5a6GZZvCuq', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('527', '0', '2019-03-29 09:50:44', null, '0', 'guanggaotu_2.png', 'https://image.99dongshi.com/FiHFtAQLmWqjGCjIgQn3PmUOOqzB', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('534', '0', '2019-03-29 09:51:03', null, '0', 'guanggaotu_1.png', 'https://image.99dongshi.com/FrRCGHWMPgBNsP2sd2VyDyGUjFuR', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('541', '0', '2019-03-29 09:52:17', null, '0', 'guanggaotu_3.png', 'https://image.99dongshi.com/Fl9PKsN-gqBh25KeT1U_eqmDPEaI', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('548', '0', '2019-04-03 14:16:38', null, '0', '首存优惠.png', 'https://image.99dongshi.com/Fpv9Eh1-4OslLNQ-Ts5-GOSSZMRQ', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('555', '0', '2019-04-03 14:20:26', null, '0', '17.png', 'https://image.99dongshi.com/Fj9Cnn5vJbfHAFzirI0MhFQZYrPL', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('562', '0', '2019-04-03 14:31:46', null, '0', '棋牌亏损彩金1.png', 'https://image.99dongshi.com/FokSMqtqkFUfK40iNc3DY6M4twSh', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('569', '0', '2019-04-03 14:41:45', null, '0', '棋牌游戏第二重.png', 'https://image.99dongshi.com/FiSHUBT5p6Z815VgrM57VQqz8Aoa', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('576', '0', '2019-04-03 14:42:53', null, '0', '棋牌游戏第三重.png', 'https://image.99dongshi.com/FjqA9d0hBWJ_CEYWMoU3TsY3fEKR', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('583', '0', '2019-04-03 14:43:55', null, '0', '棋牌游戏第四重.png', 'https://image.99dongshi.com/FsNRGjL3B40xwAvmAux_x86KXgKV', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('590', '0', '2019-04-03 14:45:16', null, '0', '棋牌游戏第五重.png', 'https://image.99dongshi.com/Fo98nm9c4B228Twmx_WFHXHIfEEO', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('597', '0', '2019-04-03 14:46:04', null, '0', '棋牌游戏第六重.png', 'https://image.99dongshi.com/FtPNWwmLJ9Gb7SpTHRFj-fKFwJt8', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('604', '0', '2019-04-03 14:47:37', null, '0', '捕鱼第一惠.png', 'https://image.99dongshi.com/Fp5bKjBwTbmKYKiBYyJehmbN7xQt', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('611', '0', '2019-04-03 14:48:23', null, '0', '捕鱼双重回馈1.png', 'https://image.99dongshi.com/Foz0c1dMWBMPBGRSpdaLbtslyceO', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('618', '0', '2019-04-03 14:59:39', null, '0', '捕鱼第二惠.png', 'https://image.99dongshi.com/Fuxu7ZUgl6cRy0r52GzAdggVPcAw', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('625', '0', '2019-04-03 15:00:01', null, '0', '捕鱼第三惠.png', 'https://image.99dongshi.com/FnX7b2Cmra4Qa6bko_QncLa30Kh2', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('632', '0', '2019-04-03 15:23:08', null, '0', '电子游艺第一惠.png', 'https://image.99dongshi.com/FtIPNZ_9Ij9jvVV6z-rsnzogPFBt', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('639', '0', '2019-04-03 15:23:59', null, '0', '电子游艺第二惠.png', 'https://image.99dongshi.com/FhSsn9XrLztS3xLOZ3eVhSTQyeV_', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('646', '0', '2019-04-03 15:25:12', null, '0', '电子游艺第三惠.png', 'https://image.99dongshi.com/FmYHdHzEFLo_EXZelNquKZ5n2wnx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('653', '0', '2019-04-03 15:27:02', null, '0', '电子游艺第四惠.png', 'https://image.99dongshi.com/FnAndoVzBnEydqhPpW-lYY0Q9pxD', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('660', '0', '2019-04-03 15:27:35', null, '0', '电子游艺第五惠.png', 'https://image.99dongshi.com/FqHfnNnfOctKz6YBrvtJhCC3pJUc', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('667', '0', '2019-04-03 15:30:51', null, '0', '棋牌神秘彩金.png', 'https://image.99dongshi.com/FiO8aNgmosG7akA8slqgS2QkoQx-', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('674', '0', '2019-04-03 15:31:38', null, '0', '信誉图片.png', 'https://image.99dongshi.com/FgHpkwhD9ne7IOd8YnUhqCEueuge', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('681', '0', '2019-04-06 20:30:06', null, '0', '轮播1.jpg', 'https://image.99dongshi.com/FhFHH9ppRBus1ySkG_6e77ZN8cye', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('689', '0', '2019-04-09 00:03:07', null, '0', '未标题-2.png', 'https://image.99dongshi.com/Fsn0gGhRs6svGKrUQdgb6C-Fm6en', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('696', '0', '2019-04-09 00:03:23', null, '0', '未标题-21.png', 'https://image.99dongshi.com/FsT2UVL51fTin7lISS1V-0o9A-m9', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('703', '0', '2019-04-09 00:03:36', null, '0', '未标题-35.png', 'https://image.99dongshi.com/FpTkNId7HClSY1oY9mS_lCUQWr7s', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('710', '0', '2019-04-10 11:01:22', null, '0', 'icon_br_bjl.png', 'https://image.99dongshi.com/Fm5beRKzGk1Ktesoy8kPR-KfJX-r', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('717', '0', '2019-04-10 18:34:23', null, '0', '1.png', 'https://image.99dongshi.com/FuCqO8TaONW1IEpqrtrrn-iZKdhX', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('724', '0', '2019-04-10 18:34:39', null, '0', '2.png', 'https://image.99dongshi.com/FsxGqEvBYSBmPBUZKzaaPfqptg_z', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('731', '0', '2019-04-10 18:42:37', null, '0', 'okok.png', 'https://image.99dongshi.com/Fm_0vY-dXSQGOqZn4vl4dHtyaSQF', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('738', '0', '2019-04-11 21:08:45', null, '0', 'icon_gg_1.png', 'https://image.99dongshi.com/FlMzm9vFRdSRw6_lRwKPKAMeKMlh', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('745', '0', '2019-04-11 21:09:06', null, '0', 'icon_gg_2.png', 'https://image.99dongshi.com/FolJnggLG1ihHBY4OZ8wpc1Z4lo-', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('752', '0', '2019-04-11 21:09:36', null, '0', 'icon_gg_3.png', 'https://image.99dongshi.com/FlWNzql2Pap3VnqrguwEpJvu-3b-', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('759', '0', '2019-04-17 14:53:01', null, '0', 'ab轮播3.png', 'https://image.99dongshi.com/FumurXaWO7QfYtWTq6554nO-S8GG', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('766', '0', '2019-04-17 14:53:47', null, '0', 'ab轮播.png', 'https://image.99dongshi.com/FoTQUCfLzmaJZP5_yaoYXAlJ3Fnx', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('773', '0', '2019-04-17 14:55:49', null, '0', 'ab轮播2.png', 'https://image.99dongshi.com/FtHfQN_GNw6DL4Znp55CCRnpWz2R', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('780', '0', '2019-04-18 14:20:39', null, '0', '微信 (1).jpg', 'https://image.99dongshi.com/FpeGqy9pJ6LN77bK5iG250KyeGHl', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('787', '0', '2019-04-18 14:20:50', null, '0', '微信 (2).jpg', 'https://image.99dongshi.com/FlGU-5dZAenboCSw05Vy7w5yv-hV', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('794', '0', '2019-04-18 14:21:05', null, '0', '窝窝扫码.png', 'https://image.99dongshi.com/FvJ0D3MHj4P6uJ9qcu0RvwS59VD1', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('801', '0', '2019-04-19 13:58:13', null, '0', 'photo_2019-04-19_13-53-02.jpg', 'https://image.99dongshi.com/Fk91ID0fSdXmCva8QbAfehGw2ADx', 'image/jpeg');
INSERT INTO `webhome_enclosure` VALUES ('808', '0', '2019-04-19 16:12:25', null, '0', '标题A.png', 'https://image.99dongshi.com/FqqY_KRs1mmVWAstHVSyTK7L4klT', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('815', '0', '2019-04-19 17:15:18', null, '0', '标题A(1).png', 'https://image.99dongshi.com/FkV3CvDXNfZ31oqNlIUCRjYG_1g0', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('822', '0', '2019-04-19 17:36:38', null, '0', '标题c.png', 'https://image.99dongshi.com/FqKPTqyFx9r2JQOyDxk_z53ljVxV', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('829', '0', '2019-04-19 17:36:58', null, '0', '标题b.png', 'https://image.99dongshi.com/FsHTev1YutVU2_OW6_9g-ljuAhP8', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('836', '0', '2019-04-19 17:57:19', null, '0', '下载APP.png', 'https://image.99dongshi.com/FsHTev1YutVU2_OW6_9g-ljuAhP8', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('843', '0', '2019-04-19 17:57:47', null, '0', '朋友圈.png', 'https://image.99dongshi.com/FkV3CvDXNfZ31oqNlIUCRjYG_1g0', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('850', '0', '2019-04-19 19:24:26', null, '0', '标题c(2).png', 'https://image.99dongshi.com/FpbduRNuGprc3oWTkiReGRsBVpOU', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('857', '0', '2019-04-21 12:13:33', null, '0', 'QQ图片20190421121205.gif', 'https://image.99dongshi.com/Fmpz9MQ10K6IRyBFb-TdOnTwBOpY', 'image/gif');
INSERT INTO `webhome_enclosure` VALUES ('864', '0', '2019-04-21 13:05:20', null, '0', '内页.gif', 'https://image.99dongshi.com/Fmpz9MQ10K6IRyBFb-TdOnTwBOpY', 'image/gif');
INSERT INTO `webhome_enclosure` VALUES ('871', '0', '2019-04-21 13:11:13', null, '0', 'QQ图片20190421130435.png', 'https://image.99dongshi.com/FnhkEPhWUVih5l78nxTZ4mWi-RVN', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('878', '0', '2019-04-21 13:11:28', null, '0', 'QQ图片20190421130417.png', 'https://image.99dongshi.com/Fj3wz_jXxKNckyt4xLS5xbhWZnh_', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('885', '0', '2019-04-21 20:38:19', null, '0', '首存.png', 'https://image.99dongshi.com/FpbduRNuGprc3oWTkiReGRsBVpOU', 'image/png');
INSERT INTO `webhome_enclosure` VALUES ('892', '0', '2019-04-21 20:38:20', null, '0', '首存1.jpg', 'https://image.99dongshi.com/Fv_60nJ7x2yIuZRtEkLg99warnG3', 'image/jpeg');

-- ----------------------------
-- Table structure for webhome_friendship
-- ----------------------------
DROP TABLE IF EXISTS `webhome_friendship`;
CREATE TABLE `webhome_friendship` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '链接',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `color` varchar(22) DEFAULT '' COMMENT '颜色',
  `type` int(1) NOT NULL COMMENT '跳转类型(1,路径跳转2.内容页跳转)',
  `icon` bigint(20) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='友情链接管理';

-- ----------------------------
-- Records of webhome_friendship
-- ----------------------------
INSERT INTO `webhome_friendship` VALUES ('40', '0', '2019-01-22 17:01:32', '2019-03-13 20:00:12', '0', '关于我们', '1', '1', 'red', '2', '83');
INSERT INTO `webhome_friendship` VALUES ('41', '0', '2019-01-30 16:01:24', '2019-03-14 11:35:40', '0', '常见问题', '1', '2', '1', '2', '0');
INSERT INTO `webhome_friendship` VALUES ('42', '0', '2019-02-03 14:49:55', '2019-03-13 15:46:23', '0', '存款帮助', '1', '3', '', '2', '0');
INSERT INTO `webhome_friendship` VALUES ('43', '0', '2019-02-03 14:50:08', '2019-03-14 11:33:43', '0', '取款帮助', '1', '4', '', '2', '0');
INSERT INTO `webhome_friendship` VALUES ('44', '0', '2019-03-12 16:37:39', '2019-03-14 11:33:52', '0', '代理加盟', '1', '5', '', '2', '0');
INSERT INTO `webhome_friendship` VALUES ('45', '0', '2019-03-12 16:37:56', '2019-03-14 11:33:59', '0', '联络我们', '1', '6', '', '2', '0');

-- ----------------------------
-- Table structure for webhome_friendship_template
-- ----------------------------
DROP TABLE IF EXISTS `webhome_friendship_template`;
CREATE TABLE `webhome_friendship_template` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT,
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `friendship_id` bigint(22) NOT NULL COMMENT '友情链接ID',
  `content` text NOT NULL COMMENT '内容',
  `icon` bigint(22) DEFAULT NULL COMMENT '图标ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='友情链接内容';

-- ----------------------------
-- Records of webhome_friendship_template
-- ----------------------------
INSERT INTO `webhome_friendship_template` VALUES ('46', '0', '2019-03-13 16:59:53', '2019-03-14 11:42:42', '0', '42', ' <h2>线上存款</h2>\n\n        <h4>电脑版网银、储蓄卡、银联、快捷在线支付（立即到账）网银快捷支付(强烈推荐)</h4>\n        <p>※ 跨行汇款或存款金额低于100000元建议使用在线支付，无需手续费，支付完成，立即火速到账。</p>\n        <p>※ 支付成功后，请等待几秒钟，提示「支付成功」按确认键后再关闭支付窗口，款项会即时入账。</p>\n\n        <h4>支付宝支付专用通道：支付宝→【在线支付、充值秒到账】（支付宝支付直通车）</h4>\n        <p>※温馨提示：这里支持支付宝，建议小额支付、大额支付可选择其它支付方式或分多次支付。</p>\n        <p>※支付成功后，请等待几秒钟，提示「支付成功」按确认键后再关闭支付窗口，款项会即时入账。</p>\n        <h4>微信支付专用通道：微信→【在线支付、充值秒到账】（微信支付直通车）</h4>\n        <p>※温馨提示：这里支持微信，建议小额支付、大额支付可选择其它支付方式或分多次支付。</p>\n        <p>※支付成功后，请等待几秒钟，提示「支付成功」按确认键后再关闭支付窗口，款项会即时入账。</p>\n        <h4>银行卡转账、支付宝、财付通、ATM、手机银行转账（推荐）</h4>\n        <p>温馨提示：独家支持银行转账、支付宝、财付通、手机银行转账、ATM存款、全程担保入款./p>\n        <p>收款帐户不定期更新，请在每次存款前，点击“银行卡转账”页面查询最新收款账号。</p>\n        <p>※※※诺存款到过期的账户导致财务无法查收，会员请自行承担损失。望珍惜！※※※</p>\n        <h4>银联无卡快捷支付专用通道：银联快捷支付→【快捷方便秒到账！】（银联扫码支付直通车）</h4>\n        <p>※支付成功后，请等待几秒钟，提示「支付成功」按确认键后再关闭支付窗口，款项会即时入账。</p>\n        <h4>银联APP扫码支付 →【独家重磅推出，扫一扫秒到账，大额无忧】(APP银联扫码专线)</h4>\n        <p>独家支持银联app扫码支付，支持所有银行，支付完成秒到账，手机银行扫一扫，轻松入款，大额无忧！</p>\n        <h4>QQ支付专用通道：QQ扫码支付→【安全可靠到帐速度最快.即时到账】（QQ扫码支付直通车）</h4>\n        <p>※支付成功后，请等待几秒钟，提示「支付成功」按确认键后再关闭支付窗口，款项会即时入账。</p>\n        <h4>京东钱包支付专用通道：京东扫码支付→【最新方式全网独家！】（京东扫码支付直通车）</h4>\n        <p>※支付成功后，请等待几秒钟，提示「支付成功」按确认键后再关闭支付窗口，款项会即时入账。</p>\n        <h4>游戏点卡、手机充值卡【点卡支付】【即时到账】</h4>\n        <p>特别声明：通过游戏点卡、手机充值卡支付系统默认15倍的打码量，可以参与每日返水。】</p>\n        <p>温馨提示：这里支持手机充值卡、各类游戏点卡多种面额在线支付（输入卡密、立即到账）</p>\n        <p>※面额多少充值金额等于多少。入款时请选对相应面额，充值卡面额最低10元，敬请留意！</p>\n        <p>※支付成功后，请等待几秒钟，提示「支付成功」按确认键后再关闭支付窗口，款项会即时入账。</p>\n        <p>温馨提示：会员游戏时如遇线路拥挤无法正常开启，请登录线路中心【0880.com】查看更多备用网址。</p>\n        <p>※ 存款遇到问题？请联系24小时在线客服为您提供服务，谢谢！</p>', '400');
INSERT INTO `webhome_friendship_template` VALUES ('47', '0', '2019-03-13 20:18:16', '2019-03-14 13:36:38', '0', '40', '<h2>关于我们</h2>\n\n        <h4>娱乐 ． 创新 ． 合法</h4>\n        <p>AB棋牌目前拥有哥斯达黎加合法注册之娱乐公司 ，一切营业行为皆遵从哥斯特黎加政府的娱乐游戏条约。我们在日渐热络的网络市场中，不断求新求变，以傲人的创意团队开发各种游戏方式。为客户提供即时、 刺激、体贴的娱乐产品与高质量服务，是本公司创建AB棋牌的首要宗旨。</p>\n        <h4>产品及服务</h4>\n        <p>AB棋牌平台是国内知名的具有自主研发能力的游戏运营商。是一个大型综合网络休闲娱乐平台，以 专业、公正的科技背景满足各种娱乐需求，致力于通过三屏形式（电脑、电视、手机）为全球华人用户提 供多元化的休闲娱乐服务。在AB棋牌平台这个统一的平台中，各种游戏精彩纷呈，成千上万的用户在轻 松愉快的游戏环境中体会到健康的网络娱乐生活。</p>\n        <p>·棋牌游戏：传统游戏，休闲益智，专业竞技！捕鱼游戏：专业捕鱼平台，极具快感，炮轰全场</p>\n        <p>良好的商誉．安全、隐密的网络环境</p>\n\n        <p>在竞争激烈的网络市场中，AB棋牌向来是众多玩家一致的选择，除了因为多元化的娱乐产品使人流连 忘返、更因为高质量的服务以及AB棋牌长久以来的良好信誉在广大玩家群众之间建立了口碑。 我们的用心随处可见，并且获得了GEOTRUST的权威性国际认证，以确保网站活动的公平、 公正，所有会员数据均经加密处理，保障玩家隐私。AB棋牌以服务不打烊的精神，全天候24小时处理会员出入款的相关事宜，严格训练的客服团队，以专业、亲切的态度解决您对于网站、游戏的种种疑难杂症，让每位玩家有宾至如归的感觉！ AB棋牌以业界前所未见的各种优惠方式回馈我们的会员，绝对是玩家最明智的选择！</p>\n        <h4>受众分析</h4>\n        <p>2000年，全球网络娱乐游戏相关产业的产值第一次超过长久以来占据娱乐产业价值榜首的影视业，成为游戏产业发展的一个里程碑。游戏产业伴随计算机技术发展而更加活跃的互联网应用服务，已经成为全球及国内日益重视的朝阳产业。网络游戏更由于其使用简单、亲和力强、互动性强、竞技性强等特长，成为游戏界乃至整个网络产业的新亮点。投资界、游戏开发商、游戏运营商等业界纷纷看好这一市场，给了高度的关注。其影响力和用户群向全国各地呈辐射状扩散，迅速从一个地方性游戏站点提升为面向全国以及全球华人的大型游戏社区，无可争议地跻身世界优秀游戏网站之林，享有极高的知名度和相当的美誉度。</p>\n        <p>旺盛的人气：AB棋牌平台一直借助网络资源的优势消除用户群结构的地域性问题；客户遍及全国各地以及海外</p>', '83');
INSERT INTO `webhome_friendship_template` VALUES ('48', '1', '2019-03-13 20:19:34', '2019-03-13 20:19:34', '0', '42', '333333', '83');
INSERT INTO `webhome_friendship_template` VALUES ('49', '0', '2019-03-14 11:40:39', '2019-03-14 11:41:18', '0', '41', '<h2>一般问题 遊戲及投注問題 技術常見問題</h2>\n\n        <h4>1. AB棋牌实力怎样？</h4>\n        <p>您好，请参阅主页“关于我们”。</p>\n\n        <h4>2. 在贵公司进行游戏是否安全？</h4>\n        <p>您好，本公司系统绝对安全。我们决不泄漏客户的个人资料给任何商业机构。此外，我们亦要求有交易往来的银行，信用卡中转代理等严格保密客户的资料。所有的存款将视为贸易户口，并不会交给其它的人士进行</p>\n\n        <h4>3. 网上博彩是否合法？</h4>\n        <p>您好，有的国家或地区当地法律禁止博彩，在这种情况下，请您务必遵守当地法律，如有任何疑问，请寻求当地法律部门的意见。本公司不能亦不会接受任何人士违犯当地法律所引致之任何责任。</p>\n\n        <h4>4. 在AB棋牌进行投注是否有年龄限制？</h4>\n        <p>您好，是的，投注合法年龄必须年满18岁。</p>\n\n        <h4>5. 开户是否要填写真实姓名？</h4>\n        <p>您好，基于安全理由，提款时财务部会按照注册姓名进行审核，银行卡户名必须与注册姓名相同方可提款，所以请您在开户时填写的姓名必须与您提款的户名相同。</p>\n\n        <h4>6. 忘记密码怎么办？</h4>\n        <p>你可点击首页忘记密码功能，填写你的会员帐号与取款密码，即可取回你当初设定的密码。当你无法收取邮件时，你也可以联系24小时在线客服人员咨询协助取回你的账号密码。</p>\n\n        <h2> 遊戲及投注問題</h2>\n        <h4>1.AB棋牌有哪些游戏？</h4>\n        <p>您好，AB棋牌目前为您提供“运动博弈”最全的“真人娱乐”和“电子游艺”“彩票游戏”五大电子平台组合、额度无需转换。</p>\n\n        <h4>2. 我该如何为我的游戏账号充值？</h4>\n        <p>您好，公司目前提供多种方式入款，1.在线存款；2.银行划款，3.微信、支付宝、财付通、手机网银、点卡支付；请您点击线上存款选择其中一种方式入款，谢谢！（注：选择公司入款的客户，请您在每次入款前务必与我们联络确认汇款账号，如将款项存入已过期账号，导致公司无法查收，恕不负责，请您留意，谢谢！）</p>\n\n        <h4>3. 为什么在线支付成功后额度没有立即返到我的游戏帐号呢？</h4>\n        <p>您好，这是因为在线支付系统需要经过密锁切换及内部验证程序，而这也是我们的保安程序之一，所以请您不必担心，款项一定不会丢失。如果您在在线支付超过30分钟后仍然还未收到您的款项，请与我们客服联系处理。</p>\n\n        <h4>4. 当我在真人游戏赢了钱，我该如何提款？</h4>\n        <p>您好，请您点击“线上提款”输入您的提款密码，再按要求填写您要提取的金额，然后再点击确定。谢谢！</p>\n\n        <h4>5. 如何证明游戏结果是实时的？</h4>\n        <p>您好，您可以通过游戏的视频影像进行监查，视频内的画面均为现场娱乐城所播放的画面是同步的。我们确保所有游戏结果均为真实的实时结果。</p>\n\n        <h4>6. 我可以先浏览你们的游戏吗？</h4>\n        <p>您好，我们非常欢迎您浏览我们的游戏系统。当然，这是免费的。您只需在我们的网站进行注册后即可以登入浏览我们的游戏。</p>\n\n        <h4>7. 为什么观看六局就会断开连接？</h4>\n        <p>您好，基于两个原因：（1）为了您的账号安全，如果阁下在打开游戏后，又忘记关闭页面就离开了电脑，万一在这个时间被其他人使用投注，您的损失就很难控制了。（2）每时每刻都有非常多的客户上线进行游戏，为了让网速快而畅通，我们的系统就自动节省网络资源，照顾了在游戏之中的客户，所以观看六局就会断开连接。如因此给您带来不便，敬请见谅。</p>\n\n        <h4>8. 请问我在哪边可以找到游戏规则？</h4>\n        <p>在你未登入之前，你可以在各个游戏项目内，看见游戏规则的选项，清楚告知游戏的玩法、规则及派彩方式。 在游戏窗口中，也有\"规则\"选项，让您在享受游戏乐趣的同时，可以弹跳窗口随时提醒您游戏规则。</p>\n\n        <h4>9. 进行游戏对系统配置有要求么？</h4>\n        <p>您好，我们设计的网页将会提供新一代浏览器服务，提供更好的特点，让您能够享有更好的投注乐趣。我们希望您能使用Internet Explorer 6.0或者是以上的浏览器版本。</p>\n\n        <h4>10. 为什么系统会将我注销？</h4>\n        <p>您好，这是网页的保安程序之一，当您在一定的时间内没有使用有关的网页，那么有关的网页将不再活跃，并且无法浏览最新的页面。</p>\n\n        <h2>技術常見問題</h2>\n\n        <h4>最低的硬件系统要求是什么？</h4>\n        <p>1. 任何可以接上互联网的计算机。</p>\n        <p>2. SVGA显示适配器–最少要1366x768像素256色彩以上。</p>\n        <p>3. 区域宽带。</p>\n        <p>4. Windows , Mac OS X , Linux作业系统。</p>\n        <p>IE10及主流浏览器！</p>', null);
INSERT INTO `webhome_friendship_template` VALUES ('50', '0', '2019-03-14 11:42:58', '2019-03-14 11:42:58', '0', '43', '<h2>取款帮助</h2>\n        <h4>取款方法;</h4>\n        <p>1.登入会员账号后点击线上取款→第一次取款的客户需先绑定的您的出款银行账户→确认提款银行账号正确，输入取款密码，取款金额，即可成功出款；</p>\n        <p>2.成功提交取款信息后，一般0-3分钟到账；</p>\n        <p>3.会员存款后需完成存款金额的一倍有效投注后即可全额取款，若未完成全额有效投注，则需扣除存款金额10%行政费用，附加手续费50元；</p>\n        <p>4.会员绑定的银行账号的户名需要与注册的真实姓名一致；</p>\n        <p>5.目前支持以下金流出款：绑定中国工商银行(优先)、中国农业银行、北京银行、交通银行、中国银行、中国建设银行、中国光大银行、兴业银行、中国民生银行总行、招商银行、中信银行、广东发展银行、中国邮政、深圳发展银行、上海浦东发展银行；</p>\n        <p>6.会员如需修改银行账号，请您联系24小时【在线客服】修改。</p>\n        <h4>取款需知:</h4>\n        <p>? 最低取款为$1人民币，取款上限为单笔$1000000人民币。(24小时内可以享有的最高取款总额上限为$10000000人民币，如当日已到达提款上限额度，需要在最后一笔提款间隔24小时方可再享受提款，但不影响会员正常游戏)。</p>\n        <p>? 完成全额有效投注，全天24小时不限次数提款，手续费全免。</p>\n        <p>? AB棋牌保留权利审核会员账户，若由上次出款起，有效下注金额未达\"\"最后一次入款后账户的总余额\"\"，而申请出款者，公司将收取\"\"最后一次入款后账户的总余额\"\"10%的行政费用，以及￥50出款手续费。</p>\n        <h4>【例1】1月1日 12:00当下额度￥1000,并在存入3000元,则在1月1日 12:00后,有效投注额必须为(1000+3000)以上出款,才不被扣除行政费用+手续费用。</h4>\n        <h4>【例2】若有效投注未达到(1000+3000)元,则会扣除行政费用(1000+3000)x10%+手续费50 =须被扣除450元 。</h4>\n        <p>? 请注意: 各游戏和局/未接受/取消注单，不纳入有效投注计算，运动博弈游戏项目，大赔率玩法计算有效投注金额，小赔率玩法计算输赢金额为有效投注，大赔率产品包括: 过关、波胆、总入球、半全场、双胜彩、冠军赛；</p>\n        <p>? 如有任何问题，请您联系24小时【在线客服】咨询；</p>\n        <p>? AB棋牌相关优惠，请详见【优惠活动】。</p>', null);
INSERT INTO `webhome_friendship_template` VALUES ('51', '0', '2019-03-14 11:44:23', '2019-03-14 13:37:33', '0', '44', '<h2>合作伙伴</h2>\n        <h4>合作伙伴 联营协议 代理登入 代理注册</h4>\n        <p>AB棋牌在线经营网络游戏投注多年。是目前世界最大的网络游戏平台之一！ 拥有多元化的产品，使用最公平、公正、公开的系统，在市场上的众多网络娱乐中，我们自豪的提供会员最优惠的回馈， 给予代理合作最优势的营利回报! 无论您拥有的是网络资源，或是人脉资源，都欢迎您来加入AB棋牌代理合作的行列， 无须负担任何费用，就可以开始无上限的收入。加入AB棋牌，绝对是您最聪明的选择!</p>\n        <h4>注册申请</h4>\n        <p>请点击【代理注册】在线提出申请，并确实填写各项资料。AB棋牌会评估审核联盟申请讯息，3日内审核的专员将会以邮件形式回复您，提供您的注册帐号、密码及推广链接，或者您主动联系QQ专员为您审核，审核通过您即可开始推广！</p>\n        <p>AB棋牌推出全新代理模式：周周结算 月月结算 双重高额返利 同步领取，你从没见过的全新版本！</p>\n        <p>申请代理，联系专员QQ 235699999审核 3.了解好步骤后，开始推广，按照周期时间领取佣金！</p>\n        <p>周周结算：每星期一到星期日为一周期，每星期一 联系专员结算上周期周周佣金！</p>\n        <h4>周周结算</h4>\n        <div class=\"div-table\">\n        <table border=\"1\" border-collapse=\"1\">\n          <tbody>\n              <tr>\n                  <th>\n                      所有下线会员的累计<br>\n                      电子有效投注\n                  </th>\n                  <th>\n                      有效会员3人以上<br>\n                      领取对应佣金\n                  </th>\n                  <th>\n                      有效会10人以上<br>\n                      领取对应佣金\n                  </th>\n                  <th>\n                      有效会员30人以上<br>\n                      领取对应佣金\n                  </th>\n                  <th>\n                      有效会员50人以上<br>\n                      领取对应佣金\n                  </th>\n              </tr>\n              <tr>\n                  <td>3000或以上</td>\n                  <td>10元</td>\n                  <td>15元</td>\n                  <td>20元</td>\n                  <td>30元</td>\n              </tr>\n              <tr>\n                  <td>5000或以上</td>\n                  <td>15元</td>\n                  <td>20元</td>\n                  <td>30元</td>\n                  <td>50元</td>\n              </tr>\n              <tr>\n                  <td>1万或以上</td>\n                  <td>30元</td>\n                  <td>40元</td>\n                  <td>60元</td>\n                  <td>80元</td>\n              </tr>\n              <tr>\n                  <td>3万或以上</td>\n                  <td>100元</td>\n                  <td>150元</td>\n                  <td>200元</td>\n                  <td>300元</td>\n              </tr>\n              <tr>\n                  <td>5万或以上</td>\n                  <td>200元</td>\n                  <td>300元</td>\n                  <td>400元</td>\n                  <td>500元</td>\n              </tr>\n              <tr>\n                  <td>10万或以上</td>\n                  <td>400元</td>\n                  <td>500元</td>\n                  <td>600元</td>\n                  <td>700元</td>\n              </tr>\n              <tr>\n                  <td>20万或以上</td>\n                  <td>600元</td>\n                  <td>700元</td>\n                  <td>800元</td>\n                  <td>900元</td>\n              </tr>\n              <tr>\n                  <td>30万或以上</td>\n                  <td>900元</td>\n                  <td>1000元</td>\n                  <td>1100元</td>\n                  <td>1200元</td>\n              </tr>\n              <tr>\n                  <td>50万或以上</td>\n                  <td>1600元</td>\n                  <td>1700元</td>\n                  <td>1800元</td>\n                  <td>1900元</td>\n              </tr>\n              <tr>\n                  <td>100万或以上</td>\n                  <td>3300元</td>\n                  <td>3600元</td>\n                  <td>3600元</td>\n                  <td>4000元</td>\n              </tr>\n              <tr>\n                  <td>300万或以上</td>\n                  <td>9000元</td>\n                  <td>9300元</td>\n                  <td>9700元</td>\n                  <td>10000元</td>\n              </tr>\n              <tr>\n                  <td>500万或以上</td>\n                  <td>16000元</td>\n                  <td>16000元</td>\n                  <td>16500元</td>\n                  <td>17000元</td>\n              </tr>\n              <tr>\n                  <td>1000万或以上</td>\n                  <td>32000元</td>\n                  <td>33000元</td>\n                  <td>34000元</td>\n                  <td>35000元</td>\n              </tr>\n              <tr>\n                  <td>1500万或以上</td>\n                  <td>50000元</td>\n                  <td>51000元</td>\n                  <td>53000元</td>\n                  <td>55000元</td>\n              </tr>\n              <tr>\n                  <td>2000万或以上</td>\n                  <td>66000元</td>\n                  <td>69000元</td>\n                  <td>72000元</td>\n                  <td>75000元</td>\n              </tr>\n              <tr>\n                  <td>3000万或以上</td>\n                  <td>90000元</td>\n                  <td>93000元</td>\n                  <td>97000元</td>\n                  <td>110000元</td>\n              </tr>\n              <tr>\n                  <td>5000万或以上</td>\n                  <td>160000元</td>\n                  <td>170000元</td>\n                  <td>175000元</td>\n                  <td>180000元</td>\n              </tr>\n              <tr>\n                  <td>8000万或以上</td>\n                  <td>270000元</td>\n                  <td>280000元</td>\n                  <td>290000元</td>\n                  <td>300000元</td>\n              </tr>\n              <tr>\n                  <td>1亿或以上</td>\n                  <td>300000元</td>\n                  <td>310000元</td>\n                  <td>320000元</td>\n                  <td>350000元</td>\n              </tr>\n              <tr>\n                  <td>2亿或以上</td>\n                  <td>700000元</td>\n                  <td>750000元</td>\n                  <td>800000元</td>\n                  <td>900000元</td>\n              </tr>\n              <tr>\n                  <td>3亿或以上</td>\n                  <td>100万元</td>\n                  <td>110万元</td>\n                  <td>130万元</td>\n                  <td>150万元</td>\n              </tr>\n          </tbody>\n        </table>\n        </div>\n        <p>注：请谨记使用任何不诚实方式骗取代理佣金者将取消代理资格并永久冻结账号，佣金一律不予派发！</p>\n        <h4>1.列如：</h4>\n        <p>A：代理下线客户拥有10个有效会员一个月累计的实际投注38000元，那么代理可得到的佣金是150元</p>\n        <p>B：代理下线客户拥有30个有效会员一个月累计的实际投注38000元，那么代理可得到的佣金是200元</p>\n        <p>C：代理下线客户拥有29个有效会员（未达到30个有效会员）一个月累计的实际投注101万元，那么代理可得到的佣金是3600元</p>\n        <p>D：代理下线客户拥有30个有效会员（达到30个有效会员）一个月累计的实际亏损101万元，那么代理可得到的佣金是3800元</p>\n        <h4>月月结算：每月1号至最后一天为一周期，每个月3号 联系专员结算上个月周期佣金！</h4>\n        <div class=\"div-table\">\n        <table border=\"1\" class=\"partnerTable\">\n          <tbody>\n              <tr>\n                  <th>\n                      所有下线会员的<br>\n                      累计电子实际亏损\n                  </th>\n                  <th>\n                      有效会员3人以上<br>\n                      领取对应佣金\n                  </th>\n                  <th>\n                      有效会员10人以上<br>\n                      领取对应佣金\n                  </th>\n                  <th>\n                      有效会员30人以上<br>\n                      领取对应佣金\n                  </th>\n                  <th>\n                      有效会员50人以上<br>\n                      领取对应佣金\n                  </th>\n              </tr>\n              <tr>\n                  <td>100-5000元</td>\n                  <td>5%</td>\n                  <td>8%</td>\n                  <td>10%</td>\n                  <td>20%</td>\n              </tr>\n              <tr>\n                  <td>5001-30000元</td>\n                  <td>8%</td>\n                  <td>10%</td>\n                  <td>15%</td>\n                  <td>25%</td>\n              </tr>\n              <tr>\n                  <td>30001-99999元</td>\n                  <td>10%</td>\n                  <td>15%</td>\n                  <td>20%</td>\n                  <td>30%</td>\n              </tr>\n              <tr>\n                  <td>10万元-299999元</td>\n                  <td>15%</td>\n                  <td>20%</td>\n                  <td>25%</td>\n                  <td>35%</td>\n              </tr>\n              <tr>\n                  <td>30万元-999999元</td>\n                  <td>20%</td>\n                  <td>25%</td>\n                  <td>30%</td>\n                  <td>40%</td>\n              </tr>\n              <tr>\n                  <td>100万元-4999999元</td>\n                  <td>30%</td>\n                  <td>35%</td>\n                  <td>40%</td>\n                  <td>50%</td>\n              </tr>\n              <tr>\n                  <td>500万元-9999999元</td>\n                  <td>35%</td>\n                  <td>40%</td>\n                  <td>50%</td>\n                  <td>50%</td>\n              </tr>\n              <tr>\n                  <td>1000万元以上</td>\n                  <td>40%</td>\n                  <td>50%</td>\n                  <td>50%</td>\n                  <td>50%</td>\n              </tr>\n          </tbody>\n        </table>\n        </div>\n        <h4>1.列如：</h4>\n        <p>A：代理下线客户拥有10个有效会员一个月累计的实际亏损38000元，那么代理可得到的佣金是38000 X 15% = 5700元</p>\n        <p>B：代理下线客户拥有30个有效会员一个月累计的实际亏损38000元，那么代理可得到的佣金是38000 X 20% = 7600元</p>\n        <p>C：代理下线客户拥有29个有效会员（未达到30个有效会员）一个月累计的实际亏损101万元，那么代理可得到的佣金是 1010000 X 35% = 353500元</p>\n        <p>D：代理下线客户拥有30个有效会员（达到30个有效会员）一个月累计的实际亏损101万元，那么代理可得到的佣金是 1010000 X 40% = 404000元</p>\n        <p>注：AB棋牌保留上述条例之最终更改权！</p>\n        <h4>联营协议</h4>\n        <p>AB棋牌与BBIN进行技术合作，为哥斯特黎加合法注册之博彩公司。我们采用最为多元、 先进、公正的系统，在众多博彩网站中，我们自豪能为会员提供最优惠的回馈、为代理商创造强劲的营利优势! AB棋牌秉持商业联营、资源整合、利益共享的理念，与合作伙伴携手打造利多的荣景。 无论您拥有的是网络资源，或是丰富的人脉，都欢迎您来加入我们的行列，不须负担任何费用， 就可以开拓无上限的营收。AB棋牌娱乐绝对是您最聪明的选择!</p>\n        <h4>一、代理商注册规约</h4>\n        <p>为防堵不肖业者滥用AB棋牌所提供的代理优惠制度，我们将严格审核每位代理商申请注册时所提供的个人资料(包括 姓名、IP、住址、电邮信箱、电话、支付方式等等)。若经审核发现代理商有任何不良营利企图，或与其他代理 商、会员进行合谋套利等行为，AB棋牌娱乐公司将关闭该合作代理商之账户、扣除账户中的本金，并收回该代理商 的所有佣金与优惠。 同一IP/同一姓名/同一收款账号的会员只能是一个合作代理商的下线，代理商本身不能成为其他代理商的下线会员。</p>\n        <h2>二、权责条款</h2>\n        <h4>一、AB棋牌对联盟伙伴的权利与义务</h4>\n        <p>AB棋牌的客服部门会登记合作代理商的下线会员并观察其投注状况。 代理商及会员皆须同意并遵守AB棋牌的会员条例、政策及操作程序。 合作代理商可随时登入管理端接口观察其下线会员的下注状况与活动概况。 AB棋牌保留所有对合作代理商或会员之账户加以拒绝或冻结的权利。 AB棋牌有权修改合约书上之任何条例(包括:现有的佣金范围、佣金计划、付款程序、及参考计划条例等 等)，AB棋牌公司会以电邮、网站公告等方法通知合作代理商。若代理商对于任何修改持有异议，可选择终止合 约、或洽客服人员提出意见。如代理商未提出异议，便视作默认合约修改，必须遵守更改后的相关规定。</p>\n        <h4>二、联盟伙伴对AB棋牌的权力及义务</h4>\n        <p>合作代理商应尽其所能，广泛地宣传、销售及推广AB棋牌使代理商本身及 AB棋牌的利润最大化。合作 代理商可在不违反法律的情况下，以正面形象宣传、销售及推广AB棋牌， 并有责任义务告知旗下会员所有关于AB棋牌的相关优惠条件及产品。 合作代理商选择推广AB棋牌的手法若需付费，则代理商应自行承担该费用。 任何AB棋牌的相关信息(包括：标志、报表、游戏画面、图样、文案等)，合作代理商不得私自复制、公开、 分发有关材料，AB棋牌保留法律追诉权。 如代理商在业务推广方面需要相关的技术支持， 欢迎随时洽询AB棋牌客服人员。</p>\n        <h4>三、各项细则</h4>\n        <p>各阶层合作代理商不可在未经AB棋牌娱乐允许下开设双/多个代理账号， 也不可从AB棋牌之游戏账户或其他相关人士赚取佣金。 请谨记任何代理商皆不能用代理帐户下注，AB棋牌 有权终止并封存账号及其所有在游戏中赚取的佣金。</p>\n        <p>为确保所有AB棋牌会员的账号隐私与权益， AB棋牌不会提供任何会员密码，或会员个人资料。 各阶层合作代理商亦不得以任何方式取得会员数据，或任意登入下层会员账号， 如发现代理商有侵害AB棋牌会员隐私的行为， AB棋牌有权取消代理商之红利，并取消该名代理商之账号。</p>\n        <p>合作代理商旗下的会员不得开设多于一个的账户。AB棋牌有权要求会员提供有效的身份证明以验证会员的身份， 并保留以IP判定会员是否重复注册的权利。如违反上述事项， AB棋牌有权终止玩家进行游戏并封存账号及所有于游戏中赚取的佣金。</p>\n        <p>如合作代理商旗下的会员因违反条例而被禁止使用AB棋牌的游戏， 或AB棋牌退回存款给会员， AB棋牌将不会分配相应的佣金给代理商。 如合作代理商旗下会员存款用的信用卡、银行资料须经审核，AB棋牌将保留相关佣金直至审核完毕。</p>\n        <p>合约条件将于AB棋牌正式接受合作代理商加入后开始生效。 AB棋牌娱乐公司及代理商可随时终止此合约。 在任何情况下，代理商若欲终止合约，都必须以书面/电邮方式提早于七日内通知AB棋牌。 代理商的表现将会每3个月审核一次，如代理商已不是现有的合作成员，则本合约书可以在任何时间终止。 如代理商违反合约条例，AB棋牌有权立即终止合约。</p>\n        <p>在没有AB棋牌的许可下， 代理商不能透露及授权AB棋牌的相关机密资料， 包括代理商所获得的回馈、佣金报表、计算方式等；代理商有义务在合约终止后仍执行机密文件及数据的保密。 合约终止之后，代理商及AB棋牌将不须履行双方的权利及义务。 终止合约并不会解除代理商于终止合约前所应履行的义务。</p>', null);
INSERT INTO `webhome_friendship_template` VALUES ('52', '0', '2019-03-14 13:35:01', '2019-03-14 13:35:01', '0', '45', '<h2>联络我们</h2>\n        <p>AB棋牌的客服中心全年无休，提供1周7天、每天24小时的优质服务。</p>\n        <p>如果您对本网站的使用有任何疑问，可以透过下列任一方式与客服人员联系，享受最实时的服务：</p>\n        <p>点击\" 在线客服 \"连结，即可进入在线客服系统与客服人员联系。</p>\n        <p>您亦可使用Email或电话与客服人员取得联系：</p>\n        <h4>客服QQ：3965578</h4>\n        <h4>客服微信： ABYLKF </h4>\n        <h4>代理QQ：235699999</h4>\n        <h4>官方邮箱：abqpvip@gmail.com</h4>', null);

-- ----------------------------
-- Table structure for webhome_head
-- ----------------------------
DROP TABLE IF EXISTS `webhome_head`;
CREATE TABLE `webhome_head` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '名字',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT 'url',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '内容',
  `enclosure_id` bigint(22) DEFAULT '0' COMMENT '附件ID',
  `type` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '菜单类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='官网头部管理';

-- ----------------------------
-- Records of webhome_head
-- ----------------------------
INSERT INTO `webhome_head` VALUES ('5', '0', '2019-01-25 11:09:17', '2019-03-09 15:57:04', '0', 'qwewqewqe', 'wqeqweqwe', '<p>qweqweqwe</p>', '395', 'APP');

-- ----------------------------
-- Table structure for webhome_head_template
-- ----------------------------
DROP TABLE IF EXISTS `webhome_head_template`;
CREATE TABLE `webhome_head_template` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT 'url',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '内容',
  `enclosure_id` bigint(22) DEFAULT '0' COMMENT '附件ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='官网头部模板';

-- ----------------------------
-- Records of webhome_head_template
-- ----------------------------

-- ----------------------------
-- Table structure for webhome_image
-- ----------------------------
DROP TABLE IF EXISTS `webhome_image`;
CREATE TABLE `webhome_image` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `enclosure_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '附件ID',
  `type` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图片类型（LOG,轮播图，对联）',
  `enable` bit(1) NOT NULL DEFAULT b'0' COMMENT '状态(启用，禁用)',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '跳转路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='官网图片管理';

-- ----------------------------
-- Records of webhome_image
-- ----------------------------
INSERT INTO `webhome_image` VALUES ('16', '0', '2019-01-22 14:14:10', '2019-03-15 13:41:53', '0', '406', 'logo', '', '1', null);
INSERT INTO `webhome_image` VALUES ('17', '0', '2019-01-22 14:14:56', '2019-04-06 20:31:52', '0', '681', 'exhibition', '', '1', null);
INSERT INTO `webhome_image` VALUES ('18', '0', '2019-01-22 14:16:24', '2019-03-05 15:39:59', '0', '366', 'exhibition', '', '2', null);
INSERT INTO `webhome_image` VALUES ('19', '0', '2019-01-22 14:16:42', '2019-03-05 15:35:52', '0', '364', 'exhibition', '', '3', null);
INSERT INTO `webhome_image` VALUES ('20', '1', '2019-01-25 10:57:33', '2019-03-06 16:15:34', '0', '375', 'couplets', '', '1', null);
INSERT INTO `webhome_image` VALUES ('21', '1', '2019-01-25 10:57:47', '2019-03-06 19:52:16', '0', '376', 'couplets', '', '7', null);
INSERT INTO `webhome_image` VALUES ('22', '0', '2019-01-27 20:28:23', '2019-04-10 18:42:45', '0', '731', 'logo', '\0', '1', null);
INSERT INTO `webhome_image` VALUES ('23', '0', '2019-02-03 17:09:12', '2019-04-10 18:39:23', '0', '717', 'gameRecord', '', '1', null);
INSERT INTO `webhome_image` VALUES ('24', '0', '2019-02-03 17:21:52', '2019-04-07 14:09:34', '0', '681', 'gameRecord', '', '3', null);
INSERT INTO `webhome_image` VALUES ('25', '0', '2019-02-03 17:22:02', '2019-04-10 18:34:44', '0', '724', 'gameRecord', '', '2', null);
INSERT INTO `webhome_image` VALUES ('26', '0', '2019-02-03 17:22:17', '2019-04-17 14:53:04', '0', '759', 'transactionRecord', '', '1', null);
INSERT INTO `webhome_image` VALUES ('27', '0', '2019-02-03 17:22:29', '2019-04-17 14:53:52', '0', '766', 'transactionRecord', '', '2', null);
INSERT INTO `webhome_image` VALUES ('28', '0', '2019-02-03 17:22:39', '2019-04-17 15:15:07', '0', '759', 'recharge', '', '1', null);
INSERT INTO `webhome_image` VALUES ('29', '0', '2019-02-07 16:02:03', '2019-04-07 23:38:19', '0', '242', 'pay', '', '1', '/vuejs/AppDown');
INSERT INTO `webhome_image` VALUES ('30', '0', '2019-02-07 16:02:44', '2019-04-07 23:38:25', '0', '243', 'pay', '', '2', '/vuejs/AppPay');
INSERT INTO `webhome_image` VALUES ('31', '0', '2019-02-07 16:02:55', '2019-04-07 23:38:51', '0', '244', 'pay', '', '3', '/vuejs/AppPay');
INSERT INTO `webhome_image` VALUES ('32', '0', '2019-02-07 16:03:06', '2019-04-07 23:38:57', '0', '245', 'pay', '', '4', '/vuejs/AppPay');
INSERT INTO `webhome_image` VALUES ('33', '0', '2019-02-07 16:03:16', '2019-04-07 23:39:04', '0', '246', 'pay', '', '5', '/vuejs/AppPay');
INSERT INTO `webhome_image` VALUES ('34', '0', '2019-02-07 16:04:11', '2019-02-07 16:04:11', '0', '241', 'promotions', '', '1', null);
INSERT INTO `webhome_image` VALUES ('35', '0', '2019-03-02 15:11:20', '2019-03-05 15:58:05', '0', '266', 'cancel', '', '1', '1');
INSERT INTO `webhome_image` VALUES ('36', '0', '2019-03-02 15:11:44', '2019-03-05 15:56:30', '0', '267', 'cancel', '', '2', '1');
INSERT INTO `webhome_image` VALUES ('37', '1', '2019-03-02 18:51:24', '2019-03-05 16:22:09', '0', '268', 'couplets', '', '5', '15');
INSERT INTO `webhome_image` VALUES ('38', '0', '2019-03-02 20:52:08', '2019-03-05 15:57:11', '0', '269', 'cancel', '', '1', '');
INSERT INTO `webhome_image` VALUES ('39', '0', '2019-03-05 16:19:06', '2019-03-05 16:19:06', '0', '367', 'exhibition', '', '4', '1');
INSERT INTO `webhome_image` VALUES ('40', '0', '2019-03-05 16:20:07', '2019-03-05 16:20:07', '0', '368', 'exhibition', '', '5', '1');
INSERT INTO `webhome_image` VALUES ('41', '0', '2019-03-06 19:46:14', '2019-03-15 15:08:06', '0', '407', 'couplets', '', '2', 'IndexPreferential?id=num12');
INSERT INTO `webhome_image` VALUES ('42', '0', '2019-03-06 19:46:36', '2019-03-15 15:08:16', '0', '408', 'couplets', '', '3', 'IndexPreferential?id=num13');
INSERT INTO `webhome_image` VALUES ('43', '0', '2019-03-06 19:46:47', '2019-03-15 15:13:45', '0', '409', 'couplets', '', '4', 'IndexPreferential?id=num15');
INSERT INTO `webhome_image` VALUES ('44', '0', '2019-03-06 19:46:56', '2019-03-15 15:13:57', '0', '410', 'couplets', '', '5', 'IndexPreferential?id=num12');
INSERT INTO `webhome_image` VALUES ('45', '0', '2019-03-06 19:56:57', '2019-03-17 01:25:47', '0', '411', 'couplets', '', '8', 'https://e-141816.chatnow.meiqia.com/dist/standalone.html');
INSERT INTO `webhome_image` VALUES ('46', '0', '2019-03-06 19:57:10', '2019-03-15 16:49:37', '0', '412', 'couplets', '', '9', '/AppDown');
INSERT INTO `webhome_image` VALUES ('47', '0', '2019-03-06 19:57:33', '2019-03-15 16:49:45', '0', '413', 'couplets', '', '10', 'http://wpa.qq.com/msgrd?v=3&uin=3965578&site=qq&menu=yes');
INSERT INTO `webhome_image` VALUES ('48', '0', '2019-03-12 15:23:55', '2019-03-12 15:36:55', '0', '397', 'bottom', '', '1', '');
INSERT INTO `webhome_image` VALUES ('49', '1', '2019-03-12 15:26:04', '2019-03-12 15:26:04', '0', '399', 'bottom', '', '1', '');
INSERT INTO `webhome_image` VALUES ('50', '0', '2019-03-12 16:30:19', '2019-03-12 16:30:19', '0', '400', 'domain', '', '1', '');
INSERT INTO `webhome_image` VALUES ('51', '0', '2019-03-13 19:57:18', '2019-03-13 19:57:18', '0', '395', 'AppDownload', '', '1', '');
INSERT INTO `webhome_image` VALUES ('52', '0', '2019-03-14 21:11:18', '2019-03-14 21:11:18', '0', '401', 'appTutorial', '', '1', '');
INSERT INTO `webhome_image` VALUES ('53', '0', '2019-03-14 21:11:31', '2019-03-15 09:37:54', '0', '402', 'appTutorial', '', '2', '');
INSERT INTO `webhome_image` VALUES ('54', '0', '2019-03-14 21:11:40', '2019-03-15 09:37:58', '0', '403', 'appTutorial', '', '3', '');
INSERT INTO `webhome_image` VALUES ('55', '0', '2019-03-14 21:12:04', '2019-03-15 09:38:03', '0', '404', 'appTutorial', '', '4', '');
INSERT INTO `webhome_image` VALUES ('59', '1', '2019-04-17 15:06:54', '2019-04-17 15:06:54', '0', '766', 'recharge', '', '1', '');

-- ----------------------------
-- Table structure for webhome_menu
-- ----------------------------
DROP TABLE IF EXISTS `webhome_menu`;
CREATE TABLE `webhome_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `parent_id` bigint(20) NOT NULL COMMENT '父菜单ID，一级菜单从0开始',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `english_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '英文名称',
  `type` int(11) NOT NULL COMMENT '类型   0：一级 1：二级   2:三级',
  `icon` bigint(255) DEFAULT '0' COMMENT '菜单图标',
  `order_num` int(11) NOT NULL COMMENT '排序',
  `game_id` bigint(255) DEFAULT '0' COMMENT '游戏ID(根据ID打开对应游戏)',
  `menu_color` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '菜单颜色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 COMMENT='首页菜单管理';

-- ----------------------------
-- Records of webhome_menu
-- ----------------------------
INSERT INTO `webhome_menu` VALUES ('2', '0', '2019-01-02 17:10:38', '2019-03-15 21:44:22', '0', '0', '捕鱼场', 'FISH', '0', '95', '2', '0', '#f60000');
INSERT INTO `webhome_menu` VALUES ('3', '0', '2019-01-02 17:10:38', '2019-03-15 21:50:19', '0', '0', '百人赌场', 'CASINO', '0', '0', '3', '0', '#FF33FF');
INSERT INTO `webhome_menu` VALUES ('4', '0', '2019-01-02 17:10:38', '2019-03-15 21:46:53', '0', '0', '电子游艺', 'Electronic', '0', '0', '4', '0', '#fcff01');
INSERT INTO `webhome_menu` VALUES ('5', '0', '2019-01-02 17:10:38', '2019-03-15 21:46:00', '0', '0', '棋牌匹配场', 'CHESS', '0', '0', '5', '0', '#10be20');
INSERT INTO `webhome_menu` VALUES ('6', '0', '2019-01-02 17:10:38', '2019-03-15 21:47:37', '0', '0', '房卡场', 'card', '0', '0', '6', '0', '#ff0101');
INSERT INTO `webhome_menu` VALUES ('7', '0', '2019-01-02 17:10:38', '2019-03-15 21:48:09', '0', '0', '休闲竞技场', 'Leisure', '0', '93', '7', '0', '#00b4ff');
INSERT INTO `webhome_menu` VALUES ('8', '1', '2019-01-02 17:10:38', '2019-01-30 11:04:59', '0', '0', '推广赚钱', '', '0', '0', '8', '0', '');
INSERT INTO `webhome_menu` VALUES ('10', '1', '2019-01-02 17:10:38', '2019-03-04 10:16:03', '0', '2', '2D捕鱼', '', '1', '270', '7', '1', '');
INSERT INTO `webhome_menu` VALUES ('11', '0', '2019-01-02 17:10:38', '2019-03-05 13:56:52', '0', '2', '海王2', '', '1', '337', '2', '0', '');
INSERT INTO `webhome_menu` VALUES ('12', '0', '2019-01-02 17:10:38', '2019-03-28 15:43:19', '0', '5', '斗地主', '', '1', '513', '1', '1', '');
INSERT INTO `webhome_menu` VALUES ('13', '0', '2019-01-02 17:10:38', '2019-03-28 15:57:56', '0', '5', '炸金花', '', '1', '478', '2', '8', '');
INSERT INTO `webhome_menu` VALUES ('14', '0', '2019-01-02 17:10:38', '2019-03-28 15:33:47', '0', '5', '二八杠', '', '1', '485', '3', '11', '');
INSERT INTO `webhome_menu` VALUES ('15', '1', '2019-01-02 17:10:38', '2019-02-16 21:04:56', '0', '5', '炸金花', '', '1', '0', '4', '0', '');
INSERT INTO `webhome_menu` VALUES ('16', '0', '2019-01-02 17:10:38', '2019-03-05 13:56:40', '0', '2', '捕鱼', '', '1', '336', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('17', '0', '2019-01-02 17:10:38', '2019-03-05 13:57:05', '0', '2', '金鲨银鲨', '', '1', '338', '2', '0', '');
INSERT INTO `webhome_menu` VALUES ('18', '1', '2019-01-02 17:10:38', '2019-01-02 17:10:38', '0', '2', '三公', '', '1', '0', '3', '0', '');
INSERT INTO `webhome_menu` VALUES ('19', '1', '2019-01-02 17:10:38', '2019-01-02 17:10:38', '0', '2', '百人牛牛', '', '1', '0', '4', '0', '');
INSERT INTO `webhome_menu` VALUES ('20', '1', '2019-01-02 17:10:38', '2019-01-02 17:10:38', '0', '2', '百人炸金花', '', '1', '0', '5', '0', '');
INSERT INTO `webhome_menu` VALUES ('21', '1', '2019-01-02 17:10:38', '2019-01-02 17:10:38', '0', '3', '爱丽丝', '', '1', '0', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('22', '1', '2019-01-02 17:10:38', '2019-02-03 14:33:14', '0', '3', '阿斯塔纳', '', '1', '0', '5', '5', '');
INSERT INTO `webhome_menu` VALUES ('23', '0', '2019-01-02 17:10:38', '2019-03-28 15:20:54', '0', '3', '龙虎斗', '', '1', '443', '2', '9', '');
INSERT INTO `webhome_menu` VALUES ('24', '0', '2019-01-02 17:10:38', '2019-03-28 15:19:22', '0', '3', '百家乐', '', '1', '436', '1', '10', '');
INSERT INTO `webhome_menu` VALUES ('25', '1', '2019-01-02 17:10:38', '2019-03-04 10:30:22', '0', '4', '爱丽丝仙境', '', '1', '277', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('33', '1', '2019-02-19 14:18:39', '2019-02-19 14:18:39', '0', '0', 'test', null, '0', '95', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('34', '0', '2019-03-04 10:32:12', '2019-03-28 15:29:39', '0', '4', '阿莱克斯塔萨', null, '1', '341', '2', '0', '');
INSERT INTO `webhome_menu` VALUES ('35', '0', '2019-03-04 10:32:47', '2019-03-28 15:29:50', '0', '4', '古埃及宝藏', null, '1', '342', '3', '0', '');
INSERT INTO `webhome_menu` VALUES ('36', '0', '2019-03-04 10:33:28', '2019-03-28 15:29:01', '0', '4', '海底宝藏', null, '1', '471', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('37', '0', '2019-03-04 10:34:10', '2019-03-28 15:30:01', '0', '4', '金瓶梅', null, '1', '344', '4', '0', '');
INSERT INTO `webhome_menu` VALUES ('38', '0', '2019-03-04 10:34:39', '2019-03-28 15:30:12', '0', '4', '罗宾汉', null, '1', '345', '5', '0', '');
INSERT INTO `webhome_menu` VALUES ('39', '0', '2019-03-04 10:35:27', '2019-03-28 15:30:38', '0', '4', '西游记', null, '1', '346', '6', '0', '');
INSERT INTO `webhome_menu` VALUES ('40', '0', '2019-03-04 10:35:56', '2019-03-28 15:30:50', '0', '4', '西部牛仔', null, '1', '347', '7', '0', '');
INSERT INTO `webhome_menu` VALUES ('41', '1', '2019-03-04 10:36:59', '2019-03-05 14:07:54', '0', '4', '亚特兰蒂斯', null, '1', '348', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('42', '0', '2019-03-04 10:37:20', '2019-03-28 15:30:59', '0', '4', '伊势丹追求', null, '1', '349', '8', '0', '');
INSERT INTO `webhome_menu` VALUES ('43', '0', '2019-03-04 10:37:46', '2019-03-28 15:31:08', '0', '4', '威尼斯', null, '1', '350', '9', '0', '');
INSERT INTO `webhome_menu` VALUES ('44', '0', '2019-03-04 10:38:39', '2019-03-28 15:31:20', '0', '4', '水浒传', null, '1', '351', '10', '0', '');
INSERT INTO `webhome_menu` VALUES ('45', '0', '2019-03-04 10:39:56', '2019-03-28 15:31:36', '0', '4', '森林舞会', null, '1', '352', '11', '0', '');
INSERT INTO `webhome_menu` VALUES ('46', '0', '2019-03-04 10:44:44', '2019-03-05 14:09:30', '0', '7', '保卫萝卜', null, '1', '356', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('47', '0', '2019-03-04 10:53:44', '2019-03-05 14:09:39', '0', '7', '连环斗宝', null, '1', '357', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('48', '0', '2019-03-04 10:54:28', '2019-03-05 14:09:47', '0', '7', '跟车联盟', null, '1', '358', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('49', '0', '2019-03-04 10:54:51', '2019-03-05 14:09:57', '0', '7', '三国', null, '1', '359', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('50', '0', '2019-03-04 10:55:06', '2019-03-05 14:10:03', '0', '7', '坦克大战', null, '1', '360', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('51', '0', '2019-03-04 10:55:17', '2019-03-05 14:10:08', '0', '7', '象棋', null, '1', '361', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('52', '0', '2019-03-05 14:10:46', '2019-03-05 14:10:46', '0', '6', '斗地主', null, '1', '362', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('53', '0', '2019-03-05 14:11:01', '2019-03-05 14:11:01', '0', '6', '炸金花', null, '1', '363', '1', '0', '');
INSERT INTO `webhome_menu` VALUES ('58', '0', '2019-03-28 15:22:39', '2019-03-28 15:27:19', '0', '3', '百人牛牛', null, '1', '450', '3', '4', '');
INSERT INTO `webhome_menu` VALUES ('65', '0', '2019-03-28 15:23:31', '2019-03-28 15:23:31', '0', '3', '百人牌九', null, '1', '457', '4', '5', '');
INSERT INTO `webhome_menu` VALUES ('72', '0', '2019-03-28 15:24:18', '2019-03-28 15:40:16', '0', '3', '百人诈金花', null, '1', '464', '5', '6', '');
INSERT INTO `webhome_menu` VALUES ('79', '0', '2019-03-28 15:34:32', '2019-03-28 15:57:38', '0', '5', '抢庄牛牛', null, '1', '506', '4', '2', '');
INSERT INTO `webhome_menu` VALUES ('86', '0', '2019-03-28 15:35:22', '2019-03-28 15:44:26', '0', '5', '通比牛牛', null, '1', '499', '5', '3', '');
INSERT INTO `webhome_menu` VALUES ('93', '0', '2019-03-28 15:37:24', '2019-03-28 15:58:17', '0', '5', '十三水', null, '1', '520', '6', '7', '');
INSERT INTO `webhome_menu` VALUES ('100', '0', '2019-03-28 16:26:51', '2019-03-28 16:26:51', '0', '6', '牛牛', null, '1', '450', '3', '13', '');

-- ----------------------------
-- Table structure for webhome_menu_template
-- ----------------------------
DROP TABLE IF EXISTS `webhome_menu_template`;
CREATE TABLE `webhome_menu_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `menu_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '菜单ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `synopsis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '简介',
  `enclosure_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '附件ID',
  `order_num` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '排序',
  `game_id` bigint(22) DEFAULT '0' COMMENT '跳转路径(游戏ID或者路径)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='菜单模板表';

-- ----------------------------
-- Records of webhome_menu_template
-- ----------------------------
INSERT INTO `webhome_menu_template` VALUES ('13', '1', '2019-02-19 15:03:51', '2019-03-04 16:26:38', '0', '2', 'test', 'testtesttesttesttest', '303', '1', '1');
INSERT INTO `webhome_menu_template` VALUES ('14', '1', '2019-02-19 15:03:51', '2019-02-19 15:10:52', '0', '2', 'test', 'testtesttesttesttest', '264', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('15', '1', '2019-02-19 15:03:51', '2019-02-19 15:10:52', '0', '2', 'test', 'testtesttesttesttest', '264', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('16', '1', '2019-02-19 15:03:51', '2019-02-19 15:10:52', '0', '2', 'test', 'testtesttesttesttest', '264', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('17', '1', '2019-02-19 15:03:51', '2019-02-19 15:10:52', '0', '2', 'test', 'testtesttesttesttest', '264', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('18', '1', '2019-02-19 15:03:51', '2019-02-19 15:10:52', '0', '2', 'test', 'testtesttesttesttest', '264', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('19', '0', '2019-02-19 15:03:51', '2019-03-04 16:35:31', '0', '2', '捕鱼', 'testtesttesttesttest', '304', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('20', '0', '2019-02-19 15:03:51', '2019-03-04 16:35:44', '0', '2', '海王2', 'testtesttesttesttest', '305', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('21', '0', '2019-02-19 15:03:51', '2019-03-04 16:36:08', '0', '2', '金鲨银鲨', 'testtesttesttesttest', '306', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('22', '1', '2019-02-19 15:03:51', '2019-02-19 16:00:15', '0', '2', 'test', 'testtesttesttesttest', '264', '23', '0');
INSERT INTO `webhome_menu_template` VALUES ('23', '1', '2019-02-19 15:03:51', '2019-02-19 15:10:52', '0', '2', 'test', 'testtesttesttesttest', '264', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('24', '1', '2019-02-19 15:03:51', '2019-02-19 15:10:52', '0', '2', 'test', 'testtesttesttesttest', '264', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('25', '1', '2019-02-19 15:03:51', '2019-02-19 15:10:52', '0', '2', 'test', 'testtesttesttesttest', '264', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('26', '1', '2019-02-19 15:03:51', '2019-02-19 17:14:16', '0', '2', 'test', 'testtesttesttesttest', '264', '6', '2');
INSERT INTO `webhome_menu_template` VALUES ('27', '1', '2019-02-19 17:25:06', '2019-02-19 18:44:05', '0', '3', 'test', '1111', '0', '6', '1');
INSERT INTO `webhome_menu_template` VALUES ('28', '1', '2019-02-19 17:25:35', '2019-02-19 17:25:35', '0', '3', '2222', 'ce', '0', '3', '1');
INSERT INTO `webhome_menu_template` VALUES ('29', '1', '2019-02-19 18:43:56', '2019-02-19 18:43:56', '0', '4', '123', '123', '0', '1', '1');
INSERT INTO `webhome_menu_template` VALUES ('30', '0', '2019-03-04 16:37:33', '2019-04-10 11:01:24', '0', '3', '百家乐', '百家乐', '710', '1', '10');
INSERT INTO `webhome_menu_template` VALUES ('31', '0', '2019-03-04 16:38:04', '2019-03-12 10:11:55', '0', '3', '十三水', '十三水', '308', '1', '7');
INSERT INTO `webhome_menu_template` VALUES ('32', '0', '2019-03-04 16:39:04', '2019-03-04 16:39:04', '0', '4', '阿莱克斯塔萨', '阿莱克斯塔萨', '309', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('33', '0', '2019-03-04 16:39:53', '2019-03-04 16:39:53', '0', '4', '爱丽丝仙境', '爱丽丝仙境', '310', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('34', '0', '2019-03-04 16:40:30', '2019-03-04 16:40:30', '0', '4', '古埃及宝藏', '古埃及宝藏', '311', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('35', '0', '2019-03-04 16:40:48', '2019-03-04 16:40:48', '0', '4', '海底宝藏', '海底宝藏', '312', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('36', '0', '2019-03-04 16:41:05', '2019-03-04 16:41:05', '0', '4', '金瓶梅', '金瓶梅', '313', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('37', '0', '2019-03-04 16:41:25', '2019-03-04 16:41:25', '0', '4', '罗宾汉', '罗宾汉', '314', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('38', '0', '2019-03-04 16:41:50', '2019-03-04 16:41:50', '0', '4', '森林舞会', '森林舞会', '315', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('39', '0', '2019-03-04 16:43:04', '2019-03-04 16:43:04', '0', '4', '水浒传', '水浒传', '316', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('40', '0', '2019-03-04 16:43:17', '2019-03-04 16:43:17', '0', '4', '威尼斯', '威尼斯', '317', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('41', '0', '2019-03-04 16:43:34', '2019-03-04 16:43:34', '0', '4', '西部牛仔', '西部牛仔', '318', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('42', '0', '2019-03-04 16:43:51', '2019-03-04 16:43:51', '0', '4', '西游记', '西游记', '319', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('43', '0', '2019-03-04 16:44:15', '2019-03-04 16:44:15', '0', '4', '亚特兰蒂斯传奇', '亚特兰蒂斯传奇', '320', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('44', '0', '2019-03-04 16:44:35', '2019-03-04 16:44:35', '0', '4', '伊势丹追求', '伊势丹追求', '321', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('45', '1', '2019-03-04 16:47:39', '2019-03-04 16:47:39', '0', '5', '斗地主', '斗地主', '322', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('46', '0', '2019-03-04 16:48:52', '2019-04-03 10:23:42', '0', '5', '斗地主', '斗地主', '323', '1', '1');
INSERT INTO `webhome_menu_template` VALUES ('47', '0', '2019-03-04 16:50:24', '2019-03-28 14:37:46', '0', '5', '龙虎斗', '龙虎斗', '324', '1', '9');
INSERT INTO `webhome_menu_template` VALUES ('48', '0', '2019-03-04 16:51:48', '2019-03-28 14:42:07', '0', '5', '炸金花', '炸金花', '325', '1', '2');
INSERT INTO `webhome_menu_template` VALUES ('49', '0', '2019-03-04 16:53:31', '2019-03-04 16:53:31', '0', '6', '斗地主', '斗地主', '326', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('50', '0', '2019-03-04 16:53:45', '2019-03-04 16:53:45', '0', '6', '炸金花', '炸金花', '327', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('51', '1', '2019-03-04 16:54:04', '2019-03-04 16:54:04', '0', '6', '1', '1', '328', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('52', '0', '2019-03-04 16:54:49', '2019-03-04 16:54:49', '0', '7', '保卫萝卜', '保卫萝卜', '329', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('53', '0', '2019-03-04 16:55:09', '2019-03-04 16:55:09', '0', '7', '连环夺宝', '连环夺宝', '330', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('54', '0', '2019-03-04 16:56:29', '2019-03-04 16:56:29', '0', '7', '跑车联盟', '跑车联盟', '331', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('55', '0', '2019-03-04 16:56:58', '2019-03-04 16:56:58', '0', '7', '三国群英传', '三国群英传', '332', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('56', '1', '2019-03-04 16:56:58', '2019-03-04 16:56:58', '0', '7', '三国群英传', '三国群英传', '332', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('57', '0', '2019-03-04 16:57:43', '2019-03-04 16:57:43', '0', '7', '坦克大战', '坦克大战', '333', '1', '0');
INSERT INTO `webhome_menu_template` VALUES ('58', '0', '2019-03-04 16:57:57', '2019-03-04 16:57:57', '0', '7', '象棋', '象棋', '334', '1', '0');

-- ----------------------------
-- Table structure for webhome_popular_games
-- ----------------------------
DROP TABLE IF EXISTS `webhome_popular_games`;
CREATE TABLE `webhome_popular_games` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `enclosure_id` bigint(22) DEFAULT '0' COMMENT '附件ID',
  `Introduction` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '介绍',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '类型(menu，game）',
  `type_id` bigint(22) NOT NULL COMMENT '类型Id',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '路径',
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='官网热门游戏';

-- ----------------------------
-- Records of webhome_popular_games
-- ----------------------------
INSERT INTO `webhome_popular_games` VALUES ('40', '0', '2019-01-29 19:56:09', '2019-03-12 10:08:22', '0', '二八杠33', '250', '11111', 'game', '1', '10101', '', '1');
INSERT INTO `webhome_popular_games` VALUES ('41', '0', '2019-01-29 19:56:09', '2019-04-08 17:04:20', '0', '二八杠44', '251', '11111', 'game', '11', '10101', '', '2');
INSERT INTO `webhome_popular_games` VALUES ('42', '0', '2019-01-29 19:56:09', '2019-03-12 10:09:59', '0', '二八杠', '255', '11111', 'game', '2', '10101', '', '6');
INSERT INTO `webhome_popular_games` VALUES ('43', '0', '2019-01-29 19:56:09', '2019-03-12 10:09:17', '0', '二八杠', '252', '11111', 'game', '6', '10101', '', '3');
INSERT INTO `webhome_popular_games` VALUES ('44', '0', '2019-01-29 19:56:09', '2019-02-16 21:02:23', '0', '二八杠', '253', '11111', 'menu', '2', '10101', '', '4');
INSERT INTO `webhome_popular_games` VALUES ('45', '0', '2019-01-29 19:56:09', '2019-02-16 21:02:33', '0', '二八杠', '254', '11111', 'menu', '2', '10101', '', '5');

-- ----------------------------
-- Table structure for webhome_promotions
-- ----------------------------
DROP TABLE IF EXISTS `webhome_promotions`;
CREATE TABLE `webhome_promotions` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `type_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '属性ID',
  `enclosure_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '附件ID',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `remake` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '备用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8 COMMENT='官网优惠活动';

-- ----------------------------
-- Records of webhome_promotions
-- ----------------------------
INSERT INTO `webhome_promotions` VALUES ('12', '1', '2019-01-28 17:13:25', '2019-03-06 09:33:24', '0', '13', '218', '<p><strong>亲爱的召唤师：</strong></p><p>菲奥娜的新手训练营活动已完成更新上线，所有游戏等级小于30级的角色可以继续参与活动。本次更新内容如下：</p><p>1、 在1—30级期间升级奖励的物品更新，原活动物品已经下线。新手召唤师将有机会体验到更多类型的英雄，皮肤，战利品。</p><p>2、 可优惠低折扣购买的英雄组合。</p><p>3、 官网和游戏客户端内均有入口可同步参与领取和购买。</p><h3><strong style=\"color: rgb(0, 0, 0);\">官网入口：</strong><a href=\"https://lol.qq.com/act/a20181228bootcamp/index.html\" target=\"_blank\" style=\"color: rgb(0, 0, 255);\"><strong>https://lol.qq.com/act/a20181228bootcamp/index.html</strong></a></h3>', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('13', '1', '2019-01-28 17:13:25', '2019-03-06 16:00:35', '0', '13', '379', '<h1><br></h1><h1>英雄联盟2019视频大赏2月1日首发</h1><p><br></p><p><span style=\"color: rgb(68, 68, 68);\">英雄联盟官方</span>&nbsp;2019-02-01</p><p>12:02:18&nbsp;字体：&nbsp;<span style=\"color: rgb(170, 170, 170);\">大</span>&nbsp;<span style=\"color: rgb(170, 170, 170);\">中</span>&nbsp;<span style=\"color: rgb(170, 170, 170);\">小</span></p><p><br></p><p>4300次阅读&nbsp;9</p><p><br></p><p>时值春节将近，《英雄联盟》即将奉上一场精彩炫目的视听盛宴，以作为给广大玩家们的新春开门礼物——英雄联盟2019视频大赏将于2月1日晚间20：00首发上线，最酷炫的操作、最欢乐的瞬间、最感人的时刻、最荣耀的冠军、最走心的演讲，关于过去的2018年，关于刚刚开启的2019赛季，年度视频大赏将与广大玩家共同领略那些经典镜头，共度新春佳节。</p><p><br></p><p><strong>英雄联盟2019视频大赏专题</strong><a href=\"https://lol.qq.com/act/a20190129spring/page7.html\" target=\"_blank\" style=\"color: rgb(187, 154, 108);\"><strong>&gt;&gt;点击查看</strong></a></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\"><img src=\"https://shp.qpic.cn/cfwebcap/0/22c350b65989e0a1967f33d99ff387ea/0?width=600&amp;height=301\" height=\"301\" width=\"600\"></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\">图1：英雄联盟2019视频大赏</p><p><br></p><p>除英雄联盟官方网站、掌上英雄联盟、客户端电视台三大官方渠道，届时广大玩家还可锁定斗鱼、虎牙、企鹅电竞、熊猫、</p><p>wegame、腾讯视频、腾讯体育、企鹅体育、企鹅号、NOW直播、新浪微博、哔哩哔哩、触手直播、快手直播观看视频首发。</p><p><br></p><p><br></p><p><strong style=\"color: rgb(53, 126, 131);\">《走位说》首秀将至 明星主播在线发红包</strong></p><p><br></p><p>《走位说》是英雄联盟首档主播演讲节目，首期节目将于2月1日正式上线。首期节目当中，余小C、节奏、王稳健、虎神将一一登场亮相，与玩家们分享他们自己的心路历程与“传奇故事”。</p><iframe class=\"ql-video ql-align-center\" frameborder=\"0\" allowfullscreen=\"true\" src=\"https://v.qq.com/txp/iframe/player.html?vid=h0833u7xnqx\" height=\"430\" width=\"600\"></iframe><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\">&nbsp;</p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\">走位说预告——余小C</p><p><br></p><p>节目当中，以一手德莱厄斯为人所知的余小C，将在节目当中在线向玩家们传授自己的游戏秘诀——竟是…多吃饭?!</p><p>唯一拥有LPL选手经历的节奏，将带领玩家们一同回顾S6时期令人影响深刻的BO10；“老王”王稳健的现场演讲，将分享自己从“阳台少年</p><p>”变身“腰间盘突出”的心路历程；而玩家心中“贾克斯”的化身虎神，将在节目中与大家分享他与武器大师的渊源。</p><p><br></p><p>与此同时，在节目首播的过程中，明星主播还将在线为观众们派送新年红包。而以首期节目为开端，《走位说》还将在未来邀请更多主播登上演讲舞台，与玩家们分享更多他们的亲身故事与电竞人生。</p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\"><img src=\"https://shp.qpic.cn/cfwebcap/0/baac136c821e8aefbd2b697f754dbb95/0?width=600&amp;height=183\" height=\"183\" width=\"600\"></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\">图2：走位说明星主播在线派发红包</p><p><br></p><p><br></p><p><strong style=\"color: rgb(53, 126, 131);\">精彩视频全放送 共同领略年度经典时刻</strong></p><p><br></p><p>除了全新亮相的《走位说》，十余部精心创作的英雄联盟视频也将在2月1日晚间与广大玩家见面。</p><p><br></p><p>由知名游戏视频制作人徐老师带来的《LOL搞笑精彩集锦》是一款英雄联盟视频集锦视频，由搞笑的失误操作和大神精彩操作共同组成，让观众在欣赏大神操作秀的同时，也能看到很多搞笑操作会心一笑。</p><p><br></p><p>此前已推出多个系列精彩视频的拳师七号将携</p><p>《冠军中年人》作为给大家的春节贺礼，《冠军中年人》是解说拳师7号制作的英雄联盟系列游戏节目。是以诙谐搞笑的解说风格，为大家带来的韩服双排视频。</p><p><br></p><p>作为一位深耕于游戏视频制作，专注于电竞赛事的视频制作者，吃瓜游戏人将从赛场视角出发，带来</p><p>《吃瓜盘点》系列视频之十大经典解说、夺冠瞬间、抢龙瞬间多部作品。而另一位 UP主鸡鸡夫斯基则将带来他关于无限乱斗的独家花式解读。</p><p><br></p><p>除了以上精选节目，LPL官方视频《英雄麦克疯》也将推出年度TOP10特集，与广大玩家一同回顾职业选手们在赛场上的“骚话</p><p>”与金句，而2018主播年度回顾则将带领大家一同用娱乐的方式对主播们2018的高光/搞笑时刻进行盘点，这里不仅有王者的操作，更有风骚的尴尬“青铜”时刻。</p><p><br></p><p>2月1日玩家 20:00，英雄联盟2019视频大赏即将首发上线，《英雄联盟》将与广大玩家共同期待，并共享其中的欢乐与精彩。英雄，一起去超越!</p>', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('14', '1', '2019-01-28 17:13:25', '2019-02-03 14:37:28', '0', '14', '220', '<p>黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗</p>', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('15', '1', '2019-01-28 17:13:25', '2019-02-03 14:38:45', '0', '15', '222', '<p>黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗</p>', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('16', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('18', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('19', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('20', '0', '2019-01-28 17:13:25', '2019-04-03 14:23:00', '0', '12', '548', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p>即日起加入AB棋牌，注册账号成为会员后，绑定好提款账号，完成第一次充值即可获得彩金。</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>首次充值</th>\n              <th>获得彩金</th>\n              <th>流水限制</th>\n            </tr>\n            <tr>\n              <td>100+</td>\n              <td>18</td>\n              <td rowspan=\"9\">一倍流水<p>即可提款</td>\n            </tr>\n            <tr>\n              <td height=\"42\">500+</td>\n              <td height=\"42\">58</td>\n            </tr>\n            <tr>\n              <td>1000+</td>\n              <td>88</td>\n            </tr>\n            <tr>\n              <td>5000+</td>\n              <td>158</td>\n            </tr>\n            <tr>\n              <td>10000+</td>\n              <td>388</td>\n            </tr>\n            <tr>\n              <td>30000+</td>\n              <td>888</td>\n            </tr>\n            <tr>\n              <td>50000+</td>\n              <td>1388</td>\n            </tr>\n            <tr>\n              <td>100000+</td>\n              <td>1888</td>\n            </tr>\n            <tr>\n              <td>300000+</td>\n              <td>5888</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：首存优惠</p>\n          <p>内容：符合条件请您在当日存款未投注之前联系在线客服申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、首存彩金只需1倍下注流水即可提款，每个会员只能申请一次；</p>\n          <p>2、符合申请条件会员请在成功存款后24小时内未投注前提交申请，如已进行投注或提款，则申请视为无效；</p>\n          <p>3、AB棋牌的所有优惠为玩家而设，如发现任何团体或个人，以不诚实方式套取红利或任何威胁、滥用公司优惠等行为，公司有权保留冻结、取消该团体或个人账户结余权力；</p>\n          <p>4、若有会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效文件，用以确认是享有该优惠的资质；</p>\n          <p>5、此活动可与其他优惠同时享有；</p>\n          <p>6、AB棋牌保留所有权利在任何时候都可以更改、停止、取消该优惠活动；</p>\n          <p>7、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '首存优惠');
INSERT INTO `webhome_promotions` VALUES ('21', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '14', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('22', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '15', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('23', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('25', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('26', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('27', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '12', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('28', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '14', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('29', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '15', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('30', '0', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('32', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('33', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('34', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '12', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('35', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '14', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('36', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '15', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('37', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('39', '1', '2019-01-28 17:13:25', '2019-01-28 17:13:25', '0', '13', '45', '黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗黑喂狗', '12321321321312323');
INSERT INTO `webhome_promotions` VALUES ('44', '0', '2019-04-03 14:21:20', '2019-04-03 14:21:20', '0', '12', '555', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动\n			详情</span></div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <font color=\"#FFFF00\">★</font>即日起下载AB棋牌【手机APP】成功注册的用户，即可获得38元彩金，尚未注册/存款的亲们强烈建议您注册/存款，越多优惠等您您哟！</div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>活动对像</th>\n              <th>手机APP下载</th>\n              <th>彩金/限制</th>\n              <th>出款上限</th> \n               <th>申请方式</th>\n            </tr>\n            <tr>\n              <td>全体新会员</td>\n              <td>苹果ios/安卓Android</td>\n              <td>下载APP送38元</td>\n              <td>满188元即可提款<p><b><font color=\"#FE0103\">提现秒到</font></b></td>\n              <td>优惠大厅<p>在线客服</td>\n            </tr>\n          </table>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <font color=\"#FFFF00\">★</font>下载APP,成功注册AB棋牌会员。<p>\n          <font color=\"#FFFF00\">★</font>完善个人信息，绑定提款银行账号，获取38元彩金，首次出款无法更改银行账号。</p>\n			<p>\n          <font color=\"#FFFF00\">★</font>活动专员审核后自动派送，多IP账号或多次申请无法再次赠送彩金（每个会员只能申请一次）。</p>\n			<p>\n          <font color=\"#FFFF00\">★</font>会员领取优惠后，第一次申请提款前不可更改银行账号及任何资料；每个IP只限一次如出现同IP多现象，则视为同一人，此IP下所有账号公司有权拒绝派送彩金。</div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p><font color=\"#FFFF00\">★</font>本活动最终解释权和裁决权归AB棋牌所有，AB棋牌保留修改暂停中止该优惠活动等所有权力。</p>\n        </div>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', 'APP优惠');
INSERT INTO `webhome_promotions` VALUES ('51', '0', '2019-04-03 14:32:49', '2019-04-03 14:32:49', '0', '12', '562', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>活动项目：棋牌游戏对战</p>\n			<p><font color=\"#FFFF00\">★</font>活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			<font color=\"#FFFF00\">★</font>活动内容：会员于AB棋牌游戏中， \n			按照美东时间计算当日盈利或亏损100元或以上，次日即可申请相应的加赠彩金，每日最高可达3555元。</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>当日盈利或者亏损</th>\n              <th>盈利加赠彩金</th>\n              <th >亏损加赠彩金</th>\n              <th >提款要求</th>\n            </tr>\n            <tr>\n              <td>300元+</td>\n              <td>3元</td>\n              <td>5元</td>\n\n              <td rowspan=\"13\">一倍流水<p>即可提款<tr>\n              <td>500元+</td>\n              <td>8元</td>\n              <td>15元</td>\n            </tr>\n            <tr>\n              <td height=\"41\">1000元+</td>\n              <td height=\"41\">15元</td>\n              <td height=\"41\">35元</td>\n            </tr>\n            <tr>\n              <td>5000元+</td>\n              <td>85元</td>\n              <td>155元</td>\n            </tr>\n            <tr>\n              <td>10000元+</td>\n              <td>155元</td>\n              <td>255元</td>\n            </tr>\n            <tr>\n              <td>30000元+</td>\n              <td>355元</td>\n              <td>555元</td>\n            </tr>\n            <tr>\n              <td>50000元+</td>\n              <td>855元</td>\n              <td>1355元</td>\n            </tr>\n            <tr>\n              <td>100000元+</td>\n              <td>1555元</td>\n              <td>2555元</td>\n            </tr>\n            <tr>\n              <td>300000元+</td>\n              <td>2555元</td>\n              <td>3555元</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>例：\n			当日在AB棋牌中盈利10000元，则次日可申请获得的得意彩金为155元。 以此类推！尚未注册/存款的亲们强烈建议您注册/存款，超多优惠等着您哟！</p>\n			        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：棋牌游戏对战</p>\n			<p>内容：符合条件申请的会员请在次日24小时内点击优惠活动办理大厅进行申请。</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金需一倍流水，即可申请提款；</p>\n          <p>\n						2、每个会员每日只能申请一次，请在次日24小时内发送申请，逾期视为放弃。</p>\n          <p>3、此项活动仅限在电子游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、所有因任何因素被取消之注单，或因中奖后彩金加码游戏所产生之单号，均不在计算范围之内。</p>\n          <p>5、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '棋牌优惠1');
INSERT INTO `webhome_promotions` VALUES ('58', '0', '2019-04-03 14:42:11', '2019-04-03 14:42:11', '0', '12', '569', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFF107\">★</font>活动项目：棋牌赔率彩金</p>\n			<p><font color=\"#FFF107\">★</font>活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			<font color=\"#FFFF00\">★</font>活动内容：AB棋牌全休会员投注棋牌单局投注产生以下中奖赔率即可申请活动加赠彩金，千万奖池，一触即发！奖金无上限！</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>棋牌专属</th>\n              <th>中奖赔率</th>\n              <th width=\"184\">加赠彩金</th>\n              <th>彩金上线</th>\n              <th>提款要求</th>\n\n\n\n            </tr>\n            <tr>\n              <td rowspan=\"5\">棋牌对战</td>\n              <td>派送彩金÷投注金额≧3倍</td>\n              <td>下注金额×1倍</td>\n              <td rowspan=\"4\">无上限</td>\n              <td rowspan=\"4\">无需流水<p>即可提款</td>\n\n\n            </tr>\n            <tr>\n              <td>派送彩金÷投注金额≧6倍</td>\n              <td>下注金额×2倍</td>\n            </tr>\n            <tr>\n              <td>派送金额÷投注金额≧15倍</td>\n              <td>下注金额×5倍</td>\n            </tr>\n            <tr>\n              <td>派送金额÷投注金额≧30倍</td>\n              <td>下注金额×10倍</td>\n            </tr>\n\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n<font color=\"#FFFF00\">★</font>例：会员单局投注棋牌100，盈利（派彩）600元，则中奖赔率为600÷100=6倍，即可获得100×2倍=200元加赠 \n彩金</div>\n\n<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：棋牌游戏二重奏</p>\n          <p>内容：符合申请条件的会员请在当日24小时内联系在线客服申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">\n			活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金无需流水，即可申请提款；</p>\n          <p>2、每位会员每日仅限申请一次，天数按美东时间来计算；</p>\n          <p>3、此项活动仅限在棋牌游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、符合申请条件的会员请在当日24小时内进行申请，预期视为自动放弃优惠；</p>\n          <p>5、所有因任何因素被取消注单，或因中奖后彩金加注码游戏所产生的单号，均不在计算内；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '棋牌优惠2');
INSERT INTO `webhome_promotions` VALUES ('65', '0', '2019-04-03 14:42:56', '2019-04-03 14:42:56', '0', '12', '576', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p>*活动项目：超级棋牌7天乐</p>\n			<p>*活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			*活动内容：AB棋牌新老会员在美东时间每周一至周日七天进行棋牌游戏累积有效投注达5000以上，即可获得周周彩金，彩金最高8888元。</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>活动时间</th>\n              <th>周有效投注</th>\n              <th width=\"184\">周周超级彩金</th>\n              <th>流水要求</th>\n              \n            </tr>\n            <tr>\n              <td rowspan=\"12\">美东时间<p>每周一至周日</td>\n              <td>5000或以上</td>\n              <td>3</td>\n              <td rowspan=\"11\">无需流水<p>即可提款</td>\n				</td>\n\n            </tr>\n            <tr>\n              <td>1万或以上</td>\n              <td>18</td>\n            </tr>\n            <tr>\n              <td>10万或以上</td>\n              <td>88</td>\n            </tr>\n            <tr>\n              <td>20万或以上</td>\n              <td>188</td>\n            </tr>\n            <tr>\n              <td>50万或以上</td>\n              <td>388</td>\n            </tr>\n            <tr>\n              <td>100万或以上</td>\n              <td>888</td>\n            </tr>\n            <tr>\n              <td>300万或以上</td>\n              <td>1888</td>\n            </tr>\n            <tr>\n              <td>500万或以上</td>\n              <td>3888</td>\n            </tr>\n            <tr>\n              <td>1000万或以上</td>\n              <td>8888</td>\n            </tr>\n            <tr>\n              <td>50000000或以上</td>\n              <td>8888</td>\n            </tr>         \n            <tr>\n              <td>100000000或以上</td>\n              <td>88888</td>\n            </tr>            \n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p>★会员在美东时间每周一至周日七天进行棋牌游戏累计有效投注达20万+，则可以获得188元周周彩金。</p>\n        </div>\n        \n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：超级棋牌7天乐</p>\n          <p>内容：符合申请条件的会员请在每周周二当天24小时内联系在线申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金无需流水，即可申请提款；</p>\n          <p>2、每位会员每周仅限申请一次，天数按照美东时间来计算；</p>\n          <p>3、此项活动仅限在棋牌游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、每个会员每周仅限申请一次，天数按照北京时间计算；</p>\n          <p>5、符合申请条件的会员请在每周星期二当天24小时内进行申请，预期视为自动放弃优惠；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '棋牌优惠3');
INSERT INTO `webhome_promotions` VALUES ('72', '0', '2019-04-03 14:44:03', '2019-04-03 14:44:03', '0', '12', '583', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p>★活动项目：棋牌日闯关</p>\n			<p>★活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			★活动内容：即日起，会员投注棋牌游戏当日累计有效投注达到以下关卡门槛，即可参与棋牌活动，关关都有奖金赠送；达到通关，可累计挽回28019元。</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>关卡</th>\n              <th>累计有效投注</th>\n              <th width=\"184\">可获奖金</th>\n              <th>累计可获奖金</th>\n              <th>取款要求</th>\n\n\n            </tr>\n            <tr>\n              <td>第一关</td>\n              <td>2000+</td>\n              <td>6</td>\n              <td>6</td>\n              <td rowspan=\"12\">一倍流水<p>即可提款</td>\n\n            </tr>\n            <tr>\n              <td>第二关</td>\n              <td>5000+</td>\n              <td>13</td>\n              <td>19</td>\n            </tr>\n            <tr>\n              <td>第三关</td>\n              <td>10000+</td>\n              <td>20</td>\n              <td>39</td>\n            </tr>\n            <tr>\n              <td>第四关</td>\n              <td>3万</td>\n              <td>50</td>\n              <td>89</td>\n            </tr>\n            <tr>\n              <td>第五关</td>\n              <td>10万</td>\n              <td>130</td>\n              <td>219</td>\n            </tr>\n            <tr>\n              <td>第六关</td>\n              <td>30万</td>\n              <td>300</td>\n              <td>519</td>\n            </tr>\n            <tr>\n              <td>第七关</td>\n              <td>50万</td>\n              <td>600</td>\n              <td>1119</td>\n            </tr>\n            <tr>\n              <td>第八关</td>\n              <td>100万</td>\n              <td>900</td>\n              <td>2019</td>\n            </tr>\n            <tr>\n              <td>第九关</td>\n              <td>300万</td>\n              <td>2000</td>\n              <td>4019</td>\n            </tr>\n            <tr>\n              <td>第十关</td>\n              <td>600万</td>\n              <td>400</td>\n              <td>8019</td>\n            </tr>         \n            <tr>\n              <td>第十一关</td>\n              <td>1000万</td>\n              <td>7000</td>\n              <td>15019</td>\n            </tr>   \n            <tr>\n              <td>第十二关</td>\n              <td>2000万</td>\n              <td>13000</td>\n              <td>28019</td>\n            </tr>  \n                    \n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p>★例：会员有效投注达到10万（第五关）即可获得累计奖金6+13+20+50+130=219元！！以此类推</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：棋牌日闯关</p>\n			<p>内容：符合申请条件的会员请在次日24小时内联系在线申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金需一倍流水，即可申请提款；</p>\n          <p>2、每位会员每关只仅限申请一次，天数按照美东时间来计算；</p>\n          <p>3、此项活动仅限在棋牌游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、每个会员每日每关仅限申请一次，天数按照北京时间计算；</p>\n          <p>5、符合申请条件的会员请在次日24小时内进行申请，预期视为自动放弃优惠；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '棋牌优惠4');
INSERT INTO `webhome_promotions` VALUES ('79', '0', '2019-04-03 14:45:36', '2019-04-03 14:45:36', '0', '12', '590', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>活动项目：抢庄牛牛加赠彩金</p>\n			<p><font color=\"#FFFF00\">★</font>活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			<font color=\"#FFFF00\">★</font>活动内容：会员只要在棋牌抢庄牛牛游戏过程中拿到指定的牌型，即可获得牛牛加赠彩金</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>房型/牌型</th>\n              <th>牛牛</th>\n              <th>四牛牛</th>\n              <th>五花牛</th>  \n              <th>四炸</th>\n              <th >五小牛</th>\n              <th >流水要求</th>\n\n\n            </tr>\n            <tr>\n              <td>初级房</td>\n              <td>6元</td>\n              <td>16</td>\n              <td>26</td>\n               <td>36</td>\n              <td>86</td>\n              <td rowspan=\"5\">无需流水<p>即可取款</td>\n\n            </tr>\n            <tr>\n              <td>中级房</td>\n              <td>8元</td>\n              <td>18</td>\n              <td>38</td>\n               <td>58</td>\n              <td>188</td>\n            </tr>\n            <tr>\n              <td>高级房</td>\n              <td>19元</td>\n              <td>39</td>\n              <td>69</td>\n               <td>99</td>\n              <td>399</td>\n            </tr>\n            <tr>\n              <td>至尊房</td>\n              <td>38元</td>\n              <td>58</td>\n              <td>98</td>\n               <td>158</td>\n              <td>588</td>\n            </tr>\n                    \n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>注：体验房与赠送彩金娱乐玩家不可参与。</p>\n			<p><font color=\"#FFFF00\">★</font>注：本活动按照美东时间当日24小时内领取，每日仅限申请一次！</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：棋牌日闯关</p>\n			<p>内容：符合申请条件的会员请在当日24小时内联系在线客服申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金无需流水，即可申请提款；</p>\n          <p>\n			2、所有因任何因素被取消的注单，或因中奖后彩金加码游戏所产生的单号，均不在计算范围之内；</p>\n          <p>3、此项活动仅限在棋牌抢庄游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、每个会员每日每关仅限申请一次，天数按照北京时间计算；</p>\n          <p>5、符合申请条件的会员请在次日24小时内进行申请，预期视为自动放弃优惠；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '棋牌优惠5');
INSERT INTO `webhome_promotions` VALUES ('86', '0', '2019-04-03 14:46:17', '2019-04-03 14:46:17', '0', '12', '597', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>活动项目：抢庄牛牛加赠彩金</p>\n			<p><font color=\"#FFFF00\">★</font>活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			<font color=\"#FFFF00\">★</font>活动内容：会员只要在棋牌抢庄牛牛游戏过程中拿到指定的牌型，即可获得牛牛加赠彩金</p>\n			<p>\n			<font color=\"#FFFF00\" size=\"5\"><b>双重豪礼一</b>：</font></p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>当日盈利</th>\n              <th>好运金</th>\n              <th >流水要求</th>\n\n\n            </tr>\n            <tr>\n              <td>500+</td>\n              <td>5元</td>\n              <td rowspan=\"9\">一倍流水<p>即可取款</td>\n            </tr>\n            <tr>\n              <td>1000+</td>\n              <td>18元</td>\n            </tr>\n            <tr>\n              <td>3000+</td>\n              <td>38元</td>\n            </tr>\n            <tr>\n              <td>5000+</td>\n              <td>88元</td>\n            </tr>\n            <tr>\n              <td>10000+</td>\n              <td>188元</td>\n            </tr>\n            <tr>\n              <td>30000+</td>\n              <td>388元</td>\n            </tr>\n            <tr>\n              <td>50000+</td>\n              <td>588元</td>\n            </tr>\n            <tr>\n              <td>100000+</td>\n              <td>888元</td>\n            </tr>\n            <tr>\n              <td>150000+</td>\n              <td>1188元</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>注：按照美东时间计算当日在房卡场中盈利500元以上，次日即可申请相应的好运礼金，每日高达1188元</p>\n			<p><font color=\"#FFFF00\">★</font>例：会员当日在房卡场中盈利金额为30000元，次日即可申请相应好运礼金388元！</p>\n			<p><font size=\"5\" color=\"#FFFF00\">双重豪礼二：</font></p>\n        </div>\n        \n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>当日亏损</th>\n              <th>救援金</th>\n              <th >流水要求</th>\n\n\n            </tr>\n            <tr>\n              <td>200+</td>\n              <td>5元</td>\n              <td rowspan=\"11\">一倍流水<p>即可取款</td>\n            </tr>\n            <tr>\n              <td>500+</td>\n              <td>18元</td>\n            </tr>\n            <tr>\n              <td>1000+</td>\n              <td>38元</td>\n            </tr>\n            <tr>\n              <td>3000+</td>\n              <td>88元</td>\n            </tr>\n            <tr>\n              <td>5000+</td>\n              <td>188元</td>\n            </tr>\n            <tr>\n              <td>10000+</td>\n              <td>588元</td>\n            </tr>\n            <tr>\n              <td>30000+</td>\n              <td>1288元</td>\n            </tr>\n            <tr>\n              <td>50000+</td>\n              <td>3888元</td>\n            </tr>\n            <tr>\n              <td>100000+</td>\n              <td>5888元</td>\n            </tr>\n            <tr>\n              <td>150000+</td>\n              <td>8888元</td>\n            <tr>\n              <td>200000+</td>\n              <td>11188元</td> \n          </table>\n        </div>\n <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>注：按照美东时间计算当日在房卡场斗地主中亏损200元以上，次日即可申请相应的救援金!每日高达11188元。</p>\n			<p><font color=\"#FFFF00\">★</font>例：会员当日在房卡场斗地主中亏损金额为30000元，次日即可申请相应救援礼金1288元！</p>\n        </div>\n\n        \n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：棋牌斗地主</p>\n			<p>内容：符合申请条件的会员请在当日24小时内联系在线客服申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金需一倍流水，即可申请提款；</p>\n          <p>\n			2、所有因任何因素被取消的注单，或因中奖后彩金加码游戏所产生的单号，均不在计算范围之内；</p>\n          <p>3、此项活动仅限在棋牌斗地主游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、每个会员仅限申请一次，天数按照北京时间计算；</p>\n          <p>5、符合申请条件的会员请在次日24小时内进行申请，预期视为自动放弃优惠；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '棋牌优惠6');
INSERT INTO `webhome_promotions` VALUES ('93', '0', '2019-04-03 14:58:36', '2019-04-03 14:58:36', '0', '12', '604', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>活动项目：捕鱼达人天天得意彩金</p>\n			<p><font color=\"#FFFF00\">★</font>活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			<font color=\"#FFFF00\">★</font>活动内容：会员进行“捕鱼游戏”投注，不计输赢，每天达到5000以上的有效投注，即可获得得意彩金，最高12888元，您还在等什么呢？</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>当日 有效累计</th>\n              <th>天天得意彩金</th>\n              <th >提款要求</th>\n              <th >申请方式</th>\n\n            </tr>\n            <tr>\n              <td>5千+</td>\n              <td>18元</td>\n              <td rowspan=\"9\">一倍流水<p>即可取款</td>\n              <td rowspan=\"9\">联系在线客服</td>\n            </tr>\n            <tr>\n              <td>1万+</td>\n              <td>38元</td>\n            </tr>\n            <tr>\n              <td>3万+</td>\n              <td>128元</td>\n            </tr>\n            <tr>\n              <td>6万+</td>\n              <td>358元</td>\n            </tr>\n            <tr>\n              <td>20万+</td>\n              <td>1288元</td>\n            </tr>\n            <tr>\n              <td>100万+</td>\n              <td>3888元</td>\n            </tr>\n            <tr>\n              <td>500万+</td>\n              <td>5888元</td>\n            </tr>\n            <tr>\n              <td>800万+</td>\n              <td>8888元</td>\n            </tr>\n            <tr>\n              <td>1000万+</td>\n              <td>12888元</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>例：当日在捕鱼游戏中有效投注5000，则次日即可获得得意彩金为18元。尚未注册/存款的亲们强烈建议您注册/存款，超多优惠等着您哟！</p>\n			        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：捕鱼游戏天天得意金</p>\n			<p>内容：符合申请条件的会员请在次日24小时内联系在线客服申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金需一倍流水，即可申请提款；</p>\n          <p>\n			2、所有因任何因素被取消的注单，或因中奖后彩金加码游戏所产生的单号，均不在计算范围之内；</p>\n          <p>3、此项活动仅限在捕鱼游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、每个会员仅限申请一次，天数按照北京时间计算；</p>\n          <p>5、符合申请条件的会员请在次日24小时内进行申请，预期视为自动放弃优惠；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '捕鱼优惠1');
INSERT INTO `webhome_promotions` VALUES ('100', '0', '2019-04-03 14:59:42', '2019-04-03 14:59:42', '0', '12', '618', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>活动项目：捕鱼达人双重回馈</p>\n			<p><font color=\"#FFFF00\">★</font>活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			<font color=\"#FFFF00\">★</font>活动内容：所有新老会员进行捕鱼游戏，单局投注产生以下中奖赔率情况即可获得加赠彩金，千万奖池，一触即送！彩金无上限！</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>当日 盈利或亏损</th>\n              <th>盈利加赠彩金</th>\n              <th >亏损加赠彩金</th>\n              <th >提款要求</th>\n\n            </tr>\n            <tr>\n              <td>200+</td>\n              <td>3元</td>\n              <td>8元</td>\n\n              <td rowspan=\"9\">一倍流水<p>即可提款</td>\n            </tr>\n            <tr>\n              <td>500+</td>\n              <td>5元</td>\n              <td>15元</td>\n            </tr>\n            <tr>\n              <td>1000+</td>\n              <td>10元</td>\n              <td>30元</td>\n            </tr>\n            <tr>\n              <td>3000+</td>\n              <td>50元</td>\n              <td>80元</td>\n            </tr>\n            <tr>\n              <td>5000+</td>\n              <td>80元</td>\n              <td>150元</td>\n            </tr>\n            <tr>\n              <td>10000+</td>\n              <td>200元</td>\n              <td>380元</td>\n            </tr>\n            <tr>\n              <td>30000+</td>\n              <td>500元</td>\n              <td>1000元</td>\n            </tr>\n            <tr>\n              <td>50000+</td>\n              <td>1000元</td>\n              <td>2888元</td>\n            </tr>\n            <tr>\n              <td>100000+</td>\n              <td>2888元</td>\n              <td>6888元</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>例：当日在捕鱼游戏中盈利10000元，则次日可申请相应彩金为200元，亏损可申请380元。</p>\n			        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：捕鱼游戏双重回馈</p>\n			<p>内容：符合申请条件的会员请在次日24小时内联系在线客服申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金需一倍流水，即可申请提款；</p>\n          <p>\n			2、所有因任何因素被取消的注单，或因中奖后彩金加码游戏所产生的单号，均不在计算范围之内；</p>\n          <p>3、此项活动仅限在捕鱼游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、每个会员仅限申请一次，天数按照北京时间计算；</p>\n          <p>5、符合申请条件的会员请在次日24小时内进行申请，预期视为自动放弃优惠；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '捕鱼优惠2');
INSERT INTO `webhome_promotions` VALUES ('107', '0', '2019-04-03 15:00:16', '2019-04-03 15:00:16', '0', '12', '625', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>活动项目：捕鱼闯关游戏</p>\n			<p><font color=\"#FFFF00\">★</font>活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			<font color=\"#FFFF00\">★</font>活动内容：所有会员凡在活动时间投注“捕鱼游戏”，累积有效投注达到以下关卡门槛，即可参与捕鱼大闯关活动，关关都有奖金赠送；达到通关，可累计抱回28888元。</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>关卡</th>\n              <th>累积有效投注</th>\n              <th >可获彩金</th>\n              <th >累计可获彩金</th>\n              <th >提款要求</th>\n            </tr>\n            <tr>\n              <td>第一关</td>\n              <td>3000+</td>\n              <td>5元</td>\n              <td>5元</td>\n\n              <td rowspan=\"13\">　一倍流水<p>即可提款</p>\n				<p>　</td>\n            </tr>\n            <tr>\n              <td>第二关</td>\n              <td>5000+</td>\n              <td>13元</td>\n              <td>18元</td>\n            </tr>\n            <tr>\n              <td height=\"41\">第三关</td>\n              <td height=\"41\">1万+</td>\n              <td height=\"41\">28元</td>\n              <td height=\"41\">46元</td>\n            </tr>\n            <tr>\n              <td>第四关</td>\n              <td>3万+</td>\n              <td>58元</td>\n              <td>104元</td> \n            </tr>\n            <tr>\n              <td>第五关</td>\n              <td>10万+</td>\n              <td>128元</td>\n              <td>232元</td>\n            </tr>\n            <tr>\n              <td>第六关</td>\n              <td>30万+</td>\n              <td>328元</td>\n              <td>560元</td>\n            </tr>\n            <tr>\n              <td>第七关</td>\n              <td>50万+</td>\n              <td>628元</td>\n              <td>1118元</td>\n            </tr>\n            <tr>\n              <td>第八关</td>\n              <td>100万+</td>\n              <td>918元</td>\n              <td>2106元</td> \n            </tr>\n            <tr>\n              <td>第九关</td>\n              <td>300万+</td>\n              <td>2018元</td>\n              <td>4124元</td>\n            </tr>\n            <tr>\n              <td>第十关</td>\n              <td>500万+</td>\n              <td>3988元</td>\n              <td>8112元</td>\n            </tr>\n            <tr>\n              <td>第十一关</td>\n              <td>1000万+</td>\n              <td>7888元</td>\n              <td>16000元</td>\n            </tr>\n            <tr>\n              <td>第十二关</td>\n              <td>2000万+</td>\n              <td>12888元</td>\n              <td>28888元</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>例：当日在捕鱼游戏中有效投注达10000元，则次日可申请相应彩金为28元，\n			累计可得46元。尚未注册/存款的亲们强烈建议您注册/存款，超多优惠等着您哟！</p>\n			        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：捕鱼游戏闯关赠送彩金</p>\n			<p>内容：符合申请条件的会员请在次日24小时内联系在线客服申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金需一倍流水，即可申请提款；</p>\n          <p>\n			2、所有因任何因素被取消的注单，或因中奖后彩金加码游戏所产生的单号，均不在计算范围之内；</p>\n          <p>3、此项活动仅限在捕鱼游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、每个会员仅限申请一次，天数按照北京时间计算；</p>\n          <p>5、符合申请条件的会员请在次日24小时内进行申请，预期视为自动放弃优惠；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '捕鱼优惠3');
INSERT INTO `webhome_promotions` VALUES ('114', '0', '2019-04-03 15:23:31', '2019-04-03 15:26:19', '0', '12', '632', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p>平台会员每天可享有一次赠送18元“以小博大”的机会，只要10元≤每日存款≤11元，存款前账号余额低于5元即可申请该优惠，出款无上限！</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>存款金额</th>\n              <th>赠送彩金</th>\n              <th>游戏限制</th>\n              <th>流水限制</th>\n              <th>提款要求</th>\n            </tr>\n            <tr>\n              <td height=\"40\">10+</td>\n              <td height=\"40\">18</td>\n              <td height=\"40\">电子游戏</td>\n              <td rowspan=\"8\">一倍流水</td>\n              <td rowspan=\"8\">180</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p>注意：此活动只支持所有电子游艺，若投注其他游戏将不计算有效投注，扣除彩金及彩金产生的盈利，账号额度低于5元再次存款则不受限制。</p>\n			<p>\n			已申请该优惠活动的会员，此次将失去享有公司其他优惠活动的申请资格（账号额度低于5元，再次存款则不受限制）。尚未注册/存款的亲们强烈建议您注册/存款，超多优惠等着您哟！</p>\n        </div>\n        \n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：电子游艺以小博大</p>\n          <p>内容：符合申请条件的会员请于当日24小时内联系在线客服，逾期视为放弃。</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所赠送的彩金只需一倍有效投注，即可提款；</p>\n          <p>2、该优惠不与官网其他优惠共享（公司入款优惠和天天返水除外）；</p>\n          <p>3、按照美东时间计算，一天只能申请一次，请在成功充值后24小时内进行申请，逾期视为无效；</p>\n          <p>4、申请该彩金之前，必须先绑定出款银行帐号才具备申请资格，且银行卡，手机号码归属地，登入IP须为同一个地区（无申请则不必为同一地区）；</p>\n          <p>5、符合申请条件的会员请在次日24小时内进行申请，预期视为自动放弃优惠；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '电子优惠1');
INSERT INTO `webhome_promotions` VALUES ('121', '0', '2019-04-03 15:24:16', '2019-04-03 15:24:16', '0', '12', '639', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>会员于【AB棋牌】进行电子游艺游戏中，存款出现负盈利，即可申请笔笔救援金，笔笔存，笔笔送，救援金金额无上限。</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>单笔存款金额</th>\n              <th>当账户余额</th>\n              <th>救援金获得比例</th>\n              <th>彩金无上限</th>\n              <th>流水限制</th>\n            </tr>\n            <tr>\n              <td height=\"40\">50元+</td>\n              <td height=\"40\">低于5元</td>\n              <td height=\"40\">5%</td>\n              <td rowspan=\"8\">无上限</td>\n              <td rowspan=\"8\">达到五倍流水即可提款</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n			<p><font color=\"#FFFF00\">★</font>例： \n			会员单笔存款1000元，在电子游艺游戏中出现负盈利，当余额低于5元，即可申请，可获1000*5%=50元救援金，达到5倍流水即可提款。</p>\n        </div>\n        \n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：电子游艺救援金</p>\n          <p>内容：符合申请条件的会员请于当日24小时内联系在线客服，逾期视为放弃。</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获得彩金只需五倍流水即可申请提款。</p>\n          <p>2、此活动不与官网任何存送活动同时进行。</p>\n          <p>3、援金每笔存款结算一次，请在账户低于5元的时候立刻申请，若玩家该笔存款失利后，直接再存款并进行下注则视为主动放弃上笔存款的救援金。</p>\n          <p>4、申请该彩金之前，必须先绑定出款银行帐号才具备申请资格，且银行卡，手机号码归属地，登入IP须为同一个地区（无申请则不必为同一地区）；</p>\n          <p>5、符合条件的会员请在未进行下次存款之前，联系在线客服申请！申请成功后即可再次存款！</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '电子优惠2');
INSERT INTO `webhome_promotions` VALUES ('128', '0', '2019-04-03 15:25:29', '2019-04-03 15:25:29', '0', '12', '646', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>活动项目：电子游艺闯关游戏</p>\n			<p><font color=\"#FFFF00\">★</font>活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			<font color=\"#FFFF00\">★</font>活动内容：所有会员凡在活动时间投注“电子游艺”，累积有效投注达到以下关卡门槛，即可参与\n			电子游艺大闯关活动，关关都有奖金赠送；达到通关，可累计抱回312188元。</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>关卡</th>\n              <th>累积有效投注</th>\n              <th >可获彩金</th>\n              <th >累计可获彩金</th>\n              <th >每期活动时间</th>\n              <th >提款要求</th>\n            </tr>\n            <tr>\n              <td>第一关</td>\n              <td>1万+</td>\n              <td>6元</td>\n              <td>6元</td>\n\n              <td rowspan=\"13\">每周一00：00<p>至</p>\n				<p>每周日23:59</p>\n				<p>（美东时间）</p>\n				<td rowspan=\"13\">一倍流水<p>即可提款</tr>\n            <tr>\n              <td>第二关</td>\n              <td>5万+</td>\n              <td>16元</td>\n              <td>22元</td>\n            </tr>\n            <tr>\n              <td height=\"41\">第三关</td>\n              <td height=\"41\">10万+</td>\n              <td height=\"41\">66元</td>\n              <td height=\"41\">88元</td>\n            </tr>\n            <tr>\n              <td>第四关</td>\n              <td>50万+</td>\n              <td>200元</td>\n              <td>288元</td> \n            </tr>\n            <tr>\n              <td>第五关</td>\n              <td>100万+</td>\n              <td>400元</td>\n              <td>688元</td>\n            </tr>\n            <tr>\n              <td>第六关</td>\n              <td>500万+</td>\n              <td>1500元</td>\n              <td>2188元</td>\n            </tr>\n            <tr>\n              <td>第七关</td>\n              <td>2000万+</td>\n              <td>5000元</td>\n              <td>7188元</td>\n            </tr>\n            <tr>\n              <td>第八关</td>\n              <td>5000万+</td>\n              <td>15000元</td>\n              <td>22188元</td> \n            </tr>\n            <tr>\n              <td>第九关</td>\n              <td>1亿+</td>\n              <td>4万元</td>\n              <td>62188元</td>\n            </tr>\n            <tr>\n              <td>第十关</td>\n              <td>3亿+</td>\n              <td>10万元</td>\n              <td>162188元</td>\n            </tr>\n            <tr>\n              <td>通关</td>\n              <td>5亿+</td>\n              <td>15万元</td>\n              <td>312188元</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>例：会员累积有效投注达到1000000+（第五关）即可获得累计奖金 \n			（6+16+66+200+400）=688元！ 以此类推！尚未注册/存款的亲们强烈建议您注册/存款，超多优惠等着您哟！</p>\n			        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：捕鱼游戏闯关赠送彩金</p>\n			<p>内容：符合申请条件的会员请在次日24小时内联系在线客服申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金需一倍流水，即可申请提款；</p>\n          <p>\n			2、有效投注只计算当期，每期以（美东时间）周一至周日为一期，每期结束后，会员需重新累计打码进行申请彩金</p>\n          <p>3、此项活动仅限在电子游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、每个会员仅限申请一次，天数按照美东时间计算；</p>\n          <p>5、符合申请条件的会员请在每周二24小时内进行申请，逾期视为自动放弃优惠；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '电子优惠3');
INSERT INTO `webhome_promotions` VALUES ('135', '0', '2019-04-03 15:27:04', '2019-04-03 15:27:04', '0', '12', '653', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>活动项目：电子悬赏彩金</p>\n			<p><font color=\"#FFFF00\">★</font>活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			<font color=\"#FFFF00\">★</font>活动内容：会员进行“电子游艺”按照美东时间计算每款游戏完成5000以上的有效投注即算完成一个任务，完成2个以上即可获得最高888元的悬赏彩金。</p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th >每款游戏有效投注</th>\n              <th>完成任务数量</th>\n              <th>可悬赏彩金</th>\n              <th >流水限制</th>\n            </tr>\n            <tr>\n              <td rowspan=\"13\">5000+</td>\n              <td>2个</td>\n              <td>18元</td>\n              <td rowspan=\"13\">一倍流水<p>即可提款</td>\n              </tr>\n            <tr>\n              <td>5个</td>\n              <td>88元</td>\n            </tr>\n            <tr>\n              <td height=\"41\">8个</td>\n              <td height=\"41\">188元</td>\n            </tr>\n            <tr>\n              <td>15个</td>\n              <td>388元</td> \n            </tr>\n            <tr>\n              <td>25个</td>\n              <td>888元</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>例：按照美东时间计算该天内完成5款电子每一款游戏有效投注额均达到5000以上，即可获得88元悬赏彩金！</p>\n			        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：电子悬赏彩金</p>\n			<p>内容：符合申请条件的会员请在次日24小时内联系在线客服申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金需一倍流水，即可申请提款；</p>\n          <p>\n						2、所有电子游艺桌面游戏及大型机台游戏的有效投注不参与优惠。</p>\n          <p>3、此项活动仅限在电子游戏，其他游戏项目有效投注不计算在内；</p>\n          <p>4、每位会员每天仅限申请一次，天数按照美东时间进行计算；</p>\n          <p>5、符合申请条件的会员请在次日24小时内进行申请，逾期视为自动放弃优惠；</p>\n          <p>6、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '电子优惠4');
INSERT INTO `webhome_promotions` VALUES ('142', '0', '2019-04-03 15:27:37', '2019-04-03 15:27:37', '0', '12', '660', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>活动项目：电子天天签到</p>\n			<p><font color=\"#FFFF00\">★</font>活动时间：2018年01月01日起（结束以官网通知为准）</p>\n			<p>\n			<font color=\"#FFFF00\">★</font>活动内容： \n			会员当天存款100元在第一天电子游艺有效投注5000，即可申请8元奖金，会员第二天存款100元在电子游艺有效投注6622，即可申请18元奖金。</p>\n			<p>\n			<font size=\"5\" color=\"#FFFF00\">★活动一</font></p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th >活动对象</th>\n              <th>签到天数</th>\n              <th>赠送彩金</th>\n              <th >提款要求</th>\n            </tr>\n            <tr>\n              <td rowspan=\"13\">每天存款100元+<p>且打码达5000元+</td>\n              <td>第一天</td>\n              <td>8元</td>\n              <td rowspan=\"13\">一倍流水<p>即可提款</td>\n              </tr>\n            <tr>\n              <td>第二天</td>\n              <td>18元</td>\n            </tr>\n            <tr>\n              <td height=\"41\">第三天</td>\n              <td height=\"41\">28元</td>\n            </tr>\n            <tr>\n              <td>第四天</td>\n              <td>38元</td> \n            </tr>\n            <tr>\n              <td>第五天</td>\n              <td>48元</td>\n            </tr>\n            <tr>\n              <td>第六天</td>\n              <td>58元</td>\n            </tr>\n            <tr>\n              <td>第七天</td>\n              <td>68元</td>\n            </tr>\n            <tr>\n              <td>连续签到7天额外嘉奖</td>\n              <td>78元</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>例：会员每天存款100元完成电子游艺有效投注5018，连续签到7天不间断，即可获得额外嘉奖78元。</p>\n			<p><font color=\"#FFFF00\" size=\"5\">★活动二</font></p>\n        </div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th >活动对象</th>\n              <th>签到天数</th>\n              <th>赠送彩金</th>\n              <th >提款要求</th>\n            </tr>\n            <tr>\n              <td rowspan=\"13\">每天存款1000元+<p>且打码达20000元+</td>\n              <td>第一天</td>\n              <td>28元</td>\n              <td rowspan=\"13\">一倍流水<p>即可提款</td>\n              </tr>\n            <tr>\n              <td>第二天</td>\n              <td>38元</td>\n            </tr>\n            <tr>\n              <td height=\"41\">第三天</td>\n              <td height=\"41\">58元</td>\n            </tr>\n            <tr>\n              <td>第四天</td>\n              <td>78元</td> \n            </tr>\n            <tr>\n              <td>第五天</td>\n              <td>88元</td>\n            </tr>\n            <tr>\n              <td>第六天</td>\n              <td>128元</td>\n            </tr>\n            <tr>\n              <td>第七天</td>\n              <td>178元</td>\n            </tr>\n            <tr>\n              <td>连续签到7天额外嘉奖</td>\n              <td>288元</td>\n            </tr>\n          </table>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p><font color=\"#FFFF00\">★</font>例：会员每天存款1000元完成电子游艺有效投注20000，连续签到7天不间断，即可获得嘉奖288元。</p>\n			        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">申请方式</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <a href=\"https://e-141816.chatnow.meiqia.com/dist/standalone.html\" target=\"_bank\" style=\"background-color:#fff107;color:#fe0103;\">联系在线客服进行申请</a>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>主题：电子天天签到</p>\n			<p>内容：符合申请条件的会员请在次日24小时内联系在线客服申请</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所获彩金需一倍流水，即可申请提款。</p>\n          <p>\n						2、每位会员每天仅限申请一次，天数按照美东时间计算。</p>\n          <p>3、捕鱼游戏，桌面游戏的有效投注不计算在内。</p>\n          <p>4、符合申请条件的会员请在次日24小时内进行申请，逾期视为自动放弃优惠；</p>\n          <p>5、参与该优惠表示您同意《一般优惠规则与条款》；</p>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '电子优惠5');
INSERT INTO `webhome_promotions` VALUES ('149', '0', '2019-04-03 15:31:05', '2019-04-03 15:31:05', '0', '12', '667', '<div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">活动\n			详情</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\" >\n          <p style=\"text-align: center\"><font color=\"#FFFF00\" size=\"5\">\n			AB棋牌●看得到绝对优势.是您最明智的选择！</font></p><font color=\"#FFFF00\">★AB棋牌</font>将不定期派发神秘彩金给予在<font color=\"#FFFF00\">AB棋牌</font>所有玩家，彩金没有门槛限制，不设上限金额。只要您在<font color=\"#FFFF00\">AB棋牌</font>有注册过账号，只要你曾经登入过<font color=\"#FFFF00\">AB棋牌</font>，就算您不曾我公司存款游戏过，<p>\n			都有可能收到我们为您随机派发惊喜彩金。</div>\n        <div style=\"margin-top:15px;\">\n          <table border=\"1\" style=\"width: 900px;border-collapse:collapse; line-height:35px; text-align:center\">\n            <tr style=\"background-color:#e21502; color:#fff107;\">\n              <th>神秘彩金随机派送</th>\n            </tr>\n            <tr>\n              <td>神秘彩金无需发送申请，直接划入会员账号或银行绑定银行账号</td>\n            </tr>\n          </table>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n        	<p><font color=\"#FFFF00\">★</font>凡是获得神秘彩金的会员，我们将在彩金存入会员账号或者绑定之银行账号后，通过会员的“个人信息”或电话通知，彩金纯属随机，还望会员能多多关注支付<font color=\"#FFF107\">AB棋牌</font>，祝您成为下一个幸运儿！</div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color: #FE0103\">活动细则</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <font color=\"#FFFF00\">★</font>存入会员账号内神秘彩金需<font color=\"#FFF107\">一倍流水</font>即可提款</div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p><font color=\"#FFFF00\">★</font>参与该优惠表示您同意《一般优惠规则与条款》。</p>\n        </div>\n        </div>\n        <div style=\"text-align:left; margin-top:15px;\">\n          <span style=\"background-color:#fe0103;display:inline-block; line-height:30px; padding:0 15px; border:2px solid #fff;\">优惠规则与条款</span>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:15px;\">\n          <p>1、所有优惠以<span style=\"color:#fbf8ab;\">人民币（CNY）</span>为结算金额，以<span style=\"color:#fbf8ab;\">北京时间</span>为计时区间。</p>\n          <p>2、AB棋牌的所有优惠特为玩家而设，如发现任何团队或者个人，以不诚实方式套取红利或者任何威胁、滥用公司优惠等行为，公司保留东京、取消该团队或个人账户及账户结余的权利。</p>\n          <p>3、若会员对活动有争议时，为确保双方利益，杜绝身份盗用行为，AB棋牌有权要求会员向我们提供充足有效的文件，用以确认是否享有该优惠的资质。</p>\n          <p>4、当参与优惠会员未能完全遵守、或违反、或滥用任何有关公司优惠或者推广的条款，又或我们有任何证据有任何团队或个人投下一连串的关联赌注，籍以造成无论赛果怎样都可以确保可以从该存款红利或其他推广活动提供的优惠获利，AB棋牌保留权利向此团队或个人停止、取消优惠或索回已支付的全部优惠红利。此外，公司亦保留权利向这些客服扣取相当于优惠红利价值的行政费用，以补偿我们的行政成本。</p>\n          <p>5、<span style=\"color:#fbf8ab;\">AB棋牌保留对活动的最终解释权</span>，以及在无通知的情况下修改，终止活动的权利，适用于所有优惠。</p>\n        </div>', '神秘彩金');
INSERT INTO `webhome_promotions` VALUES ('156', '0', '2019-04-03 15:31:50', '2019-04-03 15:31:50', '0', '12', '674', '\n        </div>\n        <div style=\"margin-top:15px;\">\n        <div style=\"text-align:left; margin-top:15px;\">\n          <p style=\"text-align: center\"><font color=\"#FFFF00\" size=\"5\">\n			<span style=\"font-weight: 700; background-color: #FE0103\">资金安全十大放心理由</span></font>\n        </div>\n        <div style=\"text-align:left; font-size:14px; margin-top:18px;\">\n          <font color=\"#FF0000\" size=\"3\">一、品牌足够权威</font><font size=\"2\"><br>\n			<font color=\"#FFFF00\">★</font>AB棋牌汇集热门棋牌游戏、电子游戏、捕鱼游戏（任你选择）。并获得GEOTRUST国际认证，确保网站公平、公正、公开。</font><p>\n			<b>\n			<font size=\"3\" color=\"#FF0000\">二、排名足够靠前</font></b><font size=\"2\"><br>\n			<font color=\"#FFFF00\">★</font>AB棋牌拥有具公信力博彩品牌，信誉驰名亚洲，在全球海量博彩公司中独占前茅的博彩集团。</font></p>\n			<p><b><font size=\"3\" color=\"#FF0000\">三、资金足够安全</font></b><font size=\"2\"><br>\n			<font color=\"#FFFF00\">★</font>AB棋牌雄厚的资金链信誉良好，大额存款无忧，资金安全有保障，免去您一切后顾之忧。</font></p>\n			<p><b><font size=\"3\" color=\"#FF0000\">四、取款足够自由</font></b><font size=\"2\"><br>\n			<font color=\"#FFFF00\">★</font>AB棋牌24小时自由存取款，取款秒到帐，且终身免手续费。</font></p>\n			<p><b><font size=\"3\" color=\"#FF0000\">五、活动足够给力</font></b><font size=\"2\"><br>\n			<font color=\"#FFFF00\">★</font>AB棋牌时时推出各项给力优惠活动，优惠送不停，让您真正赚翻天。</font></p>\n			<p><b><font color=\"#FF0000\" size=\"3\">六、安全足够保障</font></b><font size=\"2\"><br>\n			<font color=\"#FFFF00\">★</font>AB棋牌采用128位SSL加密技术和严格的安全管理体系，确保客户资料安全得到最完善的保障。</font></p>\n			<p><b><font size=\"3\" color=\"#FF0000\">七、服务足够贴心</font></b><font size=\"2\"><br>\n			<font color=\"#FFFF00\">★</font>AB棋牌7X24小时客服贴心的服务，温馨的呵护每一位玩家。</font></p>\n			<p><b><font size=\"3\" color=\"#FF0000\">八、营业足够合法</font></b><font size=\"2\"><br>\n			<font color=\"#FFFF00\">★</font>AB棋牌是首家获澳门特别行政区政府发出博彩经营权的企业，首间上市的持牌博彩经营商,新一代赌王吕志和直营的大型网络博彩公司。</font></p>\n			<p><b><font size=\"3\" color=\"#FF0000\">九、操作足够简单</font></b><font size=\"2\"><br>\n			<font color=\"#FFFF00\">★</font>AB棋牌秉承以客户为中心的宗旨，在开发之初就设计了诸多人性化的功能和特性，省去繁琐的麻烦，让您轻松操作。</font></p>\n			<p><b><font size=\"3\" color=\"#FF0000\">十、VIP足够优越</font></b><font size=\"2\"><br>\n			<font color=\"#FFFF00\">★</font>AB棋牌VIP尊享贵宾级的专属服务让您享有宾至如归般的帝王享受。</font></p>\n\n        </div\n        <div style=\"text-align:left; margin-top:15px;\">\n			<p align=\"center\">\n			<span style=\"background-color: #FE0103; font-weight:700\">\n			<font size=\"5\" color=\"#FFFF00\">\n			选择AB棋牌，等于选择一份信誉的保障！</font></span>\n        </div>', '信誉');

-- ----------------------------
-- Table structure for webhome_promotions_type
-- ----------------------------
DROP TABLE IF EXISTS `webhome_promotions_type`;
CREATE TABLE `webhome_promotions_type` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='官网优惠活动类型';

-- ----------------------------
-- Records of webhome_promotions_type
-- ----------------------------
INSERT INTO `webhome_promotions_type` VALUES ('12', '0', '2019-01-28 16:42:20', '2019-04-03 13:53:35', '0', '全部优惠');
INSERT INTO `webhome_promotions_type` VALUES ('13', '0', '2019-01-28 16:42:43', '2019-04-03 13:55:41', '0', '最新优惠');
INSERT INTO `webhome_promotions_type` VALUES ('14', '0', '2019-01-28 16:43:12', '2019-04-03 14:05:10', '0', '棋牌优惠');
INSERT INTO `webhome_promotions_type` VALUES ('15', '0', '2019-01-28 16:44:00', '2019-04-03 14:05:21', '0', '房卡场优惠');
INSERT INTO `webhome_promotions_type` VALUES ('23', '0', '2019-04-03 14:02:49', '2019-04-03 14:03:41', '0', '捕鱼优惠');
INSERT INTO `webhome_promotions_type` VALUES ('30', '0', '2019-04-03 14:03:56', '2019-04-03 14:03:56', '0', '电子优惠');
