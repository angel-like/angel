package com.xmsy.server.zxyy.webhome.modules.app.recharge.result;

import lombok.Data;

/**
 * .扫码支付方式参数定义
 * 
 * @author Administrator
 *
 */
@Data
public class AppQrcodeRechargeResult {
	
	/**
	 * id
	 */
	private String id;

	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态（0：禁用 1：启用）
	 */
	private Boolean enable;
	/**
	 * 图片url
	 */
	private String url;
	/**
	 * 类型
	 */
	private String md5;

	/**
	 * 类型
	 */
	private Long type;
	/**
	 * 类型名称
	 */
	private String typeStr;
}
