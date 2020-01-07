
 ALTER TABLE `payment_type_configuration`
  ADD COLUMN`hierarchy_id` varchar(255) DEFAULT '' COMMENT '层级id';
   ALTER TABLE `ad_channel_config`
  ADD COLUMN`publish_url` varchar(1000) DEFAULT '' COMMENT '发布地址',
 ALTER TABLE webhome_enclosure
 add COLUMN `group_id` bigint(22) DEFAULT '0' COMMENT '分组id';
