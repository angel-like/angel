ALTER TABLE `pay_config`
ADD COLUMN `order_num`  int(11) NOT NULL DEFAULT 0 COMMENT '排序字段' AFTER `alias_name`;