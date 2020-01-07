package com.xmsy.server.zxyy.schedule.event.param;


import lombok.Data;

/**
 * .游戏服信息
 * 
 * @author adu
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
			this.HierarchyId = userInfoMessage.getHierarchyId();
			this.Coin = userInfoMessage.getCoin();
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

	public GameServerMessage(Long userId, Long coin) {
		super();
		this.Uid = userId;
		this.Coin = coin;
	}

	/**
	 * 用户id
	 */
	private Long Uid;
	/**
	 * 层级id
	 */
	private Long HierarchyId;
	/**
	 * 更新金币
	 */
	private Long Coin;
	/**
	 * 性别
	 */
	private Integer Sex;
	/**
	 * 头像
	 */
	private String Head;
	/**
	 * 支付宝账号
	 */
	private String AliPayAccount;
	/**
	 * 银行账号
	 */
	private String BankCard;
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
