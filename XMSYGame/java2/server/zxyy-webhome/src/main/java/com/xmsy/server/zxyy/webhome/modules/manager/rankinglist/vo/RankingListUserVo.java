package com.xmsy.server.zxyy.webhome.modules.manager.rankinglist.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class RankingListUserVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户账号
	 */
	private String userAccount;
	/**
	 * 头像
	 */
	private String portrait;
	/**
	 * 名次
	 */
	private Integer position;
	/**
	 * 排行值
	 */
	private BigDecimal amount;
	/**
	 * 排行榜id
	 */
	private Long rankingListId;
	/**
	 * 地址
	 */
	private String address;
}
