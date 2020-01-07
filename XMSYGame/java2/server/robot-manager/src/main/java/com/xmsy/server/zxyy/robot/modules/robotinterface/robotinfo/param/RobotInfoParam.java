package com.xmsy.server.zxyy.robot.modules.robotinterface.robotinfo.param;

import lombok.Data;


/**
 * 归还机器人
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-18 17:08:45
 */
@Data
public class RobotInfoParam  {
	/**
	 * id
	 */
    private Long id;
	/**
	 * 金币
	 */
    private Long coin;
	/**
	* 账户名
	*/
	private String name;
	/**
	* 性别(false:女，true：男)
	*/
	private Boolean sex;
	/**
	* 头像
	*/
	private String portrait;
	/**
	* token
	*/
	private String token;
			
	}
