package com.xmsy.server.zxyy.manager.modules.manager.agent.param;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 代理参数定义
 * 
 * @author Administrator
 *
 */
@Data
public class TeamAgent {

	/**
	 * id,用户id
	 */
	private Long id;
	/**
	 * 账号名称
	 */
	private String account;
	/**
	 * 邀请码
	 */
	private String recommendationCode;
	/**
	 * 下线总有效打码量
	 */
	private Long sumValidBet;
	/**
	 * 直属下线有效打码量
	 */
	private Long directValidBet;
	/**
	 * 直属下线人数
	 */
	private int directNum;
	/**
	 * 总下线人数
	 */
	private int sumNum;
	/**
	 * 代理盈利佣金
	 */
	private BigDecimal agentCommission;
	/**
	 * 代理盈利金币
	 */
	private Long agentCoin;
	/**
	 * 代理层级
	 */
	private String agentHierarchyName;
	/**
	 * 邀请等级名称
	 */
	private String recommendationHierarchyName;
	/**
	 * 推荐等级(true,开启团队权限)
	 */
	private Boolean teamEnable;

}
