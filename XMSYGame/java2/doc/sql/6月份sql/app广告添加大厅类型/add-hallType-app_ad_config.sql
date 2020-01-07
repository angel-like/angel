ALTER TABLE `app_ad_cofig`
ADD COLUMN `hall_type`  tinyint(1) NULL DEFAULT 0 COMMENT '大厅类型' AFTER `type`;