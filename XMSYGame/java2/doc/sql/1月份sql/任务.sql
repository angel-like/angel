INSERT INTO `webhome`.`sys_dictionary`( `create_time`, `update_time`, `version`, `code`, `name`, `parent_code`, `enable`) VALUES (now(), now(),  0, 'recharge-money', '充值得红包', 'fortuneEvent', b'1');

ALTER TABLE player_tasks add `enable` bit(1) DEFAULT b'1' COMMENT '是否可用';
ALTER TABLE player_tasks add `grade_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '游戏场次id' AFTER `game_id`;