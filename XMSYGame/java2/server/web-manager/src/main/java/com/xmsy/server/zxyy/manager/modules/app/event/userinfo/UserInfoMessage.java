package com.xmsy.server.zxyy.manager.modules.app.event.userinfo;

import java.math.BigDecimal;

import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;

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

	public UserInfoMessage(UserEntity user, UserInfoEntity userinfo) {
		super();
		if (null != user) {
			this.uid = user.getId(); //()*10000+ Long.valueOf(HallUrlConstant.getPROVIDER_CODE());
			if (null != user.getSex()) {
				this.sex = user.getSex() ? 1 : 0;
			}
			this.hierarchyId = user.getRiskHierarchyId();
			this.account = user.getAccount();
			this.nick = user.getAccount();
			this.userName = user.getUserName();
			this.coin = user.getCoin();
			this.roomCard = user.getRoomCard();
			this.commission = user.getCommission();
			this.money = user.getMoney();
			this.unReadNum = user.getUnreadNum();
			this.head = user.getPortrait();
			this.forbiddenEnable = user.getForbiddenEnable();
			this.frozenEnable = user.getFrozenEnable();
			//点杀名单  
			this.pointKillEnable=user.getPointKillEnable();//点杀状态
			this.pointKillRate=user.getPointKillRate();//点杀概率
		}
		if (null != userinfo) {
			this.bankCard = userinfo.getBankCard();
			this.aliPayAccount = userinfo.getAlipayAccount();
		}
	}

	public UserInfoMessage(UserEntity user, UserInfoEntity userinfo, Boolean bindPassword) {
		super();
		if (null != user) {
			this.uid = user.getId();// *10000+ Long.valueOf(HallUrlConstant.getPROVIDER_CODE());
			if (null != user.getSex()) {
				this.sex = user.getSex() ? 1 : 0;
			}
			this.account = user.getAccount();
			this.hierarchyId = user.getRiskHierarchyId();
			this.nick = user.getAccount();
			this.userName = user.getUserName();
			this.coin = user.getCoin();
			this.roomCard = user.getRoomCard();
			this.commission = user.getCommission();
			this.money = user.getMoney();
			this.unReadNum = user.getUnreadNum();
			this.head = user.getPortrait();
			this.forbiddenEnable = user.getForbiddenEnable();
			this.frozenEnable = user.getFrozenEnable();
		}
		if (null != userinfo) {
			this.bankCard = userinfo.getBankCard();
			this.aliPayAccount = userinfo.getAlipayAccount();
		}
		this.bindPassword = bindPassword;
	}
	/**
	 * 层级
	 */
	private Long hierarchyId;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 真实用户姓名
	 */
	private Long uid;

	/**
	 * 金币
	 */
	private Long coin;
	
	/**
	 * 房卡
	 */
	private Long roomCard;

	/**
	 * 性别
	 */
	private Integer sex;
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
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 支付宝账号
	 */
	private String aliPayAccount;

	/**
	 * 绑定银行取款密码
	 */
	private Boolean bindPassword;
	// =========推送点杀名单 信息===============
	/**
	 * 点杀概率
	 */
	private BigDecimal pointKillRate;
	/**
	 * 点杀状态
	 */
	private Boolean pointKillEnable;
	/**
	 * 解除退出金额
	 */
	private Long removeAmount;
}
