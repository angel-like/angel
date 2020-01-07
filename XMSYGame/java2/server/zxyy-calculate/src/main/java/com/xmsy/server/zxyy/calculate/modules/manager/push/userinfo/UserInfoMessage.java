package com.xmsy.server.zxyy.calculate.modules.manager.push.userinfo;

import java.math.BigDecimal;

import com.xmsy.server.zxyy.calculate.modules.manager.user.entity.UserEntity;

import lombok.Data;

/**
 * .推送的用户信息
 * 
 * @author aleng
 *
 */
@Data
public class UserInfoMessage {

	public UserInfoMessage() {
		super();
	}
	public UserInfoMessage(Long userId,Long vipId,String vipName) {
		super();
		this.uid = userId;
		this.vipId = vipId;
		this.vipName = vipName;
	}
	public UserInfoMessage(UserEntity user) {
		super();
		if (null != user) {
			this.uid = user.getId();
			this.account = user.getAccount();
			this.nick = user.getAccount();
			this.userName = user.getUserName();
			this.coin = user.getCoin();
			this.commission = user.getCommission();
			this.money = user.getMoney();
			this.unReadNum = user.getUnreadNum();
			this.head = user.getPortrait();
			this.forbiddenEnable = user.getForbiddenEnable();
			this.frozenEnable = user.getFrozenEnable();
		}
	}

	/**
	 * 账号
	 */
	private String account;
	/**
	 * 真实用户姓名
	 */
	private Long uid;
	/**
	 * vipId
	 */
	private Long vipId;
	/**
	 * vipName
	 */
	private String vipName;
	/**
	 * 下一个vip等级Id
	 */
	private Long nextVipId;
	/**
	 * 下一个vip等级name
	 */
	private String nextVipName;
	/**
	 * 当前充值金额
	 */
	private Long currentRechargeAmount;
	/**
	 * 下一个vip等级需充值的金额
	 */
	private Long nextRechargeAmount;

	/**
	 * 金币
	 */
	private Long coin;
	/**
	 * 昵称（账号）
	 */
	private String nick;
	/**
	 * 真实用户姓名
	 */
	private String userName;
	/**
	 * 用户佣金
	 */
	private BigDecimal commission;
	/**
	 * 用户余额
	 */
	private BigDecimal money;
	/**
	 * 未读信息数量
	 */
	private Integer unReadNum;
	/**
	 * 用户头像
	 */
	private String head;
	/**
	 * 禁用
	 */
	private Boolean forbiddenEnable;
	/**
	 * 冻结
	 */
	private Boolean frozenEnable;
	/**
	 * 未开启红包个数
	 */
	private Integer unEnvelopeNum;
}
