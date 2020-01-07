update sys_user s set s.agency_rate=1 where 1=1;
ALTER TABLE `sys_user`
CHANGE COLUMN `agency_rate` `otp_enable`  bit(1) NOT NULL DEFAULT 1 COMMENT '是否开启otp校验' AFTER `token`;