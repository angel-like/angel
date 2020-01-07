

DROP TABLE IF EXISTS `user_old`;
CREATE TABLE `user_old` (
  `id` bigint(22) NOT NULL COMMENT 'id',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT '0' COMMENT '数据版本',
  `account` varchar(100) NOT NULL DEFAULT '' COMMENT '账号名称',
  `portrait` varchar(22) DEFAULT '' COMMENT '头像',
  `headframe_id` bigint(22) DEFAULT '0' COMMENT '头像框id',
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
  `abnormal_enable` bit(1) DEFAULT b'0' COMMENT '是否异常',
  `hierarchy_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '层级id',
  `risk_hierarchy_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '风控层级id',
  `vip_id` bigint(22) DEFAULT '0' COMMENT 'VIP等级id',
  `coin` bigint(22) unsigned NOT NULL DEFAULT '0' COMMENT '用户金币',
  `room_card` bigint(22) DEFAULT '0' COMMENT '房卡',
  `commission` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '佣金',
  `money` decimal(22,2) unsigned DEFAULT '0.00' COMMENT '金钱',
  `register_ip` varchar(100) DEFAULT '' COMMENT '注册ip',
  `register_ip_address` varchar(255) DEFAULT '' COMMENT '注册ip地址',
  `register_device_code` varchar(60) DEFAULT NULL COMMENT '注册机器码',
  `user_name` varchar(30) DEFAULT '' COMMENT '真实姓名',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `agent_enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '状态（1：是代理。0：禁用代理）',
  `first_recharge` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否首冲过',
  `game_info_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '在玩游戏信息id',
  `game_server_id` bigint(22) NOT NULL DEFAULT '0' COMMENT '游戏服务id',
  `no_scan` bit(1) DEFAULT b'0' COMMENT '0:检索风控  1：不检索风控',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

DROP TABLE IF EXISTS `user_info_old`;
CREATE TABLE `user_info_old` (
  `id` bigint(22) NOT NULL COMMENT 'id',
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
  `jpush_reg_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户推送id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userid_uk` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

INSERT INTO user_old (id,delete_status,create_time,update_time,version,account,portrait,headframe_id,
sex,user_type,union_type,open_id,union_id,superiors_id,forbidden_enable,nobet_enable,frozen_enable,
abnormal_enable,hierarchy_id,risk_hierarchy_id,vip_id,coin,room_card,commission,money,register_ip,
register_ip_address,register_device_code,user_name,remark,agent_enable,first_recharge,no_scan,phone)
SELECT  id,delete_status,create_time,update_time,version,account,portrait,headframe_id,
sex,user_type,union_type,open_id,union_id,superiors_id,forbidden_enable,nobet_enable,frozen_enable,
abnormal_enable,hierarchy_id,risk_hierarchy_id,vip_id,0,room_card,commission,truncate(coin/10000+money,2),register_ip,
register_ip_address,register_device_code,user_name,remark,agent_enable,first_recharge,no_scan,phone
from `user`;


INSERT INTO user_info_old(id,delete_status,create_time,update_time,version,user_identity,
user_phone,user_email,post_code,user_address,user_qq,user_birthday,bank_name,
bank_address,bank_card,bank_account_name,user_id,alipay_account,jpush_reg_id)
SELECT id,delete_status,create_time,update_time,version,user_identity,
user_phone,user_email,post_code,user_address,user_qq,user_birthday,bank_name,
bank_address,bank_card,bank_account_name,user_id,alipay_account,jpush_reg_id
from user_info;