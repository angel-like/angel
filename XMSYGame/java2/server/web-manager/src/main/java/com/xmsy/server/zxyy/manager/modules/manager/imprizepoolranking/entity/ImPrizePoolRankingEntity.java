package com.xmsy.server.zxyy.manager.modules.manager.imprizepoolranking.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 33推荐热门游戏排行
 * 
 * @author xiaoliu
 * @email xxxxx
 * @date 2019-06-24 15:25:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("im_prize_pool_ranking")
public class ImPrizePoolRankingEntity extends BaseEntity<ImPrizePoolRankingEntity> {
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 奖金
	 */
	private BigDecimal prize;
	/**
	 * 附件ID
	 */
	private Long enclosureId;
	/**
	 * 状态(启用，禁用)
	 */
	private Boolean enable;
	/**
	 * 排序号
	 */
	private Integer orderNo;
}
