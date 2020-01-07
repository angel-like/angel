INSERT INTO `webhome`.`sys_menu` ( `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ( '612', '层级选择', NULL, 'userhierarchy:userhierarchy:select', '2', NULL, '0');

update sys_menu SET perms='paychannelconfig:paychannelconfig:save,hierarchypaychannelconfigrelationship:hierarchypaychannelconfigrelationship:save' where parent_id='612' and `name`='添加'
update sys_menu SET perms='paychannelconfig:paychannelconfig:update,hierarchypaychannelconfigrelationship:hierarchypaychannelconfigrelationship:info' where parent_id='612' and `name`='修改'
