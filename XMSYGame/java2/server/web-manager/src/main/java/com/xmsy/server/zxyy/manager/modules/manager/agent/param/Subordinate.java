package com.xmsy.server.zxyy.manager.modules.manager.agent.param;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 代理下级参数定义
 * @author Administrator
 *
 */
@Data
public class Subordinate {
	
	/*
	 * id
	 */
   private String id;
   /**
	 * 账号名称
	 */
   private String account;
			/**
	 * 用户金币
	 */
   private Long coin;
   
   /**
	 * 佣金
	 */
   private BigDecimal commission;
			/**
	 * 余额
	 */
   private BigDecimal money;

			/**
	 * 下线总有效打码量
	 */
   private Long validBet;

}
