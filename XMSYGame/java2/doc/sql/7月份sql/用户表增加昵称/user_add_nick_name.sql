ALTER TABLE `user`
ADD COLUMN `nick_name`  varchar(255) CHARACTER SET utf8mb4 NULL DEFAULT '' COMMENT '第三方平台昵称' AFTER `channel_code`;
ALTER TABLE `user`
ADD UNIQUE INDEX `phone_uk` (`phone`) USING BTREE ;