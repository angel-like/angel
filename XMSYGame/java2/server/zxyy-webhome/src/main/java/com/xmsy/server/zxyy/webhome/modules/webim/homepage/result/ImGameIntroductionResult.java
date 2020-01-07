package com.xmsy.server.zxyy.webhome.modules.webim.homepage.result;

import lombok.Data;

/**
 * 33游戏介绍
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-22 19:50:09
 */
@Data
public class ImGameIntroductionResult {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 附件路径
	 */
	private String enclosureUrl;
	/**
	 * 内容
	 */
	private String content;
}
