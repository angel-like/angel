alter database webhome character set utf8;
alter table player_tasks character set utf8;
ALTER TABLE player_tasks MODIFY COLUMN `title` varchar(255) CHARACTER SET utf8;
alter table player_tasks_receive_record character set utf8;
ALTER TABLE player_tasks_receive_record MODIFY COLUMN `user_account` varchar(255) CHARACTER SET utf8;
alter table user character set utf8;
ALTER TABLE `user` MODIFY COLUMN `nick_name` varchar(255) CHARACTER SET utf8;