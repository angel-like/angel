UPDATE `webhome`.`sys_menu` SET  `name` = '线上存取款管理' WHERE `menu_id` = 1372;
UPDATE `webhome`.`sys_menu` SET `perms` = 'order:tabs:take,ordertakemoney:ordertakemoney:list'WHERE `menu_id` = 1377;
UPDATE `webhome`.`sys_menu` SET `parent_id` = 1372, `name` = '线上取款订单批量处理', `perms` = 'order:tabs:work,orderbankrecharge:orderbankrecharge:dealWithLocking,orderbankrecharge:orderbankrecharge:dealWithCanceled,orderbankrecharge:orderbankrecharge:dealWithConfirmed,orderbankrecharge:orderbankrecharge:dealWithEmancipateLocking' WHERE `menu_id` = 1379;

DELETE from`webhome`.`sys_menu` WHERE `name`='出款管理'