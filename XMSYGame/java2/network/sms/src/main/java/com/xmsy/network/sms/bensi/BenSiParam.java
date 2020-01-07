package com.xmsy.network.sms.bensi;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * .本思短信参数
 * 
 * @author Administrator
 *
 */
@Data
@Accessors(chain = true)
public class BenSiParam {

	// 请求短信url
	private String url;
	// 请求方法
	private String action = "send";
	// 账号
	private String account;
	// 密码
	private String password;
	// 手机号
	private String mobile;
	// 短信内容
	private String content;
	// 应用码
	private String extno;
	// 请求格式
	private String rt = "json";

}
