package com.xmsy.server.zxyy.manager.modules.manager.channelstatistics.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 会员渠道参数
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-03 17:05:09
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
public class UserChannelParam {
	/**
	 * 会员id
	 */
	private Long userId;
	/**
	 * 会员账号
	 */
	private String userAccount;
	/**
	 * 会员打码量
	 */
	private Long uservalidBet;
}
