package com.xmsy.server.zxyy.webhome.modules.manager.rechargechannel.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付渠道
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-03-27 10:16:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("recharge_channel")
public class RechargeChannelEntity extends BaseEntity<RechargeChannelEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 渠道名称
	 */
	private String name;
	/**
	 * 渠道别名
	 */
	private String alias;
	/**
	 * 导航类型
	 */
	private Long type;
	/**
	 * 最小金额
	 */
	private Long minAmount;
	/**
	 * 1.启用，2.禁用（状态）
	 */
	private Boolean enable;
	/**
	 * app图标ID
	 */
	private Long appIconId;
	/**
	 * appMD5
	 */
	private String appIconMd5;
	/**
	 * web端图标
	 */
	private Long webIconId;
	/**
	 * webMD5
	 */
	private String webIconMd5;
	/**
	 * 附件
	 */
	private Long enclosureId;
	/**
	 * MD5
	 */
	private String md5;
	/**
	 * 排序号
	 */
	private Integer orderNo;
	
}
