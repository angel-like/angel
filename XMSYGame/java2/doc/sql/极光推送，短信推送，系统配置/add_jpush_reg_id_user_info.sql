ALTER TABLE `user_info`
ADD COLUMN `jpush_reg_id`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户推送id' AFTER `alipay_account`;