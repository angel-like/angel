package com.xmsy.server.zxyy.webhome.modules.webim.homepage.result;

import lombok.Data;

/**
 * 33娱乐服务器列表
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-20
 */
@Data
public class ImServiceManagerResult {
	/**
	 * 服务器id
	 */
	private Long id;
	/**
	 * 服务器名称
	 */
	private String name;
	
	/**
	 * 服务器路径
	 */
	private String serviceUrl;
}
