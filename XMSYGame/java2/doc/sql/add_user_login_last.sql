ALTER TABLE `user_login`
ADD COLUMN `last_ip`  varchar(255) DEFAULT '' AFTER `ip`,
ADD COLUMN `last_device_code`  varchar(255) DEFAULT '' AFTER `device_code`,
ADD COLUMN `last_device_type`  varchar(255) DEFAULT '' AFTER `device_type`,
ADD COLUMN `last_nation`  varchar(255) DEFAULT '' AFTER `nation`,
ADD COLUMN `last_ip_address`  varchar(255) DEFAULT '' AFTER `ip_address`,
ADD COLUMN `last_edition`  varchar(255) DEFAULT '' AFTER `edition`,
ADD COLUMN `last_hall_id`  bigint(22) DEFAULT '0' AFTER `hall_id`;