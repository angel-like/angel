ALTER TABLE `user`
ADD COLUMN `channel_code`  varchar(50) NULL DEFAULT '' COMMENT '广告渠道代码' AFTER `phone`;
