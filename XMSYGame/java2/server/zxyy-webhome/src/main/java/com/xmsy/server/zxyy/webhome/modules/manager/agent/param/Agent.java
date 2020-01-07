package com.xmsy.server.zxyy.webhome.modules.manager.agent.param;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;

import lombok.Data;

/**
 * 代理参数定义
 * @author Administrator
 *
 */
@Data
public class Agent {
	
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
	 * 邀请码
	 */
   private String recommendationCode;
			/**
	 * 下线总有效打码量
	 */
   private Long validBet;
			/**
	 * 下线人数
	 */
   private int num;
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
	@TableField(exist = false)
	private String agentHierarchyName;
   

}
