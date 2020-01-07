package com.xmsy.server.zxyy.webhome.modules.webim.homepage.result;

import lombok.Data;

/**
 * 33推荐下载
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-22
 */
@Data
public class ImDownloadManageResult {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 平台
	 */
	private String platform;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 下载地址
	 */
	private String downloadUrl;
	/**
	 * 附件ID
	 */
	private String enclosureUrl;
	/**
	 * 二维码
	 */
	private String QrCode;
}
