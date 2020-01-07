package com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity;

import lombok.Data;

/**
 * APP/WEB返回参数
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-03-25 10:09:06
 */
@Data
public class PayConfigFirstEntity {
	/**
	 * 支付公司id
	 */
	private Long id;
	/**
	 * 支付方式id：支付宝12 微信13
	 */
	private Long payId;

	/**
	 * 别名
	 */
	private String aliasName;
	/**
	 * 图标路径
	 */
	private String iconUrl;
	/**
	 * 图标路径MD5
	 */
	private String iconMd5;

}
