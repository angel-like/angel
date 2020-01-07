#添加 webhome_file_package   字段属性
ALTER TABLE webhome_file_package add `channel_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '渠道码' AFTER proxy_alias;
ALTER TABLE webhome_file_package add `channel_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '渠道名称' AFTER proxy_alias;