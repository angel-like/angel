ALTER TABLE `sys_notice_management`
ADD COLUMN `notice_type`  int(255) NULL DEFAULT 0 COMMENT '大厅id：默认0:所有大厅， 1:主大厅，2:房卡大厅' AFTER `hierarchy_ids`;