package com.xmsy.server.zxyy.robot.rabbitmq.param;

import javax.validation.constraints.NotNull;

import com.xmsy.server.zxyy.robot.common.validator.group.AddGroup;

import lombok.Data;


/**
 * 归还机器人
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-18 17:08:45
 */
@Data
public class RobotParam  {
	
	/**
	 * 金币
	 */
	@NotNull(message = "金币不能为空", groups = AddGroup.class)
    private Long coin;
			/**
	 * token
	 */
	@NotNull(message = "token不能为空", groups = AddGroup.class)
    private String token;
	}
