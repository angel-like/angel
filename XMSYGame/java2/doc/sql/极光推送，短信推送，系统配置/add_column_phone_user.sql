ALTER TABLE `user`
ADD COLUMN `phone`  varchar(255) NULL DEFAULT NULL COMMENT '手机号' AFTER `no_scan`;

