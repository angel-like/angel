package com.xmsy.server.zxyy.webhome.modules.manager.paychannelconfig.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.webhome.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付渠道配置
 * 
 * @author aleng
 * @email xxxxx
 * @date 2019-04-10 14:20:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pay_channel_config")
public class PayChannelConfigEntity extends BaseEntity<PayChannelConfigEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 支付公司id
	 */
	private Long payId;
	/**
	 * 支付公司名称
	 */
	@TableField(exist = false)
	private String payName;
	/**
	 * 状态(1.启用，0.禁用)
	 */
	private Boolean enable;
	/**
	 * 支付渠道id
	 */
	private Long channelId;
	/**
	 * 支付渠道名称
	 */
	@TableField(exist = false)
	private String channelName;
	/**
	 * 限制最高支付金额
	 */
	private Long highLimit;
	/**
	 * 限制最低支付金额
	 */
	private Long lowLimit;
	/**
	 * 限制最低支付金额
	 */
	/**
	 * 充值金额
	 */
	private String amount;

	/**
	 * 显示名称
	 */
	private String showName;
	/**
	 * 排序号
	 */
	private Integer orderNo;

}
