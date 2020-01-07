package com.xmsy.server.zxyy.schedule.event.param;

import java.math.BigDecimal;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * .推送的用户信息
 * 
 * @author aleng
 *
 */
@Data
public class UserInfoMessage {
	@Value("${provider.code}")
	private String providerCode;

	public UserInfoMessage() {
		super();
	}

	public UserInfoMessage(Long id,Long hierarchyId) {
		super();
		this.uid = id;
		this.hierarchyId = hierarchyId;
	}
	
	public UserInfoMessage(Long id,Long coin,int unReadNum) {
		super();
		this.uid = id;
		this.coin = coin;
		this.unReadNum = unReadNum;
	}

	/**
	 * 账号
	 */
	private String account;
	/**
	 * 用户id
	 */
	private Long uid;
	/**
	 * 层级
	 */
	private Long hierarchyId;

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
}
