	 ALTER TABLE `pay_config`
ADD COLUMN `pay_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '支付公司名称' AFTER `version`;

 ALTER TABLE `game_record`
ADD COLUMN `gm_user`  bit(1) NULL DEFAULT 0 COMMENT '是否是GM账号' AFTER `robot`;

