package com.xmsy.server.zxyy.webhome.modules.app.event.def;

import com.xmsy.server.zxyy.webhome.modules.app.event.userinfo.UserInfoMessage;

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
			this.Account = userInfoMessage.getAccount();
			this.Nick = userInfoMessage.getNick();
			this.Coin = userInfoMessage.getCoin();
			this.Sex = userInfoMessage.getSex();
			this.HierarchyId = userInfoMessage.getHierarchyId();
			this.Head = userInfoMessage.getHead();
			this.RoomCard = userInfoMessage.getRoomCard();
			this.Commission = userInfoMessage.getCommission() == null ? null
					: userInfoMessage.getCommission().floatValue();
			this.Money = userInfoMessage.getMoney() == null ? null : userInfoMessage.getMoney().floatValue();
			this.UnReadNum = userInfoMessage.getUnReadNum();
			this.UserName = userInfoMessage.getUserName();
			this.ForbiddenEnable = userInfoMessage.getForbiddenEnable();
			this.FrozenEnable = userInfoMessage.getFrozenEnable();
			//=========点杀名单==============
			this.pointKillEnable=userInfoMessage.getPointKillEnable();
			this.removeAmount=userInfoMessage.getRemoveAmount();
		}
		this.AliPayAccount = userInfoMessage.getAliPayAccount();
		this.BankCard = userInfoMessage.getBankCard();

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
	 * 用户id
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
	 * 用户账号
	 */
	private String Account;
	/**
	 * 银行账号
	 */
	private String BankCard;
	/**
	 * 佣金
	 */
	private Float Commission;
	/**
	 * 佣金
	 */
	private Long RoomCard;
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
	 * 玩家昵称
	 */
	private String Nick;
	/**
	 * 禁用
	 */
	private Boolean ForbiddenEnable;
	/**
	 * 冻结
	 */
	private Boolean FrozenEnable;
	//=========推送点杀名单  信息===============
	/**
	 * 点杀状态
	 */
	private Boolean pointKillEnable;
	/**
	 * 解除退出金额
	 */
	private Long removeAmount;

}
