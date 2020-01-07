package com.xmsy.server.zxyy.manager.modules.manager.userrecommendationrecord.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableName;
import com.xmsy.server.zxyy.manager.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户推荐记录表
 * 
 * @author aleng
 * @email xxxxx
 * @date 2018-12-26 19:15:51
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain=true)
@TableName("user_recommendation_record")
public class UserRecommendationRecordEntity extends BaseEntity<UserRecommendationRecordEntity> {
	private static final long serialVersionUID = 1L;
							/**
	 * 被推荐人id
	 */
    private Long userId;
			/**
	 * 被推荐人账号
	 */
    private String userAccount;
			/**
	 * 推荐码
	 */
    private String recommendationCode;
			/**
	 * 推广人id
	 */
    private Long promoterId;
			/**
	 * 推广人账号
	 */
    private String promoterAccount;
			/**
	 * 推广盈利(佣金)
	 */
    private BigDecimal promotingProfit;
    /**
	 * 推广盈利(金币)
	 */
    private Long coin;
	}
