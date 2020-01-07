INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
  VALUES ('1170', '渠道统计', 'channelstatistics/channelstatistics', NULL, '1', 'config', '6');
  
  
  INSERT INTO `sys_menu` (`parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`)
    VALUES ('1171', '查看', null, 'channelstatistics:channelstatistics:list,channelstatistics:channelstatistics:info', '2', null, '6');