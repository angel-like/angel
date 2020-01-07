package com.xmsy.server.zxyy.calculate.modules.manager.push.def;

import com.xmsy.server.zxyy.calculate.modules.manager.push.userinfo.UserInfoMessage;

import lombok.Data;

/**
 * .游戏服信息
 * 
 * @author aleng
 *
 */
@Data
public class GameServerMessage {

	public GameServerMessage() {
		super();
	}

	public GameServerMessage(UserInfoMessage userInfoMessage) {
		super();
		if (null != userInfoMessage) {
			this.Uid = userInfoMessage.getUid();
			this.Coin = userInfoMessage.getCoin();
			this.VipId = userInfoMessage.getVipId();
			this.Head = userInfoMessage.getHead();
			this.Commission = userInfoMessage.getCommission() == null ? null
					: userInfoMessage.getCommission().floatValue();
			this.Money = userInfoMessage.getMoney() == null ? null : userInfoMessage.getMoney().floatValue();
			this.UnReadNum = userInfoMessage.getUnReadNum();
			this.UserName = userInfoMessage.getUserName();
			this.ForbiddenEnable = userInfoMessage.getForbiddenEnable();
			this.FrozenEnable = userInfoMessage.getFrozenEnable();
		}

	}

	/**
	 * 用户id
	 */
	private Long Uid;
	/**
	 * 更新金币
	 */
	private Long Coin;
	/**
	 * 
	 */
	private Long VipId;
	/**
	 * 头像
	 */
	private String Head;
	/**
	 * 佣金
	 */
	private Float Commission;
	/**
	 * 余额
	 */
	private Float Money;
	/**
	 * 未读信息条数
	 */
	private Integer UnReadNum;
	/**
	 * 真实姓名
	 */
	private String UserName;
	/**
	 * 禁用
	 */
	private Boolean ForbiddenEnable;
	/**
	 * 冻结
	 */
	private Boolean FrozenEnable;

}
