package com.xmsy.server.zxyy.robot.modules.robotinterface.distribution.param;

import lombok.Data;

/**
 * 归还机器人
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-18 17:08:45
 */
@Data
public class RobotResultParam {

	/**
	 * 大厅IP
	 */
	private String hallIp;
	/**
	 * 大厅端口
	 */
	private Integer port;
	/**
	 * token
	 */
	private String token;
}
