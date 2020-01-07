ALTER TABLE `sys_message_management`
ADD COLUMN `readonly`  bit(1) NULL DEFAULT 1 COMMENT '是否只读' AFTER `user_account`;

ALTER TABLE `sys_message_user`
ADD COLUMN `activity_type`  int(3) NULL DEFAULT 0 COMMENT '活动类型' AFTER `message_id`,
ADD COLUMN `activity_id`  bigint(22) NULL DEFAULT 0 COMMENT '活动id' AFTER `activity_type`,
ADD COLUMN `receive`  bit(1) NULL DEFAULT 0 COMMENT '是否领取' AFTER `delete_message`;

