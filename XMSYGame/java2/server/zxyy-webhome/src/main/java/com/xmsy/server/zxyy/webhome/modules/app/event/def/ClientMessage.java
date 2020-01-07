package com.xmsy.server.zxyy.webhome.modules.app.event.def;

import lombok.Data;

/**
 * .客户端信息
 * 
 * @author aleng
 *
 */
@Data
public class ClientMessage {

	public ClientMessage() {
		super();
	}

	public ClientMessage(Long id, Long time, Object content) {
		super();
		this.id = id;
		this.time = time;
		this.content = content;
	}

	/**
	 * 消息id
	 */
	private Long id;
	/**
	 * 创建时间
	 */
	private Long time;
	/**
	 * 内容
	 */
	private Object content;

}
