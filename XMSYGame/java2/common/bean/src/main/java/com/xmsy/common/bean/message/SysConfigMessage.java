package com.xmsy.common.bean.message;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * .系统配置修改消息
 *
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysConfigMessage extends BaseMessage {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 属性名称
	 */
	private Long id;
	/**
	 * 属性名称
	 */
	private String name;
	/**
	 * 属性值
	 */
	private String value;
	/**
	 * 上级
	 */
	private Long parent;

	private List<SysConfigMessage> children;

	private Integer type=0;
	private String aliasName;
	private String callbackIp;
}
