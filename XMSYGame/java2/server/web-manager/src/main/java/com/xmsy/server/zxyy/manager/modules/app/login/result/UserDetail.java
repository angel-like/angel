package com.xmsy.server.zxyy.manager.modules.app.login.result;

import java.io.Serializable;
import java.math.BigDecimal;

import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userinfo.entity.UserInfoEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	public UserDetail() {
		super();
	}

	public UserDetail(UserEntity user, UserInfoEntity userinfo, String officialUrl, String supportUrl,
			Boolean bindPassword) {
		super();
		if (null != user) {
			this.id = user.getId();
			this.hierarchyId = user.getRiskHierarchyId();
			this.account = user.getAccount();
			this.userName = user.getUserName();
			this.portrait = user.getPortrait();
			this.sex = user.getSex();
			this.token = user.getToken();
			this.coin = user.getCoin();
			this.commission = user.getCommission();
			this.money = user.getMoney();
			this.unreadNum = user.getUnreadNum();
			this.gameInfoId = user.getGameInfoId();
			this.gameServerId = user.getGameServerId();
			this.forbiddenEnable = user.getForbiddenEnable();
			this.frozenEnable = user.getFrozenEnable();
			this.officialUrl = officialUrl;
			this.supportUrl = supportUrl;
			this.bindPassword = bindPassword;
		}
		if (null != userinfo) {
			this.bankCard = userinfo.getBankCard();
			this.alipayAccount = userinfo.getAlipayAccount();
		}

	}

	public UserDetail(UserEntity user, UserInfoEntity userinfo, Boolean bindPassword) {
		super();
		if (null != user) {
			this.id = user.getId();
			this.hierarchyId = user.getRiskHierarchyId();
			this.account = user.getAccount();
			this.userName = user.getUserName();
			this.portrait = user.getPortrait();
			this.sex = user.getSex();
			this.coin = user.getCoin();
			this.commission = user.getCommission();
			this.money = user.getMoney();
			this.unreadNum = user.getUnreadNum();
			this.gameInfoId = user.getGameInfoId();
			this.gameServerId = user.getGameServerId();
			this.forbiddenEnable = user.getForbiddenEnable();
			this.frozenEnable = user.getFrozenEnable();
			this.bindPassword = bindPassword;
		}
		if (null != userinfo) {
			this.bankCard = userinfo.getBankCard();
			this.alipayAccount = userinfo.getAlipayAccount();
		}
	}

	/**
	 * 用户id
	 */
	private Long id;
	
		/**
	 * 层级id
	 */
	private Long hierarchyId;

	/**
	 * 账号名称
	 */
	private String account;

	/**
	 * 真实姓名
	 */
	private String userName;
	/**
	 * 头像
	 */
	private String portrait;
	/**
	 * 性别(0:女，1：男)
	 */
	private Boolean sex;
	/**
	 * token验证Id
	 */
	private String token;
	/**
	 * 用户金币
	 */
	private Long coin;
	/**
	 * 佣金
	 */
	private BigDecimal commission;
	/**
	 * 金钱
	 */
	private BigDecimal money;
	/**
	 * 未读消息条数
	 */
	private Integer unreadNum;
	/**
	 * 游戏信息id
	 */
	private Long gameInfoId;
	/**
	 * 游戏服务id
	 */
	private Long gameServerId;
	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 阿里账号
	 */
	private String alipayAccount;
	/**
	 * 禁用（正常）
	 */
	private Boolean forbiddenEnable;
	/**
	 * 冻结(正常)
	 */
	private Boolean frozenEnable;
	/**
	 * 客服url
	 */
	private String supportUrl;
	/**
	 * 官网url
	 */
	private String officialUrl;
	/**
	 * 大厅ip
	 */
	private String hallIp;
	/**
	 * 大厅端口
	 */
	private Integer hallPort;

	/**
	 * 是否绑定取款密码
	 */
	private Boolean bindPassword;

}
