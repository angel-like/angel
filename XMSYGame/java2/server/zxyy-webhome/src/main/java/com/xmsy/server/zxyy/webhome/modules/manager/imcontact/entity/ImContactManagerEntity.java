package com.xmsy.server.zxyy.webhome.modules.manager.imcontact.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 联系管理
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-01 16:52:13
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class ImContactManagerEntity {

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 信息类型
	 */
	private Integer type;

	/**
	 * 图片路径
	 */
	private String url;
	/**
	 * 跳转路径
	 */
	private String jumpUrl;
	/**
	 * 文本类型
	 */
	private Integer contentType;
}
