package com.xmsy.server.zxyy.webhome.modules.app.event.userinfo;

import java.math.BigDecimal;

import com.xmsy.server.zxyy.webhome.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.webhome.modules.manager.userinfo.entity.UserInfoEntity;

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
	
	public UserInfoMessage(Long userId,Long userBalance) {
		super();
		this.uid = userId;
		this.userBalance = userBalance;
		this.coin = userBalance*-1;
	}
	public UserInfoMessage(UserEntity user, UserInfoEntity userinfo) {
		super();
		if (null != user) {
			this.uid = user.getId();
			if (null != user.getSex()) {
				this.sex = user.getSex() ? 1 : 0;
			}
			this.hierarchyId = user.getRiskHierarchyId();
			this.account = user.getAccount();
			this.nick = user.getAccount();
			this.userName = user.getUserName();
			this.vipId = user.getVipId();
			this.coin = user.getCoin();
			this.commission = user.getCommission();
			this.roomCard = user.getRoomCard();
			this.money = user.getMoney();
			this.unReadNum = user.getUnreadNum();
			this.head = user.getPortrait();
			this.forbiddenEnable = user.getForbiddenEnable();
			this.frozenEnable = user.getFrozenEnable();
		}
		if (null != userinfo) {
			this.bankCard = userinfo.getBankCard();
			this.aliPayAccount = userinfo.getAlipayAccount();
			this.integral =userinfo.getIntegral();
		}
	}

	public UserInfoMessage(UserEntity user, UserInfoEntity userinfo, Boolean bindPassword) {
		super();
		if (null != user) {
			this.uid = user.getId();
			if (null != user.getSex()) {
				this.sex = user.getSex() ? 1 : 0;
			}
			this.account = user.getAccount();
			this.hierarchyId = user.getRiskHierarchyId();
			this.nick = user.getAccount();
			this.userName = user.getUserName();
			this.vipId = user.getVipId();
			this.coin = user.getCoin();
			this.commission = user.getCommission();
			this.roomCard = user.getRoomCard();
			this.money = user.getMoney();
			this.unReadNum = user.getUnreadNum();
			this.head = user.getPortrait();
			this.forbiddenEnable = user.getForbiddenEnable();
			this.frozenEnable = user.getFrozenEnable();
		}
		if (null != userinfo) {
			this.bankCard = userinfo.getBankCard();
			this.aliPayAccount = userinfo.getAlipayAccount();
			this.integral =userinfo.getIntegral();
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
	 * vip等級
	 */
	private Long vipId;
	
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
	 * 用户房卡
	 */
	private Long roomCard;
	/**
	 * 用户余额宝
	 */
	private Long userBalance;
	/**
	 * 用户余额
	 */
	private BigDecimal money;
	/**
	 * 未读信息数量
	 */
	private Integer unReadNum;
	/**
	 * 未开启红包个数
	 */
	private Integer unEnvelopeNum;
	/**
	 * 未领取任务个数
	 */
	private Integer unTaskNum;
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
	//=========推送点杀名单  信息===============
	/**
	 * 点杀状态
	 */
	private Boolean pointKillEnable;
	/**
	 * 解除退出金额
	 */
	private Long removeAmount;
	private Long  integral;
}
