package com.xmsy.common.bean.message;

import java.io.Serializable;

/**
 * .消息基类
 * 
 * @author Administrator
 *
 */
public class BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 消息id
	 */
	protected String messageId;
	/**
	 * 创建时间
	 */
	protected String createTime;

	/**
	 * 消息内容
	 */
	protected String messageContent;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	@Override
	public String toString() {
		return "BaseMessage [messageId=" + messageId + ", createTime=" + createTime + ", messageContent="
				+ messageContent + "]";
	}
}
