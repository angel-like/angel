ALTER TABLE `app_promotions`
ADD COLUMN `sorts`  int(11) NOT NULL DEFAULT 0 COMMENT '排序' AFTER `remake`;