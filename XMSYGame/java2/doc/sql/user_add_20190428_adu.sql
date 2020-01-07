ALTER TABLE `user`
ADD COLUMN `register_ip_address`  varchar(255) NULL DEFAULT '' COMMENT '注册ip地址' AFTER `register_ip`;

