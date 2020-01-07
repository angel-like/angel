package com.xmsy.server.zxyy.manager.modules.app.event.def;

import lombok.Data;

/**
 * 消息实体
 * 
 * @author aleng
 *
 */
@Data
public class EventEntity {
	/**
	 * 服务端信息
	 */
	private GameServerMessage Server;
	/**
	 * 客户端信息
	 */
	private ClientMessage Client;

}
