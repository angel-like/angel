package com.xmsy.common.bean.message;

import com.xmsy.common.bean.message.BaseMessage;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class FortuneMessage extends BaseMessage{

	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 打码数量
	 */
	private Long validBet;
	/**
	 * 天降财神 活动id
	 */
	private Long activityId;
	/**
	 * 天降财神  事件码
	 */
	private String eventCode;
	/**
	 * 充值金额
	 */
	private Long rechargeAmount;
}
