ALTER TABLE `sys_message_user`
  ADD COLUMN`title` varchar(255) DEFAULT '' COMMENT '标题',
  ADD COLUMN `content` text NOT NULL COMMENT '内容',
  ADD COLUMN`failure_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '失效时间',
  ADD COLUMN`effect_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '生效时间',
  ADD COLUMN `content_type` int(2) DEFAULT '1' COMMENT '类型(1：会员站内信 2:管理员站内信）',
  ADD COLUMN `readonly`  bit(1) NULL DEFAULT 1 COMMENT '是否只读(默认1)' 