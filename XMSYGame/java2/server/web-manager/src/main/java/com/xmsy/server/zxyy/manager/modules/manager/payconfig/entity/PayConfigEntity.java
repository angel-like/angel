package com.xmsy.server.zxyy.manager.modules.manager.payconfig.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付渠道配置
 *
 * @author aleng
 * @email xxxxx
 * @date 2019-03-25 10:09:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pay_config")
public class PayConfigEntity extends BaseEntity<PayConfigEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 回调IP白名单
	 */
	private String callbackIp;
	/**
	 * 支付公司名称
	 */
	private String payName;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 支付方式
	 */
	private String paymentMethod;
	/**
	 * 状态(1.启用，0.禁用)
	 */
	private Boolean enable;
	/**
	 * 是否首推
	 */
	private Boolean firstPush;
	/**
	 * 支付方式名称
	 */
	private String paymentMethodName;
	/**
	 * APP图标ID
	 */
	private Long appIconId;
	/**
	 * APP图标MD5
	 */
	private String appIconMd5;
	/**
	 * WEB图标ID
	 */
	private Long webIconId;
	/**
	 * 支付方式名称
	 */
	private String webIconMd5;
	/**
	 * 支付方式名称
	 */
	private Long enclosureId;
	/**
	 * 支付方式名称
	 */
	private String enclosureMd5;
	/**
	 * 别名
	 */
	private String aliasName;

	/**
	 * 排序字段
	 */
	private Integer orderNum;

}
