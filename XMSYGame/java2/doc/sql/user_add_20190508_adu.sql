ALTER TABLE `user`
ADD COLUMN `abnormal_enable`  bit(1) NULL DEFAULT 0 COMMENT '是否异常' AFTER `frozen_enable`;