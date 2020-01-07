package com.xmsy.network.jpush.message;

/**
 * .自定义推送的内容格式
 * 
 * @author chenjisi
 * @since 2017年8月22日
 */
public class PushMessage {

	private String code; //模块代码
	private String content; //消息内容
	private String type; //消息类型 1：业务消息
	
	public PushMessage() {
		super();
	}
	
	public PushMessage(String code, String content, String type) {
		super();
		this.code = code;
		this.content = content;
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
